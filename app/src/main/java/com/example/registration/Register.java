package com.example.registration;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {


    EditText name,password,email;
    Button register;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        name=findViewById(R.id.RegisterName);
        email=findViewById(R.id.RegisterEmail);
        password=findViewById(R.id.RegisterPassword);

         database = FirebaseDatabase.getInstance();


        register=findViewById(R.id.registerAccount);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String nameValue=name.getText().toString().trim();

                String emailValue=email.getText().toString().trim();

                String passwordValue=password.getText().toString().trim();

                if(nameValue.isEmpty()){
                    name.setError("Enter name");
                    name.requestFocus();
                    return;
                }
                if(emailValue.isEmpty()){
                    email.setError("Enter email");
                    email.requestFocus();
                    return;
                }
                if(passwordValue.isEmpty()){
                    password.setError("Enter password");
                    password.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()){
                    email.setError("Email is not correct");
                    email.requestFocus();
                    return;
                }
                if(password.length()<6){
                    password.setError("password is too short ");
                    password.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emailValue,passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            User user= new User(nameValue,passwordValue,emailValue);
                                database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                        setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "User registered!", Toast.LENGTH_SHORT).show();
                                    }
                                });


                        }
                    }
                });


            }
        });


    }
}