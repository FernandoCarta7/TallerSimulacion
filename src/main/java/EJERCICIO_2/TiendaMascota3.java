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
        int demanda = 0;
        int inventario = 0;
        int inventarioEnKG = 0;
        int donacion = 0;
        int acumuladoDonacion = 0;
        int acumuladoVenta = 0;
        double gananciasNetas = 0;
        double gananciasNetasAux = 0;

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

        for (int i = 0; i < gananciasPorPaquete.length; i++) {

            if ( gananciasPorPaquete[i] > mayorGanancia ) {
                mayorGanancia = gananciasPorPaquete[i];
                posicionGanancia = i;
            }
          //  System.out.println("Ganancias por paquete: " + gananciasPorPaquete[i] + " posción: " + i);
        }
        //System.out.println(" \n Mayor ganancia: " + mayorGanancia + " posición ganancia: " + posicionGanancia);

        cantidadPaquetes = getPaqueteOptimo(posicionGanancia);

        /*
        * cantidadPaquetes Guarda la cantidad de paquetes que luego de la simulación nos brinda la mejor opción
        * para producir y configurar la maquina con esta cantidad
        * NOTA: Al obtener este valor se muestra por consola la mejor opción
        * */

        /*Iniciamos simulación con demanda e inventario,
        teniendo en cuenta la restricción de perdida de inventario*/

        for (int j = 0; j < 2; j++) {

            for (int i = 0; i < DIAS_SIMULACION; i++) {

                demanda = simulacionMonteCarloDemanda();
                inventario = inventario + cantidadPaquetes;


                double aleatorio = random.nextDouble(0,1);
                if ( inventario > demanda ) {
                    acumuladoVenta = acumuladoVenta + demanda;
                    if ( aleatorio < 0.45 && i > 0 ){
                        inventario = 0;
                    } else {
                        inventario = inventario - demanda;
                    }
                } else {
                    acumuladoVenta = acumuladoVenta + inventario;
                    inventario = 0;
                }


                if ( inventario > 0 ){
                    inventarioEnKG = inventario*10;
                    donacion =  inventarioEnKG / 2;
                    acumuladoDonacion = acumuladoDonacion + donacion;

                    /**System.out.println("------------------------------");
                    System.out.println("Inventario en kg: " + inventarioEnKG + " donacion: " + donacion + " paquetes de 2kg");
                    System.out.println("******************************");*/
                }
                //System.out.println("Demanda : " + demanda + " Inventario: " + inventario);
            }
        }

        gananciasNetas = (acumuladoVenta * 10 * VALOR_VENTAS) - COSTO_CROQUETAS* (acumuladoVenta + acumuladoDonacion);
        //System.out.println("******************************");
        //System.out.println("******************************" + "\n");
        gananciasNetasAux = (gananciasNetas*1.2) - (COSTO_CROQUETAS*acumuladoDonacion);

        System.out.println("En " + DIAS_SIMULACION + " dias se donó: " + acumuladoDonacion + " kg");
        System.out.println("En " + DIAS_SIMULACION + " dias se vendió " + acumuladoVenta * 10 + " kg");
        System.out.println("Ganancias netas: " + gananciasNetas/1000000 + " millones de pesos");

        System.out.println("******************************" + "\n");
        if (gananciasNetasAux > gananciasNetas){
            System.out.println("Ganancias con aumento del 20%: " + gananciasNetasAux/1000000 + " millones de pesos");
            System.out.println("Se recomienda aumentar la donación a 4 kg, con la condición de un aumento del 20% en ganancias");
        }else {
            System.out.println("No se recomienda aumentar la donación, aumento en ganancias no justifica los nuevos costos");
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
    public static int simulacionMonteCarloDemanda(){

            Random random = new Random();
            double aleatorio = random.nextDouble(0,1);

            if ( aleatorio <= 0.25 ) {
                return  5;
            } else if ( aleatorio <= 0.40 ) {
                return  10;
            } else if ( aleatorio <= 0.75 ) {
                return  12;
            } else if ( aleatorio <= 0.875 ) {
                return  15;
            } else {
                return  20;
            }
        }

}
