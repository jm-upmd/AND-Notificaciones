package com.example.jose.notificaciones;

/* Ejemplo de construcción y uso de Notidicaciónes.
   Implementado un método independiente CreaNotificacon1,2,...
   para explicar creación de distintas variantes de notificación.

   Estos métodos se llaman desde método onCreate al pulsar boton
   "Crear Notificación" (R.id.boton_notif)
 */



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Id del canal de la notificación
    String ID_CANAL_NOTFICACION;
    // Canal de notificación. Es nesario para Android 8 o sup.
    NotificationChannel canal;
    BroadcastReceiver mGestorNotificacionComp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Id del canal de la notificación
        ID_CANAL_NOTFICACION = getString(R.string.canal);


        // Creamos referencias a recurso boton
        Button boton = findViewById(R.id.boton_notif);

        // Creamos listener para evento

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Crea una notificación simple que llama a otra activity (otraActivity)
                //creaNotificacion1();

                // Crea una notificación simple que llama a una url web. Para ello utiliza
                // un itent implicito para construir su PendingItent.

                //creaNotificacion2();

                // Crea notificación con texto extendido y con dos botones de acción
                // Cada botón  llama a una url web distinta usando intents implicitos para definir
                // su acción.
                // La notificación no tiene acción asociada (si se hace clic sobre ella no
                // ocurre nada. Solo tienen acción sus botones.

                //creaNotificacion3();

                // Crea notificación con accion al hacer clic en ella.
                // Incorpora dos botones de acción
                // En este caso se ha implementado mecanismo para que al hacer clic sobre los
                // botones de acción, la notifición también se cancele
                // Para ello  el evento de clic de la notificación envía el PendingItent a
                // un BroadCastReceiver (BR) registrado en el Manifiesto.
                // Esta forma de hacerlo no funcionará en Android 8 o superior

                //creaNotificacion4();

                // Misma funcionalidad que creaNotificacin4, pero en esta ocasión para que sea
                // compatible con Android 8+ no registra el BR en el manifiest sino que
                // lo hace en el código.
                // Android 8 y sup. no permite que los BR registrados en el manifiest reciban
                // intents implicitos.

                creaNotificacion5();
            }
        });


    }

    /***************************
    creaNotificacion1()
    Crea una notificación simple.
    La notificación incluye un intent explicito cuya acción consiste en abrir la acitvidad
    OtraActivity, implementada en este mismo proyecto
    ***************************/

    void creaNotificacion1(){
        // Id de la notificación
        final int ID_NOTIFICACION = 0;

        //** PASOS **//

        // 1. Crea builder de la notificación que establece contenido de la notificación.

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,ID_CANAL_NOTFICACION )
                .setSmallIcon(R.drawable.ic_notificacion)
                .setContentTitle("Título Notificación 1")
                .setContentText("Texto de la notificación")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // La notificación se cancela automáticamente cuando se hace clic en ella

        // 2. Para que funcione en Android 8+ es necesario crear un canal de notificación

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String nomCanal = getString(R.string.nom_canal);
            String descripCanal = getString(R.string.desc_canal);
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            // Creación del canal
            NotificationChannel canal = new NotificationChannel(ID_CANAL_NOTFICACION,nomCanal,importancia);
            canal.setDescription(descripCanal);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(canal);
            }

        }

        // 3. Establecer la acción a realizar al hacer el toque sobre la notificación

        // La notificación abrirá actividad OtraActivity, para ello crea un Intent explícito
        Intent intent = new Intent(this,OtraActivity.class);

        // Como la actividad que vamos a abrir es una actividad normal visible, le ponemos flags
        // para que tenga su propia task (recomendado), para que el comportamiento con los botones
        // back sea el  esperado.
        // En el AndroidMainifiest.xml hay que poner este atributo en la actividad OtraActivity:
        // android:taskAffinity="com.example.jose.task"
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Creamos un PendingIntent pasandole como parámetro el intent
        PendingIntent pendingIntent1 = PendingIntent.getActivity(this,0,intent,0);

        // Agregamos la accion al builder de la notificación
        notificationBuilder.setContentIntent(pendingIntent1);


        // 4. Muestra la notificación

        //NotificationManager notificationManager = getSystemService(NotificationManager.class);
        // NotificatonMananer funciona para SDK >= 23 por lo tanto no podemos usarlo aquí.
        // En su lugar usamos NotificationManagerCompat para que funcione con el min SDK
        // que tenemos definido que es el 15

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(ID_NOTIFICACION,notificationBuilder.build());

    }


    /***************************
     creaNotificacion2( )
     Crea una notidicación cuya acción se describe a través de un intent implicito
     para llamar a una url de una web
    ***************************/

    void creaNotificacion2( ){
        // Id de la notificación
        final int ID_NOTIFICACION = 1;

        //** PASOS **//

        // 1. Crea builder de la notificación que establece contenido de la notificación.

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,ID_CANAL_NOTFICACION )
                .setSmallIcon(R.drawable.ic_notificacion)
                .setContentTitle("Título Notificación 2")
                .setContentText("Quieres saber cómo funciona Más Palabras")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // 2. Para que funcione en Android 8+ es necesario crear un canal de notificación

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String nomCanal = getString(R.string.nom_canal);
            String descripCanal = getString(R.string.desc_canal);
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            // Creación del canal
            NotificationChannel canal = new NotificationChannel(ID_CANAL_NOTFICACION,nomCanal,importancia);
            canal.setDescription(descripCanal);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(canal);
            }

        }

        // 3. Establecer la acción a realizar al hacer el toque sobre la notificación

        // Usamos un itent implicito para cargar una url web
        // La notificación abrirá (se existe) una actividad de entre las apps que hay instaladas en
        // el dispositivo que sea capaz de mostrar el contenido de una url web.

        // Url llamada
        String miUrl = getString(R.string.url_maspalabras);
        Uri webpage = Uri.parse(miUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW,webpage);

        // Como la actividad que vamos a abrir es una actrividad normal visible le ponemos flags
        // para que tenga su propia task (recomendado), para que el comportamiento con los botones
        // back sea el  esperado.
        // **En implicitas no hace falta
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        // Creamos un PendingIntent pasandole como parámetro el intent
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this,0,intent,0);

        // Agregamos la acción al builder de la notificación
        notificationBuilder.setContentIntent(pendingIntent2);


        // 4. Muestra la notificación
        //NotificationManager notificationManager = getSystemService(NotificationManager.class);
        // NotificatonMananer funciona para SDK >= 23 por lo tanto no podemos usarlo aquí.
        // En su lugar usamos NotificationManagerCompat para que funcione con el min SDK
        // que tenemos definido que es el 15

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(ID_NOTIFICACION,notificationBuilder.build());



    }

    /***************************
     creaNotificacion3( )
    Crea notificación con texto extendio y con dos botones.
    A la notidicación no se le dota de ninguana acción (no se le pasa ningún
    PendingItent a traves de su método setContentIntent).
    Las acciones estarán en sus dos botones.
    ***************************/

    void creaNotificacion3( ){
        // Id de la notificación
        final int ID_NOTIFICACION = 2;
        final String TEXTO_LARGO = getString(R.string.texto_largo_notif);

        //** PASOS **//

        // 1. Crea builder de la notificación que establece contenido de la notificación.

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,ID_CANAL_NOTFICACION )
                .setSmallIcon(R.drawable.ic_notificacion)
                .setContentTitle("Título Notificación 3")
                .setContentText("Quieres conocer la app Más Palabras")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(TEXTO_LARGO))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // 2. Para que funcione en Android 8+ es necesario crear un canal de notificación

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String nomCanal = getString(R.string.nom_canal);
            String descripCanal = getString(R.string.desc_canal);
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            // Creación del canal
            NotificationChannel canal = new NotificationChannel(ID_CANAL_NOTFICACION,nomCanal,importancia);
            canal.setDescription(descripCanal);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(canal);
            }

        }

        // 3. Establecer la acción a realizar al hacer el toque sobre la notificación

        // Esta notificación NO tendra ninguna acción al pulsar sobre ella. Por lo tanto no ejecutamos
        // el método notificationBuilder.setContentIntent(pendingIntent);
        // Las acciones estarán en sus dos botones de acción:
        //      - "Ver Google Play", que abrirá browser con pagina de Google Play de la app
        //      - "Ver Página App", que abrirá browser con página de ayuda de la app.

        //3.1  Botón "Ver Página App"
        // Url llamada
        String miUrl = getString(R.string.url_maspalabras);
        Uri webpage = Uri.parse(miUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW,webpage);

        // Como la actividad que vamos a abrir es una actrividad normal visible le ponemos flags
        // para que tenga su propia task (recomendado), para que el comportamiento con los botones
        // back sea el  esperado.
        // **En implicitas no hace falta
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Creamos un PendingIntent pasandole como parámetro el intent
        PendingIntent pendingIntent3 = PendingIntent.getActivity(this,0,intent,0);

        notificationBuilder.addAction(android.R.drawable.ic_menu_help,"Página Ayuda",pendingIntent3);

        //3.2 Botón "Ver Google Play"
        miUrl = getString(R.string.url_gplay);
        webpage = Uri.parse(miUrl);
        intent = new Intent(Intent.ACTION_VIEW,webpage);

        pendingIntent3 = PendingIntent.getActivity(this,0,intent,0);
        notificationBuilder.addAction(0,"Ficha Google Play",pendingIntent3);


        // 4. Muestra la notificación
        //NotificationManager notificationManager = getSystemService(NotificationManager.class);
        // NotificatonMananer funciona para SDK >= 23 por lo tanto no podemos usarlo aquí.
        // En su lugar usamos NotificationManagerCompat para que funcione con el min SDK
        // que tenemos definido que es el 15

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(ID_NOTIFICACION,notificationBuilder.build());

    }

    /***************************
     creaNotificacion4()
    Notificación con acción asociada para abrir url web

    Adicionalmente tiene dos botones:
     - Botón "Ver en Google Play", llama url de Google Play para ver ficha de una app concreta.
     - Botón "Cerrar", cierra la notificación

    El clic sobre un botón de acción NO CIERRA LA NOTIFICACIÓN. Tampoco existe ningún mecanismo
    para poder hacerlo de forma directa con el NofificationCompat.Builder.

    La solución usada aquí es registrar un BroadcastReceiver (BR) que se encarge de recibir el Intent
    de la acción del botón, que incluirá el ID de la notificición. El BR cancelará  la
    notificación con el método NotificationManagerCompat.cancel(ID_NOTIFICACION);

    No olvidar registrar el BroadcastReceirver en el AndroidManifiest.xml.:
        <receiver
            android:name=".GestorNotificacionesReceiver">
            <intent-filter  >
                <action android:name="com.example.jose.notificaciones_NOTIFICATION_CANCEL" />
            </intent-filter>
            <intent-filter >
                <action android:name="com.example.jose.notificaciones_NOTIFICATION_SHOW_WEB" />
            </intent-filter>
        </receiver>

    Importante: Android Oreo (Android versión 8) incorpora una restricción que consiste en
    la imposibilidad de registrar receptores para transmisiones implícitas en su manifiesto.
    Por tanto esta implementación no tendrá efecto en un disponsitivo con Android Oreo.
    Solución en creaNotificación5().
    *************************/

    void creaNotificacion4( ){
        // Id de la notificación
        final int ID_NOTIFICACION = 3;
        final String TEXTO_LARGO = getString(R.string.texto_largo_notif2);
        final String ACCION_CANCELA_NOTIFICACION = getString(R.string.action_cancel_notification);
        final String ACCION_ABRE_URL_WEB = getString(R.string.action_show_web);


        //** PASOS **//

        // 1. Creamos un BroadcastReceiver
        // Se puede hacer aquí como clase anidada o en fichero externo. Si lo hacemos en fichero
        // externo podemos hacerlo con wizard del IDE que automáticamente tb de genera la entrada
        // en el Manifiest. Hemos optado por esta segunda opción para simplificar...
        // Implementado en GestorNotificacionesReceiver.java


        // 2. Crea builder de la notificación que establece contenido de la notificación.

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,ID_CANAL_NOTFICACION )
                .setSmallIcon(R.drawable.ic_notificacion)
                .setContentTitle("Título Notificación 4")
                .setContentText("Quieres conocer la app Más Palabras")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(TEXTO_LARGO))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // 3. Para que funcione en Android 8+ es necesario crear un canal de notificación

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String nomCanal = getString(R.string.nom_canal);
            String descripCanal = getString(R.string.desc_canal);
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            // Creación del canal
            NotificationChannel canal = new NotificationChannel(ID_CANAL_NOTFICACION,nomCanal,importancia);
            canal.setDescription(descripCanal);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(canal);
            }

        }

        // 4. Establecer la acción a realizar al hacer el toque sobre la notificación

        String miUrl = getString(R.string.url_maspalabras);
        Uri webpage = Uri.parse(miUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        // Creamos un PendingIntent pasandole como parámetro el intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        notificationBuilder.setContentIntent(pendingIntent);


        // 5. Añádir Botón de acción  "Ver Google Play"


        // Crea intent con una accion a realizar
        Intent intentMuestraWeb = new Intent(ACCION_ABRE_URL_WEB);
        intentMuestraWeb.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );




        // Metemos en el intent información extra para pasar al Broadcast

        // Id de la notificación para luego poder cancelarla
        intentMuestraWeb.putExtra("ID_NOTIFICACION",ID_NOTIFICACION);

        // url se la web que tiene que llamar
        miUrl = getString(R.string.url_gplay);
        intentMuestraWeb.putExtra("ID_URL",miUrl);

        // Crea PendingIntent que será recogido por un BroadCastReceiver
        PendingIntent muestraWeb = PendingIntent.getBroadcast(this, 0, intentMuestraWeb, PendingIntent.FLAG_CANCEL_CURRENT);
        // Añade la acción.
        notificationBuilder.addAction(0,"Ficha Google Play",muestraWeb);


        // 6. Añadir Botón de acción "Cerrar"



        // Crea intent con una accion a realizar
        Intent intentCerrarNotif = new Intent(ACCION_CANCELA_NOTIFICACION);
        intentCerrarNotif.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );


        // Metemos en el intent información extra para pasar al Broadcast

        // id de la notificación
        intentCerrarNotif.putExtra("ID_NOTIFICACION",ID_NOTIFICACION);


        // Crea PendingIntent que será recogido por un BroadCastReceiver
        PendingIntent cancelaPI = PendingIntent.getBroadcast(this, 0, intentCerrarNotif, PendingIntent.FLAG_CANCEL_CURRENT);

        // Añade la acción.
        notificationBuilder.addAction(0,"Cerrar",cancelaPI);


         // 7. Crea la notificación
        //NotificationManager notificationManager = getSystemService(NotificationManager.class);
        // NotificatonMananer funciona para SDK >= 23 por lo tanto no podemos usarlo aquí.
        // En su lugar usamos NotificationManagerCompat para que funcione con el min SDK
        // que tenemos definido que es el 15

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(ID_NOTIFICACION,notificationBuilder.build());

    }

    /*********************************
    creaNotificacion5()
    Crea notificación con la misma funcionalidad de creaNotificacion4() pero compatible con
    Android Oreo y  ( posteriores de momento...).

     Google no implementa compatibilidad hacia adelante por lo tanto al igual que ha obligado a
     los desarrolladores a modificar las apps para que determinadas componentes funcionen
     en Oreo 8 (ejemplo notificaciones), en alguna versión de Android futura es probable que
     obliguen a realizar nuevos cambios...

    En este caso para resolver el problema vamos a utilizar la misma técnica, pero el
    BroadCastReceiver lo vamos a crear y registrar programáticamente, no definiendolo
    en el AndroidManifiest.

     Nota: Para usar este BroadcastReciver, comentar el usado en los anteriores ejemplos en el
     manifiest, de lo contrario las acciónes serán recogidas y ejecutadas por ambos, en los
     disponsitivos con Android inferior a la versión 8.




    (Ver opción de usar clase LocalBroadCastReceiver)

     **********************************/

    void creaNotificacion5( ){
        // Id de la notificación
        final int ID_NOTIFICACION = 4;
        final String TEXTO_LARGO = getString(R.string.texto_largo_notif2);
        final String ACCION_CANCELA_NOTIFICACION = getString(R.string.action_cancel_notification);
        final String ACCION_ABRE_URL_WEB = getString(R.string.action_show_web);

        //** PASOS **//

        // 1. Creamos un BroadcastReceiver
        // Se puede hacer aquí como clase anidada y local al método o en fichero externo.
        // En esta ocasión vamos a implementar el BroadCastReceiver como clase anónima local
        // a este método. Ventaja: Desde dentro de la clase local tenemos visibilidad de las
        // variables de su método contenedor.

         mGestorNotificacionComp = new BroadcastReceiver() {
            // Sobrescribe método abastracto onReceive
            // Esto es idéntico a lo que hace el otro BR GestorNotificacionesReceiver.
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.d("xxx", "Acción recogida por BR mGestorNotificacionComp" );

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


                //int opcion = intent.getIntExtra("ID_ACCION",1);
                int notificacionID = intent.getIntExtra("ID_NOTIFICACION",0);

                // Obtiene la acción guradada en el intent contenido en el PendingItent
                String accion = intent.getAction();

                if(accion.equals(ACCION_ABRE_URL_WEB)){ // Pulsado boton mostrar web en notificación
                    // La url pasada en el intent
                    String miUrl = intent.getStringExtra("ID_URL");
                    Uri webpage = Uri.parse(miUrl);
                    // Construye intent implicito para visualizar la url web
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW,webpage);
                    // Abre actividad
                    context.startActivity(intentWeb);
                    // Cancela la notificacion
                    notificationManager.cancel(notificacionID);
                } else if(accion.equals(ACCION_CANCELA_NOTIFICACION)){  // Pulsado botón cerrar en notificación
                    // Solo cierra notificación
                    notificationManager.cancel(notificacionID);
                }

            }
        };


        // Creamos un IntentFilter con las dos acciones a las que respondera el BR
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(ACCION_ABRE_URL_WEB);
        filtro.addAction(ACCION_CANCELA_NOTIFICACION);

        // Registramos en BR
        // Ojo: Acordarse de eliminar registro del BR en onDestroy

        registerReceiver(mGestorNotificacionComp,filtro);



        // 2. Crea builder de la notificación que establece contenido de la notificación.

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,ID_CANAL_NOTFICACION )
                .setSmallIcon(R.drawable.ic_notificacion)
                .setContentTitle("Título Notificación 5")
                .setContentText("Quieres conocer la app Más Palabras")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(TEXTO_LARGO))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // 3. Para que funcione en Android 8+ es necesario crear un canal de notificación

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String nomCanal = getString(R.string.nom_canal);
            String descripCanal = getString(R.string.desc_canal);
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            // Creación del canal
            NotificationChannel canal = new NotificationChannel(ID_CANAL_NOTFICACION,nomCanal,importancia);
            canal.setDescription(descripCanal);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(canal);
            }

        }

        // 4. Establecer la acción a realizar al hacer el toque sobre la notificación

        String miUrl = getString(R.string.url_maspalabras);
        Uri webpage = Uri.parse(miUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW,webpage);

        // Creamos un PendingIntent pasandole como parámetro el intent
        PendingIntent pendingIntent5 = PendingIntent.getActivity(this,0,intent,0);

        notificationBuilder.setContentIntent(pendingIntent5);


        // 5. Añádir Botón de acción  "Ver Google Play"


        // Crea intent con una accion a realizar
        Intent intentMuestraWeb = new Intent(ACCION_ABRE_URL_WEB);



        // Metemos en el intent información extra para pasar al Broadcast

        // Id de la notificación para luego poder cancelarla
        intentMuestraWeb.putExtra("ID_NOTIFICACION",ID_NOTIFICACION);

        // url se la web que tiene que llamar
        miUrl = getString(R.string.url_gplay);
        intentMuestraWeb.putExtra("ID_URL",miUrl);

        // Crea PendingIntent que será recogido por un BroadCastReceiver
        PendingIntent muestraWeb = PendingIntent.getBroadcast(this, 0, intentMuestraWeb, PendingIntent.FLAG_CANCEL_CURRENT);
        // Añade la acción.
        notificationBuilder.addAction(0,"Ficha Google Play",muestraWeb);


        // 6. Añadir Botón de acción "Cerrar"



        // Crea intent con una accion a realizar
        Intent intentCerrarNotif = new Intent(ACCION_CANCELA_NOTIFICACION);

        // Metemos en el intent información extra para pasar al Broadcast

        // id de la notificación
        intentCerrarNotif.putExtra("ID_NOTIFICACION",ID_NOTIFICACION);

        // Crea PendingIntent que será recogido por un BroadCastReceiver
        PendingIntent cancelaPI = PendingIntent.getBroadcast(this, 0, intentCerrarNotif, PendingIntent.FLAG_CANCEL_CURRENT);

        // Añade la acción.
        notificationBuilder.addAction(0,"Cerrar",cancelaPI);


        // 7. Crea la notificación
        //NotificationManager notificationManager = getSystemService(NotificationManager.class);
        // NotificatonMananer funciona para SDK >= 23 por lo tanto no podemos usarlo aquí.
        // En su lugar usamos NotificationManagerCompat para que funcione con el min SDK
        // que tenemos definido que es el 15

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(ID_NOTIFICACION,notificationBuilder.build());

    }



    @Override
    protected void onPause() {

        // Limpiamos registro del BroadcastReceiver
        if (mGestorNotificacionComp != null) {
            unregisterReceiver(mGestorNotificacionComp);
            mGestorNotificacionComp = null;
        }
        super.onPause();
    }
}

