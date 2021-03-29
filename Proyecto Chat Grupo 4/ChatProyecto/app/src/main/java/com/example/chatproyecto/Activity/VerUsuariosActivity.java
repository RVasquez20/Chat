package com.example.chatproyecto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatproyecto.Models.Firebase.Usuario;
import com.example.chatproyecto.Models.Funcionalidad.LUsuario;
import com.example.chatproyecto.Holder.UsuarioViewHolder;
import com.example.chatproyecto.R;
import com.example.chatproyecto.Invariables.Invariable;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * @autor Ericka
 */
public class VerUsuariosActivity extends AppCompatActivity {

    private RecyclerView rvUsuarios;
    private  FirebaseRecyclerAdapter adapter;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuarios);
        rvUsuarios = findViewById(R.id.rvUsuarios);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvUsuarios.setLayoutManager(linearLayoutManager);


        Query query = FirebaseDatabase.getInstance().getReference().child(Invariable.TABLA_USERS);

        FirebaseRecyclerOptions<Usuario> options =new FirebaseRecyclerOptions.Builder<Usuario>().setQuery(query, Usuario.class).build();

        adapter = new FirebaseRecyclerAdapter<Usuario, UsuarioViewHolder>(options) {
            @Override
            public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_usuarios, parent, false);
                return new UsuarioViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(UsuarioViewHolder holder, int position, Usuario model) {
                if (!model.getCorreo().equals(user.getEmail())) {
                    Glide.with(VerUsuariosActivity.this).load(model.getFotoPerfilURL()).into(holder.getCivFotoPerfil());
                    holder.getTxtNombreUsuario().setText(model.getNombre());

                    final LUsuario lUsuario = new LUsuario(getSnapshots().getSnapshot(position).getKey(), model);

                    holder.getLayoutPrincipal().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(VerUsuariosActivity.this, MensajeriaActivity.class);
                            intent.putExtra("key_receptor", lUsuario.getKey());
                            startActivity(intent);
                        }
                    });

                }else{
                    Glide.with(VerUsuariosActivity.this).load(model.getFotoPerfilURL()).into(holder.getCivFotoPerfil());
                    holder.getTxtNombreUsuario().setText(model.getNombre());
                    /*Random rnd = new Random();
                    int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    holder.itemView.setBackgroundColor(currentColor);*/
                    holder.itemView.setBackgroundResource(R.drawable.carduser);
                    holder.getLayoutPrincipal().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(VerUsuariosActivity.this, "No Puedes Chatear Contigo Mismo", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
        rvUsuarios.setAdapter(adapter);

        

       
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_cerrar: {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Cerrando Sesion...", Toast.LENGTH_SHORT).show();
                returnLogin();
            }

            case R.id.item_Grupo: {
                startActivity(new Intent(VerUsuariosActivity.this, MensajeriaGrupoActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void returnLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
