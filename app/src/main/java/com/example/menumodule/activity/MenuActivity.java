package com.example.menumodule.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.menumodule.R;
import com.example.menumodule.fragments.MenuFragment;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_menu,
                new MenuFragment()).commit();

    }
}