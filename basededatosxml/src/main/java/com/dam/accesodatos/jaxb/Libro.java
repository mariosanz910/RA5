package com.dam.accesodatos.jaxb;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "libro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Libro {
    @XmlAttribute private String id;
    @XmlAttribute private String isbn;
    @XmlElement private String titulo;
    @XmlElement private String autor;
    @XmlElement private int anio;

    @XmlElementWrapper(name = "generos")
    @XmlElement(name = "genero")
    private List<String> generos;

    @XmlElement private int paginas;
    @XmlElement private boolean disponible;

    public Libro() {}
    public Libro(String id, String titulo, String autor, int anio) {
        this.id = id; this.titulo = titulo; this.autor = autor; this.anio = anio; this.disponible = true;
    }
    // Getters y setters...
}