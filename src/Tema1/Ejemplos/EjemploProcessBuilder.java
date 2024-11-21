package Tema1.Ejemplos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjemploProcessBuilder {
    public static void main(String[] args) {
        try {
            // Create a process to run the "ping" command
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "8.8.8.8");

            // Start the process
            Process process = processBuilder.start();

            // Get the process’s output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            // Read and display the process’s output line by line
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish and get the exit code
            int exitCode = process.waitFor();

            // Display the process’s exit code
            System.out.println("The process exited with exit code: " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
