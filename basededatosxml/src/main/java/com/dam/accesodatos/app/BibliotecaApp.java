package com.dam.accesodatos.app;

import com.dam.accesodatos.dao.LibroDAO;
import com.dam.accesodatos.jaxb.Libro;

import java.util.Scanner;
import java.util.List;

public class BibliotecaApp {
    private final LibroDAO libroDAO;
    private final Scanner scanner;
    public BibliotecaApp() throws Exception {
        this.libroDAO = new LibroDAO();
        this.scanner = new Scanner(System.in);
    }
    // Métodos: ejecutar(), mostrarMenu(), crearLibro(), etc. igual que en el ejemplo.

    public static void main(String[] args) {
        try {
            new BibliotecaApp().ejecutar();
        } catch (Exception e) {
            System.err.println("Error fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}