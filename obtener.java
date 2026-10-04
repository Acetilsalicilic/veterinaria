

public class obtener {
    public static int dia(String fecha){
        String dia = "";
        dia =dia+fecha.charAt(0) + fecha.charAt(1);
        int diaI =Integer.parseInt(dia);
        return diaI;
    }

    public static int mes(String fecha){
        String mes = "";
        mes =mes+fecha.charAt(3)+fecha.charAt(4);
        int mesI =Integer.parseInt(mes);
        return mesI;
        
    }

    public static int año(String fecha){
        String año = "";
        año =año+ fecha.charAt(6) +fecha.charAt(7);
        int añoI =Integer.parseInt(año);
        añoI+=2000;
        return añoI;
    }

     public static int hora(String time){
        String hora = "";
        hora =hora+time.charAt(0)+time.charAt(1);
        int horaI =Integer.parseInt(hora);
        return horaI;
    }

    public static int min(String time){
        String min = "";
        min =min+time.charAt(3)+time.charAt(4);
        int minI =Integer.parseInt(min);
        return minI;
    }
}
