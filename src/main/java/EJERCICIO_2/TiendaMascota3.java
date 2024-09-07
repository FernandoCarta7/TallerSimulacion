package EJERCICIO_2;

import java.util.Random;

public class TiendaMascota3 {


    public static void main ( String [] args ){
        final int COSTO_CROQUETAS = 800; //Costo por kg de croquetas
        final int VALOR_VENTAS = 1500; //Valor de venta por kg
        final int DIAS_SIMULACION = 360;

        int cantidadPaquetes = 0; //Paquetes de 10 kg
        int [] contadorPaquetesSimulacion = {0,0,0,0,0};
        double [] gananciasPorPaquete = {0,0,0,0,0};
        double mayorGanancia = 0; //Almacena la máxima ganancia
        int posicionGanancia = -1;

        Random random = new Random();



        //SIMULACIÓN DE MONTE CARLO PARA DETERMINAR CANTIDAD OPTIMA DE PRODUCCION
        for (int i = 0; i < DIAS_SIMULACION; i++) {
            double aleatorio = random.nextDouble(0,1);
            if ( aleatorio <= 0.25 ) {
                cantidadPaquetes = 5;
                contadorPaquetesSimulacion[0] = contadorPaquetesSimulacion[0] + 1;
                gananciasPorPaquete[0] =
                        gananciasPorPaquete[0] + getGanancias(cantidadPaquetes,VALOR_VENTAS,COSTO_CROQUETAS);
            } else if ( aleatorio <= 0.40 ) {
                cantidadPaquetes = 10;
                contadorPaquetesSimulacion[1] = contadorPaquetesSimulacion[1] + 1;
                gananciasPorPaquete[1] =
                        gananciasPorPaquete[1] + getGanancias(cantidadPaquetes,VALOR_VENTAS,COSTO_CROQUETAS);
            } else if ( aleatorio <= 0.75 ) {
                cantidadPaquetes = 12;
                contadorPaquetesSimulacion[2] = contadorPaquetesSimulacion[2] + 1;
                gananciasPorPaquete[2] =
                        gananciasPorPaquete[2] + getGanancias(cantidadPaquetes,VALOR_VENTAS,COSTO_CROQUETAS);
            } else if ( aleatorio <= 0.875 ) {
                cantidadPaquetes = 15;
                contadorPaquetesSimulacion[3] = contadorPaquetesSimulacion[3] + 1;
                gananciasPorPaquete[3] =
                        gananciasPorPaquete[3] + getGanancias(cantidadPaquetes,VALOR_VENTAS,COSTO_CROQUETAS);
            } else {
                cantidadPaquetes = 20;
                contadorPaquetesSimulacion[4] = contadorPaquetesSimulacion[4] + 1;
                gananciasPorPaquete[4] =
                        gananciasPorPaquete[4] + getGanancias(cantidadPaquetes,VALOR_VENTAS,COSTO_CROQUETAS);
            }
        }

        mayorGanancia = gananciasPorPaquete[0];
        posicionGanancia = 0;

        for (int i = 1; i < gananciasPorPaquete.length; i++) {
            if ( gananciasPorPaquete[i] > mayorGanancia ) {
                mayorGanancia = gananciasPorPaquete[i];
                posicionGanancia = i;
            }
        }

        cantidadPaquetes = getPaqueteOptimo(posicionGanancia);
        //SIMULACIÓN DE MONTE CARLO PARA EL INVENTARIO Y DEMANDA
        for (int i = 0; i < DIAS_SIMULACION; i++) {
            double aleatorio = random.nextDouble(0,1);
            if ( aleatorio <= 0.25 ) {

            } else if ( aleatorio <= 0.40 ) {

            } else if ( aleatorio <= 0.75 ) {

            } else if ( aleatorio <= 0.875 ) {

            } else {

            }
        }

    }
    public static double getGanancias(int cantidadPaquetes, int valorVenta, int costoCroqueta){
        return ((cantidadPaquetes * valorVenta * 10) - (cantidadPaquetes * costoCroqueta * 10 ))/1000;
    }
    public static int getPaqueteOptimo(int posicion){
        switch ( posicion ){
            case 0:
                System.out.println("La producción de 5 paquetes de 10 kg es la mejor opción");
                return 5;
            case 1:
                System.out.println("La producción de 10 paquetes de 10 kg es la mejor opción");
                return 10;
            case 2:
                System.out.println("La producción de 12 paquetes de 10 kg es la mejor opción");
                return 12;
            case 3:
                System.out.println("La producción de 15 paquetes de 10 kg es la mejor opción");
                return 15;
            default:
                System.out.println("La producción de 20 paquetes de 10 kg es la mejor opción");
                return 20;
        }
    }
}
