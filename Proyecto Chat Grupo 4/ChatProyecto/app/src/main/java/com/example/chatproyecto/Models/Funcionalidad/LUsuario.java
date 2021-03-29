package com.example.chatproyecto.Models.Funcionalidad;

import com.example.chatproyecto.Models.Firebase.Usuario;
import com.example.chatproyecto.DAOS.UsuarioDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LUsuario {

    private Usuario usuario;
    private String key;

    public LUsuario(String key,Usuario usuario) {
        this.key = key;
        this.usuario = usuario;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String obtenerFechaDeCreacion(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(UsuarioDAO.getInstance().fechaDeCreacionLong());
        return simpleDateFormat.format(date);
    }

    public String obtenerFechaDeUltimaVezQueSeLogeo(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(UsuarioDAO.getInstance().fechaDeUltimaVezQueSeLogeoLong());
        return simpleDateFormat.format(date);
    }

}
