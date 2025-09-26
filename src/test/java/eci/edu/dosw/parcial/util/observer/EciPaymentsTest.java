package eci.edu.dosw.parcial.util.observer;

import eci.edu.dosw.parcial.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class EciPaymentsTest {

    private PagoService pagoService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        pagoService = new PagoService();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testAgregarObserver() {
        Observer inventario = new InventarioObs();
        Observer facturacion = new FacturacionObs();
        
        // No hay forma directa de verificar que se agrego, 
        // pero podemos probarlo indirectamente con realizarPago
        pagoService.agregarObserver(inventario);
        pagoService.agregarObserver(facturacion);
        
        PagoFactory factory = new TarjetaFactory();
        pagoService.realizarPago(factory, 100.0);
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Inventario descontado."), "Debe notificar al inventario");
        assertTrue(output.contains("Factura generada."), "Debe notificar a facturacion");
    }

    @Test
    void testRealizarPagoExitoso() {
        pagoService.agregarObserver(new InventarioObs());
        pagoService.agregarObserver(new FacturacionObs());
        pagoService.agregarObserver(new NotificacionObs());
        
        PagoFactory factory = new TarjetaFactory();
        pagoService.realizarPago(factory, 150.0);
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Validando tarjeta..."), "Debe validar el pago");
        assertTrue(output.contains("Pago con tarjeta: $150.0"), "Debe procesar el pago");
        assertTrue(output.contains("Inventario descontado."), "Debe actualizar inventario");
        assertTrue(output.contains("Factura generada."), "Debe generar factura");
        assertTrue(output.contains("Correo enviado al cliente."), "Debe enviar notificacion");
    }

    @Test
    void testRealizarPagoConPayPal() {
        pagoService.agregarObserver(new InventarioObs());
        
        PagoFactory factory = new PayPalFactory();
        pagoService.realizarPago(factory, 75.5);
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Validando PayPal..."), "Debe validar PayPal");
        assertTrue(output.contains("Pago con PayPal: $75.5"), "Debe procesar pago PayPal");
        assertTrue(output.contains("Inventario descontado."), "Debe notificar observadores");
    }

    @Test
    void testRealizarPagoConCrypto() {
        pagoService.agregarObserver(new NotificacionObs());
        
        PagoFactory factory = new CryptoFactory();
        pagoService.realizarPago(factory, 0.025);
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Validando Crypto..."), "Debe validar Crypto");
        assertTrue(output.contains("Pago con Criptomonedas: $0.025"), "Debe procesar pago Crypto");
        assertTrue(output.contains("Correo enviado al cliente."), "Debe notificar observadores");
    }

    @Test
    void testSinObservadores() {
        PagoFactory factory = new TarjetaFactory();
        pagoService.realizarPago(factory, 50.0);
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Validando tarjeta..."), "Debe validar el pago");
        assertTrue(output.contains("Pago con tarjeta: $50.0"), "Debe procesar el pago");
        assertFalse(output.contains("Inventario descontado."), "No debe notificar si no hay observadores");
        assertFalse(output.contains("Factura generada."), "No debe notificar si no hay observadores");
    }

    @Test
    void testMultiplesObservadoresMismoTipo() {
        Observer inventario1 = new InventarioObs();
        Observer inventario2 = new InventarioObs();
        
        pagoService.agregarObserver(inventario1);
        pagoService.agregarObserver(inventario2);
        
        PagoFactory factory = new CryptoFactory();
        pagoService.realizarPago(factory, 100.0);
        
        String output = outputStreamCaptor.toString();
        int count = output.split("Inventario descontado.", -1).length - 1;
        assertEquals(2, count, "Debe notificar a ambos observadores de inventario");
    }
}