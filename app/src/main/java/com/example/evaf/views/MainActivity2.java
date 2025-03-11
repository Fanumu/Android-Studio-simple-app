package com.example.evaf.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.evaf.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private EditText placeholdernombre, placeholderpreci, placeholdercanti, placeholderdesc;
    private Button btnUpdate, btnDelete;
    private FirebaseFirestore db;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        placeholdernombre = findViewById(R.id.placeholdernombre);
        placeholderpreci = findViewById(R.id.placeholderpreci);
        placeholdercanti = findViewById(R.id.placeholdercanti);
        placeholderdesc = findViewById(R.id.placeholderdesc);
        btnUpdate = findViewById(R.id.button);
        btnDelete = findViewById(R.id.button2);

        db = FirebaseFirestore.getInstance();

        // Obtener el ID del producto desde el Intent
        productId = getIntent().getStringExtra("productId");
        loadProductDetails();

        btnUpdate.setOnClickListener(v -> updateProduct());
        btnDelete.setOnClickListener(v -> deleteProduct());
    }

    private void loadProductDetails() {
        // Cargar detalles del producto usando productId
        db.collection("gztshoes").document(productId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        placeholdernombre.setText(documentSnapshot.getString("nombre"));
                        placeholderpreci.setText(documentSnapshot.getString("precio"));
                        placeholdercanti.setText(documentSnapshot.getString("cantidad"));
                        placeholderdesc.setText(documentSnapshot.getString("descripcion"));
                    } else {
                        Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> Log.e("MainActivity2", "Error loading product details", e));
    }

    private void updateProduct() {
        String nombre = placeholdernombre.getText().toString();
        String precio = placeholderpreci.getText().toString();
        String cantidad = placeholdercanti.getText().toString();
        String descripcion = placeholderdesc.getText().toString();

        Map<String, Object> productUpdates = new HashMap<>();
        productUpdates.put("nombre", nombre);
        productUpdates.put("precio", precio);
        productUpdates.put("cantidad", cantidad);
        productUpdates.put("descripcion", descripcion);

        db.collection("gztshoes").document(productId) // Usar productId para actualizar el documento
                .update(productUpdates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity2.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar la actividad después de actualizar
                })
                .addOnFailureListener(e -> Log.e("MainActivity2", "Error updating product", e));
    }

    private void deleteProduct() {
        db.collection("gztshoes").document(productId) // Usar productId para eliminar el documento
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity2.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar la actividad después de eliminar
                })
                .addOnFailureListener(e -> Log.e("MainActivity2", "Error deleting product", e));
    }
}
