package com.example.m8uf2avaluable2radio;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;

public class StreamingService extends Service {

    private static final String CHANNEL_ID = "channel1"; // Cambiado de "canal1" a "channel1"
    private MediaPlayer player;
    PendingIntent pendingIntent;
    // URL EMISORA
    private String radioURL;
    // Nombre emisora
    private String nombre;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        radioURL = intent.getStringExtra("streaming_url");
        nombre = intent.getStringExtra("nombre");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificarServicio();
        } else {
            newNotificarServicio();
        }

        // Inicializar y preparar el MediaPlayer
        this.player = new MediaPlayer();
        try {
            player.setDataSource(radioURL);
            player.prepareAsync(); // Preparación asíncrona
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start(); // Iniciar reproducción

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Service.START_STICKY;
    }

    private void newNotificarServicio() {
        setPendingIntent(SecondActivity.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Radio APP")
                .setContentText("Prueba notificacion")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1, builder.build());
    }

    private void setPendingIntent(Class<?> classActivity) {
        Intent intent = new Intent(this, classActivity);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(classActivity);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void notificarServicio() {
        NotificationChannel chan = new NotificationChannel(CHANNEL_ID,
                "Notificaciones de Radio", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(chan);

        // No se necesita llamar a newNotificarServicio() aquí, ya que ya se llama en onStartCommand()
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
        StreamingService.this.stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
