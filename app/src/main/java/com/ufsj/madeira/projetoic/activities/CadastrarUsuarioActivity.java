package com.ufsj.madeira.projetoic.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.ufsj.madeira.projetoic.R;
import com.ufsj.madeira.projetoic.config.ConfiguracaoFirebase;
import com.ufsj.madeira.projetoic.helper.Base64Custom;
import com.ufsj.madeira.projetoic.helper.Preferencias;
import com.ufsj.madeira.projetoic.model.Usuario;

public class CadastrarUsuarioActivity extends AppCompatActivity {
    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        databaseReference = ConfiguracaoFirebase.getFirebase();
        nome = (EditText) findViewById(R.id.cadastroNomeEditText);
        email = (EditText) findViewById(R.id.cadastroEmailEditText);
        senha = (EditText) findViewById(R.id.cadastroSenhaEditText);
        botaoCadastrar = (Button) findViewById(R.id.cadastroButton);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                boolean valido = false;
                String emailTemp = email.getText().toString();
                String nomeTemp = nome.getText().toString();
                String senhaTemp = senha.getText().toString();
                if(!emailTemp.isEmpty() && !nomeTemp.isEmpty() && !senhaTemp.isEmpty()){
                    usuario.setEmail(emailTemp);
                    usuario.setNome(nomeTemp);
                    usuario.setSenha(senhaTemp);
                    cadastrarUsuario();
                }else{
                    Toast.makeText(CadastrarUsuarioActivity.this,"Favor preencher todos os três campos.",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void cadastrarUsuario() {
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(CadastrarUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(idUsuario);
                    usuario.salvarDados();

                    Preferencias preferencias = new Preferencias(CadastrarUsuarioActivity.this);
                    preferencias.salvarDados(idUsuario);


                    Toast.makeText(CadastrarUsuarioActivity.this,"Usuário cadastrado com sucesso!",Toast.LENGTH_LONG).show();
                    abrirTelaLogin();
                }else{
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        Toast.makeText(CadastrarUsuarioActivity.this,"Erro ao cadastrar usuário: Senha fraca.",Toast.LENGTH_LONG).show();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(CadastrarUsuarioActivity.this,"Erro ao cadastrar usuário: Email inválido.",Toast.LENGTH_LONG).show();
                    }catch (FirebaseAuthUserCollisionException e){
                        Toast.makeText(CadastrarUsuarioActivity.this,"Erro ao cadastrar usuário: Esse email já está cadastrado.",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CadastrarUsuarioActivity.this,"Erro ao cadastrar usuário.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public void abrirTelaLogin(){
        startActivity(new Intent(CadastrarUsuarioActivity.this,MainActivity.class));
        finish();
    }
}

