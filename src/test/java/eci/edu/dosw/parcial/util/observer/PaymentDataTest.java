package eci.edu.dosw.parcial.util.observer;

import eci.edu.dosw.parcial.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDataTest {

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
    void testPagoDataIntegracion() {
        // Configurar todos los observadores
        pagoService.agregarObserver(new InventarioObs());
        pagoService.agregarObserver(new FacturacionObs());
        pagoService.agregarObserver(new NotificacionObs());

        // Probar con diferentes metodos de pago y montos
        double[] montos = {100.0, 250.75, 50.25, 999.99};
        PagoFactory[] factories = {
            new TarjetaFactory(),
            new PayPalFactory(), 
            new CryptoFactory(),
            new TarjetaFactory()
        };

        for (int i = 0; i < montos.length; i++) {
            outputStreamCaptor.reset();
            pagoService.realizarPago(factories[i], montos[i]);
            
            String output = outputStreamCaptor.toString();
            assertFalse(output.isEmpty(), "Debe generar salida para cada pago");
            assertTrue(output.contains("$" + montos[i]), "Debe contener el monto correcto");
        }
    }

    @Test
    void testFlujoPagoCompleto() {
        pagoService.agregarObserver(new InventarioObs());
        pagoService.agregarObserver(new FacturacionObs());
        pagoService.agregarObserver(new NotificacionObs());

        PagoFactory factory = new TarjetaFactory();
        double monto = 123.45;
        
        pagoService.realizarPago(factory, monto);
        
        String output = outputStreamCaptor.toString();
        
        // Verificar orden de ejecucion
        int validacionIndex = output.indexOf("Validando tarjeta...");
        int pagoIndex = output.indexOf("Pago con tarjeta:");
        int inventarioIndex = output.indexOf("Inventario descontado.");
        int facturaIndex = output.indexOf("Factura generada.");
        int correoIndex = output.indexOf("Correo enviado al cliente.");
        
        assertTrue(validacionIndex < pagoIndex, "Validacion debe ocurrir antes del pago");
        assertTrue(pagoIndex < inventarioIndex, "Pago debe ocurrir antes de notificaciones");
        assertTrue(inventarioIndex >= 0, "Debe actualizar inventario");
        assertTrue(facturaIndex >= 0, "Debe generar factura");
        assertTrue(correoIndex >= 0, "Debe enviar correo");
    }

    @Test
    void testMontosExtremos() {
        pagoService.agregarObserver(new InventarioObs());
        
        // Monto muy pequeño
        PagoFactory factorySmall = new CryptoFactory();
        pagoService.realizarPago(factorySmall, 0.001);
        
        String outputSmall = outputStreamCaptor.toString();
        assertTrue(outputSmall.contains("$0.001"), "Debe manejar montos muy pequeños");
        
        outputStreamCaptor.reset();
        
        // Monto muy grande
        PagoFactory factoryBig = new PayPalFactory();
        pagoService.realizarPago(factoryBig, 999999.99);
        
        String outputBig = outputStreamCaptor.toString();
        assertTrue(outputBig.contains("$999999.99"), "Debe manejar montos muy grandes");
    }
}