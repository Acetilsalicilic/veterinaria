package veterinaria.herramientas;

import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class HerramientasMascota {
    public static List<Mascota> internoGetMascotas(Dueño dueño) {
        List<Mascota> filtrados = new LinkedList<>();
        for (var m : dueño.getMascotas())
            if (m.isActivo())
                filtrados.add(m);
        return filtrados;
    }
    public static List<Mascota> filtrarPorNombre(Dueño dueño, String termino) {
        List<Mascota> filtrados = new LinkedList<>();
        for (var m : dueño.getMascotas())
            if (m.getNombre().toLowerCase().contains(termino.toLowerCase()))
                filtrados.add(m);
        return filtrados;
    }
    public static void crearMascota(Scanner scanner, Dueño dueño){
        System.out.println("Mascota de "+ dueño.getNombre());
        System.out.println("Ingresa los siguientes datos: ");
        System.out.print("Nombre: ");
        scanner.nextLine();
        String nombre = scanner.nextLine();

        System.out.print("Especie: ");
        String especie = scanner.nextLine();
        
        System.out.print("Raza: ");
        String raza = scanner.nextLine();

        System.out.print("Fecha de nacimiento (dd/mm/yy) : ");
        String fecha = scanner.nextLine();
        int dia=Obtener.dia(fecha);
        int mes=Obtener.mes(fecha);
        int año=Obtener.año(fecha);

        LocalDate fechaNacimiento = LocalDate.of(año, mes, dia);

        Mascota mascota= new Mascota(nombre, especie, raza, fechaNacimiento, dueño);
        
        dueño.getMascotas().add(mascota);

        System.out.println("Mascota registrada exitosamente :D");
    }

    public static void bajaMascota(Scanner scanner, Dueño dueño){
        System.out.println("Mascotas de "+ dueño.getNombre());
        for (Mascota mascota : dueño.getMascotas()){
            System.out.println("Id: " + mascota.getId());
            System.out.println("Nombre: " + mascota.getNombre());
        }

        System.out.print("Escribe el id de la mascota que quieras dar de baja: ");
        int id= scanner.nextInt();

        Mascota mascotaBaja = null;
        for (Mascota mascota:dueño.getMascotas()){
            if (mascota.getId()==id){
                mascotaBaja=mascota;
            }
        }

        mascotaBaja.setActivo(false);

        System.out.println("Baja exitosa");
        System.out.println("Mascota dada de baja: ");
        System.out.println("Nombre: "+ mascotaBaja.getNombre());
    }

    public static void modificarMascota(Scanner scanner, Dueño dueño){
        System.out.println("Mascotas de "+ dueño.getNombre());
        for (Mascota mascota : dueño.getMascotas()){
            System.out.println("Id: " + mascota.getId());
            System.out.println("Nombre: " + mascota.getNombre());
        }

        System.out.print("Escribe el id de la mascota que quieres modificar: ");
        int id= scanner.nextInt();

        Mascota mascotaNew = null;
        for (Mascota mascota:dueño.getMascotas()){
            if (mascota.getId()==id){
                mascotaNew=mascota;
            }
        }

        scanner.nextLine();
        System.out.print("Escribe el nombre que le quieras poner: ");
        String newNombre = scanner.nextLine();

        mascotaNew.setNombre(newNombre);
        System.out.println("Nombre modificado a "+newNombre);
    }
}
