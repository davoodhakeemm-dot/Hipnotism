package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HypnotismRepository
import com.example.model.AppLanguage
import com.example.model.Course
import com.example.model.RegistrationStatus
import com.example.model.StudentUser
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

  LazyColumn(
    contentPadding = PaddingValues(20.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .testTag("student_dashboard_screen")
  ) {
    if (currentUser == null) {
      // Not logged in state
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = SlateCard),
          border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
          ) {
            Icon(Icons.Default.AccountCircle, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(56.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text("Student Portal Login", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
              "Log in with your registered and approved Gmail account to access private hypnotism classes.",
              color = TextSecondary,
              fontSize = 12.sp,
              modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = { showAccountSwitcherDialog = true },
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text("Select Student Account to Login", color = Color.White)
            }
          }
        }
      }
    } else {
      // Authenticated Student Header
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = SlateCard),
          border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
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
                    .size(46.dp)
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
                    text = "Welcome, ${currentUser.fullName}",
                    color = TextPrimary,
                    fontSize = 17.sp,
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
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(badgeColor))
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
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MidnightSurface)
                .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
              Text("Last Login: ${currentUser.lastLogin}", color = TextSecondary, fontSize = 11.sp)
              Text(
                text = "Switch Account",
                color = FocusCyanLight,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { showAccountSwitcherDialog = true }
              )
            }
          }
        }
      }

      // If account is PENDING or SUSPENDED, show prominent warning banner
      if (currentUser.status != RegistrationStatus.APPROVED) {
        item {
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = if (currentUser.status == RegistrationStatus.PENDING) WisdomAmber.copy(alpha = 0.15f) else ErrorRed.copy(alpha = 0.15f),
            border = CardDefaults.outlinedCardBorder().copy(
              brush = androidx.compose.ui.graphics.SolidColor(if (currentUser.status == RegistrationStatus.PENDING) WisdomAmber else ErrorRed)
            ),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(14.dp)
            ) {
              Icon(
                imageVector = if (currentUser.status == RegistrationStatus.PENDING) Icons.Default.HourglassTop else Icons.Default.Block,
                contentDescription = null,
                tint = if (currentUser.status == RegistrationStatus.PENDING) WisdomAmber else ErrorRed,
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = if (currentUser.status == RegistrationStatus.PENDING) "Account Pending Approval" else "Account Suspended",
                  color = TextPrimary,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = if (currentUser.status == RegistrationStatus.PENDING)
                    "The admin has not approved your registration yet. Course lessons remain locked until verified."
                  else
                    "Your access has been revoked or suspended by the course administrator.",
                  color = TextSecondary,
                  fontSize = 11.sp
                )
              }
            }
          }
        }
      }

      // MY CLASSES SECTION
      item {
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

      // Filter courses assigned to this student's Gmail
      val assignedCourses = courses.filter { course ->
        course.authorizedEmails.any {
          it.equals(currentUser.gmailAddress, ignoreCase = true) ||
          it.equals(currentUser.verifiedGoogleEmail, ignoreCase = true)
        }
      }

      if (assignedCourses.isEmpty()) {
        item {
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
      } else {
        items(assignedCourses) { course ->
          val completedSet = repository.getCompletedLessonIds(currentUser.id, course.id)
          val totalLessons = if (course.lessonsCount > 0) course.lessonsCount else 4
          val progressFraction = if (totalLessons > 0) completedSet.size.toFloat() / totalLessons.toFloat() else 0f

          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SlateCard),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOpenCourse(course) }
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
                  border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FocusCyan))
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
                  text = "Progress: ${completedSet.size} of $totalLessons completed",
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

              Spacer(modifier = Modifier.height(14.dp))

              Button(
                onClick = { onOpenCourse(course) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = if (completedSet.isEmpty()) "Start Learning" else "Continue Learning",
                  color = Color.White,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
              }
            }
          }
        }
      }
    }
  }

  // Account Switcher Dialog for manual/test inspection
  if (showAccountSwitcherDialog) {
    AlertDialog(
      onDismissRequest = { showAccountSwitcherDialog = false },
      title = { Text("Select Student User Session", color = TextPrimary) },
      text = {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          items(allRegistrations) { student ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (student.id == currentUser?.id) MindIndigo.copy(alpha = 0.3f) else SlateCard,
              border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  onSwitchStudent(student)
                  showAccountSwitcherDialog = false
                }
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(student.fullName, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                  Text("${student.gmailAddress} • Status: ${student.status}", color = TextSecondary, fontSize = 11.sp)
                }
                if (student.id == currentUser?.id) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = FocusCyanLight)
                }
              }
            }
          }
        }
      },
      confirmButton = {
        TextButton(onClick = { showAccountSwitcherDialog = false }) {
          Text("Close", color = FocusCyanLight)
        }
      }
    )
  }
}
