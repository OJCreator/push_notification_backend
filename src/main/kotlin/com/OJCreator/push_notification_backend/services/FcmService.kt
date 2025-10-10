package com.OJCreator.push_notification_backend.services

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service

// Beispiel-Paketname

@Service
class FcmService {
    /**
     * Sendet eine Firebase Cloud Message an einen bestimmten Geräte-Token.
     *
     * @param deviceToken Der eindeutige FCM-Token des Zielgeräts.
     * @param title Der Titel der Benachrichtigung.
     * @param body Der Inhalt der Benachrichtigung.
     * @return Den Message-ID String, falls erfolgreich, sonst null.
     */
    fun sendNotification(deviceToken: String?, title: String?, body: String?): String? {
        // Die Notification-Payload sorgt dafür, dass die Nachricht vom System angezeigt wird,
        // auch wenn die App im Hintergrund oder geschlossen ist.
        // Die Daten-Payload (putData) wird von der App verarbeitet, wenn sie läuft,
        // und kann auch bei Hintergrund-Nachrichten genutzt werden.
        val message = Message.builder()
            .setToken(deviceToken) // Ziel-Gerät über Token
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body) // Optional: Icon, Sound, Click-Action etc. können hier gesetzt werden
                    // .setImage("https://example.com/image.png")
                    .build()
            ) // Optionale Daten-Payload, die deine App selbst verarbeiten kann
            .putData("custom_key", "custom_value")
            .build()

        try {
            // Senden der Nachricht über das Firebase Admin SDK
            val response = FirebaseMessaging.getInstance().send(message)
            println("Erfolgreich Nachricht gesendet: " + response)
            return response
        } catch (e: FirebaseMessagingException) {
            System.err.println("Fehler beim Senden der Nachricht: " + e.message)
            // Logge die Exception für mehr Details
            e.printStackTrace()
            return null // Zeigt an, dass das Senden fehlgeschlagen ist
        }
    }

    // Optional: Methoden zum Senden an Topics oder Conditions
    fun sendNotificationToTopic(topic: String?, title: String?, body: String?): String? {
        val message = Message.builder()
            .setTopic(topic) // Ziel-Thema
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )
            .build()

        try {
            val response = FirebaseMessaging.getInstance().send(message)
            println("Erfolgreich Nachricht an Thema gesendet: " + response)
            return response
        } catch (e: FirebaseMessagingException) {
            System.err.println("Fehler beim Senden an Thema: " + e.message)
            e.printStackTrace()
            return null
        }
    }
}
