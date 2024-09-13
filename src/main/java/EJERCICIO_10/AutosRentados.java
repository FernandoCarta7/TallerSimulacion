package EJERCICIO_10;

import java.util.Random;

public class AutosRentados {
    public static void main(String[] args) {
        int numeroSimulaciones = 100000; // Número de simulaciones a realizar
        double costoAuto = 11000000; // Costo promedio anual de un auto
        double rentaDiaria = 52000; // Renta diaria por auto
        double costoFaltaAuto = 30000; // Costo por no tener auto disponible
        double costoOcio = 7500; // Costo por tener un auto ocioso

        // Probabilidades y número de autos alquilados por día
        int[] autosAlquilados = {0, 1, 2, 3, 4};
        double[] probabilidadAlquiler = {0.10, 0.10, 0.25, 0.30, 0.25};

        // Probabilidades y número de días rentados por auto
        int[] diasRentados = {1, 2, 3, 4};
        double[] probabilidadRenta = {0.40, 0.35, 0.15, 0.10};

        Random random = new Random();
        double mejorCosto = Double.MAX_VALUE;
        int autosOptimos = 0;

        for (int autosComprados = 0; autosComprados <= 10; autosComprados++) { // probar con diferentes cantidades de autos
            double costoTotal = 0;

            for (int i = 0; i < numeroSimulaciones; i++) {
                // Simulación de autos alquilados por día
                int autosAlquiladosHoy = generarAutosAlquilados(probabilidadAlquiler, autosAlquilados, random);
                // Simulación de días rentados por auto
                int diasRentadosPorAuto = generarDiasRentados(probabilidadRenta, diasRentados, random);

                // Calculamos el número de autos disponibles o no disponibles
                int autosNoDisponibles = Math.max(0, autosAlquiladosHoy - autosComprados);
                int autosOciosos = Math.max(0, autosComprados - autosAlquiladosHoy);

                // Cálculo del costo total
                costoTotal += autosNoDisponibles * costoFaltaAuto; // Costo por no tener autos suficientes
                costoTotal += autosOciosos * costoOcio; // Costo por autos ociosos
            }

            // Costos fijos de comprar los autos
            costoTotal += autosComprados * costoAuto;

            // Comparar costos y actualizar el número óptimo de autos
            if (costoTotal < mejorCosto) {
                mejorCosto = costoTotal;
                autosOptimos = autosComprados;
            }
        }

        System.out.println("El número óptimo de autos que debe comprar la empresa es: " + autosOptimos);
        System.out.println("El costo total mínimo estimado es: $" + mejorCosto/1000000 + " millones COP");
    }

    // Función para simular el número de autos alquilados por día
    public static int generarAutosAlquilados(double[] probabilidades, int[] autos, Random random) {
        double r = random.nextDouble();
        double acumulado = 0;
        for (int i = 0; i < probabilidades.length; i++) {
            acumulado += probabilidades[i];
            if (r <= acumulado) {
                return autos[i];
            }
        }
        return autos[autos.length - 1]; // Por defecto
    }

    // Función para simular el número de días rentados por auto
    public static int generarDiasRentados(double[] probabilidades, int[] dias, Random random) {
        double r = random.nextDouble();
        double acumulado = 0;
        for (int i = 0; i < probabilidades.length; i++) {
            acumulado += probabilidades[i];
            if (r <= acumulado) {
                return dias[i];
            }
        }
        return dias[dias.length - 1]; // Por defecto
    }
}

