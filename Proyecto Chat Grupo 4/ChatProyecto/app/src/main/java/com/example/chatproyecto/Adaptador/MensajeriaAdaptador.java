package com.example.chatproyecto.Adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.chatproyecto.Models.Funcionalidad.LMensaje;
import com.example.chatproyecto.Models.Funcionalidad.LUsuario;
import com.example.chatproyecto.Holder.MensajeriaHolder;
import com.example.chatproyecto.DAOS.UsuarioDAO;
import com.example.chatproyecto.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rodrigo
 */
public class MensajeriaAdaptador extends RecyclerView.Adapter<MensajeriaHolder> {

    private List<LMensaje> listMensaje = new ArrayList<>();
    private Context c;

    public MensajeriaAdaptador(Context c) {
        this.c = c;
    }

    public int addMensaje(LMensaje lMensaje){
        listMensaje.add(lMensaje);
        int posicion = listMensaje.size()-1;
        notifyItemInserted(listMensaje.size());
        return posicion;
    }

    public void actualizarMensaje(int posicion, LMensaje lMensaje){
        listMensaje.set(posicion, lMensaje);
        notifyItemChanged(posicion);
    }

    @Override
    public MensajeriaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType==1){
            view = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes_emisor,parent,false);
        }else{
            view = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes_receptor,parent,false);
        }
        return new MensajeriaHolder(view);
    }

    @Override
    public void onBindViewHolder(MensajeriaHolder holder, int position) {

        LMensaje lMensaje = listMensaje.get(position);
        LUsuario lUsuario = lMensaje.getlUsuario();

        if(lUsuario!=null){
            holder.getNombre().setText(lUsuario.getUsuario().getNombre());
            Glide.with(c).load(lUsuario.getUsuario().getFotoPerfilURL()).into(holder.getFotoMensajePerfil());

        }


        holder.getMensaje().setText(lMensaje.getMensaje().getMensaje());

        if(lMensaje.getMensaje().isContieneFoto()){
            holder.getMensaje().setVisibility(View.VISIBLE);
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(lMensaje.getMensaje().getUrlFoto()).into((holder.getFotoMensaje()));
        }else {
            holder.getMensaje().setVisibility(View.VISIBLE);
            holder.getFotoMensaje().setVisibility(View.GONE);
        }

        holder.getHora().setText(lMensaje.fechaDeCreacionDelMensaje());
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (listMensaje.get(position).getlUsuario()!=null){
            if(listMensaje.get(position).getlUsuario().getKey().equals(UsuarioDAO.getInstance().getKeyUsuario())){
                return 1;
            }else {
                return -1;
            }
        }else{
            return -1;
        }

    }
}
