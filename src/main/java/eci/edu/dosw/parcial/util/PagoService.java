package eci.edu.dosw.parcial.util;

import java.util.*;

public class PagoService {
    private List<Observer> observadores = new ArrayList<>();

    public void agregarObserver(Observer obs) {
        observadores.add(obs);
    }

    public void realizarPago(PagoFactory factory, double monto) {
        ValidadorPago validador = factory.crearValidador();
        if (validador.validar()) {
            MetodoPago pago = factory.crearPago();
            pago.pagar(monto);
            notificarObservadores();
        } else {
            System.out.println("Pago fallido.");
        }
    }

    private void notificarObservadores() {
        for (Observer obs : observadores) obs.actualizar();
    }
}