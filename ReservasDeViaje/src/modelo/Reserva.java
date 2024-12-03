
package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import servicio.CSVSerializable;


public abstract class Reserva implements Serializable, CSVSerializable {
    
    private int id;
    private String pasajero;
    private LocalDate fecha;

    public Reserva(int id, String pasajero, LocalDate fecha) {
        this.id = id;
        this.pasajero = pasajero;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Reserva{" + "id=" + id + ", pasajero=" + pasajero + ", fecha=" + fecha + '}';
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getPasajero() {
        return pasajero;
    }
    
    @Override
    public String toHeaderCSV() {
        return "id" + "," + "pasajero" + "," + "fecha" + ",";
    }

    @Override
    public String toCSV() {
        return id + "," + pasajero + "," + fecha + ",";
    }
    
}
