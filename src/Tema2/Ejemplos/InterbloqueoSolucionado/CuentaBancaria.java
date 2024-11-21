package Tema2.Ejemplos.InterbloqueoSolucionado;

public class CuentaBancaria {
 private int saldo;
 private int numeroCuenta;
    public CuentaBancaria(int saldoInicial, int numeroCuenta) {
        this.saldo = saldoInicial;
        this.numeroCuenta = numeroCuenta;
    }

    public int getSaldo() {
        return saldo;
    }
    public int getNumeroCuenta() {
        return numeroCuenta;
    }
    public void depositar(int cantidad) {
        synchronized (this) {
            saldo += cantidad;
        }
    }

    public void retirar(int cantidad) {
        synchronized (this) {
            saldo -= cantidad;
        }
    }

    public boolean transferir(CuentaBancaria destino, int cantidad) {
        CuentaBancaria primeraCuenta;
        CuentaBancaria segundaCuenta;

        if (this.getNumeroCuenta() < destino.getNumeroCuenta()) {
            primeraCuenta = this;
            segundaCuenta = destino;
        } else {
            primeraCuenta = destino;
            segundaCuenta = this;
        }

        synchronized (primeraCuenta) {
            System.out.println("Bloqueo de cuenta con numero menor");
            synchronized (segundaCuenta) {
                System.out.println("Bloqueo de cuenta con numero mayor");
                if (saldo >= cantidad) {
                    retirar(cantidad);
                    destino.depositar(cantidad);
                    System.out.println("Transferencia exitosa");
                    return true;
                } else {
                    System.out.println("Saldo insuficiente");
                    return false;
                }
            }
        }
    }
}


