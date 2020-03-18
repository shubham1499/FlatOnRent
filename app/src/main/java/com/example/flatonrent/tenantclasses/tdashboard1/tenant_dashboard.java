package com.example.flatonrent.tenantclasses.tdashboard1;

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

public class tenant_dashboard extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root= inflater.inflate(R.layout.activity_tenant_dashboard, null);
        return root;
    }
}
