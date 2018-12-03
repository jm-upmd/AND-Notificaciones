package com.example.jose.notificaciones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class GestorNotificacionesReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        Log.d("xxx", "Acción recogida por BR GestorNotificacionesReceiver");


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Posibles acciones
        final String CERRAR = context.getString(R.string.action_cancel_notification);
        final String MOSTRAR_WEB = context.getString(R.string.action_show_web);

        //int opcion = intent.getIntExtra("ID_ACCION",1);
        int notificacionID = intent.getIntExtra("ID_NOTIFICACION",0);


        String accion = intent.getAction();

        if(accion.equals(MOSTRAR_WEB)){ // Pulsado boton mostrar web en notificación
            // La url pasada en el intent
            String miUrl = intent.getStringExtra("ID_URL");
            Uri webpage = Uri.parse(miUrl);
            // Construye intent implicito para visualizar la url web
            Intent intentWeb = new Intent(Intent.ACTION_VIEW,webpage);
            intentWeb.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Abre actividad
            context.startActivity(intentWeb);

            // Cancela la notificacion
            notificationManager.cancel(notificacionID);
        } else if(accion.equals(CERRAR)){  // Pulsado botón cerrar en notificación
            // Solo cierra notificación
            notificationManager.cancel(notificacionID);
        }

    }
}
