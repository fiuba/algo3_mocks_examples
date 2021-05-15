package ar.edu.unqui.fakeobjects.clima;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClimaTest {

    @Test
    public void climaParaElDia() {
        Clima c = new Clima( new FakeClock("01/06/2020") );

        String resultado = c.pronostico( /* hoy */);

        assertEquals("Nublado", resultado);
    }
}
