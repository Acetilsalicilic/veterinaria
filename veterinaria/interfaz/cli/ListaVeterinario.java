package veterinaria.interfaz.cli;

import veterinaria.Principal;
import veterinaria.entidades.Veterinario;
import veterinaria.herramientas.HerramientasDueño;
import veterinaria.herramientas.HerramientasVeterinario;
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
            1. Ver información
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
            imprimirTabla(HerramientasVeterinario.internoGetVeterinarios());
            System.out.print(OPCIONES);
            System.out.printf(formatoPrompt, "veterinarios");
            int opt = sc.nextInt();
            sc.nextLine();

            boolean salir = false;
            switch (opt) {
                case 0 -> salir = true;
                case 1 -> acciónBuscarVeterinario();
                case 2 -> HerramientasVeterinario.crearVeterinario(sc);
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

    private void acciónBuscarVeterinario() {
        for (;;) {
            System.out.println("Ingrese el término de búsqueda (-1 para continuar):");
            System.out.printf(formatoPrompt, "buscar veterinario");
            String busqueda = sc.nextLine();

            if (busqueda.equals("-1"))
                break;

            var resultado = HerramientasVeterinario.filtrarPorNombre(busqueda);

            imprimirTabla(resultado);
        }

        System.out.println("Ingrese el ID del veterinario (-1 para cancelar):");
        System.out.printf(formatoPrompt, "id dueño");
        int id = sc.nextInt();
        sc.nextLine();

        if (id == -1)
            return;

        Veterinario vet = null;
        for (var v : HerramientasVeterinario.internoGetVeterinarios())
            if (v.getId() == id)
                vet = v;

        if (vet == null) {
            System.out.println("❌ El ID ingresado no existe.");
            return;
        }

        System.out.println("Elija una acción:");
        System.out.print(MENU_ACCIONES_VETERINARIO);
        System.out.printf(formatoPrompt, "id dueño = "+id);

        int opt = sc.nextInt();
        sc.nextLine();

        switch (opt) {
            case 0 -> {}
            case 1 -> {
                System.out.printf("%s: %s\n", "ID", vet.getId());
                System.out.printf("%s: %s\n", "Nombre", vet.getNombre());
                System.out.printf("%s: %s\n", "ID", vet.getEspecialidad());
            }
            case 2 -> HerramientasVeterinario.modificarVeterinario(sc, vet);
            case 3 -> HerramientasVeterinario.bajaVeterinario(vet);
            default -> System.out.println(mensajeOpcionInválida);
        }
    }
}
