package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HypnotismRepository
import com.example.model.AppLanguage
import com.example.model.Course
import com.example.model.RegistrationStatus
import com.example.model.StudentUser
import com.example.ui.components.AppFooter
import com.example.ui.theme.*

@Composable
fun StudentDashboardScreen(
  currentLanguage: AppLanguage,
  currentUser: StudentUser?,
  courses: List<Course>,
  onOpenCourse: (Course) -> Unit,
  onJoinAnotherCourse: () -> Unit,
  onLogout: () -> Unit,
  onSwitchStudent: (StudentUser) -> Unit
) {
  val repository = remember { HypnotismRepository.getInstance() }
  val allRegistrations by repository.registrations.collectAsState()
  var showAccountSwitcherDialog by remember { mutableStateOf(false) }

  BoxWithConstraints(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .testTag("student_dashboard_screen")
  ) {
    val isDesktop = maxWidth >= 960.dp
    val isTablet = maxWidth in 650.dp..959.dp
    val horizontalPadding = if (isDesktop) 36.dp else if (isTablet) 24.dp else 16.dp

    if (currentUser == null) {
      // NOT LOGGED IN STATE
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontalPadding)
      ) {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = SlateCard),
          border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 520.dp)
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
          ) {
            Icon(Icons.Default.AccountCircle, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.height(14.dp))
            Text("Student Portal Login", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
              "Log in with your registered and approved Gmail account to access private hypnotism classes.",
              color = TextSecondary,
              fontSize = 13.sp,
              lineHeight = 18.sp,
              modifier = Modifier.padding(vertical = 10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            var loginEmailInput by remember { mutableStateOf("") }
            var loginErrorMsg by remember { mutableStateOf<String?>(null) }

            OutlinedTextField(
              value = loginEmailInput,
              onValueChange = {
                loginEmailInput = it
                loginErrorMsg = null
              },
              placeholder = { Text("Enter your registered Gmail address", color = TextMuted) },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FocusCyan,
                unfocusedBorderColor = SlateCardBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
              ),
              modifier = Modifier.fillMaxWidth()
            )

            if (loginErrorMsg != null) {
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = loginErrorMsg ?: "",
                color = ErrorRed,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
              )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = {
                if (loginEmailInput.isBlank() || !loginEmailInput.contains("@")) {
                  loginErrorMsg = "Please enter a valid Gmail address."
                  return@Button
                }
                val student = allRegistrations.firstOrNull {
                  it.gmailAddress.trim().equals(loginEmailInput.trim(), ignoreCase = true) ||
                  it.verifiedGoogleEmail.trim().equals(loginEmailInput.trim(), ignoreCase = true)
                }
                if (student != null) {
                  onSwitchStudent(student)
                } else {
                  loginErrorMsg = "No registration found for '${loginEmailInput.trim()}'. Please submit your registration below."
                }
              },
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
            ) {
              Icon(Icons.Default.LockOpen, contentDescription = null, tint = Color.White)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Sign In with Gmail", color = Color.White, fontWeight = FontWeight.SemiBold)
            }

            if (allRegistrations.isNotEmpty()) {
              Spacer(modifier = Modifier.height(10.dp))
              TextButton(
                onClick = { showAccountSwitcherDialog = true },
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  "Select from registered accounts (${allRegistrations.size})",
                  color = FocusCyanLight,
                  fontSize = 13.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
              onClick = onJoinAnotherCourse,
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
            ) {
              Text("Apply to Join Class", color = FocusCyanLight)
            }
          }
        }
      }
    } else {
      // LOGGED IN STATE: RESPONSIVE DASHBOARD
      val assignedCourses = courses.filter { course ->
        course.authorizedEmails.any {
          it.equals(currentUser.gmailAddress, ignoreCase = true) ||
          it.equals(currentUser.verifiedGoogleEmail, ignoreCase = true)
        }
      }

      LazyColumn(
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        // Warning Banner for Non-Approved Status
        if (currentUser.status != RegistrationStatus.APPROVED) {
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = 8.dp)
            ) {
              Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (currentUser.status == RegistrationStatus.PENDING) WisdomAmber.copy(alpha = 0.15f) else ErrorRed.copy(alpha = 0.15f),
                border = CardDefaults.outlinedCardBorder().copy(
                  brush = SolidColor(if (currentUser.status == RegistrationStatus.PENDING) WisdomAmber else ErrorRed)
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .widthIn(max = 1300.dp)
                  .align(Alignment.Center)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(16.dp)
                ) {
                  Icon(
                    imageVector = if (currentUser.status == RegistrationStatus.PENDING) Icons.Default.HourglassTop else Icons.Default.Block,
                    contentDescription = null,
                    tint = if (currentUser.status == RegistrationStatus.PENDING) WisdomAmber else ErrorRed,
                    modifier = Modifier.size(26.dp)
                  )
                  Spacer(modifier = Modifier.width(14.dp))
                  Column {
                    Text(
                      text = if (currentUser.status == RegistrationStatus.PENDING) "Account Pending Approval" else "Account Suspended",
                      color = TextPrimary,
                      fontSize = 14.sp,
                      fontWeight = FontWeight.Bold
                    )
                    Text(
                      text = if (currentUser.status == RegistrationStatus.PENDING)
                        "The admin has not approved your registration yet. Course lessons remain locked until verified."
                      else
                        "Your access has been revoked or suspended by the course administrator.",
                      color = TextSecondary,
                      fontSize = 12.sp,
                      lineHeight = 17.sp
                    )
                  }
                }
              }
            }
          }
        }

        // DESKTOP MULTI-COLUMN OR MOBILE SINGLE-COLUMN
        if (isDesktop) {
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = 12.dp)
            ) {
              Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .widthIn(max = 1300.dp)
                  .align(Alignment.Center)
              ) {
                // LEFT PANE: Student Profile & Stats (width ~340.dp)
                Column(
                  modifier = Modifier.width(340.dp),
                  verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                  StudentProfileCard(
                    currentUser = currentUser,
                    onSwitchAccount = { showAccountSwitcherDialog = true },
                    onLogout = onLogout
                  )

                  StudentSecurityBadge(currentUser = currentUser)
                }

                // RIGHT PANE: Continue Learning + My Classes Cards
                Column(
                  modifier = Modifier.weight(1f),
                  verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                  // Continue Learning Hero Card (if authorized courses exist)
                  if (assignedCourses.isNotEmpty() && currentUser.status == RegistrationStatus.APPROVED) {
                    val primaryCourse = assignedCourses.first()
                    val completedSet = repository.getCompletedLessonIds(currentUser.id, primaryCourse.id)
                    ContinueLearningHeroCard(
                      course = primaryCourse,
                      completedLessons = completedSet.size,
                      currentLanguage = currentLanguage,
                      onContinue = { onOpenCourse(primaryCourse) }
                    )
                  }

                  // Classes Section Header
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                  ) {
                    Text(
                      text = if (currentLanguage == AppLanguage.ENGLISH) "My Classes (${assignedCourses.size})" else "എന്റെ ക്ലാസ്സുകൾ (${assignedCourses.size})",
                      color = TextPrimary,
                      fontSize = 20.sp,
                      fontWeight = FontWeight.Bold
                    )

                    TextButton(onClick = onJoinAnotherCourse) {
                      Icon(Icons.Default.AddCircle, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(16.dp))
                      Spacer(modifier = Modifier.width(6.dp))
                      Text("Apply for Another Course", color = FocusCyanLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                  }

                  // Course Cards Grid (2-column layout on Desktop)
                  if (assignedCourses.isEmpty()) {
                    NoAssignedClassesCard(currentUser = currentUser)
                  } else {
                    val chunked = assignedCourses.chunked(2)
                    for (rowCourses in chunked) {
                      Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                      ) {
                        for (c in rowCourses) {
                          val completedSet = repository.getCompletedLessonIds(currentUser.id, c.id)
                          val totalLessons = if (c.lessonsCount > 0) c.lessonsCount else 4
                          val fraction = if (totalLessons > 0) completedSet.size.toFloat() / totalLessons.toFloat() else 0f

                          Box(modifier = Modifier.weight(1f)) {
                            StudentCourseCard(
                              course = c,
                              currentLanguage = currentLanguage,
                              completedCount = completedSet.size,
                              totalLessons = totalLessons,
                              progressFraction = fraction,
                              onClick = { onOpenCourse(c) }
                            )
                          }
                        }
                        if (rowCourses.size == 1) {
                          Spacer(modifier = Modifier.weight(1f))
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        } else {
          // MOBILE & TABLET STACKED LAYOUT
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = 8.dp)
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .widthIn(max = 1300.dp)
                  .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(14.dp)
              ) {
                // Profile Header Card
                StudentProfileCard(
                  currentUser = currentUser,
                  onSwitchAccount = { showAccountSwitcherDialog = true },
                  onLogout = onLogout
                )

                // Continue Learning Hero Card (if authorized)
                if (assignedCourses.isNotEmpty() && currentUser.status == RegistrationStatus.APPROVED) {
                  val primaryCourse = assignedCourses.first()
                  val completedSet = repository.getCompletedLessonIds(currentUser.id, primaryCourse.id)
                  ContinueLearningHeroCard(
                    course = primaryCourse,
                    completedLessons = completedSet.size,
                    currentLanguage = currentLanguage,
                    onContinue = { onOpenCourse(primaryCourse) }
                  )
                }

                // Section Title
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = "My Classes",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                  )

                  Text(
                    text = "Apply for New Course",
                    color = FocusCyanLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(onClick = onJoinAnotherCourse)
                  )
                }
              }
            }
          }

          if (assignedCourses.isEmpty()) {
            item {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = horizontalPadding)
              ) {
                NoAssignedClassesCard(currentUser = currentUser)
              }
            }
          } else {
            // Responsive chunking for tablet (2 columns) vs mobile (1 column)
            val columns = if (isTablet) 2 else 1
            val chunked = assignedCourses.chunked(columns)

            items(chunked) { rowCourses ->
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = horizontalPadding, vertical = 4.dp)
              ) {
                Row(
                  horizontalArrangement = Arrangement.spacedBy(12.dp),
                  modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 1300.dp)
                    .align(Alignment.Center)
                ) {
                  for (course in rowCourses) {
                    val completedSet = repository.getCompletedLessonIds(currentUser.id, course.id)
                    val totalLessons = if (course.lessonsCount > 0) course.lessonsCount else 4
                    val fraction = if (totalLessons > 0) completedSet.size.toFloat() / totalLessons.toFloat() else 0f

                    Box(modifier = Modifier.weight(1f)) {
                      StudentCourseCard(
                        course = course,
                        currentLanguage = currentLanguage,
                        completedCount = completedSet.size,
                        totalLessons = totalLessons,
                        progressFraction = fraction,
                        onClick = { onOpenCourse(course) }
                      )
                    }
                  }
                  if (columns > rowCourses.size) {
                    Spacer(modifier = Modifier.weight(1f))
                  }
                }
              }
            }
          }
        }

        // Responsive Footer
        item {
          Spacer(modifier = Modifier.height(24.dp))
          AppFooter(
            currentLanguage = currentLanguage,
            onNavigateIntro = {},
            onNavigateCurriculum = {},
            onNavigateEthics = {},
            onNavigateClasses = {}
          )
        }
      }
    }
  }

  // Account Switcher Dialog
  if (showAccountSwitcherDialog) {
    AlertDialog(
      onDismissRequest = { showAccountSwitcherDialog = false },
      title = {
        Text("Switch Student Account", color = TextPrimary, fontWeight = FontWeight.Bold)
      },
      text = {
        Column(modifier = Modifier.fillMaxWidth()) {
          if (allRegistrations.isEmpty()) {
            Text(
              "No student registrations found yet. Please apply for a course using the registration form.",
              color = TextSecondary,
              fontSize = 13.sp,
              lineHeight = 18.sp,
              modifier = Modifier.padding(vertical = 8.dp)
            )
          } else {
            Text(
              "Select a registered student account:",
              color = TextSecondary,
              fontSize = 12.sp,
              modifier = Modifier.padding(bottom = 12.dp)
            )

            allRegistrations.forEach { student ->
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = if (currentUser?.id == student.id) MindIndigo.copy(alpha = 0.3f) else SlateCard,
              border = CardDefaults.outlinedCardBorder().copy(
                brush = SolidColor(if (currentUser?.id == student.id) FocusCyanLight else SlateCardBorder)
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable {
                  onSwitchStudent(student)
                  showAccountSwitcherDialog = false
                }
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
              ) {
                Icon(Icons.Default.AccountCircle, contentDescription = null, tint = FocusCyanLight)
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(student.fullName, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                  Text(student.gmailAddress, color = TextSecondary, fontSize = 11.sp)
                }
                Text(student.status.name, color = if (student.status == RegistrationStatus.APPROVED) SuccessGreen else WisdomAmber, fontSize = 10.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    },
      confirmButton = {
        TextButton(onClick = { showAccountSwitcherDialog = false }) {
          Text("Cancel", color = FocusCyanLight)
        }
      }
    )
  }
}

@Composable
private fun StudentProfileCard(
  currentUser: StudentUser,
  onSwitchAccount: () -> Unit,
  onLogout: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = SlateCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("student_profile_header")
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(48.dp)
              .clip(CircleShape)
              .background(MindIndigo.copy(alpha = 0.3f))
              .border(1.5.dp, FocusCyanLight, CircleShape)
          ) {
            Text(
              text = currentUser.fullName.take(1).uppercase(),
              color = FocusCyanLight,
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = currentUser.fullName,
              color = TextPrimary,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = currentUser.gmailAddress,
              color = FocusCyanLight,
              fontSize = 12.sp
            )
          }
        }

        // Status Badge
        val (badgeColor, badgeText) = when (currentUser.status) {
          RegistrationStatus.APPROVED -> Pair(SuccessGreen, "APPROVED")
          RegistrationStatus.PENDING -> Pair(WisdomAmber, "PENDING")
          RegistrationStatus.SUSPENDED -> Pair(ErrorRed, "SUSPENDED")
          RegistrationStatus.REJECTED -> Pair(ErrorRed, "REJECTED")
        }

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = badgeColor.copy(alpha = 0.2f),
          border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(badgeColor))
        ) {
          Text(
            text = badgeText,
            color = badgeColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Quick Info Row
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(MidnightSurface)
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        Text("Last Login: ${currentUser.lastLogin}", color = TextSecondary, fontSize = 11.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
          Text(
            text = "Switch",
            color = FocusCyanLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = onSwitchAccount)
          )
          Text(
            text = "Logout",
            color = ErrorRed,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = onLogout)
          )
        }
      }
    }
  }
}

