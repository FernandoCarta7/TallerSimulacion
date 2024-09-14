package EJERCICIO_10;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutosRentados {

    public static final double COSTO_PROMEDIO_ANUAL = 11000000;

    public static final int [] AUTOS_RENTAR_DISPONIBLES = { 0, 1, 2, 3, 4 };
    public static final double [] PROBABILIDAD_ALQUILER = { 0.10, 0.10, 0.25, 0.30, 0.25 };

    public static final int [] DIAS_ALQUILER_AUTO = { 1, 2, 3, 4 };
    public static final double [] PROBABILIDAD_ALQUILER_DIARIO = { 0.10, 0.10, 0.25, 0.30, 0.25 };

    public static final double VALOR_RENTA_DIARIA = 52000;
    public static final double COSTO_NO_DISPONIBILIDAD = 30000;
    public static final double COSTO_AUTO_OCIOSO_DIA = 7500;

    public static final int DIAS_A_SIMULAR = 3600;

    public static void main(String[] args) {

        int [][] disponibilidadAutos = new int[1][AUTOS_RENTAR_DISPONIBLES.length];
        int autosAlquilados = simularAlquilerDiario();
        boolean hayAutoDisponible = true;
        List<AutosRentados> listaAutos = new ArrayList<>();
        AutosRentados auto = new AutosRentados();
        for (int i = 0; i < AUTOS_RENTAR_DISPONIBLES.length; i++) {
            auto = new AutosRentados();
            listaAutos.add(auto);
        }


        for (int i = 0; i < disponibilidadAutos[0].length; i++) {
            disponibilidadAutos[0][i] = 0;
        }

        for (int dia = 0; dia < DIAS_A_SIMULAR; dia++) {
            if (autosAlquilados == AUTOS_RENTAR_DISPONIBLES[AUTOS_RENTAR_DISPONIBLES.length - 1]){
                hayAutoDisponible = false;
            }
            for (int i = 0; i < autosAlquilados; i++) {
                disponibilidadAutos[0][i] = simularDiasAlquilados();
            }
        }

    }
    public static int simularAlquilerDiario(){

        Random random = new Random();
        double aleatorio = random.nextDouble(0,1);
        int autosAlquilados = 0;
        double probabilidadAcumulada = 0.0;

        for (int i = 0; i < AUTOS_RENTAR_DISPONIBLES.length; i++) {
            probabilidadAcumulada = probabilidadAcumulada + PROBABILIDAD_ALQUILER[i];
            if (aleatorio < probabilidadAcumulada) {
                autosAlquilados = AUTOS_RENTAR_DISPONIBLES[i];
                return autosAlquilados;

            }
        }

        return AUTOS_RENTAR_DISPONIBLES[0];
    }

    public static int simularDiasAlquilados(){
        int diasAlquilados = 0;
        double probabilidadAcumulada = 0.0;
        Random random = new Random();
        double aleatorio = random.nextDouble(0,1);

        for (int i = 0; i < DIAS_ALQUILER_AUTO.length; i++) {
            probabilidadAcumulada = probabilidadAcumulada + PROBABILIDAD_ALQUILER_DIARIO[i];
            if ( aleatorio < probabilidadAcumulada ) {
                diasAlquilados = DIAS_ALQUILER_AUTO[i];
                return diasAlquilados;
            }
        }

        return diasAlquilados;
    }

    public static double calcularCostos( int carrosDisponibles ){
        double costos = 0.0;
        Random random = new Random();
        double aletorio = random.nextDouble( 0,1 );

        if ( aletorio < 0.5 && carrosDisponibles == 0 ) {
            costos = COSTO_NO_DISPONIBILIDAD;
        }else {
            costos = COSTO_AUTO_OCIOSO_DIA;
        }

        return costos;
    }

}

/*Puedo crear un vector que cada posición represente un carro y los dias
que está rentando, si el valor es de cero el carro está disponible, si es
diferente está ocupado y este valor deberá ir disminuyendo hasta que llegue
a cero
* */

