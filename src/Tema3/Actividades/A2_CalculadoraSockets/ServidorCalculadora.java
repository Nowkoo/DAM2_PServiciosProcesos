package Tema3.Actividades.A2_CalculadoraSockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServidorCalculadora {
    static ServerSocket servidor;
    static final int PUERTO = 12345;
    static ArrayList<Socket> tabla = new ArrayList<Socket>();
    static String resultado = "";

    public static void main(String[] args) throws IOException {
        servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor esperando conexiones...");

        // Se aceptan nuevas conexiones y se instancia un hilo por cada nueva conexión
        while (true) {
            Socket socket = new Socket();
            socket = servidor.accept();

            tabla.add(socket);
            System.out.println("Nuevo cliente conectado.");

            HiloServidorCalculadora hilo = new HiloServidorCalculadora(socket);
            hilo.start();
        }
    }

    // Recibe la operación y devuelve el resultado
    static public int operar(String operacion) {
        List<String> list = Arrays.asList(operacion.split("#"));
        int valor1 = Integer.parseInt(list.get(1));
        int valor2 = Integer.parseInt(list.get(2));

        switch (list.getFirst()) {
            case "1":
                return valor1 + valor2;
            case "2":
                return valor1 - valor2;
            case "3":
                return valor1 * valor2;
            case "4":
                return valor1 / valor2;
            default:
                return 0;
        }
    }
}
