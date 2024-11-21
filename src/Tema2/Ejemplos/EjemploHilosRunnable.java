package Tema2.Ejemplos;

class MiTarea implements Runnable {
 public void run() {
         // Codigo que se ejecutara en el hilo
         for (int i = 1; i <= 5; i++) {
             System.out.println("Hilo " + Thread.currentThread().getId() + ": Contador " + i);
             try {
                 Thread.sleep(1000); // Simula alguna tarea que lleva tiempo
                 } catch (InterruptedException e) {
                 e.printStackTrace();
                 }
             }
         }
 }

 public class EjemploHilosRunnable {
    public static void main(String[] args) {
         // Creamos dos instancias de la clase MiTarea
         Runnable tarea1 = new MiTarea();
         Runnable tarea2 = new MiTarea();

         // Creamos dos objetos Thread y les pasamos las instancias Runnable
         Thread hilo1 = new Thread(tarea1);
         Thread hilo2 = new Thread(tarea2);

         // Iniciamos los hilos
         hilo1.start();
         hilo2.start();

         // El hilo principal (main) tambien realiza algunas tareas
         for (int i = 1; i <= 5; i++) {
             System.out.println("Hilo principal: Contador " + i);
             try {
                 Thread.sleep(1000);
                 } catch (InterruptedException e) {
                 e.printStackTrace();
                 }
             }

         try {
             // Esperamos a que ambos hilos terminen
             hilo1.join();
             hilo2.join();
             } catch (InterruptedException e) {
             e.printStackTrace();
             }
         System.out.println("Programa finalizado");
    }
}
