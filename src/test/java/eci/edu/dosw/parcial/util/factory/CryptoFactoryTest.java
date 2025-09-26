package eci.edu.dosw.parcial.util.factory;

import eci.edu.dosw.parcial.util.CryptoFactory;
import eci.edu.dosw.parcial.util.MetodoPago;
import eci.edu.dosw.parcial.util.PagoCrypto;
import eci.edu.dosw.parcial.util.ValidadorCrypto;
import eci.edu.dosw.parcial.util.ValidadorPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoFactoryTest {

    private CryptoFactory cryptoFactory;

    @BeforeEach
    public void setUp() {
        cryptoFactory = new CryptoFactory();
    }

    @Test
    public void testCrearPago() {
        MetodoPago pago = cryptoFactory.crearPago();
        
        assertNotNull(pago, "El metodo de pago no debe ser nulo");
        assertTrue(pago instanceof PagoCrypto, "Debe crear una instancia de PagoCrypto");
    }

    @Test
    public void testCrearValidador() {
        ValidadorPago validador = cryptoFactory.crearValidador();
        
        assertNotNull(validador, "El validador no debe ser nulo");
        assertTrue(validador instanceof ValidadorCrypto, "Debe crear una instancia de ValidadorCrypto");
    }

    @Test
    public void testFactoryConsistencia() {
        MetodoPago pago1 = cryptoFactory.crearPago();
        MetodoPago pago2 = cryptoFactory.crearPago();
        ValidadorPago validador1 = cryptoFactory.crearValidador();
        ValidadorPago validador2 = cryptoFactory.crearValidador();
        
        assertNotSame(pago1, pago2, "Cada llamada debe crear una nueva instancia de pago");
        assertNotSame(validador1, validador2, "Cada llamada debe crear una nueva instancia de validador");
        assertEquals(pago1.getClass(), pago2.getClass(), "Todas las instancias de pago deben ser del mismo tipo");
        assertEquals(validador1.getClass(), validador2.getClass(), "Todas las instancias de validador deben ser del mismo tipo");
    }
}