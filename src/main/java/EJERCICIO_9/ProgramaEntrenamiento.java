package EJERCICIO_9;

import java.util.Random;

public class ProgramaEntrenamiento {

    public static final char [] ETAPA = {'A', 'B', 'C'};
    public static final double [] PROB_SUPERAR_ETAPA = { 0.4, 0.5, 0.2 };
    public static final int CANTIDAD_SIMULACIONES = 5;


    public static void main( String [] args ){
        int [] acumuladoSemanas = new int[ CANTIDAD_SIMULACIONES ];

        for (int i = 0; i < CANTIDAD_SIMULACIONES; i++) {
            acumuladoSemanas[i] = simulacionEntrenamiento();
        }
        int [] menorMayor = getMayorMenor( acumuladoSemanas );
        int totalAcumuladoSemanas = getTotalAcumulado( acumuladoSemanas );
        System.out.println("------------------------------------------------------------");
        System.out.println("a)\t Promedio de semanas por cada etapa = " + totalAcumuladoSemanas/(CANTIDAD_SIMULACIONES*ETAPA.length));
        System.out.println("b)\t Tiempo minimo:\t" + menorMayor[0]+" semanas, tiempo máximo:\t" + menorMayor[1] + " semanas.");
        System.out.println("------------------------------------------------------------");
    }

   public static int [] getMayorMenor( int [] vector ){
        int [] mayorMenor = { 0, 0};
        int mayor = 0;
        int menor = vector[0];
       for (int i = 0; i < vector.length; i++) {
           if( vector[i] > mayor ){
               mayor = vector[i];
           }
           if ( vector[i] < menor ){
               menor = vector[i];
           }
       }
        mayorMenor[0] = menor;
        mayorMenor[1] = mayor;

        return mayorMenor;
   }

    public static int simulacionEntrenamiento (){
        char etapa = 'A';
        boolean etapaFinalSuperada = false;
        Random random = new Random();
        int semanas = 0;

        do {
            double aleatorio = random.nextDouble( 0,1);
            switch ( etapa ) {
                case 'A':
                    semanas = semanas + 1;
                    if ( aleatorio < PROB_SUPERAR_ETAPA[0] ) {
                        etapa = 'B';
                        //System.out.println("Etapa " + ETAPA[0] + " supereda");
                    }
                    break;
                case 'B':
                    semanas = semanas + 1;
                    if ( aleatorio < PROB_SUPERAR_ETAPA[1] ){
                        etapa = 'C';
                        //System.out.println("Etapa " + ETAPA[1] + " supereda");
                    }
                    break;
                default:
                    semanas = semanas + 1;
                    if ( aleatorio < PROB_SUPERAR_ETAPA[2] ) {
                        etapaFinalSuperada = true;
                        //System.out.println("Etapa " + ETAPA[2] + " supereda, capacitación completada en " + semanas + " semanas.");
                    }
            }
        } while ( !etapaFinalSuperada );
        return semanas;
    }

    public static int getTotalAcumulado( int [] vector ){
        int totalAcumuladoSemanas = 0;
        for (int i = 0; i < vector.length; i++) {
            totalAcumuladoSemanas = totalAcumuladoSemanas + vector[i];
        }
        return  totalAcumuladoSemanas;
    }
}
