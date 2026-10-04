package veterinaria.persistencia;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDateTime;

import veterinaria.entidades.*;

/*
    Formato en el .csv
    ID,FechaHora,Motivo,Diagnostico,Tratamiento,IDMascota,IDConsulta
*/

public class persistenciaConsulta {
    private static Path archivoHistorico;
    private static Path archivoAgendado;

    public static void setArchivoAgendado(Path archivoAgendado) {
        persistenciaConsulta.archivoAgendado = archivoAgendado;
    }

    public static void setArchivoHistorico(Path archivoHistorico) {
        persistenciaConsulta.archivoHistorico = archivoHistorico;
    }

    private static Consulta consultaStringAObjeto(String strConsulta) {
        String[] datos = strConsulta.split(",", -1);

        if(datos.length < 7)
        {
            System.err.println("Formato invalido");
            return null;
        }

        return new Consulta(Integer.valueOf(datos[0]), LocalDateTime.parse(datos[1]), datos[2], datos[3], datos[4], null);
    }

    private static String consultaObjetoAString(Consulta consulta) {
        LinkedList<String> camposConsulta = new LinkedList<String>();
        camposConsulta.add(String.valueOf(consulta.getId()));
        camposConsulta.add(String.valueOf(consulta.getFechaYHora()));
        camposConsulta.add(consulta.getMotivo());
        camposConsulta.add(consulta.getDiagnóstico());
        camposConsulta.add(consulta.getTratamiento());
        camposConsulta.add(String.valueOf(consulta.getMascota().getId()));
        camposConsulta.add(String.valueOf(consulta.getVeterinario().getId()));

        return String.join(",", camposConsulta);
    }

    // Retorna verdadero si no puede actualizar la consulta
    public static boolean actualizarConsulta(Consulta consulta) {
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivoAgendado);
        } catch (Exception e) {
            return true;
        }
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",", -1);
            if(Integer.valueOf(datos[0]) == consulta.getId()) {
                lineas.set(i, consultaObjetoAString(consulta));

                try {
                    Files.write(archivoAgendado, lineas);
                } catch (Exception e) {
                    return true;
                }
                
                return false;
            }
        }

        agregarConsulta(consulta);
        return true;
    }

    // Retorna verdadero si no puede agregar la consulta
    public static boolean agregarConsulta(Consulta consulta) {
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivoAgendado);
        } catch (Exception e) {
            return true;
        }

        lineas.add(consultaObjetoAString(consulta));

        try {
            Files.write(archivoAgendado, lineas);
        } catch (Exception e) {
            return true;
        }
        
        return false;
    }

    // Retorna verdadero si no puede eliminar la consulta
    public static boolean eliminarConsulta(Consulta consulta) {
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivoAgendado);
        } catch (Exception e) {
            return true;
        }
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",", -1);
            if(Integer.valueOf(datos[0]) == consulta.getId()) {
                lineas.remove(i);
                
                try {
                    Files.write(archivoAgendado, lineas);
                } catch (Exception e) {
                    return true;
                }

                return false;
            }
        }
        return true;
    }

    // Retorna una lista vacia si no puede obtener las consultas
    public static LinkedList<Consulta> obtenerConsultasAgendadas(ArrayList<Integer> mascotasIDs, ArrayList<Integer> veterinariosIDs) {
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivoAgendado);
        } catch (Exception e) {
            return new LinkedList<Consulta>();
        }

        LinkedList<Consulta> consultas = new LinkedList<Consulta>();
        
        for (String linea : lineas) {
            Consulta nuevaConsulta = consultaStringAObjeto(linea);
            if(nuevaConsulta != null) {
                consultas.add(nuevaConsulta);
                String[] datos = linea.split(",", -1);
                mascotasIDs.add(Integer.valueOf(datos[5]));
                veterinariosIDs.add(Integer.valueOf(datos[6]));
            }
        }

        return consultas;
    }

    // Retorna una lista vacia si no puede obtener las consultas
    public static LinkedList<Consulta> obtenerConsultasHistoricas(ArrayList<Integer> mascotasIDs, ArrayList<Integer> veterinariosIDs) {
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivoHistorico);
        } catch (Exception e) {
            return new LinkedList<Consulta>();
        }

        LinkedList<Consulta> consultas = new LinkedList<Consulta>();
        
        for (String linea : lineas) {
            Consulta nuevaConsulta = consultaStringAObjeto(linea);
            if(nuevaConsulta != null) {
                consultas.add(nuevaConsulta);
                String[] datos = linea.split(",", -1);
                mascotasIDs.add(Integer.valueOf(datos[5]));
                veterinariosIDs.add(Integer.valueOf(datos[6]));
            }
        }

        return consultas;
    }

    // Retorna verdadero si no se pudo mover la consulta al historico
    public static boolean moverAHistorico(Consulta consulta) {
        if(eliminarConsulta(consulta))
            return true;

        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivoHistorico);
        } catch (Exception e) {
            agregarConsulta(consulta);
            return true;
        }

        lineas.add(consultaObjetoAString(consulta));

        try {
            Files.write(archivoHistorico, lineas);
        } catch (Exception e) {
            agregarConsulta(consulta);
            return true;
        }
        
        return false;
    }
}