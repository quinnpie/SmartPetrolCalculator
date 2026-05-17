package com.example.smartpetrolcalculator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottomNavigation);

        // Default Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new HomeFragment())
                .commit();

        // Bottom Navigation Click
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .commit();

                return true;
            }

            else if (item.getItemId() == R.id.nav_calculator) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new CalculatorFragment())
                        .commit();

                return true;
            }

            else if (item.getItemId() == R.id.nav_about) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new AboutFragment())
                        .commit();

                return true;
            }

            return false;
        });
    }
}