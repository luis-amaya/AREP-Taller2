package edu.escuelaing.arep.networking.webapp;

import edu.escuelaing.arep.networking.springplus.Component;
import edu.escuelaing.arep.networking.springplus.Service;

@Component
public class Cuadrado {
    @Service(uri = "/cuadrado")
    public static Double cuadrado() {
        return 2.0 * 2.0;
    }

    @Service(uri = "/cubo")
    public static Double cubo() {
        return 2.0 * 2.0 * 2.0;
    }
}
