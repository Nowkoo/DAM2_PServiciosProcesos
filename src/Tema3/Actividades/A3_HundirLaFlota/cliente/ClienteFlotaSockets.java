package Tema3.Actividades.A3_HundirLaFlota.cliente;


import Tema3.Actividades.A3_HundirLaFlota.partida.Partida;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.*;


public class ClienteFlotaSockets {

	/**
	 * Implementa el juego 'Hundir la flota' mediante una interfaz gráfica (GUI)
	 */

	/** Parametros por defecto de una partida */
	public static final int NUMFILAS=8, NUMCOLUMNAS=8, NUMBARCOS=6;
	private String hostName = "LocalHost";
	private String portNum = "8000";
	private GuiTablero guiTablero = null;			// El juego se encarga de crear y modificar la interfaz gráfica
	private AuxiliarClienteFlota aux = null;                 // Objeto con los datos de la partida en juego
	
	/** Atributos de la partida guardados en el juego para simplificar su implementación */
	private int quedan = NUMBARCOS, disparos = 0;

	/**
	 * Programa principal. Crea y lanza un nuevo juego
	 * @param args
	 */
	public static void main(String[] args) {
		ClienteFlotaSockets juego = new ClienteFlotaSockets();
		try {
			juego.ejecuta();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // end main

	/**
	 * Lanza una nueva hebra que crea la primera partida y dibuja la interfaz grafica: tablero
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	private void ejecuta() throws SocketException, UnknownHostException, IOException {
		// Instancia la primera partida
		aux = new AuxiliarClienteFlota(hostName,portNum);
		aux.nuevaPartida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				guiTablero = new GuiTablero(NUMFILAS, NUMCOLUMNAS);
				guiTablero.dibujaTablero();
			}
		});
	} // end ejecuta

	/******************************************************************************************/
	/*********************  CLASE INTERNA GuiTablero   ****************************************/
	/******************************************************************************************/
	private class GuiTablero {

		private int numFilas, numColumnas;

		private JFrame frame = null;        // Tablero de juego
		private JLabel estado = null;       // Texto en el panel de estado
		private JButton buttons[][] = null; // Botones asociados a las casillas de la partida

		/**
         * Constructor de una tablero dadas sus dimensiones
         */
		GuiTablero(int numFilas, int numColumnas) {
			this.numFilas = numFilas;
			this.numColumnas = numColumnas;
			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		}

		/**
		 * Dibuja el tablero de juego y crea la partida inicial
		 */
		public void dibujaTablero() {
			anyadeMenu();
			anyadeGrid(numFilas, numColumnas);		
			anyadePanelEstado("Intentos: " + disparos + "    Barcos restantes: " + quedan);		
			frame.setSize(300, 300);
			frame.setVisible(true);	
		} // end dibujaTablero

		/**
		 * Anyade el menu de opciones del juego y le asocia un escuchador
		 */
		private void anyadeMenu() {
			MenuListener menuListener = new MenuListener();
			JMenuBar barra = new JMenuBar(); 
		    JMenu tituloOpciones = new JMenu("Opciones");
		    JMenuItem mostrarSol = new JMenuItem("Mostrar Solución");
		    mostrarSol.addActionListener(menuListener);
		    JMenuItem nuevaPartida = new JMenuItem("Nueva Partida");
		    nuevaPartida.addActionListener(menuListener);
		    JMenuItem salir = new JMenuItem("Salir");
		    salir.addActionListener(menuListener);

		    tituloOpciones.add(mostrarSol);
		    tituloOpciones.add(nuevaPartida);
		    tituloOpciones.add(salir);
		    
		    barra.add(tituloOpciones);
		    
		    frame.add(barra, BorderLayout.NORTH);
		} // end anyadeMenu

		/**
		 * Anyade el panel con las casillas del mar y sus etiquetas.
		 * Cada casilla sera un boton con su correspondiente escuchador
		 * @param nf	numero de filas
		 * @param nc	numero de columnas
		 */
		private void anyadeGrid(int nf, int nc) {            
            //Nuevo tamaño contando Indices de filas y columnas
            int nFilasTotal = nf + 1;
            int nColumasnTotal = nc + 2;
            JPanel panelCentral = new JPanel(new GridLayout(nFilasTotal, nColumasnTotal));
            
            //Rellenar fila Indices
            panelCentral.add(new JLabel());
            for(int i = 1; i<nc+1; i++) {
            	JLabel labelNumero = new JLabel(""+i, JLabel.CENTER);
            	panelCentral.add(labelNumero);
            }
            panelCentral.add(new JLabel());
            
            //Añade las filas de botones con sus respectivos identificadores de Fila
            ButtonListener buttonListener = new ButtonListener();
            buttons = new JButton[nf][nc];
            char letras = 'A';
            for(int fila = 0; fila<nf; fila++) {
            	JLabel labelLetra = new JLabel(Character.toString(letras), JLabel.CENTER);
            	panelCentral.add(labelLetra);
            	for(int columna = 0; columna < nc; columna++) {
            		JButton boton = new JButton();
            		buttons[fila][columna] = boton;
            		boton.addActionListener(buttonListener);
            		int[] posicion = {fila, columna};
            		boton.putClientProperty("pos", posicion);
                	panelCentral.add(boton);
            	}            
            	JLabel labelLetra2 = new JLabel(Character.toString(letras++), JLabel.CENTER);
            	panelCentral.add(labelLetra2);
            }
            
            frame.add(panelCentral, BorderLayout.CENTER);
                                            
		} // end anyadeGrid


		/**
		 * Anyade el panel de estado al tablero
		 * @param cadena	cadena inicial del panel de estado
		 */
		private void anyadePanelEstado(String cadena) {	
			JPanel panelEstado = new JPanel();
			estado = new JLabel(cadena);
			panelEstado.add(estado);
			// El panel de estado queda en la posición SOUTH del frame
			frame.getContentPane().add(panelEstado, BorderLayout.SOUTH);
		} // end anyadePanel Estado

		/**
		 * Cambia la cadena mostrada en el panel de estado
		 * @param cadenaEstado	nuevo estado
		 */
		public void cambiaEstado(String cadenaEstado) {
			estado.setText(cadenaEstado);
		} // end cambiaEstado

		/**
		 * Muestra la solucion de la partida y marca la partida como finalizada
		 * @throws IOException 
		 */
		public void muestraSolucion() throws IOException {
			for(int fila = 0; fila<buttons.length; fila++) {
				for(int columna = 0; columna<buttons[fila].length; columna++) {
					this.pintaBoton(buttons[fila][columna], Color.CYAN);
				}
			}
			
			String[] barcos = aux.getSolucion();
			for(int i=0; i<barcos.length; i++) {
				String[] datos = barcos[i].split("#");
				String orientacion = datos[2];
				int filaInicial = Integer.parseInt(datos[0]);
				int columnaInicial = Integer.parseInt(datos[1]);
				int tamanyo = Integer.parseInt(datos[3]);
				
				if(orientacion.equals("H")) {
	    			for(int columna = columnaInicial; columna < columnaInicial + tamanyo; columna++) {
	    				this.pintaBoton(buttons[filaInicial][columna], Color.MAGENTA);
	    			}
	    		} else {		
	    			for(int fila = filaInicial; fila < filaInicial + tamanyo; fila++) {
	    				this.pintaBoton(buttons[fila][columnaInicial], Color.MAGENTA);
	    			}
	    		}
			}			
            quedan = 0;
			guiTablero.cambiaEstado("GAME OVER. Disparos: " + disparos);
		} // end muestraSolucion


		/**
		 * Pinta un barco como hundido en el tablero
		 * @param cadenaBarco	cadena con los datos del barco codifificados como
		 *                      "filaInicial#columnaInicial#orientacion#tamanyo"
		 */
		public void pintaBarcoHundido(String cadenaBarco) {
				//TODO
    		
		} // end pintaBarcoHundido

		/**
		 * Pinta un botón de un color dado
		 * @param b			boton a pintar
		 * @param color		color a usar
		 */
		public void pintaBoton(JButton b, Color color) {
			b.setBackground(color);
			// El siguiente código solo es necesario en Mac OS X
			b.setOpaque(true);
			b.setBorderPainted(false);
		} // end pintaBoton

		/**
		 * Limpia las casillas del tablero pintándolas del gris por defecto
		 */
		public void limpiaTablero() {
			for (int i = 0; i < numFilas; i++) {
				for (int j = 0; j < numColumnas; j++) {
					buttons[i][j].setBackground(null);
					buttons[i][j].setOpaque(true);
					buttons[i][j].setBorderPainted(true);
				}
			}
		} // end limpiaTablero

		/**
		 * 	Destruye y libera la memoria de todos los componentes del frame
		 */
		public void liberaRecursos() {
			frame.dispose();
		} // end liberaRecursos


	} // end class GuiTablero

	/******************************************************************************************/
	/*********************  CLASE INTERNA MenuListener ****************************************/
	/******************************************************************************************/

	/**
	 * Clase interna que escucha el menu de Opciones del tablero
	 * 
	 */
	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
            String opcion = e.getActionCommand();
            if(opcion.equals("Mostrar Solución")) {
            	try {
					guiTablero.muestraSolucion();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            } else if (opcion.equals("Nueva Partida")) {
            	guiTablero.limpiaTablero();
            	try {
					aux.nuevaPartida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
;
            	quedan = NUMBARCOS;
            	disparos = 0;
            	guiTablero.cambiaEstado("Intentos: " + disparos + "    Barcos restantes: " + quedan);
            } else {
            	try {
					aux.fin();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            	guiTablero.liberaRecursos();
            	System.exit(0);
            }
		} // end actionPerformed

	} // end class MenuListener



	/******************************************************************************************/
	/*********************  CLASE INTERNA ButtonListener **************************************/
	/******************************************************************************************/
	/**
	 * Clase interna que escucha cada uno de los botones del tablero
	 * Para poder identificar el boton que ha generado el evento se pueden usar las propiedades
	 * de los componentes, apoyandose en los metodos putClientProperty y getClientProperty
	 */
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(quedan != 0) {
	            JButton boton = (JButton) e.getSource();
	            int[] pos = (int[]) boton.getClientProperty("pos");
	            int estado;
				
				try {
					estado = aux.pruebaCasilla(pos[0], pos[1]);
				} catch (IOException e2) {
					e2.printStackTrace();
					estado = -4;
				}
			
	            
	            if(estado == Partida.AGUA) {
	        		guiTablero.pintaBoton(boton, Color.CYAN);
	    		} else if(estado == Partida.TOCADO) {
	    			guiTablero.pintaBoton(boton, Color.YELLOW);
				} else if(estado == Partida.HUNDIDO) {
	        		guiTablero.pintaBoton(boton, Color.RED);
	    		} else {
	        		try {
						guiTablero.pintaBarcoHundido(aux.getBarco(estado));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	        		quedan--;   
	            }
	            
	            if(quedan == 0) {
					guiTablero.cambiaEstado("HAS GANADO. Disparos: " + disparos);
				} else {
					disparos++;
					guiTablero.cambiaEstado("Intentos: " + disparos + "    Barcos restantes: " + quedan);
				}  
			}
          
        } // end actionPerformed

	} // end class ButtonListener
}
