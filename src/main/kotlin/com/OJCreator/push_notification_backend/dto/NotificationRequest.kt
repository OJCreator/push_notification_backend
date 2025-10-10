package com.OJCreator.push_notification_backend.dto

data class NotificationRequest(
    var token: String?,
    var title: String?,
    var body: String?
)