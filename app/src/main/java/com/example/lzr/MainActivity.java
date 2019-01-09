package com.example.lzr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity  extends BaseActivity implements View.OnClickListener {


    private EditText etEmail, etPass;
    private TextView uid, mail;

    private Button btnSignIn, btnSignOut;


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFirebase();

        etEmail = (EditText)findViewById(R.id.email);
        etPass = (EditText)findViewById(R.id.password);

        uid = (TextView)findViewById(R.id.uid);
        mail = (TextView)findViewById(R.id.email_text);


        btnSignIn = (Button)findViewById(R.id.login);
        btnSignOut = (Button)findViewById(R.id.sign_out);

        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);


        findViewById(R.id.send_to_fb).setOnClickListener(this);

    }


    private void initFirebase()
    {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void signIn(String email, String pass)
    {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            uid.setText(mAuth.getCurrentUser().getUid());
                            mail.setText(mAuth.getCurrentUser().getEmail());
                        }
                    }
                });
    }

    private void signOut()
    {
        mAuth.signOut();
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.login)
        {
            signIn(etEmail.getText().toString(), etPass.getText().toString());
        }
        if(i == R.id.sign_out)
        {
            signOut();
        }
        if(i == R.id.send_to_fb)
        {
            startActivity(new Intent(MainActivity.this, FacebookActivity.class));
        }
    }
}

