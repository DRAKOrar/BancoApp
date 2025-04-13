package com.tecnica.tecnica.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoCuenta;
    private String numeroCuenta;
    private String estado;
    private double saldo;
    private boolean exentaGMF;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference

    private Cliente cliente;

    public Producto() {
    }

    public Producto(Long id, String tipoCuenta, String numeroCuenta, String estado, double saldo, boolean exentaGMF, LocalDate fechaCreacion, LocalDate fechaModificacion, Cliente cliente) {
        this.id = id;
        this.tipoCuenta = tipoCuenta;
        this.numeroCuenta = numeroCuenta;
        this.estado = estado;
        this.saldo = saldo;
        this.exentaGMF = exentaGMF;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isExentaGMF() {
        return exentaGMF;
    }

    public void setExentaGMF(boolean exentaGMF) {
        this.exentaGMF = exentaGMF;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
