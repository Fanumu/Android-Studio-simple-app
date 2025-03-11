package com.example.evaf.views;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evaf.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class fragcrear extends Fragment {
    public fragcrear() {
        super(R.layout.fragcrear);
    }

    EditText produnombre, produprice, producanti, produdesc;
    Button btncrear;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageButton imgbtn;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        produnombre = requireView().findViewById(R.id.produnombre);
        produprice = requireView().findViewById(R.id.produprice);
        producanti = requireView().findViewById(R.id.producanti);
        produdesc = requireView().findViewById(R.id.produdesc);
        btncrear = requireView().findViewById(R.id.btncrear);


        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Produnombre = produnombre.getText().toString().trim();
                String Produprice = produprice.getText().toString().trim();
                String Producanti = producanti.getText().toString().trim();
                String Produdesc = produdesc.getText().toString().trim();

                // Validate input fields
                if (Produnombre.isEmpty() || Produprice.isEmpty() || Producanti.isEmpty() || Produdesc.isEmpty()) {
                    Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Generate a unique ID for the new product
                String productId = db.collection("gztshoes").document().getId(); // Firestore generates a unique ID

                // Create a new product map
                Map<String, Object> gztshoes = new HashMap<>();
                gztshoes.put("id", productId); // Add the unique ID
                gztshoes.put("nombre", Produnombre); // Assign the name
                gztshoes.put("precio", Produprice); // Assign the price
                gztshoes.put("cantidad", Producanti); // Assign the quantity
                gztshoes.put("descripcion", Produdesc); // Assign the description

                // Add a new document with the unique ID
                db.collection("gztshoes")
                        .document() // Use the generated ID as the document ID
                        .set(gztshoes) // Use set() to store the data
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Ingresado con exito ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Algo salió mal ", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Error adding document", e);
                            }
                        });
            }
        });
    }
}
