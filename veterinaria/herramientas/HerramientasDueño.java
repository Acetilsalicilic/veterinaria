package veterinaria.herramientas;

import java.util.LinkedList;
import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;
import veterinaria.persistencia.persistenciaDueño;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class HerramientasDueño {
    private static final List <Dueño> dueños = new ArrayList<>();

    public static void internoCrearDueño(Dueño d) {
        dueños.add(d);
    }

    public static void internoSetDueños(List<Dueño> dueños) {
        HerramientasDueño.dueños.addAll(dueños);
    }

    public static List<Dueño> internoGetDueños() {
        List<Dueño> activos = new LinkedList<>();
        for (var d : dueños)
            if (d.isActivo())
                activos.add(d);
        return activos;
    }

    public static List<Dueño> filtrarPorNombre(String nombre) {
        List<Dueño> filtrados = new LinkedList<>();
        for (var d : dueños)
            if (d.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                filtrados.add(d);
        return filtrados;
    }

    public static void crearDueño(Scanner scanner){
        System.out.println("Ingresa los siguientes datos: ");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Telefono de emergencia: ");
        String telEmergencia = scanner.nextLine();

        System.out.print("Nombre del titular del número de emergencia: ");
        String nombreEmergencia = scanner.nextLine();

        Dueño dueño = new Dueño(nombre, telefono, direccion, telEmergencia, nombreEmergencia);

        if (persistenciaDueño.agregarDueño(dueño)) {
            System.out.println("ERROR guardando la información");
            return;
        }

        dueños.add(dueño);
        System.out.println("Registrado exitosamemte :D");
    }

    public static void bajaDueño(Dueño dueñoEliminado){
        dueñoEliminado.setActivo(false);

        System.out.println("Baja exitosa");
        System.out.println("Usuario dado de baja: ");
        System.out.println("ID: "+dueñoEliminado.getId());
        System.out.println("Nombre: "+ dueñoEliminado.getNombre());

        if (persistenciaDueño.actualizarDueño(dueñoEliminado))
            System.out.println("ERROR actualizando la información");
    }

    public static void modificarDueño(Scanner scanner, Dueño d){
        int id = d.getId();
        boolean top=true;
        Dueño dueño=dueños.get(id);
        while (top){
            String menu="""
            Que quieres modificar:
            1) Nombre
            2) Telefono
            3) Domicilio
            4) Telefono de emergencia
            5) Nombre de emergencia
            6) Guardar
            """;
            System.out.println(menu);
            int op = scanner.nextInt();
            scanner.nextLine();
            switch (op) {
                case 1:
                    System.out.print("Nuevo nombre: ");
                    String nombre = scanner.nextLine();
                    dueño.setNombre(nombre);
                    System.out.println("Nombre modificado a: " + nombre);
                    break;
                case 2:
                    System.out.print("Nuevo telefono: ");
                    String telefono = scanner.nextLine();
                    dueño.setTeléfono(telefono);
                    System.out.println("Telefono modificado a: " + telefono);
                    break;
                case 3:
                    System.out.print("Nueva dirección: ");
                    String direccion = scanner.nextLine();
                    dueño.setDirección(direccion);
                    System.out.println("Dirección modificada a: " + direccion);
                    break;
                case 4:
                    System.out.print("Nuevo telefono de emergencia: ");
                    String telEmergencia = scanner.nextLine();
                    dueño.setTelefonoDeEmergencia(telEmergencia);
                    System.out.println("Teléfono de emergencia modificado a: " + telEmergencia);
                    break;
                case 5:
                    System.out.print("Nuevo nombre de emergencia: ");
                    String nombreEmergencia = scanner.nextLine();
                    dueño.setNombreDeEmergencia(nombreEmergencia);
                    System.out.println("Nombre de emergencia modificado a: " + nombreEmergencia);
                    break;
                case 6:
                    System.out.println("Cambios guardados.");
                    top=false;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }

        if (persistenciaDueño.actualizarDueño(dueño)) {
            System.out.println("ERROR actualizando la información");
        }
    }
}