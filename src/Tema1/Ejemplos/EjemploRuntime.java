package Tema1.Ejemplos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjemploRuntime {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        try {
            String comando = System.getProperty("os.name").toLowerCase().contains("win") ? "cmd /c dir" : "ls";
            Process proceso = runtime.exec(comando);
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            // ...

            int resultado = proceso.waitFor();
            if (resultado == 0) {
                System.out.println("Comando ejecutado correctamente");
            } else {
                System.out.println("Error en la ejecucion del comando");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
