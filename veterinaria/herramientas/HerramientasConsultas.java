package veterinaria.herramientas;

import veterinaria.entidades.Consulta;
import veterinaria.entidades.Mascota;
import veterinaria.entidades.Veterinario;
import veterinaria.persistencia.persistenciaConsulta;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.time.LocalDateTime;
import java.util.*;

public class HerramientasConsultas {
    private static Map <Mascota, List<Consulta>> historial=new HashMap<>();
    private static Map <Mascota, List<Consulta>> agendadas=new HashMap<>();
    private static List<LocalDateTime> fechasOcupadas = new ArrayList<>();

    public static void internoSetAgendadas(List<Consulta> consultas) {
        for (var c : consultas) {
            if (!agendadas.containsKey(c.getMascota()))
                agendadas.put(c.getMascota(), new LinkedList<>());
            agendadas.get(c.getMascota()).add(c);
        }
    }
    public static void internoSetHistorial(List<Consulta> consultas) {
        for (var c : consultas) {
            if (!historial.containsKey(c.getMascota()))
                historial.put(c.getMascota(), new LinkedList<>());
            historial.get(c.getMascota()).add(c);
        }
    }

    public static List<Consulta> internoGetConsultasAgendadas(Mascota mascota) {
        if (agendadas.get(mascota) == null)
            agendadas.put(mascota, new LinkedList<>());
        return agendadas.get(mascota);
    }

    public static List<Consulta> internoGetHistorial(Mascota mascota) {
        if (historial.get(mascota) == null)
            historial.put(mascota, new LinkedList<>());
        return historial.get(mascota);
    }

    public static void crearConsulta(Scanner scanner, Mascota mascota){
        System.out.println("Consulta para "+mascota.getNombre());
        boolean bandera=true;
        LocalDateTime cita = null;
        //scanner.nextLine();
        while (bandera){
            System.out.println("Cuando quiere su cita? dd/mm/yy ");
            String fecha=scanner.nextLine();
            int dia=Obtener.dia(fecha);
            int mes=Obtener.mes(fecha);
            int año=Obtener.año(fecha);

            System.out.println("A qué hora quiere su cita? ");
            String tiempo=scanner.nextLine();
            int hora=Obtener.hora(tiempo);
            int minuto=Obtener.min(tiempo);

            cita = LocalDateTime.of(año, mes, dia, hora, minuto);

            if (fechasOcupadas.contains(cita)){
                System.out.println("Esta fecha ya esta ocupada :(, escoge otra porfavor");
            }else{
                fechasOcupadas.add(cita);
                bandera=false;
            }
        }

        System.out.print("Motivo de consulta: ");
        String motivo=scanner.nextLine();

        Consulta consulta = new Consulta(cita, motivo, mascota, new Veterinario("", ""));
        if (persistenciaConsulta.agregarConsulta(consulta)) {
            System.out.println("ERROR al guardar la información");
            return;
        }

        if (agendadas.containsKey(mascota)){
            agendadas.get(mascota).add(consulta);
        }else{
            List <Consulta> citas =new ArrayList<>();
            citas.add(consulta);
            agendadas.put(mascota, citas);
        }
    }

    public static void modificarConsulta(Scanner scanner, Consulta consultaModi){
        boolean bandera=true;
        while (bandera){
            String menu = """
                Selecciona lo que quieras modificar:
                1) Modificar fecha
                2) Modificar motivo
                3) Guardar
                """;
            System.out.println(menu);

            int op = scanner.nextInt();

            switch (op) {
                case 1:
                    boolean banderaFecha = true;
                    LocalDateTime cita = null;
                    scanner.nextLine();
                    while (banderaFecha) {
                        System.out.println("Cuando quiere su cita? dd/mm/yy ");
                        String fecha=scanner.nextLine();
                        int dia=Obtener.dia(fecha);
                        int mes=Obtener.mes(fecha);
                        int año=Obtener.año(fecha);

                        System.out.println("A qué hora quiere su cita? ");
                        String tiempo=scanner.nextLine();
                        int hora=Obtener.hora(tiempo);
                        int minuto=Obtener.min(tiempo);

                        cita = LocalDateTime.of(año, mes, dia, hora, minuto);

                        if (fechasOcupadas.contains(cita)) {
                            System.out.println("Esta fecha ya esta ocupada :(, escoge otra porfavor");
                        }else{
                            fechasOcupadas.add(cita);
                            banderaFecha = false;
                        }
                    }

                    fechasOcupadas.remove(consultaModi.getFechaYHora());
                    fechasOcupadas.add(cita);
                    consultaModi.setFechaYHora(cita);

                    System.out.println("Fecha modificada exitosamente");
                    break;
                case 2:
                    System.out.print("Escribe el nuevo motivo de la consulta: ");
                    scanner.nextLine();
                    String motivo= scanner.nextLine();
                    consultaModi.setMotivo(motivo);
                    System.out.println("Motivo modificado exitosamente");
                    break;
                case 3:
                    System.out.println("Cambios guardados");
                    bandera=false;
                    break;
                default:
                    System.out.println("Opción no valida");
                    break;
            }
        }
        
        if (persistenciaConsulta.actualizarConsulta(consultaModi)) {
            System.out.println("ERROR guardando la información");
        }

    }

    public static void eliminarConsulta(Consulta consultaEliminar){
        if (persistenciaConsulta.eliminarConsulta(consultaEliminar)) {
            System.out.println("ERROR al actualizar los datos");
            return;
        }

        fechasOcupadas.remove(consultaEliminar.getFechaYHora());
        agendadas.get(consultaEliminar.getMascota()).remove(consultaEliminar);
    }

    public static void moverAHistorial(Consulta consulta){
        if (persistenciaConsulta.moverAHistorico(consulta)) {
            System.out.println("ERROR actualizando la información");
            return;
        }

        Mascota mascota=consulta.getMascota();

        agendadas.get(mascota).remove(consulta);

        if (historial.containsKey(mascota)){
                historial.get(mascota).add(consulta);
        }else{
            List <Consulta> consultasHistorial=new ArrayList<>();
            consultasHistorial.add(consulta);
            historial.put(mascota, consultasHistorial);
        }

        fechasOcupadas.remove(consulta.getFechaYHora());

        System.out.println("Consulta transferida al historial :D");
    }
}
