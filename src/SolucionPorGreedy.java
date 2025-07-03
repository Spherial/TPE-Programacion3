import java.util.ArrayList;

public class SolucionPorGreedy {
    private int piezasProducidas;

    public SolucionPorGreedy(){
        this.piezasProducidas = 0;
    }

    /**
     * Descripción del algoritmo:
     * 1) La función recibe un ArrayList de máquinas, la cual ordena en base a la cantidad de piezas que producen
     * (de mayor a menor).
     * 2) Mientras el ArrayList de máquinas no esté vacío y no hayamos encontrado una solución, se realizan los
     * siguientes pasos:
     *    a) Seleccionamos un candidato:
     *       - El candidato será siempre la primera máquina del ArrayList, ya que es la que más piezas produce, y por
     *       ende, aquella que nos acerque más rápidamente a nuestro objetivo de fabricación.
     *    b) Evaluamos si el candidato es factible:
     *       - Una máquina es factible si, al sumar la cantidad de piezas que produce a las piezas acumuladas hasta el
     *       momento, no se supera el total requerido.
     *       - Si el candidato es factible, lo añadimos al ArrayList "solución".
     *       - Si no es factible, eliminamos la primera máquina (actual candidato). No se consideran sus piezas.
     * 3) Finalización:
     *    - Si encontramos una solución (un conjunto de máquinas que producen la cantidad deseada de piezas), mostramos
     *    las métricas por consola y devolvemos la solución.
     *    - Si no encontramos ninguna solución posible, devolvemos null.
     */

    public ArrayList<Maquina> optimizar(ArrayList<Maquina> maquinas, int piezasTotales) {
        maquinas.sort(new ComparadorPorPiezas());
        ArrayList<Maquina> solucion = new ArrayList<>();
        this.piezasProducidas = 0;

        // Variables para métricas
        int metricaCandidatos = 0;
        // ------------------------------

        while(!maquinas.isEmpty() && !solucionHallada(solucion, piezasTotales)) {
            Maquina candidato = maquinas.getFirst();
            // Se cuenta el nuevo candidato considerado
            metricaCandidatos++;

            this.piezasProducidas+=candidato.getPiezas();

            if(factible(solucion, candidato, piezasTotales)) {
                solucion.add(candidato);
            } else {
                this.piezasProducidas-=candidato.getPiezas();
                maquinas.removeFirst();
            }
        }
        if(solucionHallada(solucion, piezasTotales)) {
            System.out.println("Solución: " + solucion);
            System.out.println("Cantidad de piezas producidas: " + piezasTotales);
            System.out.println("Máquinas puestas en funcionamiento: " + solucion.size());
            System.out.println("Candidatos considerados: " + metricaCandidatos);
            return solucion;
        } else {
            System.out.println("No se halló solución");
            System.out.println("Cantidad de piezas producidas: 0");
            System.out.println("Máquinas puestas en funcionamiento: " + solucion.size());
            System.out.println("Candidatos considerados: " + metricaCandidatos);
            return null;
        }
    }

    // Funciones utilizadas en greedy

    public boolean solucionHallada(ArrayList<Maquina> solucion, int piezasTotales) {
        return this.piezasProducidas == piezasTotales;
    }

    public boolean factible(ArrayList<Maquina> solucion, Maquina candidato, int piezasTotales) {
        return this.piezasProducidas <= piezasTotales;
    }

}
