package cibertec.pe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CuentaRequest {

    @NotNull(message = "Account number is required")
    @Positive(message = "Account number must be positive")
    private Integer nroCta;

    @NotBlank(message = "Bank name is required")
    @Size(max = 100, message = "Bank name must not exceed 100 characters")
    private String banco;

    @NotBlank(message = "Account type is required")
    @Size(max = 50, message = "Account type must not exceed 50 characters")
    private String tipo;

    @NotNull(message = "Employee code is required")
    @Positive(message = "Employee code must be positive")
    private Long codEmployee;

    public Integer getNroCta() { return nroCta; }
    public void setNroCta(Integer nroCta) { this.nroCta = nroCta; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getCodEmployee() { return codEmployee; }
    public void setCodEmployee(Long codEmployee) { this.codEmployee = codEmployee; }
}
