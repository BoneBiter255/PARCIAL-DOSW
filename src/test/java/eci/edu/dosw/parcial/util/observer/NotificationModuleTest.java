package eci.edu.dosw.parcial.util.observer;

import eci.edu.dosw.parcial.util.NotificacionObs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class NotificationModuleTest {

    private NotificacionObs notificacionObs;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        notificacionObs = new NotificacionObs();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testActualizar() {
        notificacionObs.actualizar();
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Correo enviado al cliente.", output, "Debe mostrar el mensaje correcto de notificacion");
    }

    @Test
    void testMultiplesActualizaciones() {
        notificacionObs.actualizar();
        notificacionObs.actualizar();
        notificacionObs.actualizar();
        notificacionObs.actualizar();
        
        String output = outputStreamCaptor.toString();
        int count = output.split("Correo enviado al cliente.", -1).length - 1;
        assertEquals(4, count, "Debe mostrar el mensaje 4 veces");
    }

    @Test
    void testConsistenciaComportamiento() {
        NotificacionObs otraNotificacion = new NotificacionObs();
        
        notificacionObs.actualizar();
        String output1 = outputStreamCaptor.toString().trim();
        
        // Reset captor
        outputStreamCaptor.reset();
        
        otraNotificacion.actualizar();
        String output2 = outputStreamCaptor.toString().trim();
        
        assertEquals(output1, output2, "Diferentes instancias deben comportarse igual");
    }
}