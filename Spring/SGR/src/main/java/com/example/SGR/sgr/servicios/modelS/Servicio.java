package com.example.SGR.sgr.servicios.modelS;

import com.example.SGR.sgr.categoriasServicios.modelCS.CategoriaServicio;
import com.example.SGR.sgr.reservaciones.modelR.Reservacion;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaServicio categoria;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
    private List<Reservacion> reservaciones;

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

    public CategoriaServicio getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaServicio categoria) {
        this.categoria = categoria;
    }

    public List<Reservacion> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<Reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }
}

