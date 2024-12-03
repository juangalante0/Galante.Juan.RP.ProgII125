
package servicio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public class GestorReservas <T extends CSVSerializable & Comparable> implements Gestionable<T>  {
    
    List<T> items = new ArrayList<>();

    @Override
    public void agregar(T item) {
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser null.");
        }
        items.add(item);
    }

    @Override
    public T eliminar(int indice) {
        validarIndice(indice);
        return items.remove(indice);
    }

    @Override
    public T obtener(int indice) {
        validarIndice(indice);
        return items.get(indice);
    }
    
    private void validarIndice(int indice){
        if (indice < 0 || indice >= items.size()){
            throw new IndexOutOfBoundsException("Incide invalido");
        }
    }

    @Override
    public void limpiarElementos() {
        items.clear();
    }

    @Override
    public void ordenar() {
        items.sort((Comparator<T>) Comparator.naturalOrder());
    }

    @Override
    public void ordenar(Comparator<T> comparator) {
        items.sort(comparator);
    }

    @Override
    public List<T> filtrar(Predicate<T> predicate) {
        List<T> toReturn = new ArrayList<>();
        for(T item : items){
            if(predicate.test(item)){
                toReturn.add(item);
            }
        }
        return toReturn;
    }

    @Override
    public void guardarEnBinario(String path) throws IOException {
        
        validarPath(path);
        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(path));
        salida.writeObject(items);
        salida.close();
    }
    

    @Override
    public void cargarDesdeBinario(String path) throws IOException, ClassNotFoundException {
        limpiarElementos();
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(path));
        items = (List<T>) entrada.readObject();
        entrada.close();
    }

    @Override
    public void guardarEnCSV(String path) throws IOException {
        
        validarPath(path);
        BufferedWriter bw  = new BufferedWriter(new FileWriter(path));
        bw.write(items.get(0).toHeaderCSV() + "\n");
        for(T item : items){
            bw.write(item.toCSV() + "\n");
        }
        bw.close();
    }
    
    private void validarPath(String path){
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("El path no puede ser null o vacío.");
        }
    }

    @Override
    public void cargarDesdeCSV(String path, Function<String, T> transformadora) throws IOException {
        limpiarElementos();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String linea = br.readLine();
            while ((linea = br.readLine()) != null){
                items.add(transformadora.apply(linea));
            }
            br.close();
        } catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void mostrarTodos() {
        for(T item : items){
            System.out.println(item);
        }
    }
    
    
    
}
