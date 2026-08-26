public interface ICalculable {
    double calcularCosto(double peso);
}

public interface IRastreable {
    String rastrearPaqueteSatelital();
}

public interface IExportable {
    void generarReporteAduana();
}

// OCA solo calcula costos: no implementa IRastreable ni IExportable
public class CorreoLocalOCA implements ICalculable {

    @Override
    public double calcularCosto(double peso) {
        return peso * 15.0;
    }
}

// FedEx puede hacer las tres cosas: implementa las tres interfaces
public class CorreoInternacionalFedEx implements ICalculable, IRastreable, IExportable {

    @Override
    public double calcularCosto(double peso) {
        return peso * 45.0;
    }

    @Override
    public String rastrearPaqueteSatelital() {
        return "Paquete localizado en tránsito internacional";
    }

    @Override
    public void generarReporteAduana() {
        System.out.println("Generando reporte de aduana para envío internacional...");
    }
}

// Andreani calcula costos y rastrea, pero no hace aduana
public class CorreoRegionalAndreani implements ICalculable, IRastreable {

    @Override
    public double calcularCosto(double peso) {
        return peso * 25.0;
    }

    @Override
    public String rastrearPaqueteSatelital() {
        return "Paquete rastreado por red terrestre de Andreani";
    }
}

public class Main {

    public static void informarCosto(ICalculable correo, double peso) {
        System.out.println("Costo del envío: $" + correo.calcularCosto(peso));
    }

    public static void informarRastreo(IRastreable correo) {
        System.out.println(correo.rastrearPaqueteSatelital());
    }

    public static void main(String[] args) {
        CorreoLocalOCA oca = new CorreoLocalOCA();
        CorreoInternacionalFedEx fedex = new CorreoInternacionalFedEx();
        CorreoRegionalAndreani andreani = new CorreoRegionalAndreani();

        informarCosto(oca, 10);       // OK: OCA implementa ICalculable
        informarCosto(fedex, 10);     // OK: FedEx también
        informarCosto(andreani, 10);  // OK: Andreani también

        informarRastreo(fedex);       // OK: FedEx implementa IRastreable
        informarRastreo(andreani);    // OK: Andreani implementa IRastreable

    }
}
