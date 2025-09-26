package eci.edu.dosw.parcial.util.factory;

import eci.edu.dosw.parcial.util.MetodoPago;
import eci.edu.dosw.parcial.util.PagoTarjeta;
import eci.edu.dosw.parcial.util.TarjetaFactory;
import eci.edu.dosw.parcial.util.ValidadorPago;
import eci.edu.dosw.parcial.util.ValidadorTarjeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardFactoryTest {

    private TarjetaFactory tarjetaFactory;

    @BeforeEach
    void setUp() {
        tarjetaFactory = new TarjetaFactory();
    }

    @Test
    void testCrearPago() {
        MetodoPago pago = tarjetaFactory.crearPago();
        
        assertNotNull(pago, "El metodo de pago no debe ser nulo");
        assertTrue(pago instanceof PagoTarjeta, "Debe crear una instancia de PagoTarjeta");
    }

    @Test
    void testCrearValidador() {
        ValidadorPago validador = tarjetaFactory.crearValidador();
        
        assertNotNull(validador, "El validador no debe ser nulo");
        assertTrue(validador instanceof ValidadorTarjeta, "Debe crear una instancia de ValidadorTarjeta");
    }

    @Test
    void testFactoryConsistencia() {
        MetodoPago pago1 = tarjetaFactory.crearPago();
        MetodoPago pago2 = tarjetaFactory.crearPago();
        ValidadorPago validador1 = tarjetaFactory.crearValidador();
        ValidadorPago validador2 = tarjetaFactory.crearValidador();
        
        assertNotSame(pago1, pago2, "Cada llamada debe crear una nueva instancia de pago");
        assertNotSame(validador1, validador2, "Cada llamada debe crear una nueva instancia de validador");
        assertEquals(pago1.getClass(), pago2.getClass(), "Todas las instancias de pago deben ser del mismo tipo");
        assertEquals(validador1.getClass(), validador2.getClass(), "Todas las instancias de validador deben ser del mismo tipo");
    }
}