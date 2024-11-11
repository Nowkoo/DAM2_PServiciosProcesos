package Tema3.Actividades.A4_CalculadoraUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteUDP {
    static Scanner scanner;
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;
//            socket.setSoTimeout(5000);

            scanner = new Scanner(System.in);

            while(true) {
                System.out.println("Operación (SUMA o RESTA, o EXIT para salir): ");
                String operacion = pedirOperacion();

                if (operacion.equals("EXIT")) {
                    byte[] mensajeBytes = operacion.getBytes();
                    DatagramPacket exitPacket = new DatagramPacket(mensajeBytes, mensajeBytes.length, serverAddress, serverPort);
                    socket.send(exitPacket);
                    System.out.println("Saliendo del programa.");
                    break;
                }

                System.out.println("Primer número: ");
                int num1 = pedirNumero();
                System.out.println("Segundo número:");
                int num2 = pedirNumero();

                String mensaje = operacion + ":" + num1 + ":" + num2;
                byte[] mensajeBytes = mensaje.getBytes();
                DatagramPacket packet = new DatagramPacket(mensajeBytes, mensajeBytes.length, serverAddress, serverPort);
                socket.send(packet);

                byte[] respuestaBytes = new byte[1024];
                DatagramPacket respuestaPacket = new DatagramPacket(respuestaBytes, respuestaBytes.length);
                socket.receive(respuestaPacket);

                String respuesta = new String(respuestaPacket.getData(), 0, respuestaPacket.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int pedirNumero() {
        String input = scanner.nextLine();
        while (input.equals("") || !esDigito(input)) {
            System.out.println("No puedo operar con eso. Introduce un número:");
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    private static boolean esDigito(String cadena) {
        for (char c : cadena.toCharArray()) {
            System.out.println(String.valueOf(c));
            if (!"0123456789".contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    private static String pedirOperacion() {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("SUMA") || input.equalsIgnoreCase("RESTA")) {
                return input.toUpperCase();
            }
            System.out.println("Esa no es una operación válida.");
        }
    }
}
