package com.dam.accesodatos.gestion;

import com.dam.accesodatos.conexion.ConexionXMLDB;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import java.io.File;
import java.nio.file.Files;

public class AlmacenDocumentos {

    public static void guardarDesdeString(String colPath, String nombre, String contenidoXML) throws Exception {
        Collection col = ConexionXMLDB.conectar(colPath);
        try {
            XMLResource recurso = (XMLResource) col.createResource(nombre, "XMLResource");
            recurso.setContent(contenidoXML);
            col.storeResource(recurso);
            System.out.println("Guardado: " + nombre);
        } finally {
            ConexionXMLDB.cerrar(col);
        }
    }

    public static void guardarDesdeFichero(String colPath, String rutaFichero) throws Exception {
        File fichero = new File(rutaFichero);
        String nombre = fichero.getName();
        String contenido = Files.readString(fichero.toPath());
        guardarDesdeString(colPath, nombre, contenido);
    }

    public static String[] listarDocumentos(String colPath) throws Exception {
        Collection col = ConexionXMLDB.conectar(colPath);
        try {
            return col.listResources();
        } finally {
            ConexionXMLDB.cerrar(col);
        }
    }

    public static String obtenerContenido(String colPath, String nombre) throws Exception {
        Collection col = ConexionXMLDB.conectar(colPath);
        try {
            XMLResource recurso = (XMLResource) col.getResource(nombre);
            if (recurso == null) {
                throw new Exception("Documento no encontrado: " + nombre);
            }
            return (String) recurso.getContent();
        } finally {
            ConexionXMLDB.cerrar(col);
        }
    }

    public static void main(String[] args) throws Exception {
        String xml = "<libro id='L001'><titulo>Test</titulo></libro>";
        guardarDesdeString("/db/biblioteca/libros", "test.xml", xml);

        for (String doc : listarDocumentos("/db/biblioteca/libros")) {
            System.out.println("- " + doc);
        }
        String contenido = obtenerContenido("/db/biblioteca/libros", "test.xml");
        System.out.println(contenido);
    }
}