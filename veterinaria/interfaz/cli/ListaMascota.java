package veterinaria.interfaz.cli;

import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static veterinaria.utiles.Utiles.maximaLongitudMascotas;

public class ListaMascota extends SubMenu {
    private final String CABECERA_MENU = """
            --- Menu Mascota ---
            """;
    private final String MENU_MASCOTA = """
            
            --- *************** ---
            1. Buscar mascota
            2. Registrar mascota
            
            0. Volver
            """;
    private final String MENU_ACCIONES_MASCOTA = """
            1. Ver detalle
            2. Modificar mascota
            3. Eliminar mascota
            
            4. Ver consultas
            
            0. Cancelar
            """;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Dueño dueño;

    public ListaMascota(Scanner sc, String formatoPrompt, String waiting, String mensajeOpInv, Dueño dueño) {
        super(sc, formatoPrompt, waiting, mensajeOpInv);
        this.dueño = dueño;
    }

    @Override
    public void iniciar() {
        for (;;) {
            boolean salir = false;

            System.out.println(CABECERA_MENU);
            imprimirTabla(dueño.getListaMascotas());
            System.out.print(MENU_MASCOTA);
            System.out.printf(formatoPrompt, "mascotas");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 0 -> salir = true;
                case 1 -> acciónBuscarMascota(dueño.getListaMascotas());
                case 2 -> acciónRegistrarMascota();
                default -> System.out.println(mensajeOpcionInválida);
            }

            if (salir) break;
        }
    }

    private void imprimirTabla(List<Mascota> mascotas) {
        if (mascotas.isEmpty()) {
            System.out.println("\t# No se encontraron registros para mostrar.");
            return;
        }

        String[] cabeceras = {"ID", "Nombre", "Edad", "Especie", "Raza"};

        // TODO no usar lambdas
        int max_id = maximaLongitudMascotas(mascotas, m -> Integer.toString(m.getId()), cabeceras[0]);
        int max_nombre = maximaLongitudMascotas(mascotas, Mascota::getNombre, cabeceras[1]);
        int max_edad = maximaLongitudMascotas(mascotas, m -> Long.toString(m.getEdad()), cabeceras[2]);
        int max_especie = maximaLongitudMascotas(mascotas, Mascota::getEspecie, cabeceras[3]);
        int max_raza = maximaLongitudMascotas(mascotas, Mascota::getRaza, cabeceras[4]);

        var formato = "|%" +
                max_id + "s|%" +
                max_nombre + "s|%" +
                max_edad + "s|%" +
                max_especie + "s|%" +
                max_raza + "s|\n";

        System.out.printf(formato, (Object[]) cabeceras);

        for (var m : mascotas) {
            System.out.printf(formato,
                    m.getId(),
                    m.getNombre(),
                    m.getEdad(),
                    m.getEspecie(),
                    m.getRaza()
                    );
        }

        System.out.println("\t# Se encontraron "+mascotas.size()+" registros.");
    }

    private void acciónBuscarMascota(List<Mascota> mascotas) {
        for (;;) {
            System.out.println("Ingrese el término de búsqueda (-1 para continuar):");
            System.out.printf(formatoPrompt, "buscar mascota");
            String busqueda = sc.nextLine();

            if (busqueda.equals("-1"))
                break;

            // TODO hacer que HerramientasMascota haga este filtrado
            var resultado = mascotas.stream()
                    .filter(m -> m.getNombre().toLowerCase().contains(busqueda.toLowerCase()))
                    .toList();

            imprimirTabla(resultado);
        }


        System.out.println("Ingrese el ID de la mascota (-1 para cancelar):");
        System.out.printf(formatoPrompt, "id mascota");
        int id = sc.nextInt();
        sc.nextLine();

        if (id == -1)
            return;

        var posibleMascota = mascotas.stream()
                .filter(d -> d.getId() == id)
                .findFirst();

        if (posibleMascota.isEmpty()) {
            System.out.println("El ID ingresado no existe.");
            return;
        }

        var mascota = posibleMascota.get();


        System.out.println("Elija una acción:");
        System.out.print(MENU_ACCIONES_MASCOTA);
        System.out.printf(formatoPrompt, "id mascota = "+id);

        int opt = sc.nextInt();
        sc.nextLine();

        boolean salir = false;
        switch (opt) {
            case 0 -> salir = true;
            case 3 -> {
                dueño.getMascotas().remove(mascota);
                System.out.println("Eliminado correctamente");
            }
            default -> System.out.println(mensajeOpcionInválida);
        }

        if (salir)
            return;
    }

    private void acciónRegistrarMascota() {
        String cabecera = "# %s:\n";

        System.out.printf(cabecera, "Nombre");
        System.out.printf(formatoPrompt, "Nombre");
        String nombre = sc.nextLine();

        System.out.printf(cabecera, "Especie");
        System.out.printf(formatoPrompt, "Especie");
        String especie = sc.nextLine();

        System.out.printf(cabecera, "Raza");
        System.out.printf(formatoPrompt, "Raza");
        String raza = sc.nextLine();


        System.out.printf(cabecera, "Fecha de nacimiento (DD/MM/AAAA)");
        System.out.printf(formatoPrompt, "Fecha de nacimiento");
        String fechaStr = sc.nextLine();
        var fechaNacimiento = LocalDate.parse(fechaStr, formatoFecha);

        dueño.getMascotas().add(new Mascota(nombre, especie, raza, fechaNacimiento, dueño));
    }
}





