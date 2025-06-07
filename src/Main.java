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

		System.out.println(optimizarMaquinas(maquinas,12));

    }

	public static ArrayList<Maquina> optimizarMaquinas(ArrayList<Maquina> maquinas, Integer piezasTotales){
		ArrayList<Maquina> solucion = new ArrayList<>();

		for (int i = 0; i < maquinas.size();i++){
			backtrack(maquinas,piezasTotales,i,new ArrayList<>(),solucion,0);
		}

		return solucion;
	}

	public static void backtrack(ArrayList<Maquina> maquinas, Integer piezasTotales, Integer indice,ArrayList<Maquina> solucionParcial, ArrayList<Maquina> solucion, Integer contadorPiezas){

		if (contadorPiezas > piezasTotales) {
			return;
		}


		if(contadorPiezas.equals(piezasTotales)){

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







}
