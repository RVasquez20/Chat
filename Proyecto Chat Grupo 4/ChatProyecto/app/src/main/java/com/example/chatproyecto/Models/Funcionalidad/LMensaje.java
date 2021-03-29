package com.example.chatproyecto.Models.Funcionalidad;

import com.example.chatproyecto.Models.Firebase.Mensaje;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

/**
 * @author Jero
 */
public class LMensaje {

    private String key;
    private Mensaje mensaje;
    private LUsuario lUsuario;

    public LMensaje(String key, Mensaje mensaje) {
        this.key = key;
        this.mensaje = mensaje;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public  long getCeatedTimestamp(){
        return (long) mensaje.getCreatedTimestamp();
    }

    public LUsuario getlUsuario() {
        return lUsuario;
    }

    public void setlUsuario(LUsuario lUsuario) {
        this.lUsuario = lUsuario;
    }

    public  String fechaDeCreacionDelMensaje(){
        Date date = new Date(getCeatedTimestamp());
        PrettyTime prettyTime = new PrettyTime(new Date(),Locale.getDefault());
        return prettyTime.format(date);


    }
}
