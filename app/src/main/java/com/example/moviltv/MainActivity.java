package com.example.moviltv;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

    private Button iniciar;
    private Button registrar;

    private EditText etUsuario;
    private EditText etPassword;

    private String usuario;
    private String password;

    //Varibles para conexion al sevicio

    private RequestQueue conexionSer;
    private StringRequest peticionSer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.et_usuario);
        etPassword = findViewById(R.id.et_pass);

        iniciar = findViewById(R.id.B_login);
        //Inicializamos la variable de conexion

        conexionSer = Volley.newRequestQueue(this);
    }


    public void iniciar(final View v) {

        usuario = etUsuario.getText().toString().toUpperCase();
        password = etPassword.getText().toString();


        peticionSer = new StringRequest(
                Request.Method.POST, "http://192.168.100.9/AppStreming/index.php/servicios/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("0")) {
                            startActivity(
                                    new Intent(
                                            MainActivity.this,
                                            inicio_admi.class


                                    )
                                            .putExtra("usuario", usuario)
                            );

                        }else if(response.equals("1")) {
                            startActivity(
                                    new Intent(
                                            MainActivity.this,
                                            inicio_admi.class


                                    )
                                            .putExtra("usuario", usuario)
                            );
                        }else if(response.equals("3")){
                            Toast.makeText(getApplicationContext(),
                                    "USUARIO O PASSWORD INCORRECTOS", Toast.LENGTH_SHORT).show();
                        }else{

                            Toast.makeText(getApplicationContext(),
                                    "Llenar todos los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }

        ) {// peticion

            //enviar datos
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datos = new HashMap<>();

                datos.put("usuario", usuario);
                datos.put("contrasena", password);
                return datos;
            }

        };

        peticionSer.setRetryPolicy(new DefaultRetryPolicy(
                0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        //Ejecutar el servicio

        conexionSer.add(peticionSer);

    }
    public void registrar(View v){

        Button registrar = (Button)findViewById(R.id.B_reg);
        startActivity(
                new Intent(
                        MainActivity.this,
                        registro_usuario.class
                )
        );


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }
}