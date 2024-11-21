package Tema3.Ejemplos.EjemploClienteServidorTCP;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Establecer conexion con el servidor en el puerto 12345
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Conexion establecida con el servidor.");

            // Enviar y recibir datos con el servidor
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Hola, servidor!");

            InputStream inputStream = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

            // Cerrar el socket cuando termines de usarlo
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

