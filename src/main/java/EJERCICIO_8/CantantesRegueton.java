package EJERCICIO_8;

import java.util.Random;

public class CantantesRegueton {
    static Random random = new Random();

    // Probabilidades de transición
    static final double PROB_CRECIENDO_CRECIENDO = 0.7;
    static final double PROB_CRECIENDO_FAMA = 0.3;
    static final double PROB_FAMA_MANTENER = 0.5;
    static final double PROB_FAMA_DECAER = 0.4;
    static final double PROB_FAMA_PERDER = 0.1;
    static final double PROB_DECAIDA_RECOBRAR = 0.2;
    static final double PROB_DECAIDA_RETIRAR = 0.8;

    // Ganancias por año en cada etapa
    static final int GANANCIA_CRECIENDO = 45000;
    static final int GANANCIA_FAMA = 100000;

    // Simulación de un cantante
    public static double simularCantante(int añosMaximos) {
        int etapa = 1; // 1: Crecimiento, 2: Fama, 3: Fama decaída
        double gananciasTotales = 0;
        int añosDeCarrera = 0;

        for (int año = 0; año < añosMaximos; año++) {
            if (etapa == 1) { // Crecimiento
                gananciasTotales += GANANCIA_CRECIENDO;
                double prob = random.nextDouble();
                if (prob <= PROB_CRECIENDO_FAMA) {
                    etapa = 2; // Avanza a Fama
                }
            } else if (etapa == 2) { // Fama
                gananciasTotales += GANANCIA_FAMA;
                double prob = random.nextDouble();
                if (prob <= PROB_FAMA_PERDER) {
                    etapa = 4; // Perder fama (retiro)
                    break; // Se retira del negocio
                } else if (prob <= PROB_FAMA_PERDER + PROB_FAMA_DECAER) {
                    etapa = 3; // Decaída de fama
                }
            } else if (etapa == 3) { // Fama decaída
                double prob = random.nextDouble();
                if (prob <= PROB_DECAIDA_RECOBRAR) {
                    etapa = 2; // Recobrar fama
                } else {
                    etapa = 4; // Retirado
                    break;
                }
            }
            añosDeCarrera++;
        }

        return gananciasTotales;
    }

    public static void main(String[] args) {
        int añosSimulacion = 20;
        int numCantantes = 30;
        int cantantesCrecimiento = 20;
        int cantantesFama = 5;
        int cantantesDecaida = 5;

        // Parte (a): Simular cuántos seguirán siendo cantantes después de 20 años
        int cantantesActivos = 0;
        for (int i = 0; i < numCantantes; i++) {
            double ganancias = simularCantante(añosSimulacion);
            if (ganancias > 0) {
                cantantesActivos++;
            }
        }
        System.out.println("Cantantes activos después de 20 años: " + cantantesActivos);

        // Parte (b) y (c) Calcular ganancias promedio en 20 años
        double gananciasTotales = 0;
        for (int i = 0; i < numCantantes; i++) {
            gananciasTotales += simularCantante(añosSimulacion);
        }
        double gananciasPromedio = gananciasTotales / numCantantes;
        System.out.println("Ganancias promedio en 20 años por cantante: " + gananciasPromedio);
    }
}
