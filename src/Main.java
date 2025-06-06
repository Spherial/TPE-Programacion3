import java.util.ArrayList;

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
    	
    	
    	
    }
}
