package veterinaria.herramientas;

import veterinaria.entidades.Veterinario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HerramientasVeterinario {
    private static List<Veterinario> veterinarios = new ArrayList<>();

    public static List<Veterinario> internoGetVeterinarios() {
        return veterinarios;
    }

    public static void crearVeterinario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa los siguientes datos: ");
        System.out.print("Nombre: ");
        scanner.nextLine();
        String nombre = scanner.nextLine();

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        Veterinario veterinario = new Veterinario(nombre, especialidad);
        veterinarios.add(veterinario);
        System.out.println("Veterinario registrado exitosamente :D");
    }

    public static void bajaVeterinario(Veterinario veterinarioEliminado){
        veterinarioEliminado.setActivo(false);

        System.out.println("Baja exitosa");
        System.out.println("Veterinario que renunció: ");
        System.out.println("ID: " + veterinarioEliminado.getId());
        System.out.println("Nombre: " + veterinarioEliminado.getNombre());
    }

    public static void modificarVeterinario(int id){
        Scanner scanner = new Scanner(System.in);
        boolean top=true;
        Veterinario vet = veterinarios.get(id);
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
    }
}
