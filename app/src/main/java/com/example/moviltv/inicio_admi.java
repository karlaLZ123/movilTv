package com.example.moviltv;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class inicio_admi extends AppCompatActivity {

    private Button notificacion;

    private EditText etCantidad;
    private String usuario;
    private String cantidad;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    private RequestQueue conexionSer;
    private StringRequest peticionSer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_admi);


        etCantidad = findViewById(R.id.et_cantidad);

        notificacion = findViewById(R.id.b_noti);
        //Inicializamos la variable de conexion

        conexionSer = Volley.newRequestQueue(this);

        usuario = getIntent().getStringExtra("usuario");
    }

    public void notificacion(final View v) {

        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);

        //se agrega la parte del reloj
        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().setHintHideIcon(true);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Felidades !");
        builder.setContentText("Haz llegado a tu meta!");
        builder.setColor(Color.BLACK);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.BLUE, 1000, 1000);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        //se vincula con el reloj
        builder.extend(wearableExtender);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());

        //al presionar la notificacion te manda a un activityb
        Intent intent = new Intent(inicio_admi.this, inicio_admi.class).putExtra("id_u", usuario);

        PendingIntent pendingIntent = PendingIntent.getActivity(inicio_admi.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICACION_ID, builder.build());

    }
//Metodo

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}