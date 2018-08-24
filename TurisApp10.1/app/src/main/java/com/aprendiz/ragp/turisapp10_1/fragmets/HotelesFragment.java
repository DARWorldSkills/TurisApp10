package com.aprendiz.ragp.turisapp10_1.fragmets;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aprendiz.ragp.turisapp10_1.R;
import com.aprendiz.ragp.turisapp10_1.controllers.Detalle;
import com.aprendiz.ragp.turisapp10_1.controllers.MenuT;
import com.aprendiz.ragp.turisapp10_1.controllers.Splash;
import com.aprendiz.ragp.turisapp10_1.models.AdapterT;
import com.aprendiz.ragp.turisapp10_1.models.Lugares;
import com.bumptech.glide.Glide;

import java.util.List;

public class HotelesFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    FloatingActionButton btnMapa;
    int item;
    int position;
    int modo=0;
    Lugares lugares;
    public HotelesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hoteles, container, false);
        inizialite();
        inputAdapter();
        return view;
    }

    //Método para inicializar las vistas
    private void inizialite() {
        recyclerView = view.findViewById(R.id.recyclerView);
        btnMapa = view.findViewById(R.id.btnMapa);
    }

    //Método para ingresar el adapter al recyclerview
    private void inputAdapter() {
        position = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        final List<Lugares> lugaresList = Splash.lugaresList.subList(9,17);
        if (position== Surface.ROTATION_0 || position== Surface.ROTATION_180){
            if (modo==0){

                item = R.layout.item_list;
                AdapterT adapterT = new AdapterT(lugaresList,getContext(),item);
                recyclerView.setAdapter(adapterT);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                recyclerView.setHasFixedSize(true);
                adapterT.setMlistener(new AdapterT.OnItemClickListener() {
                    @Override
                    public void itemClick(int position) {
                        lugares = lugaresList.get(position);
                        MenuT.lugares = lugares;
                        Intent intent = new Intent(getContext(), Detalle.class);
                        startActivity(intent);
                    }
                });

            }else {

                item = R.layout.item_grid;
                AdapterT adapterT = new AdapterT(lugaresList,getContext(),item);
                recyclerView.setAdapter(adapterT);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
                recyclerView.setHasFixedSize(true);
                adapterT.setMlistener(new AdapterT.OnItemClickListener() {
                    @Override
                    public void itemClick(int position) {
                        lugares = lugaresList.get(position);
                        MenuT.lugares = lugares;
                        Intent intent = new Intent(getContext(), Detalle.class);
                        startActivity(intent);
                    }
                });

            }
        }else {
            item = R.layout.item_land;
            AdapterT adapterT = new AdapterT(lugaresList,getContext(),item);
            recyclerView.setAdapter(adapterT);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            recyclerView.setHasFixedSize(true);
            adapterT.setMlistener(new AdapterT.OnItemClickListener() {
                @Override
                public void itemClick(int position) {
                    lugares = lugaresList.get(position);
                    MenuT.lugares = lugares;
                    TextView txtDescripcion = view.findViewById(R.id.txtDescripcionLand);
                    ImageView imgLand = view.findViewById(R.id.imgLand);
                    txtDescripcion.setText(lugares.getDescripcion());
                    Glide.with(getContext()).load(lugares.getUrl()).into(imgLand);
                }
            });
        }
    }

    //Método para cambiar el modo del item
    public void changeItem(){
        switch (modo){
            case 1:
                modo=2;
                inputAdapter();
                break;
            case 2:
                modo=1;
                inputAdapter();
                break;
        }
    }


}
