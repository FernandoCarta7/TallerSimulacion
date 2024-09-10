package EJERCICIO_3;

import java.util.Random;

public class Gambia {
    public static void main( String [] args ){

        final String [] CLASE_SOCIAL = {"BAJA", "MEDIA", "ALTA"};
        final double probSubirClase = 0.6;
        final double probBajarClase = 0.5;

        int tiempoSimulacion = 20;
        double impBajaMedia = 500;
        double impMediaAlta = 800;
        double acumImpuestosBajaMedia = 0;
        double acumImpuestosMediaAlta = 0;

        int personasClaseBaja = 1000000;
        int personasClaseMedia = 700000;
        int personasClaseAlta = 300000;

        Random random = new Random();
        double buenoOMalo = random.nextDouble(0,1);
        double numAleatorio = 0;
        int posicionClaseSocial = -1;

        //Simulación de Monte Carlo
        //Simulación para cada año
        for (int j = 0; j < tiempoSimulacion; j++) {
            //Simulación para cada habitante
            for (int i = 0; i < 2000000; i++) {
                //Caso en que si el comercio sea bueno
                if (buenoOMalo < 0.45) {
                    numAleatorio = random.nextDouble(0, 1);
                    posicionClaseSocial = determinarClaseSocial(numAleatorio);

                    numAleatorio = random.nextDouble(0, 1); //Para determinar probabilidad de subir de clase
                    if (numAleatorio <= probSubirClase) {
                        switch (posicionClaseSocial) {
                            case 0:
                                if (personasClaseBaja > 0) {
                                    personasClaseBaja--;
                                    personasClaseMedia++;
                                    acumImpuestosBajaMedia = acumImpuestosBajaMedia + impBajaMedia;

                                }
                                break;
                            case 1:
                                if (personasClaseMedia > 0) {
                                    personasClaseMedia--;
                                    personasClaseAlta++;
                                    acumImpuestosMediaAlta = acumImpuestosMediaAlta + impMediaAlta;

                                }
                                break;
                        }
                    }
                }
                //Caso en que si el comercio sea malo
                else {
                    numAleatorio = random.nextDouble(0, 1);
                    posicionClaseSocial = determinarClaseSocial(numAleatorio);

                    numAleatorio = random.nextDouble(0, 1); //Para determinar probabilidad de bajar de clase
                    if (numAleatorio <= probBajarClase) {
                        switch (posicionClaseSocial) {
                            case 0:
                                if ( personasClaseMedia > 0 ) {
                                    personasClaseBaja++;
                                    personasClaseMedia--;
                                }
                                break;
                            case 1:
                                if ( personasClaseAlta > 0) {
                                    personasClaseMedia++;
                                    personasClaseAlta--;
                                }
                                break;
                        }
                    }
                }
            }//FIN FOR INTERNO
            //System.out.println("Valor de varible buenoOMalo = " + buenoOMalo);
            buenoOMalo = random.nextDouble(0,1);
            //System.out.println();
            //mensajeYearBuenoMalo(buenoOMalo);

        }//FIN FOR EXTERNO

        //Impresión de resultados
        System.out.println("\n" + "Personas en la clase baja: " + personasClaseBaja );
        System.out.println("Personas en la clase media: " + personasClaseMedia );
        System.out.println("Personas en la clase alta: " +personasClaseAlta + "\n");

        System.out.println("Impuestos recaudados por aumento clase baja a media: " + acumImpuestosBajaMedia/1000000 + " en millones de dolares");
        System.out.println("Impuestos recaudados por aumento clase media a alta: " + acumImpuestosMediaAlta/1000000 + " en millones de dolares");
        System.out.println("Total de impuestos recaudados: " + (acumImpuestosBajaMedia + acumImpuestosMediaAlta)/1000000 + " en millones de dolares");
    }

    public static int determinarClaseSocial( double aleatorio ){

        if ( aleatorio <= 0.5 ){
            return 0;
        } else if ( aleatorio <= 85  ) {
            return 1;
        }else {
            return  2;
        }

    }

    public static void mensajeYearBuenoMalo(double valor){
        if(valor < 0.5 ) System.out.println("AÑO BUENO");
        else System.out.println("AÑO MALO");
    }
}
