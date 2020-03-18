package com.example.flatonrent.ownerclasses.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.myProperty.myProperties;
import com.example.flatonrent.ownerclasses.postingNewProperty.postNewProperty1;

public class ownerdashboardfragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root= inflater.inflate(R.layout.ownerdashboardfragment_layout, null);

        (root.findViewById(R.id.btnPostNewProperty)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), postNewProperty1.class));
            }
        });
        (root.findViewById(R.id.btnMyProperties)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), myProperties.class));
            }
        });
        return root;
    }
}
