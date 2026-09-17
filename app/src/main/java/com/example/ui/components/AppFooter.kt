package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.theme.*
import com.example.util.AppStrings

@Composable
fun AppFooter(
  currentLanguage: AppLanguage,
  onNavigateIntro: () -> Unit = {},
  onNavigateCurriculum: () -> Unit = {},
  onNavigateEthics: () -> Unit = {},
  onNavigateClasses: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  BoxWithConstraints(
    modifier = modifier
      .fillMaxWidth()
      .background(MidnightSurface)
      .border(
        width = 1.dp,
        color = SlateCardBorder,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
      )
      .padding(horizontal = 24.dp, vertical = 32.dp)
      .testTag("app_footer")
  ) {
    val isDesktop = maxWidth >= 900.dp
    val isTablet = maxWidth in 600.dp..899.dp

    if (isDesktop) {
      // 4-Column Desktop Footer
      Row(
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        // Column 1: Brand & Philosophy
        Column(modifier = Modifier.weight(1.3f)) {
          BrandHeader(currentLanguage)
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = if (currentLanguage == AppLanguage.ENGLISH)
              "Private educational platform dedicated to the scientific, ethical, and responsible understanding of focused attention, relaxation, and subconscious communication."
            else
              "ശ്രദ്ധ, വിശ്രാന്തി, ഉപബോധമനസ്സ് എന്നിവയെ ശാസ്ത്രീയവും ഉത്തരവാദിത്തപരവുമായി പഠിപ്പിക്കുന്ന സ്വകാര്യ വിദ്യാഭ്യാസ വേദി.",
            color = TextSecondary,
            fontSize = 12.sp,
            lineHeight = 18.sp
          )
          Spacer(modifier = Modifier.height(14.dp))
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Default.Security, contentDescription = null, tint = WisdomAmber, modifier = Modifier.size(14.dp))
            Text(
              text = if (currentLanguage == AppLanguage.ENGLISH) "Private Authorized Access Only" else "അംഗീകൃത വിദ്യാർത്ഥികൾക്ക് മാത്രം",
              color = WisdomAmber,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }

        // Column 2: Curriculum Highlights
        Column(modifier = Modifier.weight(1f)) {
          FooterHeading(if (currentLanguage == AppLanguage.ENGLISH) "Curriculum" else "പഠന വിഷയങ്ങൾ")
          Spacer(modifier = Modifier.height(10.dp))
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Introduction to Hypnotism" else "ഹിപ്നോട്ടിസം ആമുഖം", onNavigateIntro)
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "17 Comprehensive Modules" else "17 സമഗ്ര പാഠ്യവിഷയങ്ങൾ", onNavigateCurriculum)
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Focus & Concentration" else "ശ്രദ്ധയും ഏകാഗ്രതയും", onNavigateCurriculum)
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Relaxation & Suggestion" else "വിശ്രാന്തിയും നിർദ്ദേശങ്ങളും", onNavigateCurriculum)
        }

        // Column 3: Ethics & Safety
        Column(modifier = Modifier.weight(1f)) {
          FooterHeading(if (currentLanguage == AppLanguage.ENGLISH) "Ethics & Guidelines" else "ധാർമ്മികത & സുരക്ഷ")
          Spacer(modifier = Modifier.height(10.dp))
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Learn Responsibly" else "ഉത്തരവാദിത്ത പഠനം", onNavigateEthics)
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Informed Consent" else "പൂർണ്ണ സമ്മതം", onNavigateEthics)
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Myths vs Scientific Facts" else "ശാസ്ത്രീയ യാഥാർത്ഥ്യങ്ങൾ", onNavigateIntro)
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Psychological Well-being" else "മാനസിക സുരക്ഷ", onNavigateEthics)
        }

        // Column 4: Student & Portal Access
        Column(modifier = Modifier.weight(1f)) {
          FooterHeading(if (currentLanguage == AppLanguage.ENGLISH) "Portal & Access" else "പോർട്ടൽ പ്രവേശനം")
          Spacer(modifier = Modifier.height(10.dp))
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "My Registered Classes" else "എന്റെ ക്ലാസ്സുകൾ", onNavigateClasses)
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Student Verification" else "വിദ്യാർത്ഥി സ്ഥിരീകരണം", onNavigateClasses)
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Encrypted Streaming" else "സുരക്ഷിത വീഡിയോ സ്ട്രീമിംഗ്", onNavigateClasses)
          FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Instructor Admin Portal" else "അഡ്മിൻ പ്രവേശനം", {})
        }
      }
    } else if (isTablet) {
      // 2-Column Tablet Footer
      Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(24.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.weight(1f)) {
            BrandHeader(currentLanguage)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = AppStrings.subtitle(currentLanguage),
              color = TextSecondary,
              fontSize = 12.sp,
              lineHeight = 17.sp
            )
          }
          Column(modifier = Modifier.weight(1f)) {
            FooterHeading(if (currentLanguage == AppLanguage.ENGLISH) "Curriculum" else "പഠന വിഷയങ്ങൾ")
            Spacer(modifier = Modifier.height(8.dp))
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Introduction to Hypnotism" else "ഹിപ്നോട്ടിസം ആമുഖം", onNavigateIntro)
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Curriculum Overview" else "സിലബസ് വിവരണം", onNavigateCurriculum)
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Ethics & Safety" else "ധാർമ്മികത & സുരക്ഷ", onNavigateEthics)
          }
        }
        Row(
          horizontalArrangement = Arrangement.spacedBy(24.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.weight(1f)) {
            FooterHeading(if (currentLanguage == AppLanguage.ENGLISH) "Ethics & Safety" else "ധാർമ്മിക മാർഗ്ഗരേഖകൾ")
            Spacer(modifier = Modifier.height(8.dp))
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Informed Consent & Safety" else "പൂർണ്ണ സമ്മതം & സുരക്ഷ", onNavigateEthics)
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Scientific Perspectives" else "ശാസ്ത്രീയ കാഴ്ചപ്പാട്", onNavigateIntro)
          }
          Column(modifier = Modifier.weight(1f)) {
            FooterHeading(if (currentLanguage == AppLanguage.ENGLISH) "Student Portal" else "വിദ്യാർത്ഥി വേദി")
            Spacer(modifier = Modifier.height(8.dp))
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "My Classes" else "എന്റെ ക്ലാസ്സുകൾ", onNavigateClasses)
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Join Class Registration" else "ക്ലാസ്സിൽ ചേരുക", onNavigateClasses)
          }
        }
      }
    } else {
      // 1-Column Mobile Footer
      Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        BrandHeader(currentLanguage)
        Text(
          text = AppStrings.subtitle(currentLanguage),
          color = TextSecondary,
          fontSize = 12.sp,
          lineHeight = 17.sp
        )

        HorizontalDivider(color = SlateCardBorder, thickness = 0.8.dp)

        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.weight(1f)) {
            FooterHeading(if (currentLanguage == AppLanguage.ENGLISH) "Explore" else "വിഷയങ്ങൾ")
            Spacer(modifier = Modifier.height(6.dp))
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Course Intro" else "ആമുഖം", onNavigateIntro)
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "What You Learn" else "പഠനവിഷയങ്ങൾ", onNavigateCurriculum)
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Ethics & Safety" else "സുരക്ഷ", onNavigateEthics)
          }
          Column(modifier = Modifier.weight(1f)) {
            FooterHeading(if (currentLanguage == AppLanguage.ENGLISH) "Classes" else "ക്ലാസ്സുകൾ")
            Spacer(modifier = Modifier.height(6.dp))
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "My Classes" else "എന്റെ ക്ലാസ്സുകൾ", onNavigateClasses)
            FooterLink(if (currentLanguage == AppLanguage.ENGLISH) "Private Access" else "പ്രവേശനം", onNavigateClasses)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider(color = SlateCardBorder, thickness = 0.8.dp)
    Spacer(modifier = Modifier.height(14.dp))

    // Bottom copyright line
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "© 2026 HYPNOTISM / ഹിപ്നോട്ടിസം. All rights reserved.",
        color = TextMuted,
        fontSize = 11.sp
      )
      Text(
        text = if (currentLanguage == AppLanguage.ENGLISH) "Educational & Responsible" else "ശാസ്ത്രീയവും ധാർമ്മികവും",
        color = FocusCyanLight,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
      )
    }
  }
}

@Composable
private fun BrandHeader(currentLanguage: AppLanguage) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    HypnoticLogo(size = 32.dp, animated = false)
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(
        text = AppStrings.appTitle(currentLanguage),
        color = TextPrimary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
      Text(
        text = if (currentLanguage == AppLanguage.ENGLISH) "ഹിപ്നോട്ടിസം" else "HYPNOTISM",
        color = FocusCyanLight,
        fontSize = 11.sp
      )
    }
  }
}

@Composable
private fun FooterHeading(text: String) {
  Text(
    text = text,
    color = TextPrimary,
    fontSize = 13.sp,
    fontWeight = FontWeight.Bold
  )
}

@Composable
private fun FooterLink(text: String, onClick: () -> Unit) {
  Text(
    text = text,
    color = TextSecondary,
    fontSize = 12.sp,
    modifier = Modifier
      .clickable(onClick = onClick)
      .padding(vertical = 3.dp)
  )
}
