package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HypnotismRepository
import com.example.model.AppLanguage
import com.example.model.Course
import com.example.model.Lesson
import com.example.model.StudentUser
import com.example.ui.components.WatermarkOverlay
import com.example.ui.theme.*

@Composable
fun LessonPlayerScreen(
  course: Course,
  currentUser: StudentUser?,
  currentLanguage: AppLanguage,
  onBackToDashboard: () -> Unit
) {
  val repository = remember { HypnotismRepository.getInstance() }
  val allLessons = repository.lessons.collectAsState().value[course.id] ?: emptyList()

  // 13. STUDENT ACCESS CHECK
  val (hasAccess, accessReason) = remember(course, currentUser) {
    repository.checkCourseAccess(course.id, currentUser)
  }

  if (!hasAccess || currentUser == null) {
    // UNAUTHORIZED USER STATE
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .fillMaxSize()
        .background(DeepObsidian)
        .padding(24.dp)
        .testTag("unauthorized_access_view")
    ) {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(ErrorRed)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.padding(24.dp)
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(60.dp)
              .clip(CircleShape)
              .background(ErrorRed.copy(alpha = 0.15f))
              .border(1.5.dp, ErrorRed, CircleShape)
          ) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(32.dp))
          }

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "You don't have access to this class.",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = accessReason,
            color = TextSecondary,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(20.dp))

          Button(
            onClick = onBackToDashboard,
            colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
            modifier = Modifier.testTag("unauthorized_back_button")
          ) {
            Text("Back to Dashboard", color = Color.White)
          }
        }
      }
    }
    return
  }

  // AUTHORIZED ACCESS: Full Lesson Player
  var currentLessonIndex by remember { mutableIntStateOf(0) }
  val currentLesson = allLessons.getOrNull(currentLessonIndex) ?: allLessons.firstOrNull()

  var isVideoPlaying by remember { mutableStateOf(false) }
  var videoProgress by remember { mutableFloatStateOf(0.25f) }
  var lessonLanguageTab by remember(currentLanguage) { mutableStateOf(currentLanguage) }

  val completedSet = repository.getCompletedLessonIds(currentUser.id, course.id)
  val isCurrentCompleted = currentLesson != null && completedSet.contains(currentLesson.id)

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .verticalScroll(rememberScrollState())
      .padding(bottom = 30.dp)
      .testTag("authorized_lesson_player")
  ) {
    if (currentLesson == null) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp)
      ) {
        Text("No lessons currently published for this course.", color = TextSecondary)
      }
      return
    }

    // VIDEO PLAYER CONTAINER WITH STUDENT WATERMARK
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(240.dp)
        .background(Color.Black)
        .testTag("video_player_container")
    ) {
      // Background gradient simulation for private encrypted video stream
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            Brush.radialGradient(
              colors = listOf(MidnightSurface, Color.Black)
            )
          )
      )

      // Play / Pause center control
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
      ) {
        IconButton(
          onClick = { isVideoPlaying = !isVideoPlaying },
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(MindIndigo.copy(alpha = 0.85f))
            .testTag("video_play_pause_button")
        ) {
          Icon(
            imageVector = if (isVideoPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = if (isVideoPlaying) "Pause" else "Play",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
          )
        }
      }

      // CRITICAL: 16. STUDENT-SPECIFIC WATERMARK OVERLAY
      WatermarkOverlay(
        studentName = currentUser.fullName,
        studentEmail = currentUser.gmailAddress
      )

      // Top video overlay badges
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp)
      ) {
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = Color.Black.copy(alpha = 0.7f)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(if (isVideoPlaying) SuccessGreen else WisdomAmber)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (isVideoPlaying) "Streaming (Private Encrypted)" else "Private Video",
              color = Color.White,
              fontSize = 10.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(6.dp),
          color = Color.Black.copy(alpha = 0.7f)
        ) {
          Text(
            text = "Duration: ${currentLesson.videoDuration}",
            color = FocusCyanLight,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }

      // Bottom video controls bar
      Column(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .fillMaxWidth()
          .background(Color.Black.copy(alpha = 0.6f))
          .padding(horizontal = 12.dp, vertical = 6.dp)
      ) {
        Slider(
          value = videoProgress,
          onValueChange = { videoProgress = it },
          colors = SliderDefaults.colors(
            thumbColor = FocusCyanLight,
            activeTrackColor = FocusCyan,
            inactiveTrackColor = Color.DarkGray
          ),
          modifier = Modifier.height(20.dp)
        )

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("04:12 / ${currentLesson.videoDuration}", color = TextSecondary, fontSize = 11.sp)
          Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("1.0x", color = FocusCyanLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.Fullscreen, contentDescription = "Fullscreen", tint = Color.White, modifier = Modifier.size(16.dp))
          }
        }
      }
    }

    // LESSON CONTENT & CONTROLS
    Column(modifier = Modifier.padding(20.dp)) {
      // Lesson index indicator & completion check
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "Lesson ${currentLesson.lessonNumber} of ${allLessons.size}",
          color = FocusCyanLight,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )

        Button(
          onClick = {
            repository.markLessonCompleted(currentUser.id, course.id, currentLesson.id)
          },
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isCurrentCompleted) SuccessGreen.copy(alpha = 0.2f) else SlateCard
          ),
          border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(if (isCurrentCompleted) SuccessGreen else SlateCardBorder)
          ),
          contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
          modifier = Modifier.testTag("mark_completed_button")
        ) {
          Icon(
            imageVector = if (isCurrentCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isCurrentCompleted) SuccessGreen else TextSecondary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isCurrentCompleted) "Completed" else "Mark Complete",
            color = if (isCurrentCompleted) SuccessGreen else TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Lesson Title
      Text(
        text = if (lessonLanguageTab == AppLanguage.ENGLISH) currentLesson.titleEn else currentLesson.titleMl,
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Language Switcher Tab for Lesson Text
      TabRow(
        selectedTabIndex = if (lessonLanguageTab == AppLanguage.ENGLISH) 0 else 1,
        containerColor = SlateCard,
        contentColor = FocusCyanLight,
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .border(1.dp, SlateCardBorder, RoundedCornerShape(10.dp))
      ) {
        Tab(
          selected = lessonLanguageTab == AppLanguage.ENGLISH,
          onClick = { lessonLanguageTab = AppLanguage.ENGLISH },
          text = { Text("English Content", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
        )
        Tab(
          selected = lessonLanguageTab == AppLanguage.MALAYALAM,
          onClick = { lessonLanguageTab = AppLanguage.MALAYALAM },
          text = { Text("മലയാളം പാഠം", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Protected Lesson Body Card
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = if (lessonLanguageTab == AppLanguage.ENGLISH) currentLesson.contentEn else currentLesson.contentMl,
            color = TextPrimary,
            fontSize = 14.sp,
            lineHeight = 22.sp
          )

          if (currentLesson.notesEn.isNotBlank() || currentLesson.notesMl.isNotBlank()) {
            Spacer(modifier = Modifier.height(14.dp))
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = MidnightSurface,
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text("Lesson Notes & Key Takeaways:", color = WisdomAmber, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = if (lessonLanguageTab == AppLanguage.ENGLISH) currentLesson.notesEn else currentLesson.notesMl,
                  color = TextSecondary,
                  fontSize = 12.sp,
                  lineHeight = 17.sp
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // ATTACHMENTS & DOWNLOAD PROTECTION NOTICE
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = MidnightSurface,
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = currentLesson.pdfAttachmentName ?: "Lesson_Notes_Reference.pdf",
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
              )
            }

            Surface(
              shape = RoundedCornerShape(6.dp),
              color = SlateCard
            ) {
              Text("Protected In-App View", color = TextSecondary, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Security Notice: Direct file downloads are disabled to protect course integrity. Content is watermarked for ${currentUser.gmailAddress}.",
            color = TextMuted,
            fontSize = 10.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // PREVIOUS / NEXT LESSON BUTTONS
      Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        OutlinedButton(
          onClick = {
            if (currentLessonIndex > 0) currentLessonIndex--
          },
          enabled = currentLessonIndex > 0,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("prev_lesson_button")
        ) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Previous Lesson", fontSize = 12.sp)
        }

        Button(
          onClick = {
            if (currentLessonIndex < allLessons.size - 1) currentLessonIndex++
          },
          enabled = currentLessonIndex < allLessons.size - 1,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
          modifier = Modifier
            .weight(1f)
            .testTag("next_lesson_button")
        ) {
          Text("Next Lesson", fontSize = 12.sp, color = Color.White)
          Spacer(modifier = Modifier.width(6.dp))
          Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
        }
      }
    }
  }
}
