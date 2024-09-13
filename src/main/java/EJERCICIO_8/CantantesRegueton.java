package EJERCICIO_8;

import java.util.Random;

public class CantantesRegueton {
    public static final String [] ETAPAS_CARRERA = { "Descubrimiento artista", "Fama", "Fama decaida", "Fama perdida" };
    public static final double [] PROBABILIDAD_SUBIR_DESCUBRIMIENTO = { 0.3, 1.0 };
    //0.7 Se matiene en esta etapa, 0.3 sube a fama - Probabilidades acumuladas
    public static final double [] PROBABILIDAD_MANTENER_FAMA = { 0.5, 0.9, 1.0 }; //Probabilidades acumuladas
    /*
    * Probabilidad de mantenerse = 0.5
    * Probabilidad de decaer = 0.4
    * Probabilidad de perder la fama = 0.1

     * */
    public static final double [] PROBAILIDAD_FAMA_DECAIDA = { 0.2, 1.0 };
    /*
     *  Probabilidad de perderla y desistir = 0.8
     *  Probabilidad de recuperar la fama = 0.2
     * */
    public static final int TIEMPO_SIMULACION = 20;
    public static final double [] GANANCIAS_CADA_CANTANTE = {45000, 100000};

    public static void main(String[] args) {
        int cantantesEtapaDescubrimiento = 20;
        int cantantesFamosos = 5;
        int cantantesFamososDecaidos = 5;
        int totalCantantes = cantantesEtapaDescubrimiento + cantantesFamosos + cantantesFamososDecaidos;

        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");

            simulacionCarrera(
                    cantantesEtapaDescubrimiento,
                    cantantesFamosos,
                    cantantesFamososDecaidos,
                    0,
                    totalCantantes
            );

        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
    }

    //SIMULACIÓN MONTECARLO
    public static void simulacionCarrera(int cantanteDescubrimiento,
                                         int cantanteFamoso,
                                         int famososDecaidos,
                                         int famososDesistidos,
                                         int totalCantantesIteracion){
        Random random = new Random();
        int [] cantidadCantantes = {0,0,0,0};
        //Posición 0: Cantidad cantantes en descubrimiento
        //Posición 1: Cantidad cantantes famosos
        //Posición 2: Cantidad cantantes famosos decaidos
        //Posición 3: Cantidad cantantes famosos desistidos

        //Aleatorio para determinar si sube a famoso o se mantiene en descrubrimiento
        double descubrimientoSubirFamoso = random.nextDouble(0 , 1);

        //Aleatorio para determinar si mantiene fama o no
        double mantenerFama = random.nextDouble(0,1);

        //Aleatorio para determinar si el famoso decaido recupera su fama
        double recuperarFama = random.nextDouble(0,1);

        //Variable TIEMPO, que nos permitirá realizar el promedio de tiempo de carrera de un cantante
        int tiempo = 0;

        double ganancias = 0;

        for (int year = 1; year < TIEMPO_SIMULACION + 1; year++) {
            for (int i = 0; i < totalCantantesIteracion; i++) {

            //Cantante en descubrimiento se vuelve famoso
            if (descubrimientoSubirFamoso <= PROBABILIDAD_SUBIR_DESCUBRIMIENTO[0] && cantanteDescubrimiento > 0) {
                //Entonces sube a famoso
                cantanteDescubrimiento = cantanteDescubrimiento - 1;
                cantanteFamoso = cantanteFamoso + 1;

            }

            //Validación de si mantiene la fama, decae ó pierde la fama y desiste
            if (mantenerFama > PROBABILIDAD_MANTENER_FAMA[0] && cantanteFamoso > 0) {
                cantanteFamoso = cantanteFamoso - 1;
                if (mantenerFama < PROBABILIDAD_MANTENER_FAMA[1]) {
                    //DECAE
                    famososDecaidos = famososDecaidos + 1;
                } else {
                    //PIERDE FAMA Y DESISTE
                    famososDesistidos = famososDesistidos + 1;
                    tiempo = tiempo + year;
                }
            }

            //Validación si el cantante está decaido vuelva a ser famoso
            if (famososDecaidos > 0) {
                famososDecaidos = famososDecaidos - 1;
                if (recuperarFama < PROBAILIDAD_FAMA_DECAIDA[0]) {
                    cantanteFamoso = cantanteFamoso + 1;
                } else {
                    famososDesistidos = famososDesistidos + 1;
                    tiempo = tiempo + year;
                }
            }

            ganancias = ganancias
                    + (cantanteDescubrimiento + famososDecaidos) * GANANCIAS_CADA_CANTANTE[0]
                    + cantanteFamoso * GANANCIAS_CADA_CANTANTE[1];

            descubrimientoSubirFamoso = random.nextDouble(0, 1);
            mantenerFama = random.nextDouble(0, 1);
            recuperarFama = random.nextDouble(0, 1);
            }
        }
        int totalCantantes = cantanteDescubrimiento + cantanteFamoso + famososDecaidos + famososDesistidos;

        cantidadCantantes[0] = cantanteDescubrimiento;
        cantidadCantantes[1] = cantanteFamoso;
        cantidadCantantes[2] = famososDecaidos;
        cantidadCantantes[3] = famososDesistidos;

        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("a)\tLuego de " + TIEMPO_SIMULACION + " años, " + cantidadCantantes[3] + " artistas dejaron de cantar" );
        System.out.println("b)\tTiempo promedio de cantante de regueton: " + tiempo/famososDesistidos + " años");
        System.out.println("c)\tGanancias promedio = $" + ganancias / totalCantantes + " por cantante en: " + TIEMPO_SIMULACION + " años" );

        System.out.println("-------------------------------------------------------------------------------------------");

    }

}
