package veterinaria.interfaz.cli;

import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;
import veterinaria.herramientas.HerramientasMascota;
import veterinaria.utiles.Campo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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

    private final Dueño dueño;

    public ListaMascota(Scanner sc, String formatoPrompt, String waiting, String mensajeOpInv, Dueño dueño) {
        super(sc, formatoPrompt, waiting, mensajeOpInv);
        this.dueño = dueño;
    }

    @Override
    public void iniciar() {
        for (;;) {
            boolean salir = false;

            System.out.println(CABECERA_MENU);
            imprimirTabla(HerramientasMascota.internoGetMascotas(dueño));
            System.out.print(MENU_MASCOTA);
            System.out.printf(formatoPrompt, "mascotas");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 0 -> salir = true;
                case 1 -> acciónBuscarMascota();
                case 2 -> HerramientasMascota.crearMascota(sc, dueño);
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

        String[] cabeceras = {"ID", "NOMBRE", "EDAD", "ESPECIE", "RAZA"};

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

    private void acciónBuscarMascota() {
        for (;;) {
            System.out.println("Ingrese el término de búsqueda (-1 para continuar):");
            System.out.printf(formatoPrompt, "buscar mascota");
            String busqueda = sc.nextLine();

            if (busqueda.equals("-1"))
                break;

            var resultado = HerramientasMascota.filtrarPorNombre(dueño, busqueda);

            imprimirTabla(resultado);
        }


        System.out.println("Ingrese el ID de la mascota (-1 para cancelar):");
        System.out.printf(formatoPrompt, "id mascota");
        int id = sc.nextInt();
        sc.nextLine();

        if (id == -1)
            return;

        Mascota mascota = null;
        for (var m : dueño.getListaMascotas())
            if (m.getId() == id)
                mascota = m;

        if (mascota == null) {
            System.out.println("❌ El ID ingresado no existe.");
            return;
        }

        System.out.println("Elija una acción:");
        System.out.print(MENU_ACCIONES_MASCOTA);
        System.out.printf(formatoPrompt, "id mascota = "+id);

        int opt = sc.nextInt();
        sc.nextLine();

        boolean salir = false;
        switch (opt) {
            case 0 -> salir = true;
            case 1 -> acciónMostrarDetalleMascota(mascota);
            case 2 -> HerramientasMascota.modificarMascota(sc, dueño);
            case 3 -> HerramientasMascota.bajaMascota(sc, dueño);
            // TODO añadir menú de consultas acá
            default -> System.out.println(mensajeOpcionInválida);
        }

        if (salir) {
        }
    }

    private void acciónMostrarDetalleMascota (Mascota mascota) {
        List<Campo> info = new LinkedList<>();

        info.add(new Campo("ID", mascota.getId()));
        info.add(new Campo("Nombre", mascota.getNombre()));
        info.add(new Campo("Edad", mascota.getEdad()));
        info.add(new Campo("Fecha de nacimiento", mascota.getFechaNacimiento().format(formatoFecha)));
        info.add(new Campo("Especie", mascota.getEspecie()));
        info.add(new Campo("Raza", mascota.getRaza()));
        // TODO aladir número de consultas asociadas

        int maxAncho = 0;
        for (Campo campo : info)
            maxAncho = Integer.max(maxAncho, campo.etiqueta.length());

        String formatoCampo = "%"+maxAncho+"s: %s\n";

        System.out.println("--- Detalle Mascota ---");
        for (var campo : info)
            System.out.printf(formatoCampo, campo.etiqueta, campo.valor);
        System.out.println();

        System.out.println(waiting);
        sc.nextLine();
    }
}





