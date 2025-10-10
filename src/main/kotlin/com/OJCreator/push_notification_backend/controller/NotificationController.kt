package com.OJCreator.push_notification_backend.controller

import com.OJCreator.push_notification_backend.dto.NotificationRequest
import com.OJCreator.push_notification_backend.services.FcmService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Controller
class NotificationController(
    private val fcmService: FcmService) {


    @PostMapping("/send-notification")
    fun sendNotificationForm(request: NotificationRequest, model: Model): String {
        if (request.token.isNullOrBlank()) {
            model.addAttribute("message", "Fehler: Geräte-Token ist erforderlich.")
            model.addAttribute("success", false)
            return "send-notification-form"
        }
        if (request.title.isNullOrBlank()) {
            model.addAttribute("message", "Fehler: Titel ist erforderlich.")
            model.addAttribute("success", false)
            return "send-notification-form"
        }
        if (request.body.isNullOrBlank()) {
            model.addAttribute("message", "Fehler: Nachrichtentext ist erforderlich.")
            model.addAttribute("success", false)
            return "send-notification-form"
        }

        val messageId = fcmService.sendNotification(request.token, request.title, request.body)

        return if (messageId != null) {
            model.addAttribute("message", "Nachricht erfolgreich gesendet. ID: $messageId")
            model.addAttribute("success", true)
            "send-notification-form"
        } else {
            model.addAttribute("message", "Fehler beim Senden der Nachricht.")
            model.addAttribute("success", false)
            "send-notification-form"
        }
    }


    @GetMapping("/send-notification")
    fun showForm(): String {
        return "send-notification-form"
    }


//    @GetMapping("/send-notification/{token}/{title}/{body}")
//    fun sendNotificationGet(
//        @PathVariable token: String,
//        @PathVariable title: String,
//        @PathVariable body: String
//    ): ResponseEntity<String?> {
//        if (token.isBlank()) return ResponseEntity("Fehler: Geräte-Token ist erforderlich.", HttpStatus.BAD_REQUEST)
//        if (title.isBlank()) return ResponseEntity("Fehler: Titel ist erforderlich.", HttpStatus.BAD_REQUEST)
//        if (body.isBlank()) return ResponseEntity("Fehler: Nachrichtentext ist erforderlich.", HttpStatus.BAD_REQUEST)
//
//        val messageId = fcmService.sendNotification(token, title, body)
//
//        return if (messageId != null) {
//            ResponseEntity("Nachricht erfolgreich gesendet. ID: $messageId", HttpStatus.OK)
//        } else {
//            ResponseEntity("Fehler beim Senden der Nachricht.", HttpStatus.INTERNAL_SERVER_ERROR)
//        }
//    }
}
