package com.sa.promocion.promocion.infraestructura.entrada.rest.mapper;

import com.sa.promocion.promocion.dominio.Promocion;
import com.sa.promocion.promocion.infraestructura.entrada.rest.dto.ResponsePromocionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromocionRestMapper {

    ResponsePromocionDTO toResponseDto(Promocion promocion);
    List<ResponsePromocionDTO> toResponseDtoList(List<Promocion> promociones);
}
