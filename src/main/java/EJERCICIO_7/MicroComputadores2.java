package EJERCICIO_7;

import java.util.Random;

public class MicroComputadores2 {
    static final int PRODUCCION_MAXIMA = 2;
    static final int [] DEMANDA_DIARIA = { 1, 2, 3 };
    static final double [] DEMANDA_PROBABILIDAD = { 0.3, 0.75, 1.0 };
    static final double COSTO_PRODUCCION = 200000;
    static final double COSTO_INVENTARIO = 30000;
    static final double COSTO_DEMANDA_NO_SATISFECHA = 50000;
    static final int DIAS_SIMULACION = 3600; //DOS AÑOS
    
    public static void main( String [] args ){
        int demanda = 0;
        int inventario = 0;
        int produccion = 2;
        double [] costos = {0,0};


        //SIMULACIÓN DE MONTECARLO PRODUCCIÓN NORMAL
        costos[0] = simualacionMontecarlo(inventario, produccion, demanda);
        //SIMULACIÓN DE MONTECARLO PRODUCCIÓN PARA PRODUCCION 2 INVENTARIO = 1
        inventario = 1;
        produccion = 2;
        costos[1] = simualacionMontecarlo(inventario, produccion, demanda);
        System.out.println("*****************************************************************");
        System.out.println("Costos produccion e inventarios iniciales: " + costos[0] / 1000000 + " millones de pesos");
        System.out.println("Costos produccion e inventarios con incremento: " + costos[1]  / 1000000 + " millones de pesos");
        System.out.println("------------------------------");
        if (costos[0] < costos[1]){
            System.out.println("Se recomienda NO INCREMENTAR la producción");
        }else {
            System.out.println("Se recomienda INCREMENTAR la producción");
        }
        System.out.println("*****************************************************************");
    }
    
    
    public static int generarDemanda(){

        Random random = new Random();
        double aleatorio = random.nextDouble(0, 1);

        for (int i = 0; i < DEMANDA_DIARIA.length; i++) {
            if ( aleatorio <= DEMANDA_PROBABILIDAD[i] ){
                return DEMANDA_DIARIA[i];
            }

        }
        return 1;
    }
    public static double getCostos( int produccion, int inventario, int demanda ){
        double costos = 0;

        var mayor = Math.max(0, demanda - inventario);
        costos = (produccion * COSTO_PRODUCCION)
                + (inventario * COSTO_INVENTARIO)
                + ( mayor * COSTO_DEMANDA_NO_SATISFECHA) ;

        return costos;
    }
    public static int getProduccion( int inventario ){

        switch ( inventario ){
            case 0:
                return 2;
            case  1:
                return  1;
            case 2:
                return 1;
            default:
                System.out.println("Inventario fuera de rango");

        }

        return 0;
    }
    public static double simualacionMontecarlo( int inventario, int produccion, int demanda ){

        int sobranteDemanda = 0;
        double costos = 0;
        double acumuladorCostos = 0;

        for (int dias = 0; dias < DIAS_SIMULACION; dias++) {
            demanda = generarDemanda();
            inventario = inventario + produccion;

            if ( demanda <= inventario ){
                inventario = inventario  - demanda;
                sobranteDemanda = 0;
            }else {
                sobranteDemanda = demanda - inventario;
                inventario = 0;
            }

            produccion = getProduccion(inventario);
            costos = getCostos(produccion, inventario, sobranteDemanda);
            acumuladorCostos = acumuladorCostos + costos;
            //System.out.println("Inventario: " + inventario + ", demanda: " + demanda +  ", producción: " + produccion + ", costos: " + costos);
        }
        return acumuladorCostos;
    }

}