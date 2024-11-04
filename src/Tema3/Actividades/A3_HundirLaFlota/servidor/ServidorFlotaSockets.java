package Tema3.Actividades.A3_HundirLaFlota.servidor;

import java.net.ServerSocket;

import Tema3.Actividades.A3_HundirLaFlota.comun.MyStreamSocket;

/**
 * Este modulo contiene la logica de aplicacion del servidor del juego Hundir la flota
 * Utiliza sockets en modo stream para llevar a cabo la comunicacion entre procesos.
 * Puede servir a varios clientes de modo concurrente lanzando una hebra para atender a cada uno de ellos.
 * Se le puede indicar el puerto del servidor en linea de ordenes.
 */


public class ServidorFlotaSockets {
   
   public static void main(String[] args) {
	   
	   try (ServerSocket myConnectionSocket = new ServerSocket(8000)) {
		   	System.out.println("Activo servidor");
		            while (true) {  
		               MyStreamSocket myDataSocket = new MyStreamSocket(myConnectionSocket.accept());
		               Thread theThread = new Thread(new HiloServidorFlota(myDataSocket));
		               theThread.start();
		            } 
		          } catch (Exception ex) {
		             ex.printStackTrace();
		   	    
		          }
   } //fin main
} // fin class

