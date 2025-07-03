import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SolucionPorBacktracking {
    private ArrayList<Maquina> mejorSolucion;
    private int contadorEstados;

    public SolucionPorBacktracking(){
        this.mejorSolucion = new ArrayList<>();
        this.contadorEstados = 0;
    }

    /**
     * Esta función recibe una lista de máquinas, y una cantidad de piezas a elaborar.
     *
     * Usando un índice, para cada máquina, exploramos todas sus combinaciones con el resto.
     * Cuando nuestro índice llega al final de la lista de entrada, es un estado final, porque no hay más máquinas
     * para combinar.
     *
     * En cada rama, llevamos la cuenta de cuántas piezas fabricamos. Si dicha cantidad es igual al objetivo,
     * es una posible solución.
     *
     * solo generamos nuevos estados mientras no nos pasemos de la cantidad de piezas totales
     */
    public ArrayList<Maquina> optimizar(ArrayList<Maquina> maquinas, int piezasTotales){

        this.contadorEstados = 0;
        backtrack(maquinas,piezasTotales,0,new ArrayList<>(),0);

        if(this.mejorSolucion.isEmpty()) {
            System.out.println("No se halló solución.");
            System.out.println("Cantidad de piezas producidas: 0");
        } else {
            System.out.println("Solución: " + this.mejorSolucion);
            System.out.println("Cantidad de piezas producidas: " + piezasTotales);
        }
        System.out.println("Máquinas puestas en funcionamiento: " + this.mejorSolucion.size());
        System.out.println("Estados generados: " + contadorEstados);

        return this.mejorSolucion;
    }

    public void backtrack(ArrayList<Maquina> maquinas, int piezasTotales, int indice, ArrayList<Maquina> solucionParcial,int contadorPiezas){



        if(contadorPiezas == piezasTotales){

            if (this.mejorSolucion.isEmpty() || solucionParcial.size() < this.mejorSolucion.size()){
                this.mejorSolucion.clear();
                this.mejorSolucion.addAll(solucionParcial);
            }

            return;

        } else{
            for(int i=indice; i < maquinas.size(); i++){
                Maquina maquina = maquinas.get(i);
                solucionParcial.add(maquina);
                //System.out.println(solucionParcial);
                // Luego de añadir un elemento a la solución parcial, aumentamos la cantidad de estados generados.

                if (contadorPiezas <= piezasTotales) {
                    this.contadorEstados++;
                    backtrack(maquinas,piezasTotales,i,solucionParcial,contadorPiezas+maquinas.get(i).getPiezas());
                }
                solucionParcial.remove(solucionParcial.size()-1);
            }
        }
    }
}
