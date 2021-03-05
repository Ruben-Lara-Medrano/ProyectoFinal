package com.example.proyectofinal.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.ItemAnimation;
import com.example.proyectofinal.Perfil;
import com.example.proyectofinal.R;
import com.example.proyectofinal.pojos.DireccionesBd;
import com.example.proyectofinal.pojos.Usuario;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorBarraDeBusqueda extends RecyclerView.Adapter<AdaptadorBarraDeBusqueda.RecyclerviewHolder> {

    Context context;
    List<Usuario> usuarioList;
    List<Usuario> filteredUsuarioList;
    RequestQueue requestQueue;
    DireccionesBd direciones = new DireccionesBd();
    public AdaptadorBarraDeBusqueda(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
        this.filteredUsuarioList = usuarioList;
        requestQueue= Volley.newRequestQueue(context.getApplicationContext());

    }

    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_item, parent, false);
        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, final int position) {

        holder.userName.setText(filteredUsuarioList.get(position).getNombre());
        holder.userDesc.setText(filteredUsuarioList.get(position).getPuesto());


        ItemAnimation.animateFadeIn(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//metodo para el onclick en cada card del buscador
                Intent intent = new Intent(context, Perfil.class);
               intent.putExtra("usuarioId", filteredUsuarioList.get(position).getId());

               //intent.putExtra("userDesc", filteredUsuarioList.get(position).getDescp());
                context.startActivity(intent);
            }
        });

        /*holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "User Name Clicked", Toast.LENGTH_SHORT).show();

            }
        });*/


    }


    @Override
    public int getItemCount() {
        return filteredUsuarioList.size();
    }

    public  class RecyclerviewHolder extends RecyclerView.ViewHolder {


        CircleImageView userImage;
        TextView userName, userDesc;

        public RecyclerviewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.NombreUsuario);
            userDesc = itemView.findViewById(R.id.DescripcionUsuario);


        }
       /* void bindata (final Usuario sacarUsuario){
        cargarImagen(userImage, direciones.actualizarFoto()+sacarUsuario.getImagen());
        }*/
    }
   /* private void cargarImagen(CircleImageView imagenPerfil, String url){
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imagenPerfil.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Poner una imagen por defecto
            }
        });
        requestQueue.add(imageRequest);
    }*/
    public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String Key = charSequence.toString();
                if(Key.isEmpty()){
                    filteredUsuarioList = usuarioList;
                }
                else{

                    List<Usuario> lstFiltered = new ArrayList<>();
                    for(Usuario row: usuarioList){
                        if(row.getNombre().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);

                        }
                    }

                    filteredUsuarioList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUsuarioList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filteredUsuarioList = (List<Usuario>)filterResults.values;
                notifyDataSetChanged();

            }
        };

    }


}
