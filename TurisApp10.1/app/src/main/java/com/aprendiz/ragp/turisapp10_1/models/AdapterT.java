package com.aprendiz.ragp.turisapp10_1.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aprendiz.ragp.turisapp10_1.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdapterT extends RecyclerView.Adapter<AdapterT.Holder>{
    //Declaración de variables
    List<Lugares> lugaresList = new ArrayList<>();
    Context context;
    int item;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void itemClick(int position);
    }

    //Constructor para la utilización de esta clase
    public AdapterT(List<Lugares> lugaresList, Context context, int item) {
        this.lugaresList = lugaresList;
        this.context = context;
        this.item = item;
    }

    public void setMlistener(OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    @NonNull
    //Método para la creacion del holder
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(item,parent,false);
        Holder holder = new Holder(view,mlistener);
        return holder;
    }

    //Método para el llamado de parametros de la clase Holder
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.connectData(lugaresList.get(position));
    }

    //Método para indicar cuandos items se van a mostrar
    @Override
    public int getItemCount() {
        return lugaresList.size();
    }

    //Clase para la creación de items
    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView = itemView.findViewById(R.id.imgItem);
        TextView txtNombre = itemView.findViewById(R.id.txtNombreItem);
        public Holder(View itemView, final OnItemClickListener mlistener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlistener!=null){
                        int position = getLayoutPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mlistener.itemClick(position);
                        }
                    }
                }
            });
        }

        public void connectData(Lugares lugares){
            if (item==R.layout.item_list){
                TextView txtUbicacion = itemView.findViewById(R.id.txtUbicacion);
                TextView txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
                txtUbicacion.setText(lugares.getUbicacion());
                txtDescripcion.setText(lugares.getDescripcionC());
            }

            if (item==R.layout.item_grid){
                TextView txtUbicacion = itemView.findViewById(R.id.txtUbicacion);
                txtUbicacion.setText(lugares.getUbicacion());
            }
            txtNombre.setText(lugares.getNombre());
            Glide.with(context).load(lugares.getUrl()).into(imageView);
        }
    }
}
