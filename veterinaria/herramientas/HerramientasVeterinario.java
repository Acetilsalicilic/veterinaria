package veterinaria.herramientas;

import veterinaria.entidades.Veterinario;
import veterinaria.persistencia.persistenciaVeterinario;

import java.util.*;

public class HerramientasVeterinario {
    private static final List<Veterinario> veterinarios = new ArrayList<>();

    public static void internoSetVeterinarios(List<Veterinario> veterinarios) {
        HerramientasVeterinario.veterinarios.addAll(veterinarios);
    }

    public static List<Veterinario> internoGetVeterinarios() {
        List<Veterinario> filtrados = new LinkedList<>();
        for (var v : veterinarios)
            if (v.isActivo())
                filtrados.add(v);
        return filtrados;
    }

    public static List<Veterinario> filtrarPorNombre(String termino) {
        List<Veterinario> filtrados = new LinkedList<>();
        for (var v : veterinarios)
            if (v.getNombre().toLowerCase().contains(termino.toLowerCase()))
                filtrados.add(v);
        return filtrados;
    }

    public static void crearVeterinario(Scanner scanner) {
        System.out.println("Ingresa los siguientes datos: ");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        Veterinario veterinario = new Veterinario(nombre, especialidad);
        if (persistenciaVeterinario.agregarVeterinario(veterinario)) {
            System.out.println("ERROR al guardar los datos");
            return;
        }
        veterinarios.add(veterinario);
        System.out.println("Veterinario registrado exitosamente :D");
    }

    public static void bajaVeterinario(Veterinario veterinarioEliminado){
        veterinarioEliminado.setActivo(false);

        if (persistenciaVeterinario.actualizarVeterinario(veterinarioEliminado)) {
            System.out.println("ERROR al actualizad la información");
            return;
        }

        System.out.println("Baja exitosa");
        System.out.println("Veterinario que renunció: ");
        System.out.println("ID: " + veterinarioEliminado.getId());
        System.out.println("Nombre: " + veterinarioEliminado.getNombre());
    }

    public static void modificarVeterinario(Scanner scanner, Veterinario vet){
        boolean top=true;
        while (top){
            String menu="""
            Que quieres modificar:
            1) Nombre
            2) Especialidad
            3) Guardar
            """;

            System.out.println(menu);
            int op = scanner.nextInt();
            scanner.nextLine();
            switch (op) {
                case 1:
                    System.out.print("Nuevo nombre: ");
                    String nombre = scanner.nextLine();
                    vet.setNombre(nombre);
                    System.out.println("Nombre modificado a: " + nombre);
                    break;
                case 2:
                    System.out.print("Nueva especialidad: ");
                    String especialidad = scanner.nextLine();
                    vet.setEspecialidad(especialidad);
                    System.out.println("Especialidad modificada a: " + especialidad);
                    break;
                case 3:
                    System.out.println("Cambios guardados.");
                    top=false;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }

        if (persistenciaVeterinario.actualizarVeterinario(vet)) {
            System.out.println("ERROR al actualizar la información");
        }
    }
}
