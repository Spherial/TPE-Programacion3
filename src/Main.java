import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
		LectorTXT lector = new LectorTXT();
		ArrayList<Maquina> maquinas = lector.obtenerListadoMaquinas();
    	maquinas.sort(new ComparadorPorPiezas());

		System.out.println("Backtrack");
		optimizarMaquinasBacktrack(maquinas, lector.getPiezasTotales());

		System.out.println("\n");

		System.out.println("Greedy");
		optimizarMaquinasGreedy(maquinas, lector.getPiezasTotales());
    }

	/**
	 * Esta función recibe una lista de máquinas, y una cantidad de piezas a elaborar.
	 *
	 * Usando un índice, recorremos nuestra lista de máquinas, y para cada una de ellas, generamos tantas ramas como
	 * máquinas haya disponibles para combinar.
	 *
	 * Para cada máquina, exploramos todas sus combinaciones con el resto.
	 * Cuando nuestro índice llega al final de la lista de entrada, es un estado final, porque no hay más máquinas
	 * para combinar.
	 *
	 * En cada rama, llevamos la cuenta de cuántas piezas fabricamos. Si dicha cantidad es igual al objetivo,
	 * es una posible solución.
	 *
	 * Si la cantidad de piezas elaboradas se pasa del objetivo, podemos podar, ya que seguir agregando máquinas
	 * seguirá aumentando dicha cantidad.
	 */


	public static ArrayList<Maquina> optimizarMaquinasBacktrack(ArrayList<Maquina> maquinas, int piezasTotales){
		ArrayList<Maquina> solucion = new ArrayList<>();

		// Variables para métricas
		AtomicInteger contadorEstados = new AtomicInteger(0); // Contador global de estados
		// ---------------------------------------------------------

		for (int i = 0; i < maquinas.size();i++){
			backtrack(maquinas,piezasTotales,i,new ArrayList<>(),solucion,0, contadorEstados);
		}

		System.out.println("Solución: " + solucion);
		System.out.println("Máquinas puestas en funcionamiento: " + solucion.size());
		System.out.println("Estados generados: " + contadorEstados);

		return solucion;
	}

	public static void backtrack(ArrayList<Maquina> maquinas, int piezasTotales, int indice, ArrayList<Maquina> solucionParcial, ArrayList<Maquina> solucion, int contadorPiezas, AtomicInteger contadorEstados){

		if (contadorPiezas > piezasTotales) {
			return;
		}

		if(contadorPiezas == piezasTotales){

			if (solucion.isEmpty() || solucionParcial.size() < solucion.size()){
				solucion.clear();
				solucion.addAll(solucionParcial);
			}

			return;

		} else{
			for(int i=indice; i < maquinas.size(); i++){
				Maquina maquina = maquinas.get(i);
				solucionParcial.add(maquina);
				//System.out.println(solucionParcial);
				// Luego de añadir un elemento a la solución parcial, aumentamos la cantidad de estados generados.
				contadorEstados.incrementAndGet();
				backtrack(maquinas,piezasTotales,i,solucionParcial,solucion,contadorPiezas+maquinas.get(i).getPiezas(), contadorEstados);
				solucionParcial.remove(solucionParcial.size()-1);
			}
		}
	}


	/**
	 * Descripción del algoritmo:
	 * 1) La función recibe un ArrayList de máquinas previamente ordenadas en base a la cantidad de piezas que producen
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
	 *       - Si no es factible, eliminamos la primera máquina (actual candidato).
	 * 3) Finalización:
	 *    - Si encontramos una solución (un conjunto de máquinas que producen la cantidad deseada de piezas), mostramos
	 *    las métricas por consola y devolvemos la solución.
	 *    - Si no encontramos ninguna solución posible, devolvemos null.
	 */

	public static ArrayList<Maquina> optimizarMaquinasGreedy(ArrayList<Maquina> maquinas, int piezasTotales) {
		ArrayList<Maquina> solucion = new ArrayList<>();

		// Variables para métricas
		int metricaCandidatos = 0;
		// ------------------------------

		while(!maquinas.isEmpty() && !solucionHallada(solucion, piezasTotales)) {
			Maquina candidato = maquinas.getFirst();
			// Se cuenta el nuevo candidato considerado
			metricaCandidatos++;
			if(factible(solucion, candidato, piezasTotales)) {
				solucion.add(candidato);
			} else {
				maquinas.removeFirst();
			}
		}
		if(solucionHallada(solucion, piezasTotales)) {
			System.out.println("Solución: " + solucion);
			System.out.println("Máquinas puestas en funcionamiento: " + solucion.size());
			System.out.println("Candidatos considerados: " + metricaCandidatos);
			return solucion;
		} else {
			System.out.println("No se halló solución");
			return null;
		}
	}

	// Funciones utilizadas en greedy

	public static int calcularPiezas(ArrayList<Maquina> solucion) {
		int suma = 0;
		for(Maquina m : solucion) {
			suma+= m.getPiezas();
		}
		return suma;
	}

	public static boolean solucionHallada(ArrayList<Maquina> solucion, int piezasTotales) {
		int piezas = calcularPiezas(solucion);
		return piezas == piezasTotales;
	}

	public static boolean factible(ArrayList<Maquina> solucion, Maquina candidato, int piezasTotales) {
		int piezas = calcularPiezas(solucion) + candidato.getPiezas();
		return piezas <= piezasTotales;
	}
}
