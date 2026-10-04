package veterinaria.herramientas;

import veterinaria.entidades.Dueño;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HerramientasDueño {
    private static List <Dueño> dueños = new ArrayList<>();
    
        
    public static void crearDueño(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa los siguientes datos: ");
        System.out.print("Nombre: ");
        scanner.nextLine();
        String nombre = scanner.nextLine();

        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();
        
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Telefono de emergencia: ");
        String telEmergencia = scanner.nextLine();

        System.out.print("Nombre del titular del número de emergencia: ");
        String nombreEmergencia = scanner.nextLine();

        Dueño dueño= new Dueño(nombre, telefono, direccion, telEmergencia, nombreEmergencia);

        dueños.add(dueño);
        System.out.println("Registrado exitosamemte :D");
    }

    public static void bajaDueño(int id){
        Dueño dueñoEliminado= dueños.get(id);
        dueñoEliminado.setActivo(false);

        System.out.println("Baja exitosa");
        System.out.println("Usuario dado de baja: ");
        System.out.println("ID: "+dueñoEliminado.getId());
        System.out.println("Nombre: "+ dueñoEliminado.getNombre());
    }

    public static void modificarDueño(int id){
        Scanner scanner = new Scanner(System.in);
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
    }
}
