package ar.edu.unqui.multiplicador;

import ar.edu.unqui.stubs.calculadora.Multiplicador;

public class MultiplicadorProductivo implements Multiplicador {
    @Override
    public Integer producto(int n1, int n2) {

        return n1 * n2;
    }
}
