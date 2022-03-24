package com.example.menumodule.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.menumodule.R;
import com.example.menumodule.fragments.MenuFragment;
import com.example.menumodule.fragments.MyThemeShareFragment;

public class ThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_theme,
                new MyThemeShareFragment()).commit();

    }
}