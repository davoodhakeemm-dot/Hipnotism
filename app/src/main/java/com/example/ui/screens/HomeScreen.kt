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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.Course
import com.example.model.StudentUser
import com.example.ui.components.HypnoticLogo
import com.example.ui.theme.*
import com.example.util.AppStrings

@Composable
fun HomeScreen(
  currentLanguage: AppLanguage,
  currentUser: StudentUser?,
  courses: List<Course>,
  onJoinClassClick: () -> Unit,
  onStudentLoginClick: () -> Unit,
  onOpenCourseIntro: () -> Unit,
  onOpenWhatStudentsLearn: () -> Unit,
  onOpenEthicsSafety: () -> Unit,
  onSelectCourse: (Course) -> Unit,
  onTripleTapLogo: () -> Unit
) {
  LazyColumn(
    contentPadding = PaddingValues(bottom = 40.dp),
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .testTag("home_screen_content")
  ) {
    // HERO SECTION
    item {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(MidnightSurface, DeepObsidian)
            )
          )
          .padding(horizontal = 20.dp, vertical = 24.dp)
      ) {
        // Hypnotic Logo with 3-tap gesture
        HypnoticLogo(
          size = 96.dp,
          animated = true,
          onTripleTap = onTripleTapLogo
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = AppStrings.appTitle(currentLanguage),
          color = TextPrimary,
          fontSize = 26.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 2.sp,
          textAlign = TextAlign.Center
        )

        Text(
          text = if (currentLanguage == AppLanguage.ENGLISH) "ഹിപ്നോട്ടിസം" else "HYPNOTISM",
          color = FocusCyanLight,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = AppStrings.subtitle(currentLanguage),
          color = TextSecondary,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium,
          textAlign = TextAlign.Center,
          lineHeight = 19.sp,
          modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        // Action Buttons: Join Class & Student Login
        Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Button(
            onClick = onJoinClassClick,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("hero_join_class_button")
          ) {
            Icon(Icons.Default.School, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = AppStrings.joinClass(currentLanguage),
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }

          OutlinedButton(
            onClick = onStudentLoginClick,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = FocusCyanLight),
            border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.horizontalGradient(listOf(FocusCyan, MindIndigo))),
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("hero_student_login_button")
          ) {
            Icon(Icons.Default.LockOpen, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (currentUser != null) "My Classes" else AppStrings.login(currentLanguage),
              color = FocusCyanLight,
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp
            )
          }
        }
      }
    }

    // SCIENTIFIC & RESPONSIBLE EDUCATION BANNER
    item {
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = SlateCard,
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(SlateCardBorder, FocusCyan.copy(alpha = 0.4f)))),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 8.dp)
          .testTag("scientific_distinction_card")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(14.dp)
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(FocusCyan.copy(alpha = 0.15f))
          ) {
            Icon(
              imageVector = Icons.Default.Psychology,
              contentDescription = null,
              tint = FocusCyanLight,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = if (currentLanguage == AppLanguage.ENGLISH) "Scientific & Educational Hypnotism" else "ശാസ്ത്രീയവും ധാർമ്മികവുമായ പഠനം",
              color = TextPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = if (currentLanguage == AppLanguage.ENGLISH)
                "Focus on attention, relaxation & suggestion. Not supernatural mind control."
              else
                "ശ്രദ്ധ, വിശ്രാന്തി, നിർദ്ദേശങ്ങൾ എന്നിവയിലൂന്നിയുള്ള ശാസ്ത്രീയ പഠനം. അമാനുഷിക അവകാശവാദങ്ങൾ ഇല്ല.",
              color = TextSecondary,
              fontSize = 11.sp,
              lineHeight = 15.sp
            )
          }
        }
      }
    }

    // DEDICATED HIGHLIGHT CARDS: Intro, What Students Learn, Ethics
    item {
      Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
      ) {
        // 1. Dedicated Course Intro Card
        FeatureQuickCard(
          title = AppStrings.courseIntroTitle(currentLanguage),
          subtitle = if (currentLanguage == AppLanguage.ENGLISH)
            "Explore foundational science & responsive awareness"
          else
            "ശ്രദ്ധ, വിശ്രാന്തി, ബോധതലങ്ങൾ എന്നിവയെക്കുറിച്ചുള്ള അടിസ്ഥാന പാഠം",
          icon = Icons.Default.MenuBook,
          accentColor = MindIndigo,
          tag = "open_intro_card",
          onClick = onOpenCourseIntro
        )

        // 2. What Students Learn Card
        FeatureQuickCard(
          title = AppStrings.whatStudentsLearnTitle(currentLanguage),
          subtitle = if (currentLanguage == AppLanguage.ENGLISH)
            "Full 17-module curriculum overview"
          else
            "17 പ്രധാന പഠന വിഷയങ്ങളുടെ സമഗ്ര വിവരണം",
          icon = Icons.Default.AutoStories,
          accentColor = FocusCyan,
          tag = "open_what_students_learn_card",
          onClick = onOpenWhatStudentsLearn
        )

        // 3. Ethics & Safety Card
        FeatureQuickCard(
          title = AppStrings.ethicsTitle(currentLanguage),
          subtitle = AppStrings.ethicsSub(currentLanguage),
          icon = Icons.Default.VerifiedUser,
          accentColor = WisdomAmber,
          tag = "open_ethics_safety_card",
          onClick = onOpenEthicsSafety
        )
      }
    }

    // COURSE CATEGORIES SECTION (11 Categories from prompt)
    item {
      Spacer(modifier = Modifier.height(14.dp))
      Text(
        text = if (currentLanguage == AppLanguage.ENGLISH) "Course Categories" else "കോഴ്സ് വിഭാഗങ്ങൾ",
        color = TextPrimary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
      )
    }

    val categories = listOf(
      CategoryItem(
        "1",
        "Introduction to Hypnotism",
        "ഹിപ്നോട്ടിസം ആമുഖം",
        "Definition, brainwave states, awareness & choices.",
        "നിർവ്വചനം, മസ്തിഷ്ക തരംഗങ്ങൾ, വ്യക്തിബോധം.",
        Icons.Default.Psychology
      ),
      CategoryItem(
        "2",
        "History of Hypnosis",
        "ഹിപ്നോസിസിന്റെ ചരിത്രം",
        "From Mesmer and Braid to modern cognitive science.",
        "മെസ്മർ, ബ്രെയ്ഡ് മുതൽ ആധുനിക കോഗ്നിറ്റീവ് സയൻസ് വരെ.",
        Icons.Default.HistoryEdu
      ),
      CategoryItem(
        "3",
        "Focus & Concentration",
        "ശ്രദ്ധയും ഏകാഗ്രതയും",
        "Attention fixation, sensory gating & mono-idealism.",
        "ശ്രദ്ധ കേന്ദ്രീകരിക്കൽ, ഇന്ദ്രിയനിയന്ത്രണം.",
        Icons.Default.CenterFocusStrong
      ),
      CategoryItem(
        "4",
        "Relaxation",
        "വിശ്രമാവസ്ഥ (Relaxation)",
        "Progressive muscular easing and autonomic calm.",
        "ശരീര-മനസ്സ് വിശ്രാന്തിയും ശാന്തതയും.",
        Icons.Default.Spa
      ),
      CategoryItem(
        "5",
        "Suggestion & Imagination",
        "നിർദ്ദേശങ്ങളും ഭാവനയും",
        "Verbal frameworks, mental picturing, and ideomotor action.",
        "വാക്കുകളുടെ സ്വാധീനം, ഭാവനാ ചിത്രങ്ങൾ.",
        Icons.Default.Lightbulb
      ),
      CategoryItem(
        "6",
        "Communication",
        "ആശയവിനിമയം (Communication)",
        "Voice tone, pacing, cadence, and ethical rapport.",
        "ശബ്ദത്തിന്റെ വിന്യാസം, താളം, പരസ്പര വിശ്വാസം.",
        Icons.Default.RecordVoiceOver
      ),
      CategoryItem(
        "7",
        "Observation",
        "നിരീക്ഷണം (Observation)",
        "Reading micro-cues, breathing rhythms, and eye states.",
        "ശ്വാസോച്ഛ്വാസം, കണ്ണിന്റെ ചലനങ്ങൾ, സൂക്ഷ്മ ഭാവങ്ങൾ.",
        Icons.Default.Visibility
      ),
      CategoryItem(
        "8",
        "Myths & Facts",
        "തെറ്റിദ്ധാരണകളും യാഥാർത്ഥ്യങ്ങളും",
        "Dispelling mind-control, sleep misconceptions & myths.",
        "മനസ്സ് പിടിച്ചെടുക്കൽ, ഉറക്കം തുടങ്ങിയ തെറ്റായ ധാരണകൾ.",
        Icons.Default.FactCheck
      ),
      CategoryItem(
        "9",
        "Ethics & Safety",
        "ധാർമ്മികതയും സുരക്ഷയും",
        "Informed consent, psychological safety, and boundaries.",
        "പൂർണ്ണ സമ്മതം, മാനസിക സുരക്ഷ, അതിരുകൾ.",
        Icons.Default.Security
      ),
      CategoryItem(
        "10",
        "Scientific Perspectives",
        "ശാസ്ത്രീയ കാഴ്ചപ്പാടുകൾ",
        "fMRI research, neuro-imaging, and suggestibility scales.",
        "എഫ്.എം.ആർ.ഐ ഗവേഷണങ്ങൾ, ന്യൂറോ സയൻസ് പഠനങ്ങൾ.",
        Icons.Default.Science
      ),
      CategoryItem(
        "11",
        "Stage Hypnosis vs Therapeutic Hypnosis",
        "സ്റ്റേജ് vs തെറാപ്പിറ്റിക് ഹിപ്നോസിസ്",
        "Entertainment dynamics distinguished from medical clinical care.",
        "സ്റ്റേജ് പ്രകടനങ്ങളും ക്ലിനിക്കൽ തെറാപ്പിയും തമ്മിലുള്ള വ്യത്യാസം.",
        Icons.Default.CompareArrows
      )
    )

    items(categories) { category ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 5.dp)
          .clickable {
            if (category.id == "1") onOpenCourseIntro()
            else if (category.id == "9") onOpenEthicsSafety()
            else onOpenWhatStudentsLearn()
          }
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
              .background(MidnightSurface)
              .border(1.dp, SlateCardBorder, CircleShape)
          ) {
            Icon(
              imageVector = category.icon,
              contentDescription = null,
              tint = FocusCyanLight,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = if (currentLanguage == AppLanguage.ENGLISH) category.titleEn else category.titleMl,
              color = TextPrimary,
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              text = if (currentLanguage == AppLanguage.ENGLISH) category.descEn else category.descMl,
              color = TextSecondary,
              fontSize = 11.sp,
              lineHeight = 15.sp
            )
          }

          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }

    // PRIVATE COURSES SECTION
    item {
      Spacer(modifier = Modifier.height(20.dp))
      Text(
        text = if (currentLanguage == AppLanguage.ENGLISH) "Private Classes" else "പ്രത്യേക ക്ലാസ്സുകൾ",
        color = TextPrimary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
      )
      Text(
        text = if (currentLanguage == AppLanguage.ENGLISH)
          "Access strictly restricted to authorized Gmail accounts."
        else
          "അംഗീകൃത ജിമെയിൽ അക്കൗണ്ടുകൾക്ക് മാത്രമേ പ്രവേശനം അനുവദിക്കൂ.",
        color = TextSecondary,
        fontSize = 12.sp,
        modifier = Modifier.padding(horizontal = 20.dp)
      )
      Spacer(modifier = Modifier.height(8.dp))
    }

    items(courses) { course ->
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp)
          .clickable { onSelectCourse(course) }
          .testTag("course_card_${course.id}")
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MindIndigo.copy(alpha = 0.2f),
              border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(MindIndigo))
            ) {
              Text(
                text = course.languageType,
                color = FocusCyanLight,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MidnightSurface,
              border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder))
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              ) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = WisdomAmber, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Private Course", color = WisdomAmber, fontSize = 11.sp, fontWeight = FontWeight.Medium)
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

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = "${course.lessonsCount} ${if (currentLanguage == AppLanguage.ENGLISH) "Lessons" else "പാഠങ്ങൾ"}",
              color = TextMuted,
              fontSize = 12.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = if (currentLanguage == AppLanguage.ENGLISH) "Open Course" else "തുറക്കുക",
                color = FocusCyanLight,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
              )
              Spacer(modifier = Modifier.width(4.dp))
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = FocusCyanLight,
                modifier = Modifier.size(14.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun FeatureQuickCard(
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: Color,
  tag: String,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = SlateCard,
    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(accentColor.copy(alpha = 0.5f))),
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag(tag)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(14.dp)
    ) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(accentColor.copy(alpha = 0.15f))
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(24.dp))
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          color = TextPrimary,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = subtitle,
          color = TextSecondary,
          fontSize = 11.sp,
          lineHeight = 15.sp
        )
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null,
        tint = accentColor,
        modifier = Modifier.size(16.dp)
      )
    }
  }
}

data class CategoryItem(
  val id: String,
  val titleEn: String,
  val titleMl: String,
  val descEn: String,
  val descMl: String,
  val icon: ImageVector
)
