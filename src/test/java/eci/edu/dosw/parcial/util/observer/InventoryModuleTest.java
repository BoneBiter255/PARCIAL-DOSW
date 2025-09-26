package eci.edu.dosw.parcial.util.observer;

import eci.edu.dosw.parcial.util.InventarioObs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class InventoryModuleTest {

    private InventarioObs inventarioObs;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        inventarioObs = new InventarioObs();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testActualizar() {
        inventarioObs.actualizar();
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Inventario descontado.", output, "Debe mostrar el mensaje correcto de inventario");
    }

    @Test
    void testMultiplesActualizaciones() {
        inventarioObs.actualizar();
        inventarioObs.actualizar();
        inventarioObs.actualizar();
        
        String output = outputStreamCaptor.toString();
        int count = output.split("Inventario descontado.", -1).length - 1;
        assertEquals(3, count, "Debe mostrar el mensaje 3 veces");
    }

    @Test
    void testConsistenciaComportamiento() {
        InventarioObs otroInventario = new InventarioObs();
        
        inventarioObs.actualizar();
        String output1 = outputStreamCaptor.toString().trim();
        
        // Reset captor
        outputStreamCaptor.reset();
        
        otroInventario.actualizar();
        String output2 = outputStreamCaptor.toString().trim();
        
        assertEquals(output1, output2, "Diferentes instancias deben comportarse igual");
    }
}