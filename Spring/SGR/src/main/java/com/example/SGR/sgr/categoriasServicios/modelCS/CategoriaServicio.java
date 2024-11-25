package com.example.SGR.sgr.categoriasServicios.modelCS;

import com.example.SGR.sgr.servicios.modelS.Servicio;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;

@Entity
public class CategoriaServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Boolean status;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Servicio> servicios;

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

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
}