@Composable
private fun StudentSecurityBadge(currentUser: StudentUser) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = MidnightSurface,
    border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Security, contentDescription = null, tint = WisdomAmber, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Active Security Session", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text("Verified Google Identity: ${currentUser.verifiedGoogleEmail}", color = TextSecondary, fontSize = 11.sp)
      Text("Anti-Piracy Dynamic Watermarking: Enabled", color = FocusCyanLight, fontSize = 11.sp)
    }
  }
}

@Composable
private fun ContinueLearningHeroCard(
  course: Course,
  completedLessons: Int,
  currentLanguage: AppLanguage,
  onContinue: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = SlateCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(FocusCyan.copy(alpha = 0.5f))),
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onContinue)
      .testTag("continue_learning_hero_card")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(18.dp)
    ) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(MindIndigo)
      ) {
        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
      }

      Spacer(modifier = Modifier.width(16.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text("CONTINUE LEARNING", color = FocusCyanLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text(
          text = if (currentLanguage == AppLanguage.ENGLISH) course.titleEn else course.titleMl,
          color = TextPrimary,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold
        )
        Text("Completed: $completedLessons of ${course.lessonsCount} lessons", color = TextSecondary, fontSize = 12.sp)
      }

      Button(
        onClick = onContinue,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MindIndigo)
      ) {
        Text("Resume", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
private fun StudentCourseCard(
  course: Course,
  currentLanguage: AppLanguage,
  completedCount: Int,
  totalLessons: Int,
  progressFraction: Float,
  onClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = SlateCard),
    border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("assigned_course_${course.id}")
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = FocusCyan.copy(alpha = 0.2f),
          border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(FocusCyan))
        ) {
          Text(
            text = course.languageType,
            color = FocusCyanLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
          )
        }

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = SuccessGreen.copy(alpha = 0.2f)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Icon(Icons.Default.Verified, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Authorized", color = SuccessGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = if (currentLanguage == AppLanguage.ENGLISH) course.titleEn else course.titleMl,
        color = TextPrimary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      )

      Text(
        text = if (currentLanguage == AppLanguage.ENGLISH) course.subtitleEn else course.subtitleMl,
        color = TextSecondary,
        fontSize = 12.sp,
        modifier = Modifier.padding(vertical = 4.dp)
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Progress Bar
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "Progress: $completedCount of $totalLessons completed",
          color = TextSecondary,
          fontSize = 11.sp
        )
        Text(
          text = "${(progressFraction * 100).toInt()}%",
          color = FocusCyanLight,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      LinearProgressIndicator(
        progress = { progressFraction },
        color = FocusCyanLight,
        trackColor = MidnightSurface,
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
      )
    }
  }
}

@Composable
private fun NoAssignedClassesCard(currentUser: StudentUser) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = SlateCard),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(20.dp)
    ) {
      Icon(Icons.Default.Lock, contentDescription = null, tint = TextMuted, modifier = Modifier.size(36.dp))
      Spacer(modifier = Modifier.height(8.dp))
      Text("No Approved Classes Assigned Yet", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
      Text(
        "You don't have access to this class. The instructor must authorize your Gmail (${currentUser.gmailAddress}) first.",
        color = TextSecondary,
        fontSize = 12.sp,
        modifier = Modifier.padding(vertical = 4.dp)
      )
    }
  }
}
