package eci.edu.dosw.parcial.util;

public class CryptoFactory implements PagoFactory {
    public MetodoPago crearPago() { return new PagoCrypto(); }
    public ValidadorPago crearValidador() { return new ValidadorCrypto(); }
}