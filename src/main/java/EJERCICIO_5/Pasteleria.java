package EJERCICIO_5;

import java.util.Random;

public class Pasteleria {
    public static void main(String[] args) {
        // Datos
        int tortasProducidas = 50;
        int costoTorta = 1000;
        int precioVentaTorta = 3000;
        int multa = 30000;
        double probabilidadMulta = 0.25;
        double permisoDiario = 20000.0 / 7;

        // Distribución de la demanda y probabilidades
        int[] demanda = {10, 20, 25, 30, 50, 70, 100};
        double[] probabilidades = {0.1, 0.2, 0.3, 0.2, 0.1, 0.06, 0.04};

        // Simulaciones
        int nSimulaciones = 7;
        Random rand = new Random();

        int totalTortasNoVendidas = 0;
        double totalUtilidadSinPermiso = 0;
        double totalUtilidadConPermiso = 0;

        /** SIMULACIÓN DE MONTECARLO*/
        for (int i = 0; i < nSimulaciones; i++) {

            int demandaSimulada = simularDemanda(demanda, probabilidades, rand);

            // Calculamos las tortas no vendidas
            int tortasNoVendidas = Math.max(tortasProducidas - demandaSimulada, 0);
            totalTortasNoVendidas += tortasNoVendidas;

            boolean policiaDescubre = rand.nextDouble() < probabilidadMulta;// Simulamos si la policía descubre al vendedor

            // Calculamos la utilidad diaria
            double utilidadConMulta = Math.min(demandaSimulada, tortasProducidas) * precioVentaTorta
                    - (tortasProducidas * costoTorta)
                    - (policiaDescubre ? multa : 0);
            double utilidad = 0;
            if (policiaDescubre){
                 utilidad = utilidadConMulta - multa;
            } else {
                utilidad = utilidadConMulta;
            }
            totalUtilidadSinPermiso = totalUtilidadSinPermiso + utilidadConMulta;
            totalUtilidadConPermiso = totalUtilidadConPermiso + utilidad - permisoDiario;
            totalUtilidadConPermiso = totalUtilidadConPermiso + utilidad - permisoDiario;

        }

        // Resultados
        double tortasNoVendidasPromedio = totalTortasNoVendidas / (double) nSimulaciones;
        double utilidadMediaSinPermiso = totalUtilidadSinPermiso / nSimulaciones;
        double utilidadMediaConPermiso = totalUtilidadConPermiso / nSimulaciones;

        System.out.println("Número medio de tortas no vendidas: " + tortasNoVendidasPromedio);
        System.out.println("Utilidad media por día sin permiso: $" + utilidadMediaSinPermiso);
        System.out.println("Utilidad media por día con permiso: $" + utilidadMediaConPermiso);
        if (utilidadMediaConPermiso > utilidadMediaSinPermiso){
            System.out.println("\n \t Se recomienda adquirir el persmiso ");
        } else System.out.println("\n \t No se recomienda adquirir el persmiso");
    }

    // Método para simular la demanda basada en las probabilidades
    private static int simularDemanda(int[] demanda, double[] probabilidades, Random rand) {
        double randomValue = rand.nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < demanda.length; i++) {
            cumulativeProbability += probabilidades[i];
            if (randomValue <= cumulativeProbability) {
                return demanda[i];
            }
        }
        return demanda[demanda.length - 1]; // por si acaso no cae dentro del rango
    }
}
