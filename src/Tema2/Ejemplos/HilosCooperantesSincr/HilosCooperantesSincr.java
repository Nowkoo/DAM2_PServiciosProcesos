package Tema2.Ejemplos.HilosCooperantesSincr;

public class HilosCooperantesSincr {
    private static final int NUM_HILOS = 10;
    private static final int CUENTA_TOTAL = 100000;

    public static void main(String[] args) {
        Contador c = new Contador();
        Thread[] hilos = new Thread[NUM_HILOS];
        for (int i = 0; i < NUM_HILOS; i++) {
            Thread th = new Thread(new Hilo(i, CUENTA_TOTAL/NUM_HILOS, c));
            th.start();
            hilos[i] = th;
        }

        for (Thread h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
            }
        }

        System.out.printf("Cuenta global: %s\n", c.getCuenta());
    }
}
