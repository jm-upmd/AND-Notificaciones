# AND-Notificaciones
/* Ejemplo de construcción y uso de Notidicaciónes.
   Implementado un método independiente CreaNotificacon1,2,...
   para explicar creación de distintas variantes de notificación.

   Estos métodos se llaman desde método onCreate al pulsar boton
   "Crear Notificación" (R.id.boton_notif)
 */
 
 Dentro de MainActivity, ir descomentando uno de estos cinco métodos cada vez y probarlo. No ejecutar más de uno a la vez pues se pueden pisar...
 
// Crea una notificación simple que llama a otra activity (otraActivity)

    creaNotificacion1();

// Crea una notificación simple que llama a una url web. Para ello utiliza
// un itent implicito para construir su PendingItent.

    creaNotificacion2();

// Crea notificación con texto extendido y con dos botones de acción
// Cada botón  llama a una url web distinta usando intents implicitos para definir
// su acción.
// La notificación no tiene acción asociada (si se hace clic sobre ella no
// ocurre nada. Solo tienen acción sus botones.

    creaNotificacion3();

// Crea notificación con accion al hacer clic en ella.
// Incorpora dos botones de acción
// En este caso se ha implementado mecanismo para que al hacer clic sobre los
// botones de acción, la notifición también se cancele
// Para ello  el evento de clic de la notificación envía el PendingItent a
// un BroadCastReceiver (BR) registrado en el Manifiesto.
// Esta forma de hacerlo no funcionará en Android 8 o superior

    creaNotificacion4();

// Misma funcionalidad que creaNotificacin4, pero en esta ocasión para que sea
// compatible con Android 8+ no registra el BR en el manifiest sino que
// lo hace en el código.
// Android 8 y sup. no permite que los BR registrados en el manifiest reciban
// intents implicitos.

 creaNotificacion5();
 
 
