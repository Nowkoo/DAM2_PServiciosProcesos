package Tema3.Actividades.A2_CalculadoraSockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidorCalculadora extends Thread {
    DataInputStream fentrada;
    Socket socket = null;

    // Se inicializa el flujo de entrada de datos (datos recibidos desde el cliente)
    public HiloServidorCalculadora(Socket socket) {
        this.socket = socket;

        try {
            fentrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error al leer el servidor");
            e.printStackTrace();
        }
    }

    // Quedamos a la espera de leer las operaciones enviadas por el cliente y le devolvemos el resultado
    public void run() {
        while (true) {
            String operacion = "";
            try {
                operacion = fentrada.readUTF();
                DataOutputStream fsalida = new DataOutputStream(socket.getOutputStream());
                fsalida.writeUTF(String.valueOf(ServidorCalculadora.operar(operacion)));

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
