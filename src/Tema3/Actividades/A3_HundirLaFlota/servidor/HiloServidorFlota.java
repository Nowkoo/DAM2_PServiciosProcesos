package Tema3.Actividades.A3_HundirLaFlota.servidor;

import Tema3.Actividades.A3_HundirLaFlota.partida.*;
import Tema3.Actividades.A3_HundirLaFlota.comun.MyStreamSocket;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del juego Hundir la flota.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */

class HiloServidorFlota implements Runnable {
   MyStreamSocket myDataSocket;
   private Partida partida = null;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 */
   HiloServidorFlota(MyStreamSocket myDataSocket) {
	   this.myDataSocket = myDataSocket;	   
   }
 
   /**
	* Gestiona una sesion con un cliente	
   */
   public void run() {
      boolean done = false;
      int operacion = 0;
      // ...
      try {
         while (!done) {
        	 String[] mensaje = myDataSocket.receiveMessage().split("#");
     
        	 // Recibe una peticion del cliente
        	 // Extrae la operación y los argumentos
        	 operacion = Integer.parseInt(mensaje[0]);
             switch (operacion) {
             case 0:  // fin de conexión con el cliente - cerrar el socket y finalizar el programa
                 //TODO
                 break;

             case 1: { // Crea nueva partida
                 int nf = Integer.parseInt(mensaje[1]);
                 int nc = Integer.parseInt(mensaje[2]);
                 int nb = Integer.parseInt(mensaje[3]);
                 partida = new Partida(nf, nc, nb);
                 break;
             }             
             case 2: { // Prueba una casilla y devuelve el resultado al cliente
                 int f = Integer.parseInt(mensaje[1]);
                 int c = Integer.parseInt(mensaje[2]);
                 int resultado = partida.pruebaCasilla(f, c);
                 myDataSocket.sendMessage(String.valueOf(resultado));
                 break;
             }
             case 3: { // Obtiene los datos de un barco y se los devuelve al cliente
                 int idBarco = Integer.parseInt(mensaje[1]);
                 String datosBarco = partida.getBarco(idBarco);
                 myDataSocket.sendMessage(String.valueOf(datosBarco));
                 break;
             }
             case 4: { // Devuelve al cliente la solucion en forma de vector de cadenas
        	   // Primero envia el numero de barcos 
            	
            	 String[] barcos = partida.getSolucion();
            	 String numBarcos = ""+barcos.length;
            	 myDataSocket.sendMessage(numBarcos);
            	 
            	 for(String bar: barcos) {
            		 myDataSocket.sendMessage(bar);
            	 }
            	 
               // Despues envia una cadena por cada barco
               break;
             }
         } // fin switch
       } // fin while   
     } // fin try
     catch (Exception ex) {
        System.out.println("Exception caught in thread: " + ex);
     } // fin catch
   } //fin run
   
} //fin class 
