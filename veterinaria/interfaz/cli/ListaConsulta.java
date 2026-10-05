package veterinaria.interfaz.cli;

import veterinaria.entidades.Consulta;
import veterinaria.entidades.Mascota;
import veterinaria.herramientas.HerramientasConsultas;
import veterinaria.utiles.Campo;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ListaConsulta extends SubMenu {
    private final String ENCABEZADO = """
            --- Menu Consulta ---
            * consultas agendadas:
            """;
    private final String MENU = """
            
            --- *************** ---
            1. Seleccionar consulta
            2. Ver historial
            3. Agendar consulta
            
            0. Volver
            """;
    private final String ACCIONES_CONSULTA = """
            1. Ver información
            2. Mover a historial
            3. Modificar consulta
            4. Eliminar consulta
            
            5. Añadir diagnóstico
            6. Añadir tratamiento
            """;
    private final Mascota mascota;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public ListaConsulta(Scanner sc, String formatoPrompt, String waiting, String mensajeOpInv, Mascota mascota) {
        super(sc, formatoPrompt, waiting, mensajeOpInv);
        this.mascota = mascota;
    }

    @Override
    public void iniciar() {
        for (;;) {
            boolean salir = false;

            System.out.println(ENCABEZADO);
            imprimirTabla(HerramientasConsultas.internoGetConsultasAgendadas(mascota));
            System.out.print(MENU);
            System.out.printf(formatoPrompt, "consultas");

            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 0 -> salir = true;
                case 1 -> acciónSeleccionarConsulta();
                case 2 -> acciónVerHistorial();
                case 3 -> HerramientasConsultas.crearConsulta(sc, mascota);
                default -> System.out.println(mensajeOpcionInválida);
            }
            if (salir) break;
        }
    }

    private void imprimirTabla(List<Consulta> consultas) {
        if (consultas.isEmpty()) {
            System.out.println("\t# No se encontraron registros para mostrar.");
            return;
        }
        String[] cabeceras = {"ID", "FECHA Y HORA"};

        int max_id = cabeceras[0].length();
        for (var c : consultas)
            max_id = Integer.max(max_id, Integer.toString(c.getId()).length());

        int max_fecha = cabeceras[1].length();
        for (var c : consultas)
            max_fecha = Integer.max(max_fecha, c.getFechaYHora().format(formatoFecha).length());

        var formato = "|%" +
                max_id + "s|%" +
                max_fecha + "s|\n";

        System.out.printf(formato, (Object[]) cabeceras);

        for (var c : consultas)
            System.out.printf(formato,
                    c.getId(),
                    c.getFechaYHora().format(formatoFecha));


        System.out.println("\t# Se encontraron "+consultas.size()+" registros.");
    }

    private void acciónVerHistorial() {
        List<Consulta> historial = HerramientasConsultas.internoGetHistorial(mascota);

        if (historial.isEmpty()) {
            System.out.println("\t# No hay consultas en el historial.");
            return;
        }

        for (var c : historial) {
            imprimirConsulta(c);
            System.out.println();
        }

        System.out.println(waiting);
        sc.nextLine();
    }

    private void imprimirConsulta(Consulta c) {
        List<Campo> campos = new LinkedList<>();

        campos.add(new Campo("ID", c.getId()));
        campos.add(new Campo("Fecha y hora", c.getFechaYHora().format(formatoFecha)));
        campos.add(new Campo("Motivo", c.getMotivo()));
        campos.add(new Campo("Diagnóstico", c.getDiagnóstico()));
        campos.add(new Campo("Tratamiento", c.getTratamiento()));

        int max_campo = 0;
        for (var ca : campos)
            max_campo = Integer.max(max_campo, ca.etiqueta.length());

        String formato = "%"+max_campo+"s: %s\n";

        for (var ca : campos)
            System.out.printf(formato, ca.etiqueta, ca.valor);
    }

    private void acciónSeleccionarConsulta() {
        System.out.println("Ingrese el ID de la consulta:");
        System.out.printf(formatoPrompt, "id consulta");

        int id = sc.nextInt();

        List<Consulta> consultas = HerramientasConsultas.internoGetConsultasAgendadas(mascota);

        Consulta c = null;
        for (var d : consultas)
            if (d.getId() == id)
                c = d;

        if (c == null) {
            System.out.println("❌ El ID ingresado no existe.");
            return;
        }

        System.out.println("Elija una opción:");
        System.out.print(ACCIONES_CONSULTA);
        System.out.printf(formatoPrompt, "id consulta = "+id);

        int opt = sc.nextInt();

        switch (opt) {
            case 0 -> {}
            case 1 -> imprimirConsulta(c);
            case 2 -> HerramientasConsultas.moverAHistorial(c);
            case 3 -> HerramientasConsultas.modificarConsulta(sc, c);
            case 4 -> HerramientasConsultas.eliminarConsulta(c);
            case 5 -> {
                System.out.println("Escriba el diagnóstico:");
                System.out.printf(formatoPrompt, "diagnóstico");
                c.setDiagnóstico(sc.nextLine());
            }
            case 6 -> {
                System.out.println("Escriba el tratamiento:");
                System.out.printf(formatoPrompt, "tratamiento");
                c.setTratamiento(sc.nextLine());
            }
            default -> System.out.println(mensajeOpcionInválida);
        }
    }
}
