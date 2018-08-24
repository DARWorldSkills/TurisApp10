package com.aprendiz.ragp.turisapp10_1.fragmets;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aprendiz.ragp.turisapp10_1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SitiosFragment extends Fragment {


    public SitiosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sitios, container, false);
    }

}
