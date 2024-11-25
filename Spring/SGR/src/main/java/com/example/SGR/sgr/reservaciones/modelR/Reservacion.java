package com.example.SGR.sgr.reservaciones.modelR;

import com.example.SGR.sgr.servicios.modelS.Servicio;
import com.example.SGR.sgr.usuariosP.modelU.Usuario;
import jakarta.persistence.*;

@Entity
public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

