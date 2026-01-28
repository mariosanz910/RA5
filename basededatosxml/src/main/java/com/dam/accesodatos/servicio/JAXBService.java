package com.dam.accesodatos.servicio;

import com.dam.accesodatos.jaxb.Libro;
import com.dam.accesodatos.jaxb.Biblioteca;
import com.dam.accesodatos.gestion.AlmacenDocumentos;

import jakarta.xml.bind.*;
import java.io.StringReader;
import java.io.StringWriter;

public class JAXBService {
    private final JAXBContext context;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public JAXBService() throws JAXBException {
        context = JAXBContext.newInstance(Libro.class, Biblioteca.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        unmarshaller = context.createUnmarshaller();
    }
    public String toXML(Libro libro) throws JAXBException {
        StringWriter writer = new StringWriter();
        marshaller.marshal(libro, writer);
        return writer.toString();
    }
    public Libro fromXML(String xml) throws JAXBException {
        StringReader reader = new StringReader(xml);
        return (Libro) unmarshaller.unmarshal(reader);
    }
    public void guardar(String colPath, Libro libro) throws Exception {
        String xml = toXML(libro);
        String nombre = "libro_" + libro.getId() + ".xml";
        AlmacenDocumentos.guardarDesdeString(colPath, nombre, xml);
    }
    public Libro recuperar(String colPath, String id) throws Exception {
        String nombre = "libro_" + id + ".xml";
        String xml = AlmacenDocumentos.obtenerContenido(colPath, nombre);
        return fromXML(xml);
    }
}