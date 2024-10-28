package Tema3.Actividades.A2_CalculadoraSockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteCalculadora {
    Socket socket = null;
    DataInputStream fentrada;
    DataOutputStream fsalida;
    boolean repetir = true;
    static Scanner scanner;

    public ClienteCalculadora(Socket socket) {
        this.socket = socket;
        try {
            fentrada = new DataInputStream(socket.getInputStream());
            fsalida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int puerto = 12345;
        Socket socket = null;
        try {
            socket = new Socket("localhost", puerto);
        } catch (IOException e) {
            System.out.println("Error al conectar el servidor: " + e.getMessage());
            System.exit(0);
        }
        scanner = new Scanner(System.in);
        ClienteCalculadora cliente = new ClienteCalculadora(socket);
        cliente.ejecutar();
    }

    private void ejecutar() {
        String operacion = "";
        while (repetir) {
            String op = pedirOperacion();
            if (op.equals("0")) System.exit(0);
            System.out.println("Introduce el primer valor: ");
            String valor1 = pedirNumero();
            System.out.println("Ahora introduce el segundo valor: ");
            String valor2 = pedirNumero();
            operacion = op + "#" + valor1 + "#" + valor2;

            try {
                fsalida.writeUTF(operacion);
                System.out.println("Resultado: " + fentrada.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String pedirOperacion() {
        String input;
        while (true) {
            System.out.println("Introduce tipo de operación: 1-Suma, 2-Resta, 3-Multiplicación, 4-División. O pulsa 0 para salir.");
            input = scanner.nextLine();
            for (int i = 0; i <= 4; i++) {
                if (input.equals(String.valueOf(i))) {
                    return input;
                }
            }
            System.out.println("Esa no es una operación válida.");
        }
    }

    private String pedirNumero() {
        String input = scanner.nextLine();
        while (input.equals("") || !isDigito(input)) {
            System.out.println("No puedo operar con eso. Introduce un número:");
            input = scanner.nextLine();
        }
        return input;
    }

    private boolean isDigito(String cadena) {
        for (char c : cadena.toCharArray()) {
            System.out.println(String.valueOf(c));
            if (!"0123456789".contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }
}
