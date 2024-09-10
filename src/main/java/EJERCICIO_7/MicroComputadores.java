package EJERCICIO_7;

import java.util.Random;

public class MicroComputadores {
    // Variables constantes
    static final double COSTO_PRODUCCION = 200000;
    static final double COSTO_INVENTARIO = 30000;
    static final double COSTO_NO_SATISFACER = 50000;

    // Probabilidades de la demanda
    static final double PROB_DEMANDA_1 = 0.3;
    static final double PROB_DEMANDA_2 = 0.45;
    static final double PROB_DEMANDA_3 = 0.25;

    // Política de producción
    static int[][] politicaProduccion = {
            {0, 2}, // Si el inventario es 0, se producen 2 computadoras.
            {1, 1}, // Si el inventario es 1, se produce 1 computadora.
            {2, 1}  // Si el inventario es 2, se produce 1 computadora.
    };

    public static void main(String[] args) {
        int inventarioInicial = 1; // Empezamos con 1 computadora en inventario.
        int diasSimulacion = 1000; // Número de días para la simulación.

        double costoTotal = 0;

        // Simulamos la demanda y producción durante varios días
        for (int dia = 0; dia < diasSimulacion; dia++) {
            int demanda = generarDemanda();
            int produccion = aplicarPolitica(inventarioInicial);

            // Actualizamos el inventario
            int inventarioFinal = inventarioInicial + produccion - demanda;

            // Calculamos los costos del día
            double costoDia = calcularCostos(produccion, inventarioInicial, demanda, inventarioFinal);
            costoTotal += costoDia;

            // El inventario para el siguiente día es el inventario final
            inventarioInicial = Math.max(0, inventarioFinal); // No puede ser negativo
        }

        // Mostramos el costo promedio por día
        double costoPromedio = costoTotal / diasSimulacion;
        System.out.printf("Costo promedio por día: %.2f $%n", costoPromedio);
    }

    // Método para generar la demanda según las probabilidades
    public static int generarDemanda() {
        Random rand = new Random();
        double r = rand.nextDouble();

        if (r < PROB_DEMANDA_1) {
            return 1;
        } else if (r < PROB_DEMANDA_1 + PROB_DEMANDA_2) {
            return 2;
        } else {
            return 3;
        }
    }

    // Método para aplicar la política de producción según el inventario inicial
    public static int aplicarPolitica(int inventario) {
        if (inventario == 0) {
            return politicaProduccion[0][1];
        } else if (inventario == 1) {
            return politicaProduccion[1][1];
        } else {
            return politicaProduccion[2][1];
        }
    }

    // Método para calcular los costos del día
    public static double calcularCostos(int produccion, int inventarioInicial, int demanda, int inventarioFinal) {
        double costoProduccion = produccion * COSTO_PRODUCCION;
        double costoInventario = Math.max(0, inventarioInicial) * COSTO_INVENTARIO;
        double costoNoSatisfacer = Math.max(0, demanda - (inventarioInicial + produccion)) * COSTO_NO_SATISFACER;

        return costoProduccion + costoInventario + costoNoSatisfacer;
    }
}
