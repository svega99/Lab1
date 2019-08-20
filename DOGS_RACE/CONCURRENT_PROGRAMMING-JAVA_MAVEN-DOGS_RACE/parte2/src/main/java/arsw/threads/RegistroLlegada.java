package arsw.threads;

import java.util.concurrent.atomic.AtomicLong;

public class RegistroLlegada {

	
	
	private final AtomicLong ultimaPosicionAlcanzada = new AtomicLong(0);

	private String ganador=null;
	
	public String getGanador() {
		return ganador;
	}

	public void setGanador(String ganador) {
		this.ganador = ganador;
	}
	
	public long aumentarUltimaPosicionAlcanzada() {
		return ultimaPosicionAlcanzada.incrementAndGet();
	}

	public long getUltimaPosicionAlcanzada() {
		return ultimaPosicionAlcanzada.get();
	}

	
	
}
