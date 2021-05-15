package ar.edu.unqui.mockobjects.logging;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class LogsForServiceTest {

    @Test
    public void VerificarQueLosLogsDelServicioSeEnvian() {
        Logger logger = Mockito.mock(Logger.class);

        MyService c = new MyService(logger);

        c.doTask();

        verify(logger).Info("Task done successfully!");

    }
}
