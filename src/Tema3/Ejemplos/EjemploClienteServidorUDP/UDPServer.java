package Tema3.Ejemplos.EjemploClienteServidorUDP;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(12345); // Puerto del servidor
            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Recibido desde el cliente: " + message);

                // Aqui puedes procesar el mensaje segun tus necesidades
                // Preparar y enviar una respuesta al cliente (si es necesario)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

