package com.example.model

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String) {
  ENGLISH("en", "English", "English"),
  MALAYALAM("ml", "Malayalam", "മലയാളം")
}

enum class RegistrationStatus {
  PENDING,
  APPROVED,
  SUSPENDED,
  REJECTED
}

data class StudentUser(
  val id: String,
  val fullName: String,
  val age: Int,
  val phoneNumber: String,
  val whatsAppNumber: String,
  val profilePhotoUri: String? = null,
  val gmailAddress: String,
  val verifiedGoogleEmail: String,
  val address: String,
  val selectedCourseId: String,
  val registrationDate: String,
  val status: RegistrationStatus = RegistrationStatus.PENDING,
  val lastLogin: String = "Just now",
  val consentAccepted: Boolean = true
)

data class Course(
  val id: String,
  val titleEn: String,
  val titleMl: String,
  val subtitleEn: String,
  val subtitleMl: String,
  val languageType: String, // "Malayalam", "English", "Malayalam & English"
  val authorizedEmails: List<String>,
  val lessonsCount: Int = 0,
  val isPrivate: Boolean = true
)

data class Lesson(
  val id: String,
  val courseId: String,
  val lessonNumber: Int,
  val titleEn: String,
  val titleMl: String,
  val descriptionEn: String,
  val descriptionMl: String,
  val contentEn: String,
  val contentMl: String,
  val videoUrl: String? = null,
  val videoDuration: String = "15:30",
  val pdfAttachmentName: String? = null,
  val imageAttachmentName: String? = null,
  val notesEn: String = "",
  val notesMl: String = ""
)

data class CourseCategory(
  val id: String,
  val titleEn: String,
  val titleMl: String,
  val descEn: String,
  val descMl: String,
  val iconName: String
)

data class AdminStats(
  val totalStudents: Int,
  val pendingRegistrations: Int,
  val approvedStudents: Int,
  val suspendedStudents: Int,
  val totalCourses: Int,
  val totalLessons: Int,
  val totalVideos: Int
)
