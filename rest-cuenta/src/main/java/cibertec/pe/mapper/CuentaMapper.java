package cibertec.pe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import cibertec.pe.dto.CuentaRequest;
import cibertec.pe.dto.CuentaResponse;
import cibertec.pe.entity.Cuenta;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    CuentaResponse toResponse(Cuenta cuenta);

    @Mapping(target = "id", ignore = true)
    Cuenta toEntity(CuentaRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(CuentaRequest request, @MappingTarget Cuenta cuenta);
}
