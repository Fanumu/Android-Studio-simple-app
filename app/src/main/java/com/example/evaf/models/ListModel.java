package com.example.evaf.models;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.evaf.dtos.productos;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListModel {
    private final DatabaseReference firedb = FirebaseDatabase.getInstance().getReference("gztshoes");

//No use esto al final pero me da miedo eliminar cosas c:
};