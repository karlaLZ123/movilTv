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


public class registrar_tarjeta extends AppCompatActivity {

    // Variables para el registro
    private EditText et_titular;
    private EditText et_noTarjeta;
    private EditText et_fechaVen;
    private EditText et_cvv;
    private Button registrar;
    private String titular;
    private String no_tarjeta;
    private String fechaVen;
    private String cvv;
    private String usuario;

    //Varibles para conexion al sevicio

    private RequestQueue conexionSer;
    private StringRequest peticionSer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_tarjeta);

        usuario = getIntent().getStringExtra("usuario");
        et_titular = findViewById(R.id.et_titular);
        et_noTarjeta = findViewById(R.id.et_noTarjeta);
        et_cvv = findViewById(R.id.et_cvv);
        et_fechaVen = findViewById(R.id.et_fechaVen);

        //Inicializamos la variable de conexion

        conexionSer = Volley.newRequestQueue(this);
    }
    public void registrar(final View V){

        //Validacion de datos
        //ToUpperCase es para pasar la palabra a Mayusculas

        titular = et_titular.getText().toString().toUpperCase();
        fechaVen = et_fechaVen.getText().toString();
        cvv = et_cvv.getText().toString();
        no_tarjeta = et_noTarjeta.getText().toString();

        //Inhabilitar campos

        et_titular.setText("");
        et_titular.setEnabled(false);
        et_noTarjeta.setText("");
        et_noTarjeta.setEnabled(false);
        et_cvv.setText("");
        et_cvv.setEnabled(false);
        et_fechaVen.setText("");
        et_fechaVen.setEnabled(false);

        V.setEnabled(false);


        //Peticion al servicio

        peticionSer = new StringRequest(
                Request.Method.POST,
                "http://192.168.100.9/AppStreming/index.php/servicios/registro_Tarjeta",
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Toast.makeText(registrar_tarjeta.this, response, Toast.LENGTH_SHORT).show();

                        if(response.equals("1")){
                            limpiarCampos(V);
                            startActivity(
                                    new Intent(
                                            registrar_tarjeta.this,
                                            inicio_usuario.class
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
                        Toast.makeText(registrar_tarjeta.this, error.toString(),Toast.LENGTH_SHORT).show();
                        limpiarCampos(V);
                    }
                }
        ){
            //ENVIAR LOS DATOS AL SERVICIO

            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> datos = new HashMap<>();

                datos.put("usuario", usuario);
                datos.put("noTarjeta", no_tarjeta);
                datos.put("cvv", cvv );
                datos.put("fecha_ven", fechaVen);
                datos.put("titular", titular);
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
        et_noTarjeta.setEnabled(true);
        et_titular.setEnabled(true);
        btn.setEnabled(true);
    }
}