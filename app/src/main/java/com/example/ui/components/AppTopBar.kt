package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Language
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
import com.example.model.StudentUser
import com.example.ui.theme.*
import com.example.util.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
  currentLanguage: AppLanguage,
  currentUser: StudentUser?,
  isAdminLoggedIn: Boolean,
  canNavigateBack: Boolean,
  onNavigateBack: () -> Unit,
  onToggleLanguage: (AppLanguage) -> Unit,
  onOpenProfileOrDashboard: () -> Unit,
  onTripleTapLogo: (() -> Unit)? = null
) {
  TopAppBar(
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        HypnoticLogo(
          size = 38.dp,
          animated = true,
          onTripleTap = onTripleTapLogo
        )
        Column {
          Text(
            text = AppStrings.appTitle(currentLanguage),
            color = TextPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Text(
            text = if (currentLanguage == AppLanguage.ENGLISH) "ഹിപ്നോട്ടിസം" else "HYPNOTISM",
            color = FocusCyanLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal
          )
        }
      }
    },
    navigationIcon = {
      if (canNavigateBack) {
        IconButton(
          onClick = onNavigateBack,
          modifier = Modifier.testTag("topbar_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = TextPrimary
          )
        }
      }
    },
    actions = {
      // Language Switcher Pill
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .padding(end = 6.dp)
          .clip(RoundedCornerShape(20.dp))
          .background(SlateCard)
          .border(1.dp, SlateCardBorder, RoundedCornerShape(20.dp))
          .padding(horizontal = 4.dp, vertical = 2.dp)
          .testTag("language_switcher_toggle")
      ) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (currentLanguage == AppLanguage.ENGLISH) MindIndigo else Color.Transparent)
            .clickable { onToggleLanguage(AppLanguage.ENGLISH) }
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "EN",
            color = if (currentLanguage == AppLanguage.ENGLISH) Color.White else TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (currentLanguage == AppLanguage.MALAYALAM) MindIndigo else Color.Transparent)
            .clickable { onToggleLanguage(AppLanguage.MALAYALAM) }
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "മലയാളം",
            color = if (currentLanguage == AppLanguage.MALAYALAM) Color.White else TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      // User / Admin quick button
      if (isAdminLoggedIn) {
        IconButton(
          onClick = onOpenProfileOrDashboard,
          modifier = Modifier.testTag("topbar_admin_badge")
        ) {
          Icon(
            imageVector = Icons.Default.AdminPanelSettings,
            contentDescription = "Admin Space",
            tint = WisdomAmber
          )
        }
      } else {
        IconButton(
          onClick = onOpenProfileOrDashboard,
          modifier = Modifier.testTag("topbar_user_profile_button")
        ) {
          Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "My Profile / Classes",
            tint = if (currentUser != null) FocusCyan else TextSecondary
          )
        }
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MidnightSurface,
      titleContentColor = TextPrimary
    )
  )
}
