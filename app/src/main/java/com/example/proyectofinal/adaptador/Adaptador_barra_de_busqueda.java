package com.example.proyectofinal.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinal.ItemAnimation;
import com.example.proyectofinal.R;
import com.example.proyectofinal.DetallesUsuario;
import com.example.proyectofinal.modelo.InformacionUsuario;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador_barra_de_busqueda extends RecyclerView.Adapter<Adaptador_barra_de_busqueda.RecyclerviewHolder> {

    Context context;
    List<InformacionUsuario> informacionUsuarioList;
    List<InformacionUsuario> filteredInformacionUsuarioList;

    public Adaptador_barra_de_busqueda(Context context, List<InformacionUsuario> informacionUsuarioList) {
        this.context = context;
        this.informacionUsuarioList = informacionUsuarioList;
        this.filteredInformacionUsuarioList = informacionUsuarioList;
    }

    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_item, parent, false);
        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, final int position) {

        holder.userName.setText(filteredInformacionUsuarioList.get(position).getUserName());
        holder.userDesc.setText(filteredInformacionUsuarioList.get(position).getDescp());
        holder.userImage.setImageResource(filteredInformacionUsuarioList.get(position).getImageUrl());

        ItemAnimation.animateFadeIn(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//metodo para el onclick en cada card del buscador
                Intent intent = new Intent(context, DetallesUsuario.class);
                intent.putExtra("username", filteredInformacionUsuarioList.get(position).getUserName());
                intent.putExtra("userDesc", filteredInformacionUsuarioList.get(position).getDescp());
                context.startActivity(intent);
                }
        });

        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "User Name Clicked", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return filteredInformacionUsuarioList.size();
    }

    public static final class RecyclerviewHolder extends RecyclerView.ViewHolder {


        CircleImageView userImage;
        TextView userName, userDesc;

        public RecyclerviewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.NombreUsuario);
            userDesc = itemView.findViewById(R.id.DescripcionUsuario);


        }
    }

    public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String Key = charSequence.toString();
                if(Key.isEmpty()){
                    filteredInformacionUsuarioList = informacionUsuarioList;
                }
                else{

                    List<InformacionUsuario> lstFiltered = new ArrayList<>();
                    for(InformacionUsuario row: informacionUsuarioList){
                        if(row.getUserName().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);

                        }
                    }

                    filteredInformacionUsuarioList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredInformacionUsuarioList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filteredInformacionUsuarioList = (List<InformacionUsuario>)filterResults.values;
                notifyDataSetChanged();

            }
        };

    }


}
