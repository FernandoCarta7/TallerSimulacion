package EJERCICIO_10;

import java.util.Random;

public class AutosRentados2 {
    // Datos del problema
    static final int COSTO_NO_DISPONIBLE = 30000; // Costo por no tener un auto disponible
    static final int COSTO_OCIOSO = 7500;        // Costo por auto ocioso por día
    static final int INGRESO_ALQUILER = 52000;   // Ingreso por alquiler de un auto por día
    static final int COSTO_ANUAL_AUTO = 11000000; // Costo anual de un auto

    // Probabilidades de autos alquilados por día
    static final double[] PROB_ALQUILER_DIA = {0.10, 0.10, 0.25, 0.30, 0.25};
    static final int[] ALQUILERES_POSIBLES = {0, 1, 2, 3, 4};

    // Probabilidades de días que un auto es rentado
    static final double[] PROB_DIAS_RENTADOS = {0.40, 0.35, 0.15, 0.10};
    static final int[] DIAS_POSIBLES = {1, 2, 3, 4};

    // Generador de números aleatorios
    static Random random = new Random();

    public static void main(String[] args) {
        int diasSimulacion = 50000; // Simulamos un año
        int autosMaximos = 10;      // Probamos con hasta 10 autos disponibles

        // Realizamos simulación para diferentes números de autos
        for (int autos = 1; autos <= autosMaximos; autos++) {
            double balance = simulacion(autos, diasSimulacion);
            System.out.printf("Autos disponibles: %d, Balance anual: $%,.2f%n", autos, balance);
        }
    }

    // Método que simula un día de alquiler
    public static double[] simularAlquilerDia(int autosDisponibles) {
        int autosDemandados = randomChoice(ALQUILERES_POSIBLES, PROB_ALQUILER_DIA);
        int autosAlquilados = Math.min(autosDemandados, autosDisponibles);
        int autosNoDisponibles = Math.max(0, autosDemandados - autosDisponibles);
        int autosOciosos = Math.max(0, autosDisponibles - autosAlquilados);

        // Calcular costos y ganancias
        double gananciaDia = autosAlquilados * INGRESO_ALQUILER;
        double costoNoDisponiblesDia = autosNoDisponibles * COSTO_NO_DISPONIBLE;
        double costoOciososDia = autosOciosos * COSTO_OCIOSO;

        return new double[]{gananciaDia, costoNoDisponiblesDia, costoOciososDia};
    }

    // Método que simula el número de días que un auto es rentado
    public static int simularDiasRentados() {
        return randomChoice(DIAS_POSIBLES, PROB_DIAS_RENTADOS);
    }

    // Método para realizar la simulación de Montecarlo
    public static double simulacion(int autosDisponibles, int diasSimulacion) {
        double gananciaTotal = 0;
        double costoNoDisponiblesTotal = 0;
        double costoOciososTotal = 0;

        for (int i = 0; i < diasSimulacion; i++) {
            double[] resultadosDia = simularAlquilerDia(autosDisponibles);
            gananciaTotal += resultadosDia[0];
            costoNoDisponiblesTotal += resultadosDia[1];
            costoOciososTotal += resultadosDia[2];
        }

        // Costo anual de mantener los autos
        double costoMantenerAutos = autosDisponibles * COSTO_ANUAL_AUTO;

        // Resultado total
        return gananciaTotal - (costoNoDisponiblesTotal + costoOciososTotal + costoMantenerAutos);
    }

    // Método que selecciona un valor basado en las probabilidades dadas
    public static int randomChoice(int[] valores, double[] probabilidades) {
        double rand = random.nextDouble();
        double suma = 0;
        for (int i = 0; i < probabilidades.length; i++) {
            suma += probabilidades[i];
            if (rand <= suma) {
                return valores[i];
            }
        }
        return valores[valores.length - 1]; // Por si no se selecciona ningún valor
    }
}
