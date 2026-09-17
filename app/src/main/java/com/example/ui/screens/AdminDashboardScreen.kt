package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HypnotismRepository
import com.example.model.AppLanguage
import com.example.model.Course
import com.example.model.RegistrationStatus
import com.example.model.StudentUser
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class AdminTab {
  STATISTICS,
  STUDENTS,
  COURSES,
  GMAIL_PERMISSIONS,
  VIDEO_UPLOAD
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
  currentLanguage: AppLanguage,
  onExitAdmin: () -> Unit
) {
  val repository = remember { HypnotismRepository.getInstance() }
  val courses by repository.courses.collectAsState()
  val registrations by repository.registrations.collectAsState()
  val stats = remember(registrations, courses) { repository.getAdminStats() }

  var selectedTab by remember { mutableStateOf(AdminTab.STATISTICS) }
  var selectedStudentForProfile by remember { mutableStateOf<StudentUser?>(null) }

  // Course Creation Dialog State
  var showCreateCourseDialog by remember { mutableStateOf(false) }
  var newCourseTitleEn by remember { mutableStateOf("") }
  var newCourseTitleMl by remember { mutableStateOf("") }
  var newCourseSubtitleEn by remember { mutableStateOf("") }
  var newCourseLanguageType by remember { mutableStateOf("Malayalam & English") }

  // Add Lesson Dialog State
  var showAddLessonDialogForCourse by remember { mutableStateOf<Course?>(null) }
  var newLessonTitleEn by remember { mutableStateOf("") }
  var newLessonTitleMl by remember { mutableStateOf("") }
  var newLessonContentEn by remember { mutableStateOf("") }
  var newLessonContentMl by remember { mutableStateOf("") }

  // Gmail Permission Management State
  var selectedCourseForGmail by remember { mutableStateOf(courses.firstOrNull()) }
  var newAuthorizedEmailInput by remember { mutableStateOf("") }

  // Video Upload Simulation State (Android Phone Upload)
  var isUploadingVideo by remember { mutableStateOf(false) }
  var uploadProgress by remember { mutableFloatStateOf(0f) }
  var uploadStatusText by remember { mutableStateOf("Ready to upload") }
  var uploadSuccess by remember { mutableStateOf(false) }
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = WisdomAmber)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Admin Space", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
          }
        },
        navigationIcon = {
          IconButton(onClick = onExitAdmin) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Exit Admin", tint = TextPrimary)
          }
        },
        actions = {
          TextButton(
            onClick = {
              repository.logoutAdmin()
              onExitAdmin()
            }
          ) {
            Text("Logout", color = ErrorRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MidnightSurface)
      )
    },
    containerColor = DeepObsidian
  ) { innerPadding ->
    BoxWithConstraints(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .testTag("admin_dashboard_root")
    ) {
      val isDesktop = maxWidth >= 960.dp

      if (isDesktop) {
        // DESKTOP SIDEBAR + MAIN CONTENT PANE
        Row(modifier = Modifier.fillMaxSize()) {
          // SIDEBAR (240.dp)
          Surface(
            shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
            color = MidnightSurface,
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
            modifier = Modifier
              .width(250.dp)
              .fillMaxHeight()
          ) {
            Column(
              modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Text(
                text = "ADMIN PORTAL",
                color = TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 8.dp)
              )

              AdminSidebarItem(
                title = "Overview & Stats",
                icon = Icons.Default.Analytics,
                isSelected = selectedTab == AdminTab.STATISTICS,
                onClick = { selectedTab = AdminTab.STATISTICS }
              )

              AdminSidebarItem(
                title = "Students (${registrations.size})",
                icon = Icons.Default.People,
                isSelected = selectedTab == AdminTab.STUDENTS,
                onClick = { selectedTab = AdminTab.STUDENTS }
              )

              AdminSidebarItem(
                title = "Courses & Lessons",
                icon = Icons.Default.School,
                isSelected = selectedTab == AdminTab.COURSES,
                onClick = { selectedTab = AdminTab.COURSES }
              )

              AdminSidebarItem(
                title = "Gmail Permissions",
                icon = Icons.Default.VpnKey,
                isSelected = selectedTab == AdminTab.GMAIL_PERMISSIONS,
                onClick = { selectedTab = AdminTab.GMAIL_PERMISSIONS }
              )

              AdminSidebarItem(
                title = "Video Upload",
                icon = Icons.Default.CloudUpload,
                isSelected = selectedTab == AdminTab.VIDEO_UPLOAD,
                onClick = { selectedTab = AdminTab.VIDEO_UPLOAD }
              )

              Spacer(modifier = Modifier.weight(1f))

              Surface(
                shape = RoundedCornerShape(10.dp),
                color = SlateCard,
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(10.dp)) {
                  Text("System Security", color = WisdomAmber, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  Spacer(modifier = Modifier.height(2.dp))
                  Text("Rate Limiter: Active", color = TextSecondary, fontSize = 10.sp)
                  Text("DRM Watermarking: Active", color = TextSecondary, fontSize = 10.sp)
                }
              }
            }
          }

          // MAIN CONTENT AREA
          Box(
            modifier = Modifier
              .weight(1f)
              .fillMaxHeight()
          ) {
            when (selectedTab) {
              AdminTab.STATISTICS -> AdminStatisticsTab(stats = stats, registrations = registrations, onSelectStudent = { selectedStudentForProfile = it })
              AdminTab.STUDENTS -> AdminStudentsTab(
                registrations = registrations,
                onApprove = { repository.approveStudent(it.id) },
                onSuspend = { repository.suspendStudent(it.id) },
                onReactivate = { repository.reactivateStudent(it.id) },
                onReject = { repository.rejectStudent(it.id) },
                onRemove = { repository.removeStudentAccess(it.id) },
                onViewProfile = { selectedStudentForProfile = it }
              )
              AdminTab.COURSES -> AdminCoursesTab(
                courses = courses,
                onCreateCourseClick = { showCreateCourseDialog = true },
                onAddLessonClick = { showAddLessonDialogForCourse = it }
              )
              AdminTab.GMAIL_PERMISSIONS -> AdminGmailPermissionsTab(
                courses = courses,
                selectedCourse = selectedCourseForGmail ?: courses.firstOrNull(),
                onSelectCourse = { selectedCourseForGmail = it },
                newEmailInput = newAuthorizedEmailInput,
                onNewEmailChange = { newAuthorizedEmailInput = it },
                onAddEmail = { courseId, email ->
                  if (email.isNotBlank() && email.contains("@")) {
                    repository.addAuthorizedEmailToCourse(courseId, email)
                    newAuthorizedEmailInput = ""
                  }
                },
                onRemoveEmail = { courseId, email ->
                  repository.removeAuthorizedEmailFromCourse(courseId, email)
                }
              )
              AdminTab.VIDEO_UPLOAD -> AdminVideoUploadTab(
                courses = courses,
                isUploading = isUploadingVideo,
                progress = uploadProgress,
                statusText = uploadStatusText,
                isSuccess = uploadSuccess,
                onStartUpload = { courseId, title ->
                  coroutineScope.launch {
                    isUploadingVideo = true
                    uploadSuccess = false
                    uploadStatusText = "Connecting to private Firebase Storage..."
                    uploadProgress = 0.1f
                    delay(400)
                    uploadStatusText = "Encrypting video file..."
                    uploadProgress = 0.35f
                    delay(500)
                    uploadStatusText = "Uploading private encrypted chunks..."
                    uploadProgress = 0.75f
                    delay(600)
                    uploadStatusText = "Verifying DRM token & watermarking tags..."
                    uploadProgress = 0.95f
                    delay(400)
                    uploadProgress = 1.0f
                    uploadStatusText = "Upload complete! Video secured in private course storage."
                    uploadSuccess = true
                    isUploadingVideo = false

                    repository.addLesson(
                      courseId = courseId,
                      titleEn = title.ifBlank { "New Uploaded Video Lesson" },
                      titleMl = "പുതിയ വീഡിയോ പാഠം",
                      descriptionEn = "Uploaded private lesson from storage.",
                      descriptionMl = "സുരക്ഷിതമായി അപ്‌ലോഡ് ചെയ്ത വീഡിയോ പാഠം.",
                      contentEn = "Video content protected by student watermarks.",
                      contentMl = "വിദ്യാർത്ഥി വാട്ടർമാർക്ക് വഴി സുരക്ഷിതമാക്കിയ ഉള്ളടക്കം.",
                      videoUrl = "secure_storage://videos/${System.currentTimeMillis()}.mp4",
                      videoDuration = "24:00"
                    )
                  }
                }
              )
            }
          }
        }
      } else {
        // MOBILE & TABLET LAYOUT: Scrollable Tab Bar
        Column(modifier = Modifier.fillMaxSize()) {
          ScrollableTabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = MidnightSurface,
            contentColor = FocusCyanLight,
            edgePadding = 16.dp,
            modifier = Modifier.fillMaxWidth()
          ) {
            Tab(
              selected = selectedTab == AdminTab.STATISTICS,
              onClick = { selectedTab = AdminTab.STATISTICS },
              text = { Text("Overview & Stats") }
            )
            Tab(
              selected = selectedTab == AdminTab.STUDENTS,
              onClick = { selectedTab = AdminTab.STUDENTS },
              text = { Text("Students (${registrations.size})") }
            )
            Tab(
              selected = selectedTab == AdminTab.COURSES,
              onClick = { selectedTab = AdminTab.COURSES },
              text = { Text("Courses & Lessons") }
            )
            Tab(
              selected = selectedTab == AdminTab.GMAIL_PERMISSIONS,
              onClick = { selectedTab = AdminTab.GMAIL_PERMISSIONS },
              text = { Text("Gmail Access") }
            )
            Tab(
              selected = selectedTab == AdminTab.VIDEO_UPLOAD,
              onClick = { selectedTab = AdminTab.VIDEO_UPLOAD },
              text = { Text("Video Upload") }
            )
          }

          when (selectedTab) {
            AdminTab.STATISTICS -> AdminStatisticsTab(stats = stats, registrations = registrations, onSelectStudent = { selectedStudentForProfile = it })
            AdminTab.STUDENTS -> AdminStudentsTab(
              registrations = registrations,
              onApprove = { repository.approveStudent(it.id) },
              onSuspend = { repository.suspendStudent(it.id) },
              onReactivate = { repository.reactivateStudent(it.id) },
              onReject = { repository.rejectStudent(it.id) },
              onRemove = { repository.removeStudentAccess(it.id) },
              onViewProfile = { selectedStudentForProfile = it }
            )
            AdminTab.COURSES -> AdminCoursesTab(
              courses = courses,
              onCreateCourseClick = { showCreateCourseDialog = true },
              onAddLessonClick = { showAddLessonDialogForCourse = it }
            )
            AdminTab.GMAIL_PERMISSIONS -> AdminGmailPermissionsTab(
              courses = courses,
              selectedCourse = selectedCourseForGmail ?: courses.firstOrNull(),
              onSelectCourse = { selectedCourseForGmail = it },
              newEmailInput = newAuthorizedEmailInput,
              onNewEmailChange = { newAuthorizedEmailInput = it },
              onAddEmail = { courseId, email ->
                if (email.isNotBlank() && email.contains("@")) {
                  repository.addAuthorizedEmailToCourse(courseId, email)
                  newAuthorizedEmailInput = ""
                }
              },
              onRemoveEmail = { courseId, email ->
                repository.removeAuthorizedEmailFromCourse(courseId, email)
              }
            )
            AdminTab.VIDEO_UPLOAD -> AdminVideoUploadTab(
              courses = courses,
              isUploading = isUploadingVideo,
              progress = uploadProgress,
              statusText = uploadStatusText,
              isSuccess = uploadSuccess,
              onStartUpload = { courseId, title ->
                coroutineScope.launch {
                  isUploadingVideo = true
                  uploadSuccess = false
                  uploadStatusText = "Connecting to private Firebase Storage..."
                  uploadProgress = 0.1f
                  delay(400)
                  uploadStatusText = "Encrypting video file on Android phone..."
                  uploadProgress = 0.35f
                  delay(500)
                  uploadStatusText = "Uploading private encrypted chunks..."
                  uploadProgress = 0.75f
                  delay(600)
                  uploadStatusText = "Verifying DRM token & watermarking tags..."
                  uploadProgress = 0.95f
                  delay(400)
                  uploadProgress = 1.0f
                  uploadStatusText = "Upload complete! Video secured in private course storage."
                  uploadSuccess = true
                  isUploadingVideo = false

                  repository.addLesson(
                    courseId = courseId,
                    titleEn = title.ifBlank { "New Uploaded Video Lesson" },
                    titleMl = "പുതിയ വീഡിയോ പാഠം",
                    descriptionEn = "Uploaded private lesson from Android device.",
                    descriptionMl = "സുരക്ഷിതമായി അപ്‌ലോഡ് ചെയ്ത വീഡിയോ പാഠം.",
                    contentEn = "Video content protected by student watermarks.",
                    contentMl = "വിദ്യാർത്ഥി വാട്ടർമാർക്ക് വഴി സുരക്ഷിതമാക്കിയ ഉള്ളടക്കം.",
                    videoUrl = "secure_storage://videos/${System.currentTimeMillis()}.mp4",
                    videoDuration = "24:00"
                  )
                }
              }
            )
          }
        }
      }
    }
  }

  // Student Profile Dialog (Section 17: STUDENT PROFILE IN ADMIN)
  if (selectedStudentForProfile != null) {
    val student = selectedStudentForProfile!!
    AlertDialog(
      onDismissRequest = { selectedStudentForProfile = null },
      title = {
        Text("Student Profile", color = TextPrimary, fontWeight = FontWeight.Bold)
      },
      text = {
        Column(modifier = Modifier.fillMaxWidth()) {
          ProfileFieldRow("Full Name", student.fullName)
          ProfileFieldRow("Age", "${student.age} years")
          ProfileFieldRow("Gmail", student.gmailAddress)
          ProfileFieldRow("Verified Google ID", student.verifiedGoogleEmail)
          ProfileFieldRow("Phone Number", student.phoneNumber)
          ProfileFieldRow("WhatsApp", student.whatsAppNumber)
          ProfileFieldRow("Address", student.address)
          ProfileFieldRow("Registered Course", student.selectedCourseId)
          ProfileFieldRow("Registration Date", student.registrationDate)
          ProfileFieldRow("Current Status", student.status.name)
          ProfileFieldRow("Last Login", student.lastLogin)
        }
      },
      confirmButton = {
        TextButton(onClick = { selectedStudentForProfile = null }) {
          Text("Close", color = FocusCyanLight)
        }
      }
    )
  }

  // Create Course Dialog (Section 10)
  if (showCreateCourseDialog) {
    AlertDialog(
      onDismissRequest = { showCreateCourseDialog = false },
      title = { Text("Create New Course", color = TextPrimary, fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          OutlinedTextField(
            value = newCourseTitleEn,
            onValueChange = { newCourseTitleEn = it },
            label = { Text("Course Title (English)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newCourseTitleMl,
            onValueChange = { newCourseTitleMl = it },
            label = { Text("Course Title (Malayalam)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newCourseSubtitleEn,
            onValueChange = { newCourseSubtitleEn = it },
            label = { Text("Subtitle / Description") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newCourseLanguageType,
            onValueChange = { newCourseLanguageType = it },
            label = { Text("Language Type (Malayalam / English / Bilingual)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (newCourseTitleEn.isNotBlank()) {
              repository.createCourse(
                titleEn = newCourseTitleEn,
                titleMl = newCourseTitleMl.ifBlank { newCourseTitleEn },
                subtitleEn = newCourseSubtitleEn,
                subtitleMl = "വിദ്യാഭ്യാസ കോഴ്സ്",
                languageType = newCourseLanguageType,
                initialAuthorizedEmails = emptyList()
              )
              showCreateCourseDialog = false
              newCourseTitleEn = ""
              newCourseTitleMl = ""
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = MindIndigo)
        ) {
          Text("Create Course", color = Color.White)
        }
      },
      dismissButton = {
        TextButton(onClick = { showCreateCourseDialog = false }) {
          Text("Cancel", color = TextSecondary)
        }
      }
    )
  }

  // Add Lesson Dialog (Section 10)
  if (showAddLessonDialogForCourse != null) {
    val targetCourse = showAddLessonDialogForCourse!!
    AlertDialog(
      onDismissRequest = { showAddLessonDialogForCourse = null },
      title = { Text("Add Lesson to ${targetCourse.titleEn}", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          OutlinedTextField(
            value = newLessonTitleEn,
            onValueChange = { newLessonTitleEn = it },
            label = { Text("Lesson Title (English)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newLessonTitleMl,
            onValueChange = { newLessonTitleMl = it },
            label = { Text("Lesson Title (Malayalam)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newLessonContentEn,
            onValueChange = { newLessonContentEn = it },
            label = { Text("Content (English)") },
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newLessonContentMl,
            onValueChange = { newLessonContentMl = it },
            label = { Text("Content (Malayalam)") },
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (newLessonTitleEn.isNotBlank()) {
              repository.addLesson(
                courseId = targetCourse.id,
                titleEn = newLessonTitleEn,
                titleMl = newLessonTitleMl.ifBlank { newLessonTitleEn },
                descriptionEn = "Structured lesson in ${targetCourse.titleEn}",
                descriptionMl = "പാഠ്യപദ്ധതി ഭാഗം",
                contentEn = newLessonContentEn.ifBlank { "Educational material for hypnotism." },
                contentMl = newLessonContentMl.ifBlank { "ഹിപ്നോട്ടിസം പഠന വിവരണം." },
                videoUrl = "secure_storage://videos/custom_${System.currentTimeMillis()}.mp4",
                videoDuration = "20:00"
              )
              showAddLessonDialogForCourse = null
              newLessonTitleEn = ""
              newLessonTitleMl = ""
              newLessonContentEn = ""
              newLessonContentMl = ""
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = MindIndigo)
        ) {
          Text("Add Lesson", color = Color.White)
        }
      },
      dismissButton = {
        TextButton(onClick = { showAddLessonDialogForCourse = null }) {
          Text("Cancel", color = TextSecondary)
        }
      }
    )
  }
}

// 18. ADMIN STATISTICS TAB
@Composable
private fun AdminStatisticsTab(
  stats: com.example.model.AdminStats,
  registrations: List<StudentUser>,
  onSelectStudent: (StudentUser) -> Unit
) {
  BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
    val isWide = maxWidth >= 700.dp

    LazyColumn(
      contentPadding = PaddingValues(20.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      item {
        Text("Platform Overview", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
      }

      if (isWide) {
        // Desktop / Wide: 4-Column and 3-Column Rows
        item {
          Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(title = "Total Students", value = stats.totalStudents.toString(), icon = Icons.Default.People, accent = FocusCyanLight, modifier = Modifier.weight(1f))
            StatCard(title = "Pending Review", value = stats.pendingRegistrations.toString(), icon = Icons.Default.HourglassTop, accent = WisdomAmber, modifier = Modifier.weight(1f))
            StatCard(title = "Approved", value = stats.approvedStudents.toString(), icon = Icons.Default.Verified, accent = SuccessGreen, modifier = Modifier.weight(1f))
            StatCard(title = "Suspended", value = stats.suspendedStudents.toString(), icon = Icons.Default.Block, accent = ErrorRed, modifier = Modifier.weight(1f))
          }
        }

        item {
          Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(title = "Total Courses", value = stats.totalCourses.toString(), icon = Icons.Default.School, accent = MindIndigoLight, modifier = Modifier.weight(1f))
            StatCard(title = "Total Lessons", value = stats.totalLessons.toString(), icon = Icons.Default.MenuBook, accent = FocusCyan, modifier = Modifier.weight(1f))
            StatCard(title = "Protected Videos", value = stats.totalVideos.toString(), icon = Icons.Default.VideoLibrary, accent = WisdomAmber, modifier = Modifier.weight(1f))
          }
        }
      } else {
        // Mobile: 2-Column Rows
        item {
          Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(title = "Total Students", value = stats.totalStudents.toString(), icon = Icons.Default.People, accent = FocusCyanLight, modifier = Modifier.weight(1f))
            StatCard(title = "Pending Review", value = stats.pendingRegistrations.toString(), icon = Icons.Default.HourglassTop, accent = WisdomAmber, modifier = Modifier.weight(1f))
          }
        }

        item {
          Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(title = "Approved", value = stats.approvedStudents.toString(), icon = Icons.Default.Verified, accent = SuccessGreen, modifier = Modifier.weight(1f))
            StatCard(title = "Suspended", value = stats.suspendedStudents.toString(), icon = Icons.Default.Block, accent = ErrorRed, modifier = Modifier.weight(1f))
          }
        }

        item {
          Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(title = "Total Courses", value = stats.totalCourses.toString(), icon = Icons.Default.School, accent = MindIndigoLight, modifier = Modifier.weight(1f))
            StatCard(title = "Total Lessons", value = stats.totalLessons.toString(), icon = Icons.Default.MenuBook, accent = FocusCyan, modifier = Modifier.weight(1f))
          }
        }

        item {
          StatCard(title = "Protected Videos", value = stats.totalVideos.toString(), icon = Icons.Default.VideoLibrary, accent = WisdomAmber, modifier = Modifier.fillMaxWidth())
        }
      }

      item {
        Spacer(modifier = Modifier.height(8.dp))
        Text("Recent Registrations", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
      }

      items(registrations.take(5)) { student ->
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = SlateCard,
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectStudent(student) }
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(student.fullName, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
              Text("${student.gmailAddress} • ${student.registrationDate}", color = TextSecondary, fontSize = 11.sp)
            }
            StatusChip(student.status)
          }
        }
      }
    }
  }
}

// 8 & 17: STUDENT REGISTRATIONS & MANAGEMENT TAB
@Composable
private fun AdminStudentsTab(
  registrations: List<StudentUser>,
  onApprove: (StudentUser) -> Unit,
  onSuspend: (StudentUser) -> Unit,
  onReactivate: (StudentUser) -> Unit,
  onReject: (StudentUser) -> Unit,
  onRemove: (StudentUser) -> Unit,
  onViewProfile: (StudentUser) -> Unit
) {
  LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = Modifier.fillMaxSize()
  ) {
    items(registrations) { student ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(student.fullName, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
              Text("Gmail: ${student.gmailAddress}", color = FocusCyanLight, fontSize = 12.sp)
              Text("Course: ${student.selectedCourseId} • Age: ${student.age}", color = TextSecondary, fontSize = 11.sp)
            }
            StatusChip(student.status)
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            // View profile
            OutlinedButton(
              onClick = { onViewProfile(student) },
              modifier = Modifier.weight(1f),
              contentPadding = PaddingValues(4.dp)
            ) {
              Text("Profile", fontSize = 11.sp)
            }

            // Action according to current status
            when (student.status) {
              RegistrationStatus.PENDING -> {
                Button(
                  onClick = { onApprove(student) },
                  colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                  modifier = Modifier.weight(1f),
                  contentPadding = PaddingValues(4.dp)
                ) {
                  Text("Approve", fontSize = 11.sp, color = Color.White)
                }
                OutlinedButton(
                  onClick = { onReject(student) },
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                  modifier = Modifier.weight(1f),
                  contentPadding = PaddingValues(4.dp)
                ) {
                  Text("Reject", fontSize = 11.sp)
                }
              }
              RegistrationStatus.APPROVED -> {
                Button(
                  onClick = { onSuspend(student) },
                  colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                  modifier = Modifier.weight(1f),
                  contentPadding = PaddingValues(4.dp)
                ) {
                  Text("Suspend", fontSize = 11.sp, color = Color.White)
                }
              }
              RegistrationStatus.SUSPENDED -> {
                Button(
                  onClick = { onReactivate(student) },
                  colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                  modifier = Modifier.weight(1f),
                  contentPadding = PaddingValues(4.dp)
                ) {
                  Text("Reactivate", fontSize = 11.sp, color = Color.White)
                }
              }
              RegistrationStatus.REJECTED -> {
                Button(
                  onClick = { onApprove(student) },
                  colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                  modifier = Modifier.weight(1f),
                  contentPadding = PaddingValues(4.dp)
                ) {
                  Text("Approve", fontSize = 11.sp, color = Color.White)
                }
              }
            }

            // Remove access
            IconButton(
              onClick = { onRemove(student) },
              modifier = Modifier.size(36.dp)
            ) {
              Icon(Icons.Default.Delete, contentDescription = "Remove Access", tint = TextMuted, modifier = Modifier.size(18.dp))
            }
          }
        }
      }
    }
  }
}

// 10: COURSE & LESSON MANAGEMENT TAB
@Composable
private fun AdminCoursesTab(
  courses: List<Course>,
  onCreateCourseClick: () -> Unit,
  onAddLessonClick: (Course) -> Unit
) {
  val repository = remember { HypnotismRepository.getInstance() }
  val lessonsMap by repository.lessons.collectAsState()

  LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    modifier = Modifier.fillMaxSize()
  ) {
    item {
      Button(
        onClick = onCreateCourseClick,
        colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Create New Course", color = Color.White, fontWeight = FontWeight.Bold)
      }
    }

    items(courses) { course ->
      val courseLessons = lessonsMap[course.id] ?: emptyList()
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(course.titleEn, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
              Text(course.titleMl, color = FocusCyanLight, fontSize = 13.sp)
              Text("Language: ${course.languageType} • Authorized Accounts: ${course.authorizedEmails.size}", color = TextSecondary, fontSize = 11.sp)
            }

            Button(
              onClick = { onAddLessonClick(course) },
              colors = ButtonDefaults.buttonColors(containerColor = SlateCardBorder),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Icon(Icons.Default.Add, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("+ Lesson", color = FocusCyanLight, fontSize = 11.sp)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text("Lessons (${courseLessons.size}):", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
          courseLessons.forEach { lesson ->
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
            ) {
              Icon(Icons.Default.PlayCircleOutline, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(lesson.titleEn, color = TextPrimary, fontSize = 12.sp, modifier = Modifier.weight(1f))
              Text(lesson.videoDuration, color = TextMuted, fontSize = 11.sp)
            }
          }
        }
      }
    }
  }
}

// 12: PRIVATE COURSE ACCESS & GMAIL PERMISSIONS TAB
@Composable
private fun AdminGmailPermissionsTab(
  courses: List<Course>,
  selectedCourse: Course?,
  onSelectCourse: (Course) -> Unit,
  newEmailInput: String,
  onNewEmailChange: (String) -> Unit,
  onAddEmail: (String, String) -> Unit,
  onRemoveEmail: (String, String) -> Unit
) {
  if (selectedCourse == null) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
      Text("No courses available", color = TextSecondary)
    }
    return
  }

  LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = Modifier.fillMaxSize()
  ) {
    item {
      Text("Select Course to Manage Access:", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(8.dp))
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        courses.forEach { c ->
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (c.id == selectedCourse.id) MindIndigo.copy(alpha = 0.3f) else SlateCard,
            border = CardDefaults.outlinedCardBorder().copy(
              brush = androidx.compose.ui.graphics.SolidColor(if (c.id == selectedCourse.id) FocusCyanLight else SlateCardBorder)
            ),
            modifier = Modifier.clickable { onSelectCourse(c) }
          ) {
            Text(
              text = c.languageType,
              color = if (c.id == selectedCourse.id) FocusCyanLight else TextSecondary,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(4.dp))
      Text("Add Authorized Gmail Account:", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        OutlinedTextField(
          value = newEmailInput,
          onValueChange = onNewEmailChange,
          placeholder = { Text("student@gmail.com", color = TextMuted) },
          singleLine = true,
          modifier = Modifier.weight(1f)
        )
        Button(
          onClick = { onAddEmail(selectedCourse.id, newEmailInput) },
          colors = ButtonDefaults.buttonColors(containerColor = MindIndigo)
        ) {
          Text("Authorize")
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(8.dp))
      Text("Currently Authorized Gmail Accounts (${selectedCourse.authorizedEmails.size}):", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }

    items(selectedCourse.authorizedEmails) { email ->
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = SlateCard,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(12.dp)
        ) {
          Icon(Icons.Default.AccountCircle, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(10.dp))
          Text(email, color = TextPrimary, fontSize = 13.sp, modifier = Modifier.weight(1f))
          if (email != HypnotismRepository.OWNER_ADMIN_EMAIL) {
            TextButton(
              onClick = { onRemoveEmail(selectedCourse.id, email) }
            ) {
              Text("Revoke", color = ErrorRed, fontSize = 12.sp)
            }
          } else {
            Text("Owner", color = WisdomAmber, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

// 11: VIDEO UPLOAD TAB (from Android Phone)
@Composable
private fun AdminVideoUploadTab(
  courses: List<Course>,
  isUploading: Boolean,
  progress: Float,
  statusText: String,
  isSuccess: Boolean,
  onStartUpload: (String, String) -> Unit
) {
  var selectedCourseId by remember { mutableStateOf(courses.firstOrNull()?.id ?: "") }
  var videoTitle by remember { mutableStateOf("") }

  LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    modifier = Modifier.fillMaxSize()
  ) {
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CloudUpload, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text("Upload Video from Android Phone", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            "Select a course and upload private MP4 recordings from Android Gallery or File Picker directly into encrypted private storage.",
            color = TextSecondary,
            fontSize = 12.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          OutlinedTextField(
            value = videoTitle,
            onValueChange = { videoTitle = it },
            label = { Text("Lesson / Video Title") },
            placeholder = { Text("e.g. Rapid Induction & Eye Fixation Lab") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(12.dp))

          Text("Select Destination Course:", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
          courses.forEach { c ->
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedCourseId = c.id }
                .padding(vertical = 4.dp)
            ) {
              RadioButton(selected = selectedCourseId == c.id, onClick = { selectedCourseId = c.id })
              Spacer(modifier = Modifier.width(6.dp))
              Text(c.titleEn, color = TextPrimary, fontSize = 13.sp)
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          if (isUploading) {
            Text(statusText, color = FocusCyanLight, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
              progress = { progress },
              color = FocusCyanLight,
              trackColor = MidnightSurface,
              modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("${(progress * 100).toInt()}% Uploaded", color = TextSecondary, fontSize = 11.sp)
          } else if (isSuccess) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = SuccessGreen.copy(alpha = 0.2f),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "✓ $statusText",
                color = SuccessGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = {
              if (selectedCourseId.isNotBlank()) {
                onStartUpload(selectedCourseId, videoTitle)
              }
            },
            enabled = !isUploading,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("upload_video_button")
          ) {
            Icon(Icons.Default.VideoCall, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isUploading) "Uploading Video..." else "+ Upload Video from Phone", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

@Composable
private fun StatCard(
  title: String,
  value: String,
  icon: ImageVector,
  accent: Color,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = SlateCard,
    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
    modifier = modifier
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(14.dp)
    ) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(accent.copy(alpha = 0.15f))
      ) {
        Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.size(20.dp))
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(value, color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Black)
        Text(title, color = TextSecondary, fontSize = 11.sp)
      }
    }
  }
}

@Composable
private fun StatusChip(status: RegistrationStatus) {
  val (color, text) = when (status) {
    RegistrationStatus.APPROVED -> Pair(SuccessGreen, "APPROVED")
    RegistrationStatus.PENDING -> Pair(WisdomAmber, "PENDING")
    RegistrationStatus.SUSPENDED -> Pair(ErrorRed, "SUSPENDED")
    RegistrationStatus.REJECTED -> Pair(ErrorRed, "REJECTED")
  }
  Surface(
    shape = RoundedCornerShape(6.dp),
    color = color.copy(alpha = 0.2f),
    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(color))
  ) {
    Text(
      text = text,
      color = color,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
    )
  }
}

@Composable
private fun ProfileFieldRow(label: String, value: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Text(label, color = TextSecondary, fontSize = 12.sp)
    Text(value, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
  }
}

@Composable
private fun AdminSidebarItem(
  title: String,
  icon: ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = if (isSelected) MindIndigo.copy(alpha = 0.35f) else Color.Transparent,
    border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FocusCyanLight)) else null,
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isSelected) FocusCyanLight else TextSecondary,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(12.dp))
      Text(
        text = title,
        color = if (isSelected) TextPrimary else TextSecondary,
        fontSize = 13.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
      )
    }
  }
}

