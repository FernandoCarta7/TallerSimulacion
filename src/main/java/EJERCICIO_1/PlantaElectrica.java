package EJERCICIO_1;

import java.util.Random;

public class PlantaElectrica {

    public static void main(String [] args){
        //Variables iniciales o conocidas
        final int CAPACIDAD_REPRESA = 4;
        int cantidadAguaEnRepresa = 1;
        double desperdicio = 0;
        Random random = new Random();
        double numeroRandom = random.nextDouble(0,1);
        int contador = 0; //Nos servirá para contar las veces que la represa operó con menos de 2 unidades

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 180; i++) {

                /*if (numeroRandom <= 0.15) {
                    //cantidadAguaEnRepresa =  cantidadAguaEnRepresa + 0;
                 */
                if (numeroRandom >= 0.15 && numeroRandom <= 0.50) {
                    cantidadAguaEnRepresa = cantidadAguaEnRepresa + 1;
                } else if (numeroRandom <= 0.80) {
                    cantidadAguaEnRepresa = cantidadAguaEnRepresa + 2;
                } else {
                    cantidadAguaEnRepresa = cantidadAguaEnRepresa + 3;
                }

                //Validación de capacidad de la represa y desperdicio
                if (cantidadAguaEnRepresa > CAPACIDAD_REPRESA) {
                    desperdicio = desperdicio + (cantidadAguaEnRepresa - CAPACIDAD_REPRESA);
                    cantidadAguaEnRepresa = CAPACIDAD_REPRESA - 2; //Al final de mes se botan 2 unidades, según planteamiento del problema

                } else if (cantidadAguaEnRepresa >= 2) {
                    cantidadAguaEnRepresa = cantidadAguaEnRepresa - 2; //Al final de mes se botan 2 unidades, según planteamiento del problema
                } else {
                    //cantidadAguaEnRepresa = 0;
                    contador++;
                }

                numeroRandom = random.nextDouble(0, 1);
            } //FIN DEL FOR INTERNO

                System.out.println( "\n" + "Desperdicio después de 15 años: " + desperdicio + " unidades de agua");
                System.out.println("Cantidad de veces que se tuvo que generar energía con menos de dos unidades: " + contador );

                if (desperdicio <= 5){
                    System.out.println("Se recomienda NO tomar medidas, desperdicios bajo" + "\n");
                } else System.out.println("Se recomienda aumentar la producción de la represa");
            desperdicio = 0;
            contador = 0;
        }//FIN DEL FOR EXTERNO

        }
    }

