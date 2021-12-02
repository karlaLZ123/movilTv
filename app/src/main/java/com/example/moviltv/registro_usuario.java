package com.example.moviltv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class registro_usuario extends AppCompatActivity {

    // Variables para el registro
    private EditText etcorreo;
    private EditText etPass;
    private EditText etUsuario;
    private EditText etconPass;
    private Button registrar;
    private String usuario;
    private String pass;
    private String correo;
    private String conPass;

    //Varibles para conexion al sevicio

    private RequestQueue conexionSer;
    private StringRequest peticionSer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);

        etcorreo = findViewById(R.id.et_correo);
        etPass = findViewById(R.id.et_pass);
        etUsuario = findViewById(R.id.et_usuario);
        etconPass = findViewById(R.id.et_conpass);

        //Inicializamos la variable de conexion

        conexionSer = Volley.newRequestQueue(this);
    }
    public void registrar(final View V){

        //Validacion de datos
        //ToUpperCase es para pasar la palabra a Mayusculas

        usuario = etUsuario.getText().toString().toUpperCase();
        pass = etPass.getText().toString();
        conPass = etconPass.getText().toString();
        correo = etcorreo.getText().toString();

        //Inhabilitar campos

        etUsuario.setText("");
        etUsuario.setEnabled(false);
        etconPass.setText("");
        etUsuario.setEnabled(false);

        V.setEnabled(false);


        //Peticion al servicio

        peticionSer = new StringRequest(
                Request.Method.POST,
                "http://192.168.100.9/AppStreming/index.php/servicios/registro_Usuario",
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Toast.makeText(registro_usuario.this, response, Toast.LENGTH_SHORT).show();

                        if(response.equals("1")){
                            limpiarCampos(V);
                            startActivity(
                                    new Intent(
                                            registro_usuario.this,
                                            registrar_tarjeta.class
                                    )
                                            .putExtra("usuario", usuario)
                            );
                        }else if(response.equals("0")){
                            Toast.makeText(getApplicationContext(),
                                    "Las contrasenas deben coincidir", Toast.LENGTH_SHORT).show();

                        }else if(response.equals(2)){
                            Toast.makeText(getApplicationContext(),
                                    "Ingresa un usuario diferente", Toast.LENGTH_SHORT).show();

                        }else if(response.equals(3)){
                            Toast.makeText(getApplicationContext(),
                                    "Ingresa un correo diferente", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Mostrar error
                        Toast.makeText(registro_usuario.this, error.toString(),Toast.LENGTH_SHORT).show();
                        limpiarCampos(V);
                    }
                }
        ){
            //ENVIAR LOS DATOS AL SERVICIO

            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> datos = new HashMap<>();

                datos.put("correo", correo);
                datos.put("nombreUsuario", usuario);
                datos.put("conpass", pass);
                datos.put("password", conPass);
                return datos;
            }
        };

        //Evitar que el registro se duplique

        peticionSer.setRetryPolicy(new DefaultRetryPolicy(
                0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        //Ejecutar el servicio

        conexionSer.add(peticionSer);
    }

    public void limpiarCampos(View btn) {
        etUsuario.setEnabled(true);
        etPass.setEnabled(true);
        btn.setEnabled(true);
    }
}