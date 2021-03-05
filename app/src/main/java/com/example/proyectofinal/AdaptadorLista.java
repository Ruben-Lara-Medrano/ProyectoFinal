package com.example.proyectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.ViewHolder> {
    private List<Publicacion> nData;
    private LayoutInflater nInflater;
    private Context context;

    public AdaptadorLista(List<Publicacion> itemList, Context context){
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.nData = itemList;
    }
    @Override
    public int getItemCount () {
        return nData.size();
    }
    @Override
    public AdaptadorLista.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View view = nInflater.from(parent.getContext()).inflate(R.layout.cardview, null, false);
        return new AdaptadorLista.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.transicion_fade));
        holder.binData(nData.get(position));
    }

    public void setItems(List<Publicacion> items){
        nData=items;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagenPerfil;
        TextView textoPublicacion;
        CardView cv;
        ViewHolder(View itemView) {
            super(itemView);
            imagenPerfil = itemView.findViewById(R.id.FotoPerfil);
            textoPublicacion = itemView.findViewById(R.id.TextoPublicacion);
            cv = itemView.findViewById(R.id.cv);
        }

        void binData (final Publicacion item){
            //imagenPerfil.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            textoPublicacion.setText(item.getTextoPublicacion());
        }
    }
}

