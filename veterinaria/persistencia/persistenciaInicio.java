package veterinaria.persistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;

import veterinaria.entidades.*;
import veterinaria.herramientas.HerramientasConsultas;
import veterinaria.herramientas.HerramientasDueño;
import veterinaria.herramientas.HerramientasVeterinario;

public class persistenciaInicio {
    private static LinkedList<Dueño> obteniendoDueños() {
        return persistenciaDueño.obteniendoDueños();
    }

    private static LinkedList<Mascota> obteniendoMascotas(LinkedList<Dueño> dueños) {
        ArrayList<Integer> dueñosIds = new ArrayList<Integer>();
        LinkedList<Mascota> mascotas = persistenciaMascota.obteniendoMascotas(dueñosIds);
        LinkedList<Mascota> mascotasNoEncontradas = new LinkedList<Mascota>();

        int i = 0;
        for(Mascota mascota : mascotas) {
            int id = dueñosIds.get(i++);
            for(Dueño dueño : dueños) {
                if(dueño.getId() == id) {
                    mascota.setDueño(dueño);
                    break;
                }
            }
            if(mascota.getDueño() == null) 
                mascotasNoEncontradas.add(mascota);
        }

        for(Mascota mascota : mascotasNoEncontradas) {
            mascotas.remove(mascota);
        }

        return mascotas;
    }

    private static LinkedList<Veterinario> obteniendoVeterinarios() {
        return persistenciaVeterinario.obteniendoVeterinarios();
    }
    
    private static LinkedList<Consulta> obteniendoConsultasAgendadas(LinkedList<Veterinario> veterinarios, LinkedList<Mascota> mascotas) {
        ArrayList<Integer> veterinariosIDs = new ArrayList<Integer>();
        ArrayList<Integer> mascotasIDs = new ArrayList<Integer>();
        LinkedList<Consulta> consultas = persistenciaConsulta.obtenerConsultasAgendadas(mascotasIDs, veterinariosIDs);
        LinkedList<Consulta> consultasNoEnctontradas = new LinkedList<Consulta>();

        int i = 0;
        for(Consulta consulta : consultas) {
            int id = veterinariosIDs.get(i);
            for(Veterinario veterinario : veterinarios) {
                if(veterinario.getId() == id) {
                    consulta.setVeterinario(veterinario);
                    break;
                }
            }
            id = mascotasIDs.get(i++);
            for(Mascota mascota : mascotas) {
                if(mascota.getId() == id) {
                    consulta.setMascota(mascota);
                    break;
                }
            }
//            if(consulta.getVeterinario() == null || consulta.getMascota() == null)
//                consultasNoEnctontradas.add(consulta);
            if (consulta.getMascota() == null)
                consultasNoEnctontradas.add(consulta);
        }

        for(Consulta consulta : consultasNoEnctontradas) {
            consultas.remove(consulta);
        }

        return consultas;
    }
    
    private static LinkedList<Consulta> obteniendoConsultasHistoricas(LinkedList<Veterinario> veterinarios, LinkedList<Mascota> mascotas) {
        ArrayList<Integer> veterinariosIDs = new ArrayList<Integer>();
        ArrayList<Integer> mascotasIDs = new ArrayList<Integer>();
        LinkedList<Consulta> consultas = persistenciaConsulta.obtenerConsultasHistoricas(mascotasIDs, veterinariosIDs);
        LinkedList<Consulta> consultasNoEnctontradas = new LinkedList<Consulta>();

        int i = 0;
        for(Consulta consulta : consultas) {
            int id = veterinariosIDs.get(i);
            for(Veterinario veterinario : veterinarios) {
                if(veterinario.getId() == id) {
                    consulta.setVeterinario(veterinario);
                    break;
                }
            }
            id = mascotasIDs.get(i++);
            for(Mascota mascota : mascotas) {
                if(mascota.getId() == id) {
                    consulta.setMascota(mascota);
                    break;
                }
            }
            if(consulta.getVeterinario() == null || consulta.getMascota() == null) 
                consultasNoEnctontradas.add(consulta);
        }

        for(Consulta consulta : consultasNoEnctontradas) {
            consultas.remove(consulta);
        }

        return consultas;
    }

    public static boolean inicializadorDatos() {
        var pdueños = Path.of("datosDueños.csv");
        var pmascotas = Path.of("datosMascotas.csv");
        var pveterinarios = Path.of("datosVeterinarios.csv");
        var pagendados = Path.of("datosConsultasAgendadas.csv");
        var phistorico = Path.of("datosConsultasHistoricas.csv");

        try {
            pdueños.toFile().createNewFile();
            pmascotas.toFile().createNewFile();
            pveterinarios.toFile().createNewFile();
            pagendados.toFile().createNewFile();
            phistorico.toFile().createNewFile();
        } catch (IOException e) {
            return true;
        }

        persistenciaDueño.setArchivo(pdueños);
        persistenciaMascota.setArchivo(pmascotas);
        persistenciaVeterinario.setArchivo(pveterinarios);
        persistenciaConsulta.setArchivoAgendado(pagendados);
        persistenciaConsulta.setArchivoHistorico(phistorico);

        LinkedList<Dueño> dueños = obteniendoDueños();
        LinkedList<Mascota> mascotas = obteniendoMascotas(dueños);
        LinkedList<Veterinario> veterinarios = obteniendoVeterinarios();
        LinkedList<Consulta> consultasAgendadas = obteniendoConsultasAgendadas(veterinarios, mascotas);
        LinkedList<Consulta> consultasHistoricas = obteniendoConsultasHistoricas(veterinarios, mascotas);

        for (var m : mascotas) {
            m.getDueño().getMascotas().add(m);
        }
        HerramientasDueño.internoSetDueños(dueños);

        HerramientasConsultas.internoSetAgendadas(consultasAgendadas);
        HerramientasConsultas.internoSetHistorial(consultasHistoricas);

        HerramientasVeterinario.internoSetVeterinarios(veterinarios);

        return false;
    }

}