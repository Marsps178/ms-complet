package cibertec.pe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nro_cta", nullable = false, unique = true)
    private Integer nroCta;

    @Column(nullable = false, length = 100)
    private String banco;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(name = "cod_employee", nullable = false)
    private Long codEmployee;

    public Cuenta() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNroCta() {
        return nroCta;
    }

    public void setNroCta(Integer nroCta) {
        this.nroCta = nroCta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getCodEmployee() {
        return codEmployee;
    }

    public void setCodEmployee(Long codEmployee) {
        this.codEmployee = codEmployee;
    }
    

}
