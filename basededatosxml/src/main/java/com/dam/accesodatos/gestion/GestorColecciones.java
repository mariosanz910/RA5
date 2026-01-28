package com.dam.accesodatos.gestion;

import com.dam.accesodatos.conexion.ConexionXMLDB;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.CollectionManagementService;

/**
 * Clase para gestionar colecciones en eXist-db
 * Ejercicio 4 - Actividad 2
 */
public class GestorColecciones {

    /**
     * Crea una nueva colección dentro de una colección padre
     * @param padre Ruta de la colección padre
     * @param nombre Nombre de la nueva colección
     */
    public static void crearColeccion(String padre, String nombre)
            throws Exception {
        Collection col = ConexionXMLDB.conectar(padre);
        try {
            CollectionManagementService mgt =
                    (CollectionManagementService) col.getService(
                            "CollectionManagementService", "1.0");
            mgt.createCollection(nombre);
            System.out.println("Creada: " + padre + "/" + nombre);
        } finally {
            ConexionXMLDB.cerrar(col);
        }
    }

    /**
     * Lista todas las subcolecciones de una colección
     * @param path Ruta de la colección
     * @return Array con nombres de las subcolecciones
     */
    public static String[] listarSubcolecciones(String path)
            throws Exception {
        Collection col = ConexionXMLDB.conectar(path);
        try {
            return col.listChildCollections();
        } finally {
            ConexionXMLDB.cerrar(col);
        }
    }

    /**
     * Elimina una colección
     * @param padre Ruta de la colección padre
     * @param nombre Nombre de la colección a eliminar
     */
    public static void eliminarColeccion(String padre, String nombre)
            throws Exception {
        Collection col = ConexionXMLDB.conectar(padre);
        try {
            CollectionManagementService mgt =
                    (CollectionManagementService) col.getService(
                            "CollectionManagementService", "1.0");
            mgt.removeCollection(nombre);
            System.out.println("Eliminada: " + nombre);
        } finally {
            ConexionXMLDB.cerrar(col);
        }
    }

    /**
     * Método main de ejemplo
     */
    public static void main(String[] args) throws Exception {
        // Crear estructura
        crearColeccion("/db", "biblioteca");
        crearColeccion("/db/biblioteca", "libros");
        crearColeccion("/db/biblioteca", "autores");

        // Listar
        String[] subs = listarSubcolecciones("/db/biblioteca");
        System.out.println("Subcolecciones:");
        for (String s : subs) {
            System.out.println("  - " + s);
        }
    }
}