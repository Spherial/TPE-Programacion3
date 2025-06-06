import java.util.Comparator;

public class ComparadorPorPiezas implements Comparator<Maquina>{

	@Override
	public int compare(Maquina o1, Maquina o2) {
		return o2.getPiezas() - o1.getPiezas();
	}

}
