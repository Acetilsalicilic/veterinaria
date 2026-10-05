package veterinaria.herramientas;

import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;
import veterinaria.persistencia.persistenciaMascota;

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

        if (persistenciaMascota.agregarMascota(mascota)) {
            System.out.println("ERROR al guardar la información");
            return;
        }

        dueño.getMascotas().add(mascota);

        System.out.println("Mascota registrada exitosamente :D");
    }

    public static void bajaMascota(Mascota mascotaBaja){
        mascotaBaja.setActivo(false);

        System.out.println("Baja exitosa");
        System.out.println("Mascota dada de baja: ");
        System.out.println("Nombre: "+ mascotaBaja.getNombre());

        if (persistenciaMascota.actualizarMascota(mascotaBaja)) {
            System.out.println("ERROR al guardar la información");
        }
    }

    public static void modificarMascota(Scanner scanner, Mascota mascotaNew){

        boolean bandera=true;
        while (bandera) {
            String menu = """
                    Que quieres modificar:
                    1) Nombre
                    2) Especie
                    3) Raza
                    4) Fecha de nacimiento
                    5) Guardar
                    """;
            System.out.println(menu);
            int op = scanner.nextInt();
            scanner.nextLine();
            switch (op) {
                case 1:
                    System.out.print("Nuevo nombre: ");
                    String newNombre = scanner.nextLine();

                    mascotaNew.setNombre(newNombre);
                    System.out.println("Nombre modificado a " + newNombre);
                    break;
                case 2:
                    System.out.print("Nueva especie: ");
                    String newEspecie = scanner.nextLine();

                    mascotaNew.setEspecie(newEspecie);
                    System.out.println("Especie modificada a " + newEspecie);
                    break;
                case 3:
                    System.out.print("Nueva raza: ");
                    String newRaza = scanner.nextLine();

                    mascotaNew.setRaza(newRaza);
                    System.out.println("Raza modificada a " + newRaza);
                    break;
                case 4:
                    System.out.print("Nueva fecha de nacimiento (dd/mm/yy) : ");
                    String fecha = scanner.nextLine();
                    int dia = Obtener.dia(fecha);
                    int mes = Obtener.mes(fecha);
                    int año = Obtener.año(fecha);

                    LocalDate fechaNacimiento = LocalDate.of(año, mes, dia);

                    mascotaNew.setFechaNacimiento(fechaNacimiento);
                    System.out.println("Fecha de nacimiento modificada a " + fechaNacimiento);
                    break;
                case 5:
                    System.out.println("Cambios guardados");
                    bandera = false;
                    break;
                default:
                    break;
            }
        }

        if (persistenciaMascota.actualizarMascota(mascotaNew)) {
            System.out.println("ERROR al guardar la información");
        }
    }
}
