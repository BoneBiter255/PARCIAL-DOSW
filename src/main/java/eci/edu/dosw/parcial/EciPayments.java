package eci.edu.dosw.parcial;

import java.util.Scanner;
import eci.edu.dosw.parcial.util.CryptoFactory;
import eci.edu.dosw.parcial.util.FacturacionObs;
import eci.edu.dosw.parcial.util.InventarioObs;
import eci.edu.dosw.parcial.util.NotificacionObs;
import eci.edu.dosw.parcial.util.PagoFactory;
import eci.edu.dosw.parcial.util.PagoService;
import eci.edu.dosw.parcial.util.PayPalFactory;
import eci.edu.dosw.parcial.util.TarjetaFactory;

public class EciPayments {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void run() {
        System.out.println("========================================");
        System.out.println("    SISTEMA DE PAGOS ECIPAYMENTS");
        System.out.println("========================================\n");

        // Configurar el servicio de pagos con observadores
        PagoService servicio = new PagoService();
        servicio.agregarObserver(new InventarioObs());
        servicio.agregarObserver(new FacturacionObs());
        servicio.agregarObserver(new NotificacionObs());

        boolean continuar = true;
        
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    procesarPago(servicio, new TarjetaFactory(), "Tarjeta de Credito");
                    break;
                case 2:
                    procesarPago(servicio, new PayPalFactory(), "PayPal");
                    break;
                case 3:
                    procesarPago(servicio, new CryptoFactory(), "Criptomonedas");
                    break;
                case 4:
                    continuar = false;
                    System.out.println("\n¡Gracias por usar EciPayments!");
                    break;
                default:
                    System.out.println("\nOpcion invalida. Por favor, seleccione una opcion valida.\n");
            }
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("Seleccione el metodo de pago:");
        System.out.println("1. Tarjeta de Credito");
        System.out.println("2. PayPal");
        System.out.println("3. riptomonedas");
        System.out.println("4. Salir");
        System.out.print("\nIngrese su opcion (1-4): ");
    }
    
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Opción inválida
        }
    }
    
    private static double leerMonto() {
        while (true) {
            System.out.print("Ingrese el monto a pagar ($): ");
            try {
                double monto = Double.parseDouble(scanner.nextLine().trim());
                if (monto > 0) {
                    return monto;
                } else {
                    System.out.println("El monto debe ser mayor a 0. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un numero valido. Intente nuevamente.");
            }
        }
    }
    
    private static void procesarPago(PagoService servicio, PagoFactory factory, String tipoPago) {
        System.out.println("\nProcesando pago con " + tipoPago + "");
        
        double monto = leerMonto();
        
        System.out.println("\nProcesando pago...\n");
        
        try {
            servicio.realizarPago(factory, monto);
            System.out.println("\n¡Pago procesado exitosamente!");
        } catch (Exception e) {
            System.out.println("\nError al procesar el pago: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
}