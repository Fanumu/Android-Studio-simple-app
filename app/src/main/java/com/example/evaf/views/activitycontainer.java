package com.example.evaf.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.evaf.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activitycontainer extends AppCompatActivity {

    TabLayout tabLayout;
    FloatingActionButton btnlogout;
    FirebaseAuth auth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activitycontainer);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.actconte), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });


        tabLayout = findViewById(R.id.tabLayout);

        btnlogout = findViewById(R.id.btnlogout);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null){
            Intent intent= new Intent(activitycontainer.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FirebaseAuth.getInstance().signOut();
                Toast.makeText(activitycontainer.this, "Se ha cerrado session", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(activitycontainer.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Cuando seleccionamos un tab
                switch (tab.getPosition()) {
                    case 0:
                        //Lista
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainerView, Listfg.class, null)
                                .commit();
                        break;

                    case 1: //Create
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainerView, fragcrear.class, null)
                                .commit();
                        break;

                    default:
                        //Lista
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Cuando se deselecciona una pestaña
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Acción al volver a seleccionar un tab
            }
        });
    }
}
