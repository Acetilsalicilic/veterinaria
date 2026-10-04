import java.time.LocalDate;
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

        System.out.print("Fecha de nacimiento (dd/mm/yy) : ");
        String fecha = scanner.nextLine();
        int dia=obtener.dia(fecha);
        int mes=obtener.mes(fecha);
        int año=obtener.año(fecha);

        LocalDate fechaNacimiento = LocalDate.of(año, mes, dia);

        Mascota mascota= new Mascota(nombre, especie, raza, fechaNacimiento, dueño);
        
        dueño.getMascotas().add(mascota);

        System.out.println("Mascota registrada exitosamente :D");
    }

    public static void bajaMascota(Dueño dueño){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mascotas de "+ dueño.getNombre());
        for (Mascota mascota : dueño.getMascotas()){
            System.out.println("Id: " + mascota.getId());
            System.out.println("Nombre: " + mascota.getNombre());
        }

        System.out.print("Escribe el id de la mascota que quieras dar de baja: ");
        int id= scanner.nextInt();

        Mascota mascotaBaja;
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

    public static void modificarMascota(Dueño dueño){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mascotas de "+ dueño.getNombre());
        for (Mascota mascota : dueño.getMascotas()){
            System.out.println("Id: " + mascota.getId());
            System.out.println("Nombre: " + mascota.getNombre());
        }

        System.out.print("Escribe el id de la mascota que quieres modificar: ");
        int id= scanner.nextInt();

        Mascota mascotaNew;
        for (Mascota mascota:dueño.getMascotas()){
            if (mascota.getId()==id){
                mascotaNew=mascota;
                break;
            }
        }

        boolean bandera=true;
        while (bandera){
            String menu="""
            Que quieres modificar:
            1) Nombre
            2) Especie
            3) Raza
            4) Fecha de nacimiento
            5) Guardar
            """;
            System.out.println(menu);
            int op= scanner.nextInt();
            scanner.nextLine();
            switch (op) {
                case 1:
                    System.out.print("Nuevo nombre: ");
                    String newNombre = scanner.nextLine();

                    mascotaNew.setNombre(newNombre);
                    System.out.println("Nombre modificado a "+newNombre);
                    break;
                case 2:
                    System.out.print("Nueva especie: ");
                    String newEspecie = scanner.nextLine();

                    mascotaNew.setEspecie(newEspecie);
                    System.out.println("Especie modificada a "+newEspecie);
                    break;
                case 3:
                    System.out.print("Nueva raza: ");
                    String newRaza = scanner.nextLine();

                    mascotaNew.setRaza(newRaza);
                    System.out.println("Raza modificada a "+newRaza);
                    break;
                case 4:
                    System.out.print("Nueva fecha de nacimiento (dd/mm/yy) : ");
                    String fecha = scanner.nextLine();
                    int dia=obtener.dia(fecha);
                    int mes=obtener.mes(fecha);
                    int año=obtener.año(fecha);

                    LocalDate fechaNacimiento = LocalDate.of(año, mes, dia);

                    mascotaNew.setFechaNacimiento(fechaNacimiento);
                    System.out.println("Fecha de nacimiento modificada a "+fechaNacimiento);
                    break;
                case 5:
                    System.out.println("Cambios guardados");
                    bandera=false;
                    break;
                default:
                    break;
            }
        }

        
    }
}
