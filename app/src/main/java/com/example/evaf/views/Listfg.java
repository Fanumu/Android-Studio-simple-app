package com.example.evaf.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.evaf.R;
import com.example.evaf.dtos.productos;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Listfg extends Fragment {

    private LinearLayout LLvert;
    private FirebaseFirestore db;
    private List<productos> productosList;

    public Listfg() {
        super(R.layout.fraglista);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fraglista, container, false);
        LLvert = view.findViewById(R.id.LLvert);
        db = FirebaseFirestore.getInstance();
        productosList = new ArrayList<>();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchProductsFromFirestore();
    }

    private void fetchProductsFromFirestore() {
        db.collection("gztshoes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        productosList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            productos produ = document.toObject(productos.class);
                            produ.setId(document.getId());
                            productosList.add(produ);
                        }
                        displayProducts();
                    } else {
                        Log.w("Listfg", "Error getting documents.", task.getException());
                    }
                });
    }

    private void displayProducts() {
        LLvert.removeAllViews(); // Limpiar vistas existentes
        for (productos produ : productosList) {
            CardView productoscard = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.cardlist, LLvert, false);

            TextView txtproductname = productoscard.findViewById(R.id.txtproductname);
            TextView txtproductdesc = productoscard.findViewById(R.id.txtproductprice);

            if (txtproductname != null) {
                txtproductname.setText(produ.getNombre());
            }

            if (txtproductdesc != null) {
                txtproductdesc.setText(produ.getDescripcion());
            }

            MaterialButton btnproducto = productoscard.findViewById(R.id.btnproducto);
            if (btnproducto != null) {
                btnproducto.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Entrando a detalles ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Listfg.this.getActivity(), MainActivity2.class);
                    intent.putExtra("nombre", produ.getNombre());
                    intent.putExtra("cantidad", produ.getCantidad());
                    intent.putExtra("precio", produ.getPrecio());
                    intent.putExtra("descripcion", produ.getDescripcion());
                    intent.putExtra("productId", produ.getId());
                    startActivity(intent);
                });
            }

            LLvert.addView(productoscard);
        }
    }
}
