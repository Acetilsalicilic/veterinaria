package veterinaria.interfaz.cli;

import java.util.Scanner;

public abstract class SubMenu {
	protected Scanner sc;
	protected String formatoPrompt;
	protected String waiting;
	
	public SubMenu(Scanner sc, String formatoPrompt, String waiting) {
		this.sc = sc;
		this.formatoPrompt = formatoPrompt;
		this.waiting = waiting;
	}
	
	public abstract void iniciar();
}
