package edu.escuelaing.arep.networking.webapp;

import edu.escuelaing.arep.networking.springplus.Service;

public class Cuadrado {
    @Service(uri = "/cuadrado")
    public Double cuadrado() {
        return 2.0 * 2.0;
    }
}
