package EJERCICIO_10;

public class AutoRentado {
    boolean estaRentado;
    int diasRentado;

    public AutoRentado(boolean estaRentado, int diasRentado) {
        this.estaRentado = estaRentado;
        this.diasRentado = diasRentado;
    }

    public AutoRentado() {
        this.estaRentado = false;
        this.diasRentado = 0;
    }

    public boolean isEstaRentado() {
        return estaRentado;
    }

    public void setEstaRentado(boolean estaRentado) {
        this.estaRentado = estaRentado;
    }

    public int getDiasRentado() {
        return diasRentado;
    }

    public void setDiasRentado(int diasRentado) {
        this.diasRentado = diasRentado;
    }
}
