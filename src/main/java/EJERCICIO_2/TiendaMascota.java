package EJERCICIO_2;

import java.util.Random;

public class TiendaMascota {
    public static void main(String [] args){
        //DECLARACIÓN DE VARIABLES
        int costoCroquetas = 800; //Costo por 1 kg de croqueta
        int precioVenta = 1500; //Precio de venta de 1 kg de croqueta

        double numeroAleatorio = 0; //Numero aleatorio entre 0 y 1
        double numeroAleatorio2 = 0; // Controla la probabilidad de que se pierda
        int paquetesVendidos = 0; //Paquetes de 10 kg vendidos en 1 dia
        int diasSimulados = 360; //Tiempo en dias a simular

        int contador [] = {0,0,0,0,0};
        double ganancias [] = {0,0,0,0,0};
        Random random = new Random();
        int mayor = 0;
        double mayorGanancia = 0;
        int posicion = 0;
        int posicionG = 0;

        //SIMULACIÓN DE MONTECARLO - DETERMINAR CUAL PAQUETE GENERA MEJOR GANANCIA
        for (int i = 0; i < diasSimulados; i++) {
            numeroAleatorio = random.nextDouble(0,1);
            if (numeroAleatorio <= 0.25) {
                paquetesVendidos = 5;
                contador[0] = contador[0] + 1; //Contando paquetes vendidos
            }
            else if (numeroAleatorio <= 0.40) {
                paquetesVendidos = 10;
                contador[1] = contador[1] + 1;
            }
            else if (numeroAleatorio <= 0.75) {
                paquetesVendidos = 12;
                contador[2] = contador[2] + 1;
            }
            else if (numeroAleatorio <= 0.875) {
                paquetesVendidos = 15;
                contador[3] = contador[3] + 1;
            }
            else {
                paquetesVendidos = 20;
                contador[4] = contador[4] + 1;
            }

            //System.out.println("Paquetes vendido: " + paquetesVendidos);
        }

        //Calculo de las ganacias
        for (int i = 0; i < ganancias.length; i++)
            ganancias[i] = (5*contador[i]*10*precioVenta) - (costoCroquetas*10) - (paquetesVendidos*2*800);

        //Determinar cual es el mayor tipo de paquete que se vende
        mayor = calculoMayor(contador);
        mayorGanancia = calculoMayor(ganancias);

        posicionG = returnPosicion(ganancias,mayorGanancia);

        System.out.println("La mayor cantidad de paquetes vendidos es: " + mayor + " del paquete de: " + mensaje(posicion));
        System.out.println("La mayor ganancia generada es de: " + mayorGanancia + mensaje(posicionG));


    }

        public static double calculoMayor(double[] vector){
            double mayor = vector[0];
            for (int i = 1; i < vector.length; i++) {
                if ( mayor < vector[i] ) mayor = vector[i];
            }
            return mayor;
        }
        public static int calculoMayor(int[] vector){
            int mayor = vector[0];
            for (int i = 1; i < vector.length; i++) {
                if ( mayor < vector[i] ) mayor = vector[i];
            }
            return mayor;
        }
        public static String mensaje(int posicion){
            switch ( posicion ) {
                case 0:
                    return " 5 paquetes de 10kg";
                case 1:
                    return " 10 paquetes de 10kg";
                case 2:
                    return " 12 paquetes de 10kg";
                case 3:
                    return " 15 paquetes de 10kg";
                default:
                    return " 20 paquetes de 10kg";
            }
        }
        public static int returnPosicion(double [] vector, double valor){
            for (int j = 0; j < vector.length; j++) {
                if (valor == vector[j]) return j;
            }
            return 0;
        }
        public static void  recomendarAumentoDonacion(double ganancias, int posicion){

        }
    }


