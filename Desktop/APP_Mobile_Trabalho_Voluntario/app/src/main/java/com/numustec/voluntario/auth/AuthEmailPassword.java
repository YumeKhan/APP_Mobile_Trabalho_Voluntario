package com.numustec.voluntario.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.numustec.voluntario.R;
import com.numustec.voluntario.post.PostListActivity;

public class AuthEmailPassword extends AppCompatActivity {

    EditText etEmail,etPass;
    LinearLayout layoutLoading,layoutInput;
    FirebaseAuth mauth;
    CardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_email_password);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass  = (EditText)findViewById(R.id.etPassword);
        view = (CardView)findViewById(R.id.inLoading);
        layoutLoading = (LinearLayout)view.findViewById(R.id.layoutLoading);
        layoutInput = (LinearLayout)findViewById(R.id.layoutInput);
        mauth = FirebaseAuth.getInstance();
        layoutLoading.setVisibility(View.GONE);
        layoutInput.setVisibility(View.VISIBLE);
    }

    public void login(View view){
        if(etEmail.getText().toString().length() > 5 && etPass.getText().toString().length() > 4){
            layoutLoading.setVisibility(View.VISIBLE);
            layoutInput.setVisibility(View.GONE);
            mauth.signInWithEmailAndPassword(etEmail.toString(),etPass.toString())
                    .addOnFailureListener(e -> {
                        Toast.makeText(getBaseContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    })
                    .addOnSuccessListener(authResult -> {
                        Intent intent = new Intent(this, PostListActivity.class);
                        startActivity(intent);
                    })
                    .addOnCompleteListener(task -> {
                        layoutLoading.setVisibility(View.GONE);
                        layoutInput.setVisibility(View.VISIBLE);
                    });
        }
    }

    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}