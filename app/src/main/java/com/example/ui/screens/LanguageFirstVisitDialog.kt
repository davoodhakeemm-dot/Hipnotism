package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.AppLanguage
import com.example.ui.components.HypnoticLogo
import com.example.ui.theme.*

@Composable
fun LanguageFirstVisitDialog(
  onSelectLanguage: (AppLanguage) -> Unit
) {
  Dialog(onDismissRequest = { /* Force selection on first visit */ }) {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = MidnightSurface),
      border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("first_visit_language_dialog")
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
      ) {
        HypnoticLogo(size = 56.dp, animated = true)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "Select Language",
          color = TextPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "ഭാഷ തിരഞ്ഞെടുക്കുക",
          color = FocusCyanLight,
          fontSize = 15.sp,
          fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Choose your preferred language for the Hypnotism learning platform. You can switch at any time.",
          color = TextSecondary,
          fontSize = 13.sp,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // English Option
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = SlateCard,
          border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(MindIndigo)),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectLanguage(AppLanguage.ENGLISH) }
            .testTag("select_english_button")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "English",
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Learn Hypnotism in English",
                color = TextSecondary,
                fontSize = 12.sp
              )
            }
            Text(
              text = "Continue →",
              color = FocusCyanLight,
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Malayalam Option
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = SlateCard,
          border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FocusCyan)),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectLanguage(AppLanguage.MALAYALAM) }
            .testTag("select_malayalam_button")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "മലയാളം (Malayalam)",
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "മലയാളത്തിൽ ഹിപ്നോട്ടിസം പഠിക്കുക",
                color = TextSecondary,
                fontSize = 12.sp
              )
            }
            Text(
              text = "തുടങ്ങുക →",
              color = FocusCyanLight,
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }
  }
}
