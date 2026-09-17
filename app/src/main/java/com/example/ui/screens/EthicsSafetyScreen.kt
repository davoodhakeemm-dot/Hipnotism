package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.theme.*
import com.example.util.AppStrings

@Composable
fun EthicsSafetyScreen(
  currentLanguage: AppLanguage
) {
  val directives = AppStrings.ethicsItems(currentLanguage)

  LazyColumn(
    contentPadding = PaddingValues(20.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .testTag("ethics_safety_screen")
  ) {
    // Header
    item {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(
            Brush.verticalGradient(
              colors = listOf(MidnightSurface, SlateCard)
            )
          )
          .border(1.dp, WisdomAmber.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
          .padding(20.dp)
      ) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(WisdomAmber.copy(alpha = 0.15f))
            .border(1.5.dp, WisdomAmber, CircleShape)
        ) {
          Icon(Icons.Default.Shield, contentDescription = null, tint = WisdomAmber, modifier = Modifier.size(28.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = if (currentLanguage == AppLanguage.ENGLISH) "Learn Responsibly" else "ഉത്തരവാദിത്തത്തോടെ പഠിക്കുക",
          color = TextPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = MidnightSurface,
          border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder))
        ) {
          Text(
            text = AppStrings.ethicsSub(currentLanguage),
            color = FocusCyanLight,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = if (currentLanguage == AppLanguage.ENGLISH)
          "Mandatory Ethical Directives"
        else
          "നിർബന്ധിത ധാർമ്മിക നിർദ്ദേശങ്ങൾ",
        color = TextPrimary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      )
    }

    // Directives
    itemsIndexed(directives) { index, directive ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          verticalAlignment = Alignment.Top,
          modifier = Modifier.padding(14.dp)
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(WisdomAmber.copy(alpha = 0.2f))
          ) {
            Text(
              text = "${index + 1}",
              color = WisdomAmber,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Text(
            text = directive,
            color = TextPrimary,
            fontSize = 13.sp,
            lineHeight = 19.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // Medical Disclaimer Card
    item {
      Spacer(modifier = Modifier.height(6.dp))
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = MidnightSurface,
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(ErrorRed.copy(alpha = 0.4f))),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(14.dp)
        ) {
          Icon(Icons.Default.LocalHospital, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(24.dp))
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = if (currentLanguage == AppLanguage.ENGLISH)
              "Medical Notice: This is strictly an educational training program. Do not attempt to use hypnotism to treat clinical disorders, depression, trauma, or medical pain without certified medical and healthcare licenses."
            else
              "മെഡിക്കൽ അറിയിപ്പ്: ഇത് കേവലം ഒരു വിദ്യാഭ്യാസ പരിശീലന പരിപാടി മാത്രമാണ്. ലൈസൻസുള്ള ഡോക്ടറുടെയോ മെഡിക്കൽ വിദഗ്ദ്ധന്റെയോ അനുമതിയില്ലാതെ രോഗചികിത്സയ്ക്കായി ഇത് ഉപയോഗിക്കാൻ പാടില്ല.",
            color = TextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
          )
        }
      }
    }
  }
}
