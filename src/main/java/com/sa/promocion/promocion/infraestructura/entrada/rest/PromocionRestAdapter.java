package com.sa.promocion.promocion.infraestructura.entrada.rest;

import com.sa.promocion.promocion.aplicacion.dto.CrearPromocionDTO;
import com.sa.promocion.promocion.aplicacion.dto.EditarPromocionDTO;
import com.sa.promocion.promocion.aplicacion.dto.FiltroPromocionDTO;
import com.sa.promocion.promocion.aplicacion.puertos.entrada.CrearPromocionInputPort;
import com.sa.promocion.promocion.aplicacion.puertos.entrada.EditarPromocionInputPort;
import com.sa.promocion.promocion.aplicacion.puertos.entrada.ListarPromocionesInputPort;
import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import com.sa.promocion.promocion.infraestructura.entrada.rest.dto.ResponsePromocionDTO;
import com.sa.promocion.promocion.infraestructura.entrada.rest.mapper.PromocionRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(name = "Promociones", description = "API para gestión de promociones")
@RestController
@AllArgsConstructor
@RequestMapping("/promociones")
public class PromocionRestAdapter {

    private final CrearPromocionInputPort crearPromocionInputPort;
    private final EditarPromocionInputPort editarPromocionInputPort;
    private final ListarPromocionesInputPort listarPromocionesInputPort;
    private final PromocionRestMapper mapper;

    @Operation(summary = "Crear promoción", description = "Crea una nueva promoción")
    @PostMapping
    public ResponseEntity<ResponsePromocionDTO> crearPromocion(
            @Valid @RequestBody CrearPromocionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponseDto(
                        crearPromocionInputPort.crearPromocion(dto)
                ));
    }

    @Operation(summary = "Editar promoción", description = "Actualiza los datos de una promoción")
    @PutMapping("/{promocionId}")
    public ResponseEntity<ResponsePromocionDTO> editarPromocion(
            @PathVariable UUID promocionId,
            @Valid @RequestBody EditarPromocionDTO dto) {
        return ResponseEntity.ok(
                mapper.toResponseDto(
                        editarPromocionInputPort.editarPromocion(promocionId, dto)
                )
        );
    }

    @Operation(summary = "Activar promoción", description = "Activa una promoción desactivada")
    @PatchMapping("/{promocionId}/activar")
    public ResponseEntity<Void> activarPromocion(@PathVariable UUID promocionId) {
        editarPromocionInputPort.activarPromocion(promocionId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Desactivar promoción", description = "Desactiva una promoción activa")
    @PatchMapping("/{promocionId}/desactivar")
    public ResponseEntity<Void> desactivarPromocion(@PathVariable UUID promocionId) {
        editarPromocionInputPort.desactivarPromocion(promocionId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar promociones", description = "Lista promociones con filtros opcionales")
    @GetMapping
    public ResponseEntity<List<ResponsePromocionDTO>> listarPromociones(
            @Parameter(description = "ID del cine") @RequestParam(required = false) UUID cineId,
            @Parameter(description = "ID de la sala") @RequestParam(required = false) UUID salaId,
            @Parameter(description = "ID de la película") @RequestParam(required = false) UUID peliculaId,
            @Parameter(description = "ID del cliente") @RequestParam(required = false) UUID clienteId,
            @Parameter(description = "Tipo de promoción") @RequestParam(required = false) TipoPromocion tipo,
            @Parameter(description = "Estado activo/inactivo") @RequestParam(required = false) Boolean activa,
            @Parameter(description = "Fecha para verificar vigencia")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        FiltroPromocionDTO filtro = new FiltroPromocionDTO(
                cineId, salaId, peliculaId, clienteId, tipo, activa, fecha
        );

        return ResponseEntity.ok(
                mapper.toResponseDtoList(
                        listarPromocionesInputPort.listarPromociones(filtro)
                )
        );
    }

    @Operation(summary = "Obtener promoción por ID", description = "Obtiene los detalles de una promoción")
    @GetMapping("/{promocionId}")
    public ResponseEntity<ResponsePromocionDTO> obtenerPromocion(@PathVariable UUID promocionId) {
        return ResponseEntity.ok(
                mapper.toResponseDto(
                        listarPromocionesInputPort.obtenerPromocionPorId(promocionId)
                )
        );
    }

    @Operation(summary = "Obtener mejor promoción",
            description = "Obtiene la promoción con mayor descuento que aplica según los filtros")
    @GetMapping("/mejor")
    public ResponseEntity<ResponsePromocionDTO> obtenerMejorPromocion(
            @RequestParam(required = false) UUID cineId,
            @RequestParam(required = false) UUID salaId,
            @RequestParam(required = false) UUID peliculaId,
            @RequestParam(required = false) UUID clienteId,
            @RequestParam(required = false) TipoPromocion tipo) {

        FiltroPromocionDTO filtro = new FiltroPromocionDTO(
                cineId, salaId, peliculaId, clienteId, tipo, true, LocalDate.now()
        );

        var mejorPromocion = listarPromocionesInputPort.obtenerMejorPromocion(filtro);
        if (mejorPromocion == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mapper.toResponseDto(mejorPromocion));
    }
}
