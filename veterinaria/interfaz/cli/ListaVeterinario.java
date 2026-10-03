package veterinaria.interfaz.cli;

import veterinaria.Principal;
import veterinaria.entidades.Veterinario;
import veterinaria.utiles.Campo;

import java.util.LinkedList;
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
    private final String MENU_ACCIONES_VETERINARIO = """
            1. Ver detalle
            2. Modificar veterinario
            3. Eliminar veterinario
            
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
                case 1 -> acciónBuscarVeterinario(Principal.veterinarios);
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

    private void acciónBuscarVeterinario(List<Veterinario> veterinarios) {
        for (;;) {
            System.out.println("Ingrese el término de búsqueda (-1 para continuar):");
            System.out.printf(formatoPrompt, "buscar dueño");
            String busqueda = sc.nextLine();

            if (busqueda.equals("-1"))
                break;

            // TODO hacer que HerramientasDueño haga este filtrado
            var resultado = veterinarios.stream()
                    .filter(d -> d.getNombre().toLowerCase().contains(busqueda.toLowerCase()))
                    .toList();

            imprimirTabla(resultado);
        }

        System.out.println("Ingrese el ID del veterinario (-1 para cancelar):");
        System.out.printf(formatoPrompt, "id veterinario");
        int id = sc.nextInt();
        sc.nextLine();

        if (id == -1)
            return;

        Veterinario veterinario = null;
        for (var v : veterinarios) {
            System.out.println("* id: " + v.getId());
            if (v.getId() == id) {
                veterinario = v;
                break;
            }
        }

        if (veterinario == null) {
            System.out.println("El ID ingresado no existe.");
            return;
        }

        System.out.println("Elija una acción:");
        System.out.print(MENU_ACCIONES_VETERINARIO);
        System.out.printf(formatoPrompt, "id veterinario = "+id);

        int opt = sc.nextInt();
        sc.nextLine();

        boolean salir = false;
        switch (opt) {
            case 0 -> salir = true;
            case 1 -> acciónMostrarDetalleVeterinario(veterinario);
            case 3 -> {
                Principal.veterinarios.remove(veterinario);
                System.out.println("Eliminado con éxito.");
            }
            default -> System.out.println(mensajeOpcionInválida);
        }
        if (salir)
            return;
    }

    private void acciónMostrarDetalleVeterinario (Veterinario v) {
        List<Campo> info = new LinkedList<>();

        info.add(new Campo("ID", v.getId()));
        info.add(new Campo("Nombre", v.getNombre()));
        info.add(new Campo("Especialidad", v.getEspecialidad()));

        int maxAncho = 0;
        for (Campo campo : info)
            maxAncho = Integer.max(maxAncho, campo.etiqueta.length());

        String formatoCampo = "%"+maxAncho+"s: %s\n";

        System.out.println("--- Detalle Veterinario ----");

        for (var c : info)
            System.out.printf(formatoCampo, c.etiqueta, c.valor);
        System.out.println();

        System.out.println(waiting);
        sc.nextLine();
    }
}
