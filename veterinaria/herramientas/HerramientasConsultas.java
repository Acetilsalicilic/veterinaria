package veterinaria.herramientas;

import veterinaria.entidades.Consulta;
import veterinaria.entidades.Mascota;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HerramientasConsultas {
    private static Map <Mascota, List<Consulta>> historial=new HashMap<>();
    private static Map <Mascota, List<Consulta>> agendadas=new HashMap<>();
    private static List<LocalDateTime> fechasOcupadas = new ArrayList<>();

    public static void crearConsulta(Mascota mascota){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Consulta para "+mascota.getNombre());
        boolean bandera=true;
        LocalDateTime cita = null;
        scanner.nextLine();
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

        Consulta consulta = new Consulta(cita, motivo, mascota);

        if (agendadas.containsKey(mascota)){
            agendadas.get(mascota).add(consulta);
        }else{
            List <Consulta> citas =new ArrayList<>();
            citas.add(consulta);
            agendadas.put(mascota, citas);
        }
    }

    public static void modificarConsulta(Mascota mascota){
        Scanner scanner =new Scanner(System.in);
        if (agendadas.get(mascota).isEmpty()){
            System.out.println("La mascota no tiene consultas agendadas");
            return;
        }

        System.out.println("Consultas agendadas de "+mascota.getNombre());
        for (Consulta consulta:agendadas.get(mascota)){
            System.out.println("ID: " + consulta.getId());
            System.out.println("Fecha: " + consulta.getFechaYHora());
            System.out.println("Motivo: " + consulta.getMotivo());
        }

        System.out.print("Escribe el ID de la cita que quieras modificar: ");
        int id= scanner.nextInt();
        Consulta consultaModi = null;
        for (Consulta consulta : agendadas.get(mascota)) {
            if (consulta.getId() == id) {
                consultaModi = consulta;
                break;
            }
        }

        scanner.nextLine();
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
        



    }

    public static void eliminarConsulta(Mascota mascota){
        Scanner scanner =new Scanner(System.in);
        if (agendadas.get(mascota).isEmpty()){
            System.out.println("La mascota no tiene consultas agendadas");
            return;
        }

        System.out.println("Consultas agendadas de "+mascota.getNombre());
        for (Consulta consulta:agendadas.get(mascota)){
            System.out.println("ID: " + consulta.getId());
            System.out.println("Fecha: " + consulta.getFechaYHora());
            System.out.println("Motivo: " + consulta.getMotivo());
        }

        System.out.print("Escribe el ID de la cita que quieras cancelar: ");
        int id= scanner.nextInt();
        Consulta consultaEliminar = null;
        for (Consulta consulta : agendadas.get(mascota)) {
            if (consulta.getId() == id) {
                consultaEliminar = consulta;
                break;
            }
        }

        fechasOcupadas.remove(consultaEliminar.getFechaYHora());
        agendadas.get(mascota).remove(consultaEliminar);
    }

    public static void moverAHistorial(Consulta consulta){
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
