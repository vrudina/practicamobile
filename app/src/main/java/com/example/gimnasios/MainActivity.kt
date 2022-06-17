package com.example.gimnasios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gimnasios.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        //metodo para login
        binding.btLogin.setOnClickListener {
            haceLogin();
        }

        
        //metodo para register
        binding.btRegister.setOnClickListener {
            haceRegistrar();
        }

    }
        private fun haceLogin() {
            val email = binding.tfEmail.text.toString()
            val clave = binding.tfPw.text.toString()

            auth.signInWithEmailAndPassword(email,clave).addOnCompleteListener(this){
                    task -> if (task.isSuccessful){
                Log.d("Autenticando", "Autenticado")
                val user = auth.currentUser
                actualiza(user)
            }
            else{
                Log.d("Autenticando", "Fallo")
                Toast.makeText(baseContext,"Fallo",Toast.LENGTH_LONG).show()
                actualiza(null)
            }
            }
        }


        private fun haceRegistrar() {
            val email = binding.tfEmail.text.toString()
            val clave = binding.tfPw.text.toString()
            
            auth.createUserWithEmailAndPassword(email,clave).addOnCompleteListener(this){
                task -> if (task.isSuccessful){
                    Log.d("creando usuario", "Registrado")
                val user = auth.currentUser
                actualiza(user)
                }
                else{
                    Log.d("creando usuario", "Fallo")
                Toast.makeText(baseContext,"Fallo",Toast.LENGTH_LONG).show()
                actualiza(null)
                }
            }
        }

    private fun actualiza(user: FirebaseUser?) {
        if(user != null){
            val intent = Intent(this,Principal::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart(){
        super.onStart()
        val usuario = auth.currentUser
        actualiza(usuario)
    }


}
