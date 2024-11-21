package Tema2.A4_reparto;

import java.util.ArrayList;
import java.util.Scanner;

public class E2_RepartoBloques {
    static int n; //Cantidad total de trabajo
    static int m; //Numero de hilos

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Thread> hilos = new ArrayList<>();

        System.out.println("Introduce la cantidad total de trabajo a realizar: ");
        n = scanner.nextInt();

        System.out.println("Introduce el número de hilos que se ejecutarán para realizar el trabajo: ");
        m = scanner.nextInt();

        for (int threadId = 0; threadId < m; threadId++) {
            hilos.add(new Thread((new HiloTrabajo(threadId))));
        }

        for (Thread hilo : hilos) {
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class HiloTrabajo implements Runnable {
        int threadId;

        public HiloTrabajo(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            int blockSize = (n + m - 1) / m;
            int ini = blockSize * threadId;
            int fin = Math.min(ini + blockSize, n);

            for (int i = ini; i < fin; i++) {
                System.out.println("Soy el hilo " + threadId + ", digo el num " + i + " y el cuadrado es " + Math.pow(i, 2));
            }
        }
    }
}
