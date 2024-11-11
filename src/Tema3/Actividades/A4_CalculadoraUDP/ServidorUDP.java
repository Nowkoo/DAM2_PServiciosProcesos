package Tema3.Actividades.A4_CalculadoraUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Recibido desde el cliente: " + message);

                if (message.equalsIgnoreCase("EXIT")) {
                    System.out.println("El cliente ha cerrado la conexión.");
                    break;
                }

                String[] partes = message.split(":");
                String operacion = partes[0];
                int num1 = Integer.parseInt(partes[1]);
                int num2 = Integer.parseInt(partes[2]);

                int resultado = 0;
                if (operacion.equals("SUMA")) {
                    resultado = num1 + num2;
                } else if (operacion.equals("RESTA")) {
                    resultado = num1 - num2;
                }

                String respuesta = "Resultado: " + resultado;
                byte[] respuestaBytes = respuesta.getBytes();

                InetAddress direccionCliente = packet.getAddress();
                int puertoCliente = packet.getPort();
                DatagramPacket respuestaPacket = new DatagramPacket(respuestaBytes, respuestaBytes.length, direccionCliente, puertoCliente);
                socket.send(respuestaPacket);

                System.out.println("Resultado enviado al cliente: " + respuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
