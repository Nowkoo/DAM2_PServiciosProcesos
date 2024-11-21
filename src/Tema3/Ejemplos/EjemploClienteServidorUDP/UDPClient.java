package Tema3.Ejemplos.EjemploClienteServidorUDP;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost"); // Direccion IP del servidor
            int serverPort = 12345; // Puerto del servidor
            String message = "Hola, servidor!";
            byte[] sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            // Aqui puedes esperar una respuesta del servidor si esnecesario
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


