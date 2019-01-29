package ca.leomoraes.bakingapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.leomoraes.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterDetailFragment extends Fragment {


    public MasterDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master_detail, container, false);
    }

}
