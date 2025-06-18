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
			return null;
		}
	}
}
