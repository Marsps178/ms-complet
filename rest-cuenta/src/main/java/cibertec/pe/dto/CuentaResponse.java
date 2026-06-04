package cibertec.pe.dto;

public class CuentaResponse {

    private Long id;
    private Integer nroCta;
    private String banco;
    private String tipo;
    private Long codEmployee;

    public CuentaResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNroCta() { return nroCta; }
    public void setNroCta(Integer nroCta) { this.nroCta = nroCta; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getCodEmployee() { return codEmployee; }
    public void setCodEmployee(Long codEmployee) { this.codEmployee = codEmployee; }
}
