package Tema2.Ejemplos.InterbloqueoSolucionado;

public class Transferencia implements Runnable {
    private CuentaBancaria origen;
    private CuentaBancaria destino;
    private int cantidad;
    public Transferencia(CuentaBancaria origen, CuentaBancaria destino, int cantidad) {
        this.origen = origen;
        this.destino = destino;
        this.cantidad = cantidad;
    }
    public void run() {
        origen.transferir(destino, cantidad);
    }
}
