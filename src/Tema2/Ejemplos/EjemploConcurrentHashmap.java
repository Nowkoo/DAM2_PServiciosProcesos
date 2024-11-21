package Tema2.Ejemplos;

import java.util.concurrent.ConcurrentHashMap;

public class EjemploConcurrentHashmap {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

        // Hilos para realizar operaciones concurrentes
        Thread thread1 = new Thread(() -> {
            concurrentMap.put("A", 1);
            concurrentMap.put("B", 2);
        });

        Thread thread2 = new Thread(() -> {
            concurrentMap.put("C", 3);
            concurrentMap.put("D", 4);
        });

        // Iniciar los hilos
        thread1.start();
        thread2.start();

        // Esperar a que ambos hilos terminen
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Imprimir el mapa despues de las operaciones concurrentes
        System.out.println("ConcurrentHashMap despues de las operaciones concurrentes: " + concurrentMap);
    }
}
