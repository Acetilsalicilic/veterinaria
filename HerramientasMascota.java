import java.time.LocalDateTime;
import java.util.Scanner;

public class HerramientasMascota {
    public static void crearMascota(Dueño dueño){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mascota de "+ dueño.getNombre());
        System.out.println("Ingresa los siguientes datos: ");
        System.out.print("Nombre: ");
        scanner.nextLine();
        String nombre = scanner.nextLine();

        System.out.print("Especie: ");
        String especie = scanner.nextLine();
        
        System.out.print("Raza: ");
        String raza = scanner.nextLine();

        System.out.print("Fecha de nacimiento: ");
        LocalDateTime fechaNacimiento //leer;

        Mascota mascota= new Mascota(nombre, especie, raza, dueño);
        //agregar mascota al set

        System.out.println("Mascota registrada exitosamente :D");
    }

    public static void bajaMascota(Dueño dueño){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mascotas de "+ dueño.getNombre());
        int numMascota=1;
        for (Mascota mascota : dueño.getMascotas()){
            System.err.println(numMascota +") " + mascota.getNombre());
        }

        System.out.print("Escoge el número de mascota a la que quieras dar de baja: ");
        int mascotaEliminada= scanner.nextInt();

        Mascota mascotaEliminada //sacar a la mascota del set
        //eliminar a la mascota

        System.out.println("Eliminación exitosa");
        System.out.println("Eliminado: ");
        System.out.println("Nombre: "+ mascotaEliminada.getNombre());
    }

    public static void modificarMascota(Dueño dueño){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mascotas de "+ dueño.getNombre());
        int numMascota=1;
        for (Mascota mascota : dueño.getMascotas()){
            System.err.println(numMascota +") " + mascota.getNombre());
        }

        System.out.print("Escoge el número de mascota a la que le quieras modificar el nombre: ");
        int mascotaEliminada= scanner.nextInt();

        String newNombre = scanner.nextLine();

        Mascota mascota // sacar a la mascota del set

        mascota.setNombre(newNombre);
        System.out.println("Nombre modificado a "+newNombre);
    }
}
