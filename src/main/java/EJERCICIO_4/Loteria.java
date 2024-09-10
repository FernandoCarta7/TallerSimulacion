package EJERCICIO_4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Loteria {
    public static void main(String [] args){
        int [] posiblePremio = {1000,5000};
        final int CANTIDAD_SIMULACIONES = 100000;
        List<Double> ganancias = new ArrayList<>();
        double gananciaTotal = 0;
        double gananciaPromedio = 0;

        for (int i = 0; i < CANTIDAD_SIMULACIONES; i++) {
            double ganancia = 0;
            ganancia = simularLoteria(posiblePremio);
            if (ganancia != 0){
                ganancias.add(ganancia);
                gananciaTotal = gananciaTotal + ganancia;
            }
        }

        gananciaPromedio = gananciaTotal / CANTIDAD_SIMULACIONES;
        System.out.println("Lo minimo que debe vender cada tarjeta es de: " + gananciaPromedio);
    }

    public static int simularLoteria( int [] posiblePremio ){
        Random random = new Random();
        int [] seleccion = {0,0,0};
        for (int i = 0; i < seleccion.length; i++) {
            seleccion[i] = posiblePremio[random.nextInt(0,2)];
        }
        if ((seleccion[0] == seleccion[1]) && (seleccion[1] == seleccion[2])){
            return seleccion[0];
        }else {
            return 0;
        }

    }
}
