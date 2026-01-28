package com.dam.accesodatos.excepciones;

/**
 * Excepción para errores de autenticación
 * Ejercicio 6 - Actividad 2
 */
public class XMLDBAuthException extends XMLDBConnectionException {

    public XMLDBAuthException(String message) {
        super(message, null);
    }
}