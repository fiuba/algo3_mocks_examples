package ar.edu.unqui.mockobjects.logging;

public class MyService {
    private Logger logger;

    public MyService(Logger logger) {

        this.logger = logger;
    }

    public void doTask() {
        this.logger.Info("Task done successfully!");
    }
}
