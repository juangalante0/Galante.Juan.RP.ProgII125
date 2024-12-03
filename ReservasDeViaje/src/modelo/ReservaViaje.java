
package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import servicio.CSVSerializable;


public class ReservaViaje extends Reserva implements Serializable, Comparable<ReservaViaje>, CSVSerializable {
    
    private String destino;
    private TipoTransporte transporte;

    public ReservaViaje(int id, String pasajero, LocalDate fecha, String destino, TipoTransporte transporte) {
        super(id, pasajero, fecha);
        this.destino = destino;
        this.transporte = transporte;
    }

    public TipoTransporte getTransporte() {
        return transporte;
    }

    @Override
    public int compareTo(ReservaViaje o) {
        return this.getFecha().compareTo(o.getFecha());  
    }

    @Override
    public String toHeaderCSV() {
        return super.toHeaderCSV() + "destino" + "," + "transporte";
    }

    @Override
    public String toCSV() {
        return super.toCSV() + destino + "," + transporte;
    }
    
    public static ReservaViaje fromCSV(String reservaCSV){
        ReservaViaje toReturn = null;
        String[] values = reservaCSV.split(",");
        if(values.length == 5){
            toReturn = new ReservaViaje(Integer.parseInt(values[0]), values[1], LocalDate.parse(values[2]), values[3], TipoTransporte.valueOf(values[4]));
        }
        return toReturn;
    }
    
    
    
    
    
}
