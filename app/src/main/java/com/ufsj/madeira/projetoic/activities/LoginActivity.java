package com.ufsj.madeira.projetoic.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;
import com.ufsj.madeira.projetoic.R;
import com.ufsj.madeira.projetoic.config.ConfiguracaoFirebase;
import com.ufsj.madeira.projetoic.helper.Base64Custom;
import com.ufsj.madeira.projetoic.helper.Permissao;
import com.ufsj.madeira.projetoic.helper.Preferencias;
import com.ufsj.madeira.projetoic.model.Usuario;

public class LoginActivity extends Activity {
    private DatabaseReference firebaseReference;
    private FirebaseAuth firebaseAuth;
    private Usuario usuario;
    //private DatabaseReference userReference = firebaseReference.child("usuarios");
    private Button botao;
    private int cont=0;
    private int ee=0;
    private boolean ativar_ee = true;
    private EditText email;
    private EditText senha;
    private String emailTempo="";
    private String senhaTempo="";
    private Button creditos;
    private Button avaliacao;
    private Button sql;
    private SQLiteDatabase bancoDados;
    private TextView cadastrar;
    private String[] permissoesNecessarias = new String[]{
            android.Manifest.permission.INTERNET,android.Manifest.permission.SEND_SMS
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        verificarUsuarioLogado();
        //firebaseReference = ConfiguracaoFirebase.getFirebase();
        Permissao.validarPermissoes(1,this,permissoesNecessarias);
        //firebaseReference.child("teste").setValue("comevaiem");
        botao = (Button) findViewById(R.id.entrarButton);
        email = (EditText) findViewById(R.id.emailTextField);
        cadastrar = (TextView) findViewById(R.id.cadastrarTextView);
        senha = (EditText) findViewById(R.id.senhaTextField);
        //creditos = (Button) findViewById(R.id.buttonCreditos);
        //avaliacao = (Button) findViewById(R.id.buttonAvaliacao);
        //sql = (Button) findViewById(R.id.buttonSQL);

        botao.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                //startActivity(new Intent(MainActivity.this,SecondActivity.class));
                cont++;
                ee=0;
                ativar_ee=false;
                emailTempo = email.getText().toString();
                senhaTempo = senha.getText().toString();
                usuario = new Usuario();
                usuario.setSenha(senhaTempo);
                usuario.setEmail(emailTempo);
                try {
                    if(!emailTempo.equals("") && !senhaTempo.equals("")) {
                        validarUsuario();

                    }else{
                        Toast.makeText(LoginActivity.this, "Favor preencher os campos email e senha.", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Erro ao fazer Login.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,CadastrarUsuarioActivity.class));
            }
        });

    }

    public void validarUsuario(){
        firebaseReference = ConfiguracaoFirebase.getFirebase();
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail().toString(),usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    preferencias.salvarDados(idUsuario);
                    abrirMenuPrincipal();
                }else{
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        Toast.makeText(LoginActivity.this, "Erro ao fazer Login: Email não cadastrado ou inexistente.",Toast.LENGTH_SHORT).show();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(LoginActivity.this, "Erro ao fazer Login: Senha incorreta.",Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "Erro ao fazer Login.",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void abrirMenuPrincipal(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void verificarUsuarioLogado(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(firebaseAuth.getCurrentUser() != null){
            abrirMenuPrincipal();
        }
    }

}

