package veterinaria.interfaz.cli;

import veterinaria.Principal;
import veterinaria.entidades.Veterinario;

import java.util.List;
import java.util.Scanner;

public class ListaVeterinario extends SubMenu {
    private final String ENCABEZADO = """
            --- Lista Veterinarios ---
            """;
    private final String OPCIONES = """
            
            --- *************** ---
            1. Buscar veterinario
            2. Registrar veterinario
            
            0. Volver
            """;

    public ListaVeterinario(Scanner sc, String formatoPrompt, String waiting, String mensajeOpInv) {
        super(sc, formatoPrompt, waiting, mensajeOpInv);
    }

    @Override
    public void iniciar() {
        while (true) {
            System.out.println(ENCABEZADO);
            imprimirTabla(Principal.veterinarios);
            System.out.print(OPCIONES);
            System.out.printf(formatoPrompt, "veterinarios");
            int opt = sc.nextInt();
            sc.nextLine();

            boolean salir = false;
            switch (opt) {
                case 0 -> salir = true;
                default -> System.out.println(mensajeOpcionInválida);
            }
            if (salir) break;
        }
    }

    private void imprimirTabla(List<Veterinario> veterinarios) {
        String[] cabeceras = {"ID", "NOMBRE", "ESPECIALIDAD"};

        if (veterinarios.isEmpty()) {
            System.out.println("\t# No se encontraron registros para mostrar.");
            return;
        }

        int max_id = cabeceras[0].length();
        for (var v : veterinarios)
            max_id = Integer.max(max_id, Integer.toString(v.getId()).length());

        int max_nombre = cabeceras[1].length();
        for (var v : veterinarios)
            max_nombre = Integer.max(max_nombre, v.getNombre().length());

        int max_especialidad = cabeceras[2].length();
        for (var v : veterinarios)
            max_especialidad = Integer.max(max_especialidad, v.getEspecialidad().length());

        var formato = "|%" +
                max_id + "s|%" +
                max_nombre + "s|%" +
                max_especialidad + "s|\n";

        System.out.printf(formato, (Object[]) cabeceras);

        for (var v : veterinarios) {
            System.out.printf(formato,
                    v.getId(),
                    v.getNombre(),
                    v.getEspecialidad());
        }

        System.out.println("\t# Se encontraron "+veterinarios.size()+" registros.");
    }
}
