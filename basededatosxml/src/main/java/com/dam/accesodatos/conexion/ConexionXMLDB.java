package com.dam.accesodatos.conexion;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;

/**
 * Clase para gestionar conexiones a eXist-db usando XML:DB API
 * Ejercicio 2 - Actividad 2
 */
public class ConexionXMLDB {

    private static final String DRIVER = "org.exist.xmldb.DatabaseImpl";
    private static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private static final String USER = "admin";
    private static final String PASSWORD = "";

    private static boolean driverRegistrado = false;

    /**
     * Registra el driver de eXist-db (solo una vez)
     */
    public static synchronized void registrarDriver() throws Exception {
        if (!driverRegistrado) {
            Class<?> cl = Class.forName(DRIVER);
            Database database = (Database) cl.getDeclaredConstructor()
                    .newInstance();
            DatabaseManager.registerDatabase(database);
            driverRegistrado = true;
            System.out.println("Driver registrado correctamente");
        }
    }

    /**
     * Conecta a una colección específica
     * @param coleccion Ruta de la colección (ej: "/db")
     * @return Collection conectada
     */
    public static Collection conectar(String coleccion) throws Exception {
        registrarDriver();
        String fullUri = URI + coleccion;
        Collection col = DatabaseManager.getCollection(fullUri, USER, PASSWORD);

        if (col == null) {
            throw new Exception("No se pudo conectar a: " + fullUri);
        }

        return col;
    }

    /**
     * Cierra la conexión de forma segura
     * @param col Colección a cerrar
     */
    public static void cerrar(Collection col) {
        if (col != null) {
            try {
                col.close();
            } catch (XMLDBException e) {
                System.err.println("Error al cerrar: " + e.getMessage());
            }
        }
    }

    /**
     * Método main para probar la conexión
     */
    public static void main(String[] args) {
        Collection col = null;
        try {
            col = conectar("/db");
            System.out.println("Conexión exitosa!");
            System.out.println("Colección: " + col.getName());
            System.out.println("Recursos: " + col.getResourceCount());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            cerrar(col);
        }
    }
}