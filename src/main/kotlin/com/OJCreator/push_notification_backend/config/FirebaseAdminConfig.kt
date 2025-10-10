package com.OJCreator.push_notification_backend.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream
import java.io.IOException


@Configuration
class FirebaseAdminConfig {
    private val serviceAccountPath = "static/check24-pushnotification-firebase-adminsdk-fbsvc-79dc99eb1c.json"

    @Bean
    @Throws(IOException::class)
    fun initializeFirebase(): FirebaseApp? {
        val serviceAccount = this::class.java.classLoader.getResourceAsStream(serviceAccountPath)
            ?: throw RuntimeException("Firebase JSON file not found at $serviceAccountPath")

        serviceAccount.use { stream ->
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                // .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com") // optional
                .build()

            return if (FirebaseApp.getApps().isEmpty()) {
                println("Firebase Admin SDK initialisiert.")
                FirebaseApp.initializeApp(options)
            } else {
                println("Firebase Admin SDK bereits initialisiert.")
                FirebaseApp.getInstance()
            }
        }
    }
}
