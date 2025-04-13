package com.tecnica.tecnica.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="transaccion")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoTransaccion;
    private double monto;
    private LocalDateTime fechaTransaccion;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Transaccion() {
    }

    public Transaccion(Long id, String tipoTransaccion, double monto, LocalDateTime fechaTransaccion, Producto producto) {
        this.id = id;
        this.tipoTransaccion = tipoTransaccion;
        this.monto = monto;
        this.fechaTransaccion = fechaTransaccion;
        this.producto = producto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(LocalDateTime fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}