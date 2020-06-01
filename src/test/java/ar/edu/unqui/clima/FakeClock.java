package ar.edu.unqui.clima;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FakeClock implements Reloj {
    private String fechaHoy;

    public FakeClock(String fechaHoy) {
        this.fechaHoy = fechaHoy;
    }

    @Override
    public Date ahora() {
        return FakeClock.crearFecha(fechaHoy);
    }

    private static Date crearFecha(String s) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("??");
        }
    }
}
