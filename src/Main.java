import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
		LectorTXT lector = new LectorTXT();
		ArrayList<Maquina> maquinas = lector.obtenerListadoMaquinas();

		System.out.println("Backtrack");
		SolucionPorBacktracking backtrack = new SolucionPorBacktracking();
		backtrack.optimizar(maquinas, lector.getPiezasTotales());

		System.out.println("\n");

		System.out.println("Greedy");
		SolucionPorGreedy greedy = new SolucionPorGreedy();
		greedy.optimizar(maquinas, lector.getPiezasTotales());
    }
}
