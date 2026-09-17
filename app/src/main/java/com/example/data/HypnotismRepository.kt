package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class HypnotismRepository private constructor() {

  companion object {
    @Volatile
    private var instance: HypnotismRepository? = null

    fun getInstance(): HypnotismRepository {
      return instance ?: synchronized(this) {
        instance ?: HypnotismRepository().also { instance = it }
      }
    }

    // Owner / Super Admin email from project instructions
    const val OWNER_ADMIN_EMAIL = "davoodhakeemm@gmail.com"
    const val PROTOTYPE_ADMIN_KEY = "91870"
  }

  // Language state
  private val _currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)
  val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

  fun setLanguage(lang: AppLanguage) {
    _currentLanguage.value = lang
  }

  // Active Current Authenticated User (Simulated Google Auth & Verified Session)
  private val _currentUser = MutableStateFlow<StudentUser?>(null)
  val currentUser: StateFlow<StudentUser?> = _currentUser.asStateFlow()

  // Admin Session State
  private val _isAdminAuthenticated = MutableStateFlow(false)
  val isAdminAuthenticated: StateFlow<Boolean> = _isAdminAuthenticated.asStateFlow()

  // Admin Access Key Rate Limiter
  private val _failedAttempts = MutableStateFlow(0)
  val failedAttempts: StateFlow<Int> = _failedAttempts.asStateFlow()

  private val _lockoutUntilTimestamp = MutableStateFlow(0L)
  val lockoutUntilTimestamp: StateFlow<Long> = _lockoutUntilTimestamp.asStateFlow()

  // Courses
  private val _courses = MutableStateFlow<List<Course>>(emptyList())
  val courses: StateFlow<List<Course>> = _courses.asStateFlow()

  // Lessons mapped by courseId
  private val _lessons = MutableStateFlow<Map<String, List<Lesson>>>(emptyMap())
  val lessons: StateFlow<Map<String, List<Lesson>>> = _lessons.asStateFlow()

  // Student Registrations (Private to Admin and Respective Student)
  private val _registrations = MutableStateFlow<List<StudentUser>>(emptyList())
  val registrations: StateFlow<List<StudentUser>> = _registrations.asStateFlow()

  // Student Completed Lessons progress: map of "${userId}_${courseId}" to set of lessonIds
  private val _completedLessons = MutableStateFlow<Map<String, Set<String>>>(emptyMap())
  val completedLessons: StateFlow<Map<String, Set<String>>> = _completedLessons.asStateFlow()

  init {
    seedInitialData()
  }

  private fun seedInitialData() {
    val initialCourses = listOf(
      Course(
        id = "hypno-ml",
        titleEn = "Hypnotism — Malayalam",
        titleMl = "ഹിപ്നോട്ടിസം — മലയാളം",
        subtitleEn = "Comprehensive educational program in Malayalam",
        subtitleMl = "മലയാളത്തിലുള്ള സമഗ്ര പഠന പരിപാടി",
        languageType = "Malayalam",
        authorizedEmails = listOf(OWNER_ADMIN_EMAIL),
        lessonsCount = 4
      ),
      Course(
        id = "hypno-en",
        titleEn = "Hypnotism — English",
        titleMl = "ഹിപ്നോട്ടിസം — ഇംഗ്ലീഷ്",
        subtitleEn = "Complete scientific focus and hypnosis curriculum",
        subtitleMl = "ശാസ്ത്രീയ പഠനവും പരിശീലനവും",
        languageType = "English",
        authorizedEmails = listOf(OWNER_ADMIN_EMAIL),
        lessonsCount = 4
      ),
      Course(
        id = "hypno-bi",
        titleEn = "Hypnotism — Malayalam & English",
        titleMl = "ഹിപ്നോട്ടിസം — മലയാളം & ഇംഗ്ലീഷ് (Bilingual)",
        subtitleEn = "Full dual-language masterclass with clinical vs educational perspectives",
        subtitleMl = "ഇംഗ്ലീഷ് & മലയാളം സമഗ്ര മാസ്റ്റർക്ലാസ്",
        languageType = "Malayalam & English",
        authorizedEmails = listOf(OWNER_ADMIN_EMAIL),
        lessonsCount = 4
      )
    )
    _courses.value = initialCourses

    // Initial Lessons for hypno-ml
    val mlLessons = listOf(
      Lesson(
        id = "ml-1",
        courseId = "hypno-ml",
        lessonNumber = 1,
        titleEn = "Lesson 1: Introduction to Hypnotism",
        titleMl = "പാഠം 1: ഹിപ്നോട്ടിസം ആമുഖം",
        descriptionEn = "Scientific foundations of focused attention, relaxation, and responsiveness.",
        descriptionMl = "ശ്രദ്ധ കേന്ദ്രീകരിക്കൽ, വിശ്രാന്തി, നിർദ്ദേശങ്ങളോടുള്ള പ്രതികരണം എന്നിവയുടെ ശാസ്ത്രീയ അടിത്തറ.",
        contentEn = """
Hypnotism is a technique involving focused attention, relaxation, and increased responsiveness to suggestions.

Hypnosis is commonly described as a state involving focused attention and heightened suggestibility. It is not the same as ordinary sleep, and a person generally retains awareness and the ability to respond according to their own choices.

Hypnotism has been studied in psychological and scientific contexts and has also been used in performance and certain complementary or clinical settings by appropriately trained professionals.

The course should teach students to understand hypnotism responsibly rather than presenting it as supernatural mind control.
""".trimIndent(),
        contentMl = """
ഹിപ്നോട്ടിസം (Hypnotism) എന്നത് ശ്രദ്ധയെ ഒരു പ്രത്യേക കാര്യത്തിൽ കേന്ദ്രീകരിക്കൽ, വിശ്രമാവസ്ഥ, നിർദ്ദേശങ്ങളോട് കൂടുതൽ ശ്രദ്ധ പുലർത്തുന്ന അവസ്ഥ എന്നിവയുമായി ബന്ധപ്പെട്ട ഒരു സാങ്കേതികവിദ്യയാണ്.

ഹിപ്നോസിസ് സാധാരണ ഉറക്കത്തിന് തുല്യമല്ല. ഹിപ്നോട്ടിക് അവസ്ഥയിലുള്ള വ്യക്തിക്ക് ചുറ്റുപാടുകളെക്കുറിച്ച് ഒരു പരിധിവരെ ബോധവാനായിരിക്കാം. ഒരാളുടെ മനസ്സിന്റെ പൂർണ്ണ നിയന്ത്രണം മറ്റൊരാൾക്ക് ലഭിക്കുന്നു എന്നത് ഹിപ്നോസിസിന്റെ ശാസ്ത്രീയമായ വിവരണം അല്ല.

ഈ കോഴ്സ് ഹിപ്നോട്ടിസത്തെ ശാസ്ത്രീയവും ഉത്തരവാദിത്തപരവുമായ രീതിയിൽ മനസ്സിലാക്കാൻ സഹായിക്കുന്നതിനാണ്.
""".trimIndent(),
        videoUrl = "secure_storage://videos/ml_lesson_1.mp4",
        videoDuration = "18:45",
        pdfAttachmentName = "Hypnotism_Intro_Malayalam.pdf",
        notesEn = "Key points: Retains free will, scientific basis, no occult claims.",
        notesMl = "പ്രധാന കാര്യങ്ങൾ: വ്യക്തിയുടെ പൂർണ്ണ സ്വാതന്ത്ര്യം, ശാസ്ത്രീയ സമീപനം, അമാനുഷിക അവകാശവാദങ്ങൾ പാടില്ല."
      ),
      Lesson(
        id = "ml-2",
        courseId = "hypno-ml",
        lessonNumber = 2,
        titleEn = "Lesson 2: Focus & Concentration Techniques",
        titleMl = "പാഠം 2: ശ്രദ്ധയും ഏകാഗ്രതയും",
        descriptionEn = "Systematic focus exercises, eye fixation concepts, and sensory awareness.",
        descriptionMl = "ശ്രദ്ധ കേന്ദ്രീകരിക്കാനുള്ള പരിശീലനങ്ങൾ, ഐ-ഫിക്സേഷൻ രീതികൾ, ഇന്ദ്രിയാവബോധം.",
        contentEn = "Exploration of how the human brain naturally enters focused states during reading, driving, or intense concentration.",
        contentMl = "പുസ്തകം വായിക്കുമ്പോഴും വാഹനം ഓടിക്കുമ്പോഴും മനുഷ്യ മനസ്സ് സ്വാഭാവികമായി എത്തുന്ന ഏകാഗ്രതയുടെ തലങ്ങൾ.",
        videoUrl = "secure_storage://videos/ml_lesson_2.mp4",
        videoDuration = "22:10",
        notesEn = "Focus is an internal capability of the subject.",
        notesMl = "ശ്രദ്ധ എന്നത് വ്യക്തിയുടെ ഉള്ളിലുള്ള സ്വാഭാവിക ശേഷിയാണ്."
      ),
      Lesson(
        id = "ml-3",
        courseId = "hypno-ml",
        lessonNumber = 3,
        titleEn = "Lesson 3: Suggestion & Imagination",
        titleMl = "പാഠം 3: സജഷനും ഭാവനയും (Suggestion & Imagination)",
        descriptionEn = "How verbal suggestions and mental imagery shape physiological and psychological responses.",
        descriptionMl = "വാക്കുകളിലൂടെയുള്ള നിർദ്ദേശങ്ങളും ഭാവനയും മനസ്സിലും ശരീരത്തിലും ഉണ്ടാക്കുന്ന സ്വാധീനം.",
        contentEn = "Understanding direct and indirect suggestions, rapport, and ethical communication.",
        contentMl = "ഡയറക്റ്റ്, ഇൻഡയറക്റ്റ് സജഷനുകൾ, റപ്പോർട്ട് (Rapport), മാന്യമായ ആശയവിനിമയം.",
        videoUrl = "secure_storage://videos/ml_lesson_3.mp4",
        videoDuration = "26:30",
        notesEn = "Suggestions are invited invitations, never coercive commands.",
        notesMl = "സജഷനുകൾ നിർദ്ദേശങ്ങൾ മാത്രമാണ്, നിർബന്ധപൂർവ്വമുള്ള കൽപ്പനകളല്ല."
      ),
      Lesson(
        id = "ml-4",
        courseId = "hypno-ml",
        lessonNumber = 4,
        titleEn = "Lesson 4: Ethics, Safety & Consent",
        titleMl = "പാഠം 4: ധാർമ്മികത, സുരക്ഷ, പൂർണ്ണ സമ്മതം",
        descriptionEn = "Strict ethical boundaries, personal integrity, and distinguishing clinical hypnosis.",
        descriptionMl = "കർശനമായ ധാർമ്മിക അതിരുകൾ, വ്യക്തിത്വ ആദരവ്, ക്ലിനിക്കൽ ഹിപ്നോസിസ് തിരിച്ചറിയൽ.",
        contentEn = "Comprehensive study of informed consent, non-manipulation, and healthcare professional boundaries.",
        contentMl = "ഇൻഫോംഡ് കൺസെന്റ്, ചൂഷണം ഒഴിവാക്കൽ, മെഡിക്കൽ ചികിത്സയുമായി കൂട്ടിക്കുഴയ്ക്കാതിരിക്കൽ.",
        videoUrl = "secure_storage://videos/ml_lesson_4.mp4",
        videoDuration = "31:15",
        notesEn = "Consent • Safety • Respect • Responsibility.",
        notesMl = "സമ്മതം • സുരക്ഷ • ആദരവ് • ഉത്തരവാദിത്തം."
      )
    )

    // Lessons for hypno-en
    val enLessons = mlLessons.map { lesson ->
      lesson.copy(
        id = "en-${lesson.lessonNumber}",
        courseId = "hypno-en",
        titleEn = "Lesson ${lesson.lessonNumber}: ${lesson.titleEn.substringAfter(": ")}",
        videoUrl = "secure_storage://videos/en_lesson_${lesson.lessonNumber}.mp4"
      )
    }

    // Lessons for hypno-bi
    val biLessons = mlLessons.map { lesson ->
      lesson.copy(
        id = "bi-${lesson.lessonNumber}",
        courseId = "hypno-bi",
        titleEn = "Bilingual Lesson ${lesson.lessonNumber}: ${lesson.titleEn.substringAfter(": ")}",
        titleMl = "ദ്വിഭാഷാ പാഠം ${lesson.lessonNumber}: ${lesson.titleMl.substringAfter(": ")}",
        videoUrl = "secure_storage://videos/bi_lesson_${lesson.lessonNumber}.mp4"
      )
    }

    _lessons.value = mapOf(
      "hypno-ml" to mlLessons,
      "hypno-en" to enLessons,
      "hypno-bi" to biLessons
    )

    // No fake bot students - only real student registrations
    _registrations.value = emptyList()
    _currentUser.value = null
  }

  // --- STUDENT REGISTRATION & GOOGLE AUTH VERIFICATION ---
  fun registerStudent(
    fullName: String,
    age: Int,
    phoneNumber: String,
    whatsAppNumber: String,
    profilePhotoUri: String?,
    gmailAddress: String,
    verifiedGoogleEmail: String,
    address: String,
    selectedCourseId: String,
    consentAccepted: Boolean
  ): Result<StudentUser> {
    // Security verification: The Gmail obtained from Google authentication must be verified against the Gmail submitted in the form
    if (!gmailAddress.trim().equals(verifiedGoogleEmail.trim(), ignoreCase = true)) {
      return Result.failure(
        IllegalArgumentException("Google authentication email ($verifiedGoogleEmail) does not match the entered Gmail ($gmailAddress). Identity verification required.")
      )
    }

    if (!consentAccepted) {
      return Result.failure(
        IllegalArgumentException("You must accept the Ethics & Safety Consent agreement.")
      )
    }

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = dateFormat.format(Date())

    val newStudent = StudentUser(
      id = "stud-${System.currentTimeMillis()}",
      fullName = fullName.trim(),
      age = age,
      phoneNumber = phoneNumber.trim(),
      whatsAppNumber = whatsAppNumber.trim(),
      profilePhotoUri = profilePhotoUri,
      gmailAddress = gmailAddress.trim().lowercase(),
      verifiedGoogleEmail = verifiedGoogleEmail.trim().lowercase(),
      address = address.trim(),
      selectedCourseId = selectedCourseId,
      registrationDate = currentDate,
      status = RegistrationStatus.PENDING,
      lastLogin = "Just now",
      consentAccepted = true
    )

    _registrations.value = _registrations.value + newStudent
    _currentUser.value = newStudent
    return Result.success(newStudent)
  }

  // Switch / Login current student by Gmail
  fun loginStudent(gmail: String): Result<StudentUser> {
    val student = _registrations.value.find {
      it.gmailAddress.equals(gmail.trim(), ignoreCase = true)
    }
    return if (student != null) {
      _currentUser.value = student.copy(lastLogin = "Just now")
      Result.success(student)
    } else {
      // If user logs in with Google email that is not yet registered
      Result.failure(NoSuchElementException("No registration found for $gmail. Please Join Class first."))
    }
  }

  fun logoutStudent() {
    _currentUser.value = null
  }

  // Set simulated logged in user directly (useful for testing approval/unauthorized states)
  fun setCurrentUser(user: StudentUser?) {
    _currentUser.value = user
  }

  // --- 13. STUDENT ACCESS CHECK ---
  // Returns Pair<Boolean, String>: (isAllowed, reason)
  fun checkCourseAccess(courseId: String, user: StudentUser?): Pair<Boolean, String> {
    if (user == null) {
      return Pair(false, "Authentication required. Please log in with your verified Google account.")
    }

    // Check account status
    when (user.status) {
      RegistrationStatus.PENDING -> {
        return Pair(false, "Your registration is currently pending admin review and approval.")
      }
      RegistrationStatus.SUSPENDED -> {
        return Pair(false, "Your account has been suspended by the administration.")
      }
      RegistrationStatus.REJECTED -> {
        return Pair(false, "Your registration was not approved.")
      }
      RegistrationStatus.APPROVED -> {
        // Proceed to check course authorization
      }
    }

    val course = _courses.value.find { it.id == courseId }
      ?: return Pair(false, "Course not found.")

    val isEmailAuthorized = course.authorizedEmails.any {
      it.equals(user.gmailAddress, ignoreCase = true) ||
      it.equals(user.verifiedGoogleEmail, ignoreCase = true)
    }

    return if (isEmailAuthorized) {
      Pair(true, "Access Granted")
    } else {
      Pair(false, "You don't have access to this class. Your Gmail (${user.gmailAddress}) is not authorized for this course.")
    }
  }

  // Mark lesson progress
  fun markLessonCompleted(userId: String, courseId: String, lessonId: String) {
    val key = "${userId}_$courseId"
    val existing = _completedLessons.value[key] ?: emptySet()
    _completedLessons.value = _completedLessons.value + (key to (existing + lessonId))
  }

  fun getCompletedLessonIds(userId: String, courseId: String): Set<String> {
    val key = "${userId}_$courseId"
    return _completedLessons.value[key] ?: emptySet()
  }

  // --- 9. HIDDEN ADMIN ENTRY & RATE LIMITING ---
  fun verifyAdminAccessKey(enteredKey: String): Pair<Boolean, String> {
    val now = System.currentTimeMillis()
    if (now < _lockoutUntilTimestamp.value) {
      val remainingSec = ((_lockoutUntilTimestamp.value - now) / 1000) + 1
      return Pair(false, "Locked out due to repeated failed attempts. Try again in $remainingSec seconds.")
    }

    if (enteredKey.trim() == PROTOTYPE_ADMIN_KEY) {
      _failedAttempts.value = 0
      return Pair(true, "Access key verified. Proceed to admin authentication.")
    } else {
      val newAttempts = _failedAttempts.value + 1
      _failedAttempts.value = newAttempts
      if (newAttempts >= 3) {
        _lockoutUntilTimestamp.value = now + 30_000 // 30 seconds lockout
        return Pair(false, "Incorrect key. 3 failed attempts. Temporary lockout for 30 seconds.")
      }
      val remaining = 3 - newAttempts
      return Pair(false, "Incorrect access key. $remaining attempts remaining before temporary lockout.")
    }
  }

  fun authenticateAdmin(adminEmail: String): Boolean {
    // Only owner's authorized account can access Admin Space
    val isAuthorized = adminEmail.trim().equals(OWNER_ADMIN_EMAIL, ignoreCase = true)
    _isAdminAuthenticated.value = isAuthorized
    return isAuthorized
  }

  fun logoutAdmin() {
    _isAdminAuthenticated.value = false
  }

  // --- ADMIN ACTIONS: STUDENT MANAGEMENT ---
  fun approveStudent(studentId: String) {
    val list = _registrations.value.toMutableList()
    val index = list.indexOfFirst { it.id == studentId }
    if (index != -1) {
      val student = list[index]
      val updated = student.copy(status = RegistrationStatus.APPROVED)
      list[index] = updated
      _registrations.value = list

      // Also automatically add student's Gmail to selected course authorizedEmails
      addAuthorizedEmailToCourse(student.selectedCourseId, student.gmailAddress)

      // If active current user is this student, update session
      if (_currentUser.value?.id == studentId) {
        _currentUser.value = updated
      }
    }
  }

  fun suspendStudent(studentId: String) {
    updateStudentStatus(studentId, RegistrationStatus.SUSPENDED)
  }

  fun reactivateStudent(studentId: String) {
    updateStudentStatus(studentId, RegistrationStatus.APPROVED)
  }

  fun rejectStudent(studentId: String) {
    updateStudentStatus(studentId, RegistrationStatus.REJECTED)
  }

  fun removeStudentAccess(studentId: String) {
    val list = _registrations.value.toMutableList()
    val student = list.find { it.id == studentId }
    if (student != null) {
      // Remove from authorized emails in all courses
      _courses.value.forEach { course ->
        removeAuthorizedEmailFromCourse(course.id, student.gmailAddress)
      }
      list.remove(student)
      _registrations.value = list
      if (_currentUser.value?.id == studentId) {
        _currentUser.value = null
      }
    }
  }

  private fun updateStudentStatus(studentId: String, status: RegistrationStatus) {
    val list = _registrations.value.toMutableList()
    val index = list.indexOfFirst { it.id == studentId }
    if (index != -1) {
      val updated = list[index].copy(status = status)
      list[index] = updated
      _registrations.value = list

      if (_currentUser.value?.id == studentId) {
        _currentUser.value = updated
      }
    }
  }

  // --- ADMIN ACTIONS: COURSE MANAGEMENT ---
  fun createCourse(
    titleEn: String,
    titleMl: String,
    subtitleEn: String,
    subtitleMl: String,
    languageType: String,
    initialAuthorizedEmails: List<String>
  ): Course {
    val newId = "course-${System.currentTimeMillis()}"
    val newCourse = Course(
      id = newId,
      titleEn = titleEn,
      titleMl = titleMl,
      subtitleEn = subtitleEn,
      subtitleMl = subtitleMl,
      languageType = languageType,
      authorizedEmails = (initialAuthorizedEmails + OWNER_ADMIN_EMAIL).distinct(),
      lessonsCount = 0
    )
    _courses.value = _courses.value + newCourse
    _lessons.value = _lessons.value + (newId to emptyList())
    return newCourse
  }

  // --- ADMIN ACTIONS: GMAIL PERMISSION MANAGEMENT ---
  fun addAuthorizedEmailToCourse(courseId: String, email: String) {
    val courseList = _courses.value.toMutableList()
    val index = courseList.indexOfFirst { it.id == courseId }
    if (index != -1) {
      val course = courseList[index]
      val normalized = email.trim().lowercase()
      if (!course.authorizedEmails.any { it.equals(normalized, ignoreCase = true) }) {
        val updated = course.copy(authorizedEmails = course.authorizedEmails + normalized)
        courseList[index] = updated
        _courses.value = courseList
      }
    }
  }

  fun removeAuthorizedEmailFromCourse(courseId: String, email: String) {
    val courseList = _courses.value.toMutableList()
    val index = courseList.indexOfFirst { it.id == courseId }
    if (index != -1) {
      val course = courseList[index]
      val updated = course.copy(
        authorizedEmails = course.authorizedEmails.filterNot { it.equals(email.trim(), ignoreCase = true) }
      )
      courseList[index] = updated
      _courses.value = courseList
    }
  }

  // --- ADMIN ACTIONS: LESSON CREATION ---
  fun addLesson(
    courseId: String,
    titleEn: String,
    titleMl: String,
    descriptionEn: String,
    descriptionMl: String,
    contentEn: String,
    contentMl: String,
    videoUrl: String?,
    videoDuration: String = "15:00",
    pdfAttachmentName: String? = null,
    imageAttachmentName: String? = null,
    notesEn: String = "",
    notesMl: String = ""
  ): Lesson {
    val courseLessons = (_lessons.value[courseId] ?: emptyList()).toMutableList()
    val nextNum = courseLessons.size + 1
    val newLesson = Lesson(
      id = "$courseId-les-$nextNum",
      courseId = courseId,
      lessonNumber = nextNum,
      titleEn = titleEn,
      titleMl = titleMl,
      descriptionEn = descriptionEn,
      descriptionMl = descriptionMl,
      contentEn = contentEn,
      contentMl = contentMl,
      videoUrl = videoUrl,
      videoDuration = videoDuration,
      pdfAttachmentName = pdfAttachmentName,
      imageAttachmentName = imageAttachmentName,
      notesEn = notesEn,
      notesMl = notesMl
    )
    courseLessons.add(newLesson)
    _lessons.value = _lessons.value + (courseId to courseLessons)

    // update lesson count in course
    val courseList = _courses.value.toMutableList()
    val index = courseList.indexOfFirst { it.id == courseId }
    if (index != -1) {
      courseList[index] = courseList[index].copy(lessonsCount = courseLessons.size)
      _courses.value = courseList
    }

    return newLesson
  }

  // --- ADMIN STATISTICS ---
  fun getAdminStats(): AdminStats {
    val regs = _registrations.value
    val totalCourses = _courses.value.size
    val allLessons = _lessons.value.values.flatten()
    val totalLessons = allLessons.size
    val totalVideos = allLessons.count { it.videoUrl != null }

    return AdminStats(
      totalStudents = regs.size,
      pendingRegistrations = regs.count { it.status == RegistrationStatus.PENDING },
      approvedStudents = regs.count { it.status == RegistrationStatus.APPROVED },
      suspendedStudents = regs.count { it.status == RegistrationStatus.SUSPENDED },
      totalCourses = totalCourses,
      totalLessons = totalLessons,
      totalVideos = totalVideos
    )
  }
}
