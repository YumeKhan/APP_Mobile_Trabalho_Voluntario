package com.numustec.voluntario.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.numustec.voluntario.HomeActivity;
import com.numustec.voluntario.R;
import com.numustec.voluntario.post.PostListActivity;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail,etPass1,etPass2,etName;
    LinearLayout layoutLoading,layoutInput;
    FirebaseAuth mauth;
    FirebaseFirestore db;
    CardView view;

    String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        etName  = (EditText)findViewById(R.id.etName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass1  = (EditText)findViewById(R.id.etPassword1);
        etPass2  = (EditText)findViewById(R.id.etPassword2);
        view = (CardView)findViewById(R.id.inLoading);
        layoutLoading = (LinearLayout)view.findViewById(R.id.layoutLoading);
        layoutInput = (LinearLayout)findViewById(R.id.layoutInput);
        mauth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        layoutLoading.setVisibility(View.GONE);
        layoutInput.setVisibility(View.VISIBLE);
    }

    public void register(View view) {
        String email = etEmail.getText().toString();
        String pass1 = etPass1.getText().toString();
        String pass2 = etPass2.getText().toString();
        String name = etName.getText().toString();
        if (email.length() > 5 && pass1.length() > 4 && pass2.length() > 4 && name.length() > 6) {
            if (pass2.contains(pass1)) {
                layoutLoading.setVisibility(View.VISIBLE);
                mauth.createUserWithEmailAndPassword(
                        email,
                        pass1)
                .addOnFailureListener(e -> {
                    Toast.makeText(getBaseContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                })
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG,"Resultado => "+authResult.toString());
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("uuid_login", authResult.getUser().getUid());
                    db.collection("usuarios")
                            .add(user)
                            .addOnCompleteListener(task -> {
                                Log.d(TAG, "DocumentSnapshot successfully written");
                                Toast.makeText(getBaseContext(),getString(R.string.register_save),Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(this, HomeActivity.class);
                                startActivity(intent);
                            })
                    ;
                })
                .addOnCompleteListener(task -> {
                      layoutLoading.setVisibility(View.GONE);
                      layoutInput.setVisibility(View.VISIBLE);
                });
            }
            else{
                Log.d(TAG,"Segundo else  pass1 => "+pass1+" pass2 => "+pass2);
            }
        }
        else{
            Log.d(TAG,"Primeiro else => {name:"+name+",email:"+email+",pass1:"+pass1+",pass2:"+pass2);
        }
    }

    public void login(View view){
        Intent intent = new Intent(this, AuthEmailPassword.class);
        startActivity(intent);
    }
}