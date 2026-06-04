package cibertec.pe.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cibertec.pe.dto.CuentaRequest;

@FeignClient(name = "Rest-Cuenta", path = "/api/cuentas")
public interface CuentaFeignClient {

    @PostMapping
    void create(@RequestBody CuentaRequest request);
}
