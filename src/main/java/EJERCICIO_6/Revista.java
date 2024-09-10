package EJERCICIO_6;

import java.util.Random;

public class Revista {
    public static void main(String[] args) {
        
        final int costoEjemplar = 6000;
        final int precioVenta = 8000;
        final int precioDevolucion10 = 3600;
        final int precioCompraAdicional = 4800;
        final int precioDevolucionFinMes = 3000;

        // Distribución de la demanda en los primeros 10 días
        int [] demanda10Dias = { 5, 6, 7, 8, 9, 10, 11};
        double[] probabilidad10Dias = {0.05, 0.05, 0.10, 0.15, 0.25, 0.25, 0.15};
        int [] mejorOpcionCompra10Dias = {0,0,0,0,0,0,0};

        // Distribución de la demanda en los siguientes 20 días
        int[] demanda20Dias = {4, 5, 6, 7, 8};
        double[] probabilidad20Dias = {0.15, 0.20, 0.30, 0.20, 0.15};
        int [] mejorOpcionCompra20Dias = {0,0,0,0,0};

        //SIMULACIONES
        final int CANTIDAD_SIMULACIONES = 1800;
        Random random = new Random();
        int revistasCompradas = random.nextInt(6, 12);
        int revistasSobrantes = 0;

        double totalUtilidad = 0;

        for (int i = 0; i < CANTIDAD_SIMULACIONES; i++) {

            int demandaInicial = simularDemanda( demanda10Dias, probabilidad10Dias );
            int posicion = demandaInicial - 5;
            mejorOpcionCompra10Dias[posicion] = mejorOpcionCompra10Dias[posicion] + 1;


            revistasSobrantes = revistasCompradas - demandaInicial;

            //Decisión al dia 10: Devolver o comprar más
            if (revistasSobrantes > 0){
                totalUtilidad = totalUtilidad +revistasSobrantes*precioDevolucion10;
            } else {
                int revistasExtraCompradas = Math.abs(revistasSobrantes);
                totalUtilidad = totalUtilidad - revistasExtraCompradas * precioCompraAdicional;
                revistasCompradas = revistasCompradas + revistasExtraCompradas;
            }

            //Simular demanda de los siguientes 20 dias
            int demandaSegundaParte = simularDemanda(demanda20Dias,probabilidad20Dias);
            posicion = demandaSegundaParte - 4;
            mejorOpcionCompra20Dias[posicion] = mejorOpcionCompra20Dias[posicion] + 1;
            int totalDemanda = demandaInicial + demandaSegundaParte;

            //Calcular cuantas revistas se venden y cuantas se quedan
            int revistasVendidas = Math.min(totalDemanda,revistasCompradas);
            totalUtilidad = totalUtilidad + revistasVendidas * precioVenta;

            //Devolucion de revistas sobrantes al final de mes
            int revistasSobranteMes = revistasCompradas - revistasVendidas;
            if (revistasSobranteMes > 0){
                totalUtilidad = totalUtilidad - revistasCompradas*costoEjemplar;
            }
            System.out.println( (i + 1) + ", Revistas vendidas: " + revistasVendidas  + ", total utilidad: " + totalUtilidad);
            revistasCompradas = random.nextInt(6, 12);
        }
        System.out.println("UTILIDAD TOTAL = " + totalUtilidad / 1000000 + " millones de pesos");
        System.out.println("------------------------------");
        imprimirMejorOpcion(mejorOpcionCompra10Dias,mejorOpcionCompra20Dias,demanda10Dias,demanda20Dias);


    }

    private static int simularDemanda(int[] demanda, double[] probabilidades) {
        
        Random random = new Random();
        double aleatorio = random.nextDouble(0, 1);
        double probabilidadAcumulada = 0;

        for (int i = 0; i < demanda.length; i++) {
            probabilidadAcumulada = probabilidadAcumulada + probabilidades[i];
            if ( aleatorio <= probabilidadAcumulada ) {
                return demanda[i];
            }
        }
        return demanda[demanda.length - 1];
    }
    private static void imprimirMejorOpcion(int[] vector10Dias, int[] vector20Dias, int [] demanda10, int [] demanda20){
        int mayor = 0;
        int posicion = 0;
        for (int i = 0; i < vector10Dias.length; i++) {
            if (vector10Dias[i] > mayor ){
                mayor = vector10Dias[i];
                posicion = i;
            }
        }
        System.out.println("La mejor opción será comprar " + demanda10[posicion] + " revistas, en los primeros 10 días");
        mayor = 0;
        posicion = 0;
        for (int i = 0; i < vector20Dias.length; i++) {
            if (vector20Dias[i] > mayor ){
                mayor = vector20Dias[i];
                posicion = i;
            }
        }
        System.out.println("La mejor opción será comprar " + demanda20[posicion] + " revistas, en los ultimos 20 días");
    }
    
}
