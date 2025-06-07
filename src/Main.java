import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
    	Maquina m1 = new Maquina("M1",7);
    	Maquina m2 = new Maquina("M2",3);
    	Maquina m3 = new Maquina("M3",4);
    	Maquina m4 = new Maquina("M4",1);
    	
    	ArrayList<Maquina> maquinas = new ArrayList<>();
    	maquinas.add(m1);
    	maquinas.add(m2);
    	maquinas.add(m3);
    	maquinas.add(m4);
    	
    	maquinas.sort(new ComparadorPorPiezas());
    	
    	System.out.println(maquinas);
    	for(Maquina m : maquinas) {
    		System.out.println(m.getNombre() + " - " + m.getPiezas());
    	}

		System.out.println(optimizarMaquinasGreedy(maquinas,12));

    }

	public static ArrayList<Maquina> optimizarMaquinasBacktrack(ArrayList<Maquina> maquinas, int piezasTotales){
		ArrayList<Maquina> solucion = new ArrayList<>();

		for (int i = 0; i < maquinas.size();i++){
			backtrack(maquinas,piezasTotales,i,new ArrayList<>(),solucion,0);
		}

		return solucion;
	}

	public static void backtrack(ArrayList<Maquina> maquinas, int piezasTotales, int indice,ArrayList<Maquina> solucionParcial, ArrayList<Maquina> solucion, int contadorPiezas){

		if (contadorPiezas > piezasTotales) {
			return;
		}


		if(contadorPiezas == piezasTotales){

			if (solucion.isEmpty() || solucionParcial.size() < solucion.size()){
				solucion.clear();
				solucion.addAll(solucionParcial);
			}
			return;
		}
		else{
			for(int i=indice; i < maquinas.size(); i++){
				Maquina maquina = maquinas.get(i);
				solucionParcial.add(maquina);
				backtrack(maquinas,piezasTotales,i,solucionParcial,solucion,contadorPiezas+maquinas.get(i).getPiezas());
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
		while(!maquinas.isEmpty() && !solucionHallada(solucion, piezasTotales)) {
			Maquina candidato = maquinas.getFirst();
			if(factible(solucion, candidato, piezasTotales)) {
				solucion.add(candidato);
			} else {
				maquinas.removeFirst();
			}
		}
		if(solucionHallada(solucion, piezasTotales)) {
			return solucion;
		} else {
			return null;
		}
	}
}
