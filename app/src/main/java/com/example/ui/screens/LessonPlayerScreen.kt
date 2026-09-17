package com.example.ui.screens

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
import androidx.compose.ui.graphics.SolidColor
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
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(ErrorRed)),
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 500.dp)
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

  // AUTHORIZED ACCESS: Full Responsive Lesson Player
  var currentLessonIndex by remember { mutableIntStateOf(0) }
  val currentLesson = allLessons.getOrNull(currentLessonIndex) ?: allLessons.firstOrNull()

  var isVideoPlaying by remember { mutableStateOf(false) }
  var videoProgress by remember { mutableFloatStateOf(0.25f) }
  var lessonLanguageTab by remember(currentLanguage) { mutableStateOf(currentLanguage) }

  val completedSet = repository.getCompletedLessonIds(currentUser.id, course.id)
  val isCurrentCompleted = currentLesson != null && completedSet.contains(currentLesson.id)

  BoxWithConstraints(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .testTag("authorized_lesson_player")
  ) {
    val isDesktop = maxWidth >= 960.dp
    val contentPadding = if (isDesktop) 28.dp else 16.dp

    if (currentLesson == null) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
      ) {
        Text("No lessons currently published for this course.", color = TextSecondary)
      }
      return@BoxWithConstraints
    }

    if (isDesktop) {
      // DESKTOP 2-COLUMN LAYOUT (Left: Video + Content, Right: Playlist & Controls)
      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
      ) {
        // LEFT COLUMN (60% width)
        Column(
          modifier = Modifier
            .weight(1.35f)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
        ) {
          // Responsive 16:9 Video Player
          VideoPlayerComponent(
            currentLesson = currentLesson,
            isVideoPlaying = isVideoPlaying,
            videoProgress = videoProgress,
            studentName = currentUser.fullName,
            studentEmail = currentUser.gmailAddress,
            onTogglePlay = { isVideoPlaying = !isVideoPlaying },
            onProgressChange = { videoProgress = it }
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Lesson Title & Complete Toggle
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Lesson ${currentLesson.lessonNumber} of ${allLessons.size}",
                color = FocusCyanLight,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = if (lessonLanguageTab == AppLanguage.ENGLISH) currentLesson.titleEn else currentLesson.titleMl,
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Button(
              onClick = {
                repository.markLessonCompleted(currentUser.id, course.id, currentLesson.id)
              },
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isCurrentCompleted) SuccessGreen.copy(alpha = 0.2f) else SlateCard
              ),
              border = CardDefaults.outlinedCardBorder().copy(
                brush = SolidColor(if (isCurrentCompleted) SuccessGreen else SlateCardBorder)
              ),
              contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
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
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Bilingual Text Switcher Tab
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
              text = { Text("മലയാളം ഉള്ളടക്കം", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Lesson Content Card
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SlateCard),
            border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Text(
                text = if (lessonLanguageTab == AppLanguage.ENGLISH) currentLesson.contentEn else currentLesson.contentMl,
                color = TextPrimary,
                fontSize = 14.sp,
                lineHeight = 22.sp
              )
            }
          }
        }

        // RIGHT COLUMN (40% width): Playlist, Notes, Prev/Next
        Column(
          modifier = Modifier
            .weight(0.9f)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          // Course Progress Card
          val progressFraction = if (allLessons.isNotEmpty()) completedSet.size.toFloat() / allLessons.size.toFloat() else 0f
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = SlateCard,
            border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text("Course Progress", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
              Spacer(modifier = Modifier.height(6.dp))
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
              ) {
                Text("${completedSet.size} of ${allLessons.size} completed", color = TextSecondary, fontSize = 12.sp)
                Text("${(progressFraction * 100).toInt()}%", color = FocusCyanLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
              Spacer(modifier = Modifier.height(6.dp))
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

          // Lesson Navigation Buttons
          Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            OutlinedButton(
              onClick = { if (currentLessonIndex > 0) currentLessonIndex-- },
              enabled = currentLessonIndex > 0,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("prev_lesson_button")
            ) {
              Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Previous", fontSize = 12.sp)
            }

            Button(
              onClick = { if (currentLessonIndex < allLessons.size - 1) currentLessonIndex++ },
              enabled = currentLessonIndex < allLessons.size - 1,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
              modifier = Modifier
                .weight(1f)
                .testTag("next_lesson_button")
            ) {
              Text("Next", fontSize = 12.sp, color = Color.White)
              Spacer(modifier = Modifier.width(4.dp))
              Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
            }
          }

          // Course Playlist
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = SlateCard,
            border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text("Course Curriculum (${allLessons.size} Lessons)", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
              Spacer(modifier = Modifier.height(10.dp))

              allLessons.forEachIndexed { index, lesson ->
                val isSelected = index == currentLessonIndex
                val isCompleted = completedSet.contains(lesson.id)

                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) MindIndigo.copy(alpha = 0.25f) else Color.Transparent)
                    .clickable { currentLessonIndex = index }
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                  Icon(
                    imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.PlayCircleOutline,
                    contentDescription = null,
                    tint = if (isCompleted) SuccessGreen else if (isSelected) FocusCyanLight else TextMuted,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = lesson.titleEn,
                      color = if (isSelected) FocusCyanLight else TextPrimary,
                      fontSize = 12.sp,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                    Text(lesson.videoDuration, color = TextMuted, fontSize = 10.sp)
                  }
                }
              }
            }
          }

          // Notes & Resources Card
          if (currentLesson.notesEn.isNotBlank() || currentLesson.notesMl.isNotBlank()) {
            Surface(
              shape = RoundedCornerShape(14.dp),
              color = MidnightSurface,
              border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text("Lesson Notes & Key Concepts:", color = WisdomAmber, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = if (lessonLanguageTab == AppLanguage.ENGLISH) currentLesson.notesEn else currentLesson.notesMl,
                  color = TextSecondary,
                  fontSize = 12.sp,
                  lineHeight = 17.sp
                )
              }
            }
          }

          // Protected PDF Resource
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MidnightSurface,
            border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = currentLesson.pdfAttachmentName ?: "Lesson_Notes_Reference.pdf",
                  color = TextPrimary,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Protected In-App View. Watermarked for ${currentUser.gmailAddress}.",
                color = TextMuted,
                fontSize = 10.sp
              )
            }
          }
        }
      }
    } else {
      // MOBILE & TABLET STACKED LAYOUT (Preserving mobile responsive single-column layout)
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(bottom = 30.dp)
      ) {
        // Responsive 16:9 Video Player (Takes full available width without hardcoded height)
        VideoPlayerComponent(
          currentLesson = currentLesson,
          isVideoPlaying = isVideoPlaying,
          videoProgress = videoProgress,
          studentName = currentUser.fullName,
          studentEmail = currentUser.gmailAddress,
          onTogglePlay = { isVideoPlaying = !isVideoPlaying },
          onProgressChange = { videoProgress = it }
        )

        // LESSON CONTENT & CONTROLS
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
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
                brush = SolidColor(if (isCurrentCompleted) SuccessGreen else SlateCardBorder)
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
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Language Switcher Tab
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

          Spacer(modifier = Modifier.height(14.dp))

          // Protected Lesson Body Card
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SlateCard),
            border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
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

          // Attachments Notice
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MidnightSurface,
            border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
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

          Spacer(modifier = Modifier.height(18.dp))

          // Previous / Next Lesson Buttons
          Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            OutlinedButton(
              onClick = { if (currentLessonIndex > 0) currentLessonIndex-- },
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
              onClick = { if (currentLessonIndex < allLessons.size - 1) currentLessonIndex++ },
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
  }
}

@Composable
private fun VideoPlayerComponent(
  currentLesson: Lesson,
  isVideoPlaying: Boolean,
  videoProgress: Float,
  studentName: String,
  studentEmail: String,
  onTogglePlay: () -> Unit,
  onProgressChange: (Float) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(16f / 9f)
      .clip(RoundedCornerShape(12.dp))
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
        onClick = onTogglePlay,
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
      studentName = studentName,
      studentEmail = studentEmail
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
        .background(Color.Black.copy(alpha = 0.65f))
        .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
      Slider(
        value = videoProgress,
        onValueChange = onProgressChange,
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
}
