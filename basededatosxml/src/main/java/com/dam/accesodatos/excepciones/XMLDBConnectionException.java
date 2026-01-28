package com.dam.accesodatos.excepciones;

/**
 * Excepción base para errores de conexión XML:DB
 * Ejercicio 6 - Actividad 2
 */
public class XMLDBConnectionException extends Exception {

    public XMLDBConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLDBConnectionException(String message) {
        super(message);
    }
}