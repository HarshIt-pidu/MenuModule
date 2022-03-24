package com.example.menumodule.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.menumodule.R;
import com.example.menumodule.adapters.menuListAdapter;
import com.example.menumodule.models.MenuListModel;
import com.example.menumodule.utils.BaseFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class MenuDetailsFragment extends BaseFragment{

    private TextView txtMenuTiming, txtMenuName, txtMenuDesc, txtMenuMode;
    private androidx.appcompat.widget.SwitchCompat btnDisableSwitch;
    private MenuListModel menuListModelList;
    public MenuDetailsFragment(MenuListModel menuListModelList){
        this.menuListModelList = menuListModelList;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_menu_details, container, false);

        findViewById(view);

        txtMenuTiming.setText(new StringBuilder("Timing: ").append(menuListModelList.getStarttime() + " - " + menuListModelList.getEndtime()));
        txtMenuName.setText(menuListModelList.getName());
        txtMenuDesc.setText(menuListModelList.getDescription());
        txtMenuMode.setText(new StringBuilder().append(ListMode()));

        if(!menuListModelList.getDisable().equals("1")){
            btnDisableSwitch.setChecked(true);
        }

        return view;
    }

    private String ListMode() {
        String delivery = "";
        String pickup = "";
        String dinein = "";
        String result = "";
        if(menuListModelList.getDelivery().equals("1") ){
            delivery = "Delivery";
        }
        if(menuListModelList.getPickup().equals("1") ){
            pickup = " Pickup";
        }
        if(menuListModelList.getDinein().equals("1") ){
            dinein = " Dining";
        }
        return result = delivery + pickup + dinein;
    }

    private void findViewById(View view){
        txtMenuTiming = view.findViewById(R.id.txtMenuTiming);
        txtMenuName = view.findViewById(R.id.txtMenuName);
        txtMenuDesc = view.findViewById(R.id.txtMenuDesc);
        txtMenuMode = view.findViewById(R.id.txtMenuMode);
        btnDisableSwitch = view.findViewById(R.id.btnDisableSwitch);
    }

}
