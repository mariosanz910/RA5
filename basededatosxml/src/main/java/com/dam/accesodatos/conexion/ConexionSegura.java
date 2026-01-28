package com.dam.accesodatos.conexion;

import com.dam.accesodatos.excepciones.*;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;

import java.net.ConnectException;

/**
 * Clase de conexión con manejo robusto de errores
 * Ejercicio 6 - Actividad 2
 */
public class ConexionSegura {

    private static final String DRIVER = "org.exist.xmldb.DatabaseImpl";
    private static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private static final String USER = "admin";
    private static final String PASSWORD = "";

    /**
     * Conecta a una colección con manejo mejorado de errores
     * @param coleccion Ruta de la colección
     * @return Collection conectada
     * @throws XMLDBConnectionException Si hay error de conexión
     * @throws XMLDBAuthException Si las credenciales son inválidas
     * @throws XMLDBNotFoundException Si la colección no existe
     */
    public static Collection conectar(String coleccion)
            throws XMLDBConnectionException {
        try {
            ConexionXMLDB.registrarDriver();

            String fullUri = URI + coleccion;
            Collection col = DatabaseManager.getCollection(
                    fullUri, USER, PASSWORD);

            if (col == null) {
                throw new XMLDBNotFoundException(coleccion);
            }

            return col;

        } catch (XMLDBException e) {
            if (e.errorCode == ErrorCodes.PERMISSION_DENIED) {
                throw new XMLDBAuthException(
                        "Credenciales inválidas");
            }
            throw new XMLDBConnectionException(
                    "Error de conexión", e);

        } catch (ConnectException e) {
            throw new XMLDBConnectionException(
                    "Servidor no disponible", e);

        } catch (Exception e) {
            if (e instanceof XMLDBConnectionException) {
                throw (XMLDBConnectionException) e;
            }
            throw new XMLDBConnectionException(
                    "Error inesperado", e);
        }
    }

    /**
     * Método main de ejemplo con manejo de errores
     */
    public static void main(String[] args) {
        try {
            Collection col = ConexionSegura.conectar("/db/noexiste");
            System.out.println("Conexión exitosa");
            ConexionXMLDB.cerrar(col);
        } catch (XMLDBNotFoundException e) {
            System.err.println("No encontrado: " + e.getMessage());
        } catch (XMLDBAuthException e) {
            System.err.println("Autenticación: " + e.getMessage());
        } catch (XMLDBConnectionException e) {
            System.err.println("Conexión: " + e.getMessage());
        }
    }
}