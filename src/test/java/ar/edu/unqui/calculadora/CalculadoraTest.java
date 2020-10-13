package ar.edu.unqui.calculadora;

import ar.edu.unqui.multiplicador.MultiplicadorProductivo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CalculadoraTest {

    @Test
    public void calculadoraDependeModuloMultiplicadorNoImplementado() {
        Multiplicador m = Mockito.mock(Multiplicador.class);
        when(m.producto(1, 2)).thenReturn(4);       // Test Stub con comportamiento fallido

        Calculadora c = new Calculadora(m);

        assertEquals(4, c.producto(1,2));

        // Agrega valor?
        // verify(m).producto(1,2);
    }

    @Test
    public void calculadoraDependeModuloMultiplicadorProductivo() {
        Multiplicador m = new MultiplicadorProductivo();

        Calculadora c = new Calculadora(m);

        assertEquals(2, c.producto(1,2));
    }

}
