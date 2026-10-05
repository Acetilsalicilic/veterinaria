package veterinaria.interfaz.cli;

import java.util.Scanner;

public class SubMenu {
	protected Scanner sc;
	protected String formatoPrompt;
	protected String waiting;
	protected String mensajeOpcionInválida;
	
	public SubMenu(Scanner sc, String formatoPrompt, String waiting, String mensajeOpInv) {
		this.sc = sc;
		this.formatoPrompt = formatoPrompt;
		this.waiting = waiting;
		this.mensajeOpcionInválida = mensajeOpInv;
	}
	
	public void iniciar() {};
}
