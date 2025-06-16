import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LectorTXT {
    private final File archivo = new File("src/mi_archivo.txt");

    public ArrayList<Maquina> obtenerListadoMaquinas() {
        ArrayList<Maquina> maquinas = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(this.archivo);

            // Saltar la primera línea (puede ser un título o el total de piezas)
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Ignorar la primera línea
            }

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                String[] partes = linea.split(",");

                if (partes.length == 2) {
                    String maquina = partes[0].trim();
                    int piezas = Integer.parseInt(partes[1].trim());

                    maquinas.add(new Maquina(maquina, piezas));
                } else {
                    System.out.println("Línea con formato incorrecto: " + linea);
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("El archivo no se encontró: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir a número: " + e.getMessage());
        }

        return maquinas;
    }

    public int getPiezasTotales() {
        int piezasTotales = -1;
        try {
            Scanner scanner = new Scanner(archivo);

            // Primera linea (el total de piezas a crear)

            if (scanner.hasNextLine()) {
                String primeraLinea = scanner.nextLine().trim();
                piezasTotales = Integer.parseInt(primeraLinea);
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no se encontró: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir a número: " + e.getMessage());
        }
        return piezasTotales;
    }

}
