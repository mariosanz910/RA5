package com.dam.accesodatos.rest;

import java.net.http.*;
import java.net.URI;
import java.util.Base64;
import java.time.Duration;

/**
 * Cliente REST para interactuar con eXist-db vía HTTP
 * Ejercicio 5 - Actividad 2
 */
public class ExistRESTClient {

    private final String baseUrl;
    private final HttpClient client;
    private final String authHeader;

    /**
     * Constructor del cliente REST
     * @param host Servidor (ej: "localhost")
     * @param port Puerto (ej: 8080)
     * @param user Usuario
     * @param password Contraseña
     */
    public ExistRESTClient(String host, int port,
                           String user, String password) {
        this.baseUrl = String.format(
                "http://%s:%d/exist/rest", host, port);
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        String creds = user + ":" + password;
        this.authHeader = "Basic " + Base64.getEncoder()
                .encodeToString(creds.getBytes());
    }

    /**
     * Obtiene el contenido de un recurso
     * @param path Ruta del recurso
     * @return Contenido del recurso
     */
    public String get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Authorization", authHeader)
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        }
        throw new Exception("Error " + response.statusCode());
    }

    /**
     * Crea o actualiza un documento XML
     * @param path Ruta donde guardar el documento
     * @param xml Contenido XML
     */
    public void put(String path, String xml) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Authorization", authHeader)
                .header("Content-Type", "application/xml")
                .PUT(HttpRequest.BodyPublishers.ofString(xml))
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201 &&
                response.statusCode() != 200) {
            throw new Exception("Error " + response.statusCode());
        }
    }

    /**
     * Elimina un documento
     * @param path Ruta del documento a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean delete(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Authorization", authHeader)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString());

        return response.statusCode() == 200;
    }

    /**
     * Método main de ejemplo
     */
    public static void main(String[] args) throws Exception {
        ExistRESTClient client = new ExistRESTClient(
                "localhost", 8080, "admin", "");

        // Crear documento
        client.put("/db/test.xml", "<test>Hello</test>");
        System.out.println("Documento creado");

        // Leer documento
        String content = client.get("/db/test.xml");
        System.out.println("Contenido: " + content);

        // Eliminar documento
        client.delete("/db/test.xml");
        System.out.println("Documento eliminado");
    }
}