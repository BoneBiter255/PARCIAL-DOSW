package eci.edu.dosw.parcial.util.factory;

import eci.edu.dosw.parcial.util.MetodoPago;
import eci.edu.dosw.parcial.util.PagoPayPal;
import eci.edu.dosw.parcial.util.PayPalFactory;
import eci.edu.dosw.parcial.util.ValidadorPago;
import eci.edu.dosw.parcial.util.ValidadorPayPal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayPalFactoryTest {

    private PayPalFactory payPalFactory;

    @BeforeEach
    void setUp() {
        payPalFactory = new PayPalFactory();
    }

    @Test
    void testCrearPago() {
        MetodoPago pago = payPalFactory.crearPago();
        
        assertNotNull(pago, "El metodo de pago no debe ser nulo");
        assertTrue(pago instanceof PagoPayPal, "Debe crear una instancia de PagoPayPal");
    }

    @Test
    void testCrearValidador() {
        ValidadorPago validador = payPalFactory.crearValidador();
        
        assertNotNull(validador, "El validador no debe ser nulo");
        assertTrue(validador instanceof ValidadorPayPal, "Debe crear una instancia de ValidadorPayPal");
    }

    @Test
    void testFactoryConsistencia() {
        MetodoPago pago1 = payPalFactory.crearPago();
        MetodoPago pago2 = payPalFactory.crearPago();
        ValidadorPago validador1 = payPalFactory.crearValidador();
        ValidadorPago validador2 = payPalFactory.crearValidador();
        
        assertNotSame(pago1, pago2, "Cada llamada debe crear una nueva instancia de pago");
        assertNotSame(validador1, validador2, "Cada llamada debe crear una nueva instancia de validador");
        assertEquals(pago1.getClass(), pago2.getClass(), "Todas las instancias de pago deben ser del mismo tipo");
        assertEquals(validador1.getClass(), validador2.getClass(), "Todas las instancias de validador deben ser del mismo tipo");
    }
}