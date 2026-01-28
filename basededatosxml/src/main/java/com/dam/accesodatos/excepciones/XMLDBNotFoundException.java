package com.dam.accesodatos.excepciones;

/**
 * Excepción para colecciones no encontradas
 * Ejercicio 6 - Actividad 2
 */
public class XMLDBNotFoundException extends XMLDBConnectionException {

    public XMLDBNotFoundException(String path) {
        super("Colección no encontrada: " + path, null);
    }
}