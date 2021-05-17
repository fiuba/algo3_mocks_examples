package ar.edu.unqui.stubs.calculadora;

public class Calculadora {

    private Multiplicador m;

    public Calculadora(Multiplicador m) {

        this.m = m;
    }

    public int producto(int n1, int n2) {


        return this.m.producto(n1, n2);
    }
}
