package model;

import java.util.List;

public class Inventario {
    private List<String> lista;

    public Inventario(List<String> lista) {

        this.lista = lista;
    }

    public void agregar(String item) {
        lista.add(item);
    }
}
