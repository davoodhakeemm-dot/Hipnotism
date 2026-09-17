package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.components.AppFooter
import com.example.ui.theme.*
import com.example.util.AppStrings

@Composable
fun CourseIntroScreen(
  currentLanguage: AppLanguage
) {
  var activeTabLanguage by remember(currentLanguage) { mutableStateOf(currentLanguage) }

  BoxWithConstraints(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .testTag("course_intro_screen")
  ) {
    val isDesktop = maxWidth >= 960.dp
    val hPadding = if (isDesktop) 40.dp else 16.dp

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(top = 20.dp, bottom = 24.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 1100.dp)
          .padding(horizontal = hPadding)
      ) {
        // Header Banner
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(MindIndigo.copy(alpha = 0.2f))
          .border(1.dp, MindIndigo, CircleShape)
      ) {
        Icon(Icons.Default.Psychology, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(26.dp))
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column {
        Text(
          text = AppStrings.courseIntroTitle(activeTabLanguage),
          color = TextPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = if (activeTabLanguage == AppLanguage.ENGLISH) "Foundational Scientific Lesson" else "അടിസ്ഥാന ശാസ്ത്രീയ പാഠം",
          color = FocusCyanLight,
          fontSize = 12.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Language Toggle for reading this lesson
    TabRow(
      selectedTabIndex = if (activeTabLanguage == AppLanguage.ENGLISH) 0 else 1,
      containerColor = MidnightSurface,
      contentColor = FocusCyanLight,
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .border(1.dp, SlateCardBorder, RoundedCornerShape(12.dp))
    ) {
      Tab(
        selected = activeTabLanguage == AppLanguage.ENGLISH,
        onClick = { activeTabLanguage = AppLanguage.ENGLISH },
        text = { Text("English Content", fontWeight = FontWeight.SemiBold) }
      )
      Tab(
        selected = activeTabLanguage == AppLanguage.MALAYALAM,
        onClick = { activeTabLanguage = AppLanguage.MALAYALAM },
        text = { Text("മലയാളം വിവരണം", fontWeight = FontWeight.SemiBold) }
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Lesson Content Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SlateCard),
      border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        if (activeTabLanguage == AppLanguage.ENGLISH) {
          Text(
            text = "Hypnotism is a technique involving focused attention, relaxation, and increased responsiveness to suggestions.",
            color = TextPrimary,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Medium
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Hypnosis is commonly described as a state involving focused attention and heightened suggestibility. It is not the same as ordinary sleep, and a person generally retains awareness and the ability to respond according to their own choices.",
            color = TextSecondary,
            fontSize = 14.sp,
            lineHeight = 21.sp
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Hypnotism has been studied in psychological and scientific contexts and has also been used in performance and certain complementary or clinical settings by appropriately trained professionals.",
            color = TextSecondary,
            fontSize = 14.sp,
            lineHeight = 21.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MidnightSurface,
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FocusCyan.copy(alpha = 0.3f))),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(14.dp)
            ) {
              Icon(Icons.Default.Security, contentDescription = null, tint = WisdomAmber, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "The course should teach students to understand hypnotism responsibly rather than presenting it as supernatural mind control.",
                color = WisdomAmber,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 18.sp
              )
            }
          }
        } else {
          // Malayalam
          Text(
            text = "ഹിപ്നോട്ടിസം (Hypnotism) എന്നത് ശ്രദ്ധയെ ഒരു പ്രത്യേക കാര്യത്തിൽ കേന്ദ്രീകരിക്കൽ, വിശ്രമാവസ്ഥ, നിർദ്ദേശങ്ങളോട് കൂടുതൽ ശ്രദ്ധ പുലർത്തുന്ന അവസ്ഥ എന്നിവയുമായി ബന്ധപ്പെട്ട ഒരു സാങ്കേതികവിദ്യയാണ്.",
            color = TextPrimary,
            fontSize = 15.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Medium
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "ഹിപ്നോസിസ് സാധാരണ ഉറക്കത്തിന് തുല്യമല്ല. ഹിപ്നോട്ടിക് അവസ്ഥയിലുള്ള വ്യക്തിക്ക് ചുറ്റുപാടുകളെക്കുറിച്ച് ഒരു പരിധിവരെ ബോധവാനായിരിക്കാം. ഒരാളുടെ മനസ്സിന്റെ പൂർണ്ണ നിയന്ത്രണം മറ്റൊരാൾക്ക് ലഭിക്കുന്നു എന്നത് ഹിപ്നോസിസിന്റെ ശാസ്ത്രീയമായ വിവരണം അല്ല.",
            color = TextSecondary,
            fontSize = 14.sp,
            lineHeight = 23.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MidnightSurface,
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FocusCyan.copy(alpha = 0.3f))),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(14.dp)
            ) {
              Icon(Icons.Default.Security, contentDescription = null, tint = WisdomAmber, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "ഈ കോഴ്സ് ഹിപ്നോട്ടിസത്തെ ശാസ്ത്രീയവും ഉത്തരവാദിത്തപരവുമായ രീതിയിൽ മനസ്സിലാക്കാൻ സഹായിക്കുന്നതിനാണ്.",
                color = WisdomAmber,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Three distinctions card
    Text(
      text = if (activeTabLanguage == AppLanguage.ENGLISH) "Three Distinct Domains" else "മൂന്ന് വ്യത്യസ്ത മേഖലകൾ",
      color = TextPrimary,
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    DomainDistinctionCard(
      title = if (activeTabLanguage == AppLanguage.ENGLISH) "1. Educational Hypnotism (This Course)" else "1. വിദ്യാഭ്യാസപരമായ ഹിപ്നോട്ടിസം (ഈ കോഴ്സ്)",
      desc = if (activeTabLanguage == AppLanguage.ENGLISH)
        "Learning concentration, focus mechanisms, relaxation reflexes, and communication science."
      else
        "ശ്രദ്ധ, ഏകാഗ്രത, ശരീര-മനസ്സ് വിശ്രാന്തി, മാന്യമായ ആശയവിനിമയം എന്നിവ പഠിക്കുക.",
      accent = FocusCyan
    )

    Spacer(modifier = Modifier.height(8.dp))

    DomainDistinctionCard(
      title = if (activeTabLanguage == AppLanguage.ENGLISH) "2. Clinical / Medical Hypnotherapy" else "2. ക്ലിനിക്കൽ / മെഡിക്കൽ ഹിപ്നോതെറാപ്പി",
      desc = if (activeTabLanguage == AppLanguage.ENGLISH)
        "Therapeutic interventions conducted exclusively by licensed medical and psychological healthcare professionals."
      else
        "യോഗ്യരായ ഡോക്ടർമാരും മനശാസ്ത്ര വിദഗ്ദ്ധരും മാത്രം രോഗചികിത്സയ്ക്കായി ഉപയോഗിക്കുന്നത്.",
      accent = MindIndigo
    )

    Spacer(modifier = Modifier.height(8.dp))

    DomainDistinctionCard(
      title = if (activeTabLanguage == AppLanguage.ENGLISH) "3. Stage / Performance Hypnosis" else "3. സ്റ്റേജ് / പെർഫോമൻസ് ഹിപ്നോസിസ്",
      desc = if (activeTabLanguage == AppLanguage.ENGLISH)
        "Theatrical entertainment and showmanship, requiring willing stage volunteers, separate from scientific training."
      else
        "വിനോദത്തിനും സ്റ്റേജ് ഷോകൾക്കുമായി നടത്തുന്ന പ്രകടനങ്ങൾ; ഇത് പഠനത്തിൽ നിന്നും ചികിത്സയിൽ നിന്നും വേർതിരിഞ്ഞു നിൽക്കുന്നു.",
      accent = WisdomAmber
    )
  }

  Spacer(modifier = Modifier.height(32.dp))

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

@Composable
private fun DomainDistinctionCard(
  title: String,
  desc: String,
  accent: Color
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = SlateCard,
    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(accent.copy(alpha = 0.4f))),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(text = title, color = accent, fontSize = 13.sp, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(4.dp))
      Text(text = desc, color = TextSecondary, fontSize = 12.sp, lineHeight = 16.sp)
    }
  }
}
