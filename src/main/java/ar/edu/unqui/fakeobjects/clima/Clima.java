package ar.edu.unqui.fakeobjects.clima;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Clima {
    private Reloj reloj;

    private HashMap<Date, String> pronosticoPorDia;

    public Clima(Reloj reloj) {

        this.reloj = reloj;
        this.pronosticoPorDia = new HashMap<Date, String>() {{
                put(Clima.crearFecha("01/06/2020"), "Nublado");
        }};
    }

    public String pronostico() {
        Date fechaConsulta = reloj.ahora();

        if (pronosticoPorDia.containsKey(fechaConsulta)) {
            return pronosticoPorDia.get(fechaConsulta);
        }

        return "Desconocido";
    }

    private static Date crearFecha(String s) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("??");
        }
    }
}
