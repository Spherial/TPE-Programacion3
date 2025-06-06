import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LectorTXT {

    private Map<String, Integer> piezasPorMaquina;
    private int piezasTotales;

    public LectorTXT(){
        this.piezasTotales = 0;
        this.piezasPorMaquina = new HashMap<>();
    }


    public Map<String, Integer> getPiezasPorMaquina() {
        return this.piezasPorMaquina;
    }

    public int getPiezasTotales() {
        return this.piezasTotales;
    }

    public void leerArchivoTXT(){
        try {
            File archivo = new File("mi_archivo.txt");
            Scanner scanner = new Scanner(archivo);

            // Primera linea (el total de piezas a crear)

            if (scanner.hasNextLine()) {
                String primeraLinea = scanner.nextLine().trim();
                this.piezasTotales = Integer.parseInt(primeraLinea);
            }

            // El resto de las lineas (maquinas con sus piezas)

            int sumaMaquinas = 0;

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                String[] partes = linea.split(",");

                if (partes.length == 2) {
                    String maquina = partes[0].trim();
                    int piezas = Integer.parseInt(partes[1].trim());

                    this.piezasPorMaquina.put(maquina, piezas);
                    sumaMaquinas += piezas;
                }
            }

            scanner.close();


        } catch (FileNotFoundException e) {
            System.out.println("El archivo no se encontró: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir a número: " + e.getMessage());
        }
    }

}
