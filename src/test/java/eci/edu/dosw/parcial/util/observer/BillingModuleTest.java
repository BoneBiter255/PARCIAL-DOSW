package eci.edu.dosw.parcial.util.observer;

import eci.edu.dosw.parcial.util.FacturacionObs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BillingModuleTest {

    private FacturacionObs facturacionObs;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        facturacionObs = new FacturacionObs();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testActualizar() {
        facturacionObs.actualizar();
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Factura generada.", output, "Debe mostrar el mensaje correcto de facturacion");
    }

    @Test
    void testMultiplesActualizaciones() {
        facturacionObs.actualizar();
        facturacionObs.actualizar();
        
        String output = outputStreamCaptor.toString();
        int count = output.split("Factura generada.", -1).length - 1;
        assertEquals(2, count, "Debe mostrar el mensaje 2 veces");
    }

    @Test
    void testConsistenciaComportamiento() {
        FacturacionObs otraFacturacion = new FacturacionObs();
        
        facturacionObs.actualizar();
        String output1 = outputStreamCaptor.toString().trim();
        
        // Reset captor
        outputStreamCaptor.reset();
        
        otraFacturacion.actualizar();
        String output2 = outputStreamCaptor.toString().trim();
        
        assertEquals(output1, output2, "Diferentes instancias deben comportarse igual");
    }
}