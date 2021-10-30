package ar.edu.unqui.fakeobjects.clima;

import java.util.Date;

public class RelojProductivo implements Reloj {
    @Override
    public Date ahora() {
        return new Date();
    }
}
