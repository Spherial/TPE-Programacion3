
public class Maquina {
	private String nombre;
	private int piezas;
	
	public Maquina(String nombre, int piezas) {
		this.nombre = nombre;
		this.piezas = piezas;
	
	}


	@Override
	public String toString() {
		return "Maquina: " + nombre + ", Piezas producidas: " + piezas;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public int getPiezas() {
		return this.piezas;
	}
}
