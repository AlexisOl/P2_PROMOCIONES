package com.sa.promocion.promocion.infraestructura.salida.mapper;

import com.sa.promocion.promocion.dominio.Promocion;
import com.sa.promocion.promocion.infraestructura.salida.entidades.PromocionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromocionMapper {

    PromocionEntity toEntity(Promocion promocion);
    Promocion toDomain(PromocionEntity entity);
    List<Promocion> toDomainList(List<PromocionEntity> entities);
}
