package eci.edu.dosw.parcial.util;

public class ValidadorTarjeta implements ValidadorPago {
    public boolean validar() {
        System.out.println("Validando tarjeta...");
        return true;
    }
}