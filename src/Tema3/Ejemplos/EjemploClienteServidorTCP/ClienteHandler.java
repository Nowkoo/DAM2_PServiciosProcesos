package Tema3.Ejemplos.EjemploClienteServidorTCP;

import java.io.*;
import java.net.Socket;

public class ClienteHandler implements Runnable {
    private Socket clientSocket;

    public ClienteHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            // Logica para enviar y recibir datos con el cliente
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String mensaje = in.readLine();
            System.out.println(mensaje);

            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Hola, cliente!");
            // Cerrar el socket cuando termines de usarlo
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
