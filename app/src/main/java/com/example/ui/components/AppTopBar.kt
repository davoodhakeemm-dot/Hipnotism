package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
import com.example.model.StudentUser
import com.example.ui.theme.*
import com.example.util.AppStrings

@Composable
fun AppTopBar(
  currentLanguage: AppLanguage,
  currentUser: StudentUser?,
  isAdminLoggedIn: Boolean,
  canNavigateBack: Boolean,
  onNavigateBack: () -> Unit,
  onToggleLanguage: (AppLanguage) -> Unit,
  onOpenProfileOrDashboard: () -> Unit,
  onTripleTapLogo: (() -> Unit)? = null,
  onNavigateHome: () -> Unit = {},
  onNavigateCourses: () -> Unit = {},
  onNavigateWhatStudentsLearn: () -> Unit = {},
  onNavigateEthicsSafety: () -> Unit = {},
  onNavigateAbout: () -> Unit = {},
  onNavigateMyClasses: () -> Unit = {},
  onNavigateJoinClass: () -> Unit = {}
) {
  var showMobileNavMenu by remember { mutableStateOf(false) }

  BoxWithConstraints(
    modifier = Modifier
      .fillMaxWidth()
      .background(MidnightSurface)
      .border(width = 1.dp, color = SlateCardBorder.copy(alpha = 0.5f))
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .testTag("app_top_bar")
  ) {
    val isDesktop = maxWidth >= 960.dp
    val isTablet = maxWidth in 650.dp..959.dp

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      // LEFT: Logo & Brand + Optional Back Button
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
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

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clickable(onClick = onNavigateHome)
            .padding(vertical = 4.dp)
        ) {
          HypnoticLogo(
            size = 38.dp,
            animated = true,
            onTripleTap = onTripleTapLogo
          )
          Spacer(modifier = Modifier.width(10.dp))
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
      }

      // CENTER: Navigation Links (Desktop & Wide Screen)
      if (isDesktop) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp),
          modifier = Modifier.testTag("desktop_nav_links")
        ) {
          NavButton(
            text = if (currentLanguage == AppLanguage.ENGLISH) "Home" else "ഹോം",
            onClick = onNavigateHome
          )
          NavButton(
            text = if (currentLanguage == AppLanguage.ENGLISH) "Courses" else "കോഴ്സുകൾ",
            onClick = onNavigateCourses
          )
          NavButton(
            text = if (currentLanguage == AppLanguage.ENGLISH) "What You Learn" else "പഠനവിഷയങ്ങൾ",
            onClick = onNavigateWhatStudentsLearn
          )
          NavButton(
            text = if (currentLanguage == AppLanguage.ENGLISH) "Ethics & Safety" else "ധാർമ്മികത & സുരക്ഷ",
            onClick = onNavigateEthicsSafety
          )
          NavButton(
            text = if (currentLanguage == AppLanguage.ENGLISH) "About" else "ആമുഖം",
            onClick = onNavigateAbout
          )
          NavButton(
            text = if (currentLanguage == AppLanguage.ENGLISH) "My Classes" else "എന്റെ ക്ലാസ്സുകൾ",
            onClick = onNavigateMyClasses
          )
        }
      }

      // RIGHT: Language switcher, Join Class button, and Profile/Menu
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Language Switcher Pill
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
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

        // Join Class Button (Desktop / Tablet)
        if (isDesktop || isTablet) {
          Button(
            onClick = onNavigateJoinClass,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            modifier = Modifier.testTag("topbar_join_class_button")
          ) {
            Icon(Icons.Default.School, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = AppStrings.joinClass(currentLanguage),
              color = Color.White,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Profile / Dashboard or Admin Icon
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

        // Mobile Hamburger Menu for navigation
        if (!isDesktop) {
          Box {
            IconButton(
              onClick = { showMobileNavMenu = !showMobileNavMenu },
              modifier = Modifier.testTag("topbar_mobile_menu_button")
            ) {
              Icon(
                imageVector = if (showMobileNavMenu) Icons.Default.Close else Icons.Default.Menu,
                contentDescription = "Navigation Menu",
                tint = TextPrimary
              )
            }

            DropdownMenu(
              expanded = showMobileNavMenu,
              onDismissRequest = { showMobileNavMenu = false },
              modifier = Modifier
                .background(SlateCard)
                .border(1.dp, SlateCardBorder, RoundedCornerShape(8.dp))
            ) {
              DropdownMenuItem(
                text = { Text(if (currentLanguage == AppLanguage.ENGLISH) "Home" else "ഹോം", color = TextPrimary) },
                leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = FocusCyanLight) },
                onClick = {
                  showMobileNavMenu = false
                  onNavigateHome()
                }
              )
              DropdownMenuItem(
                text = { Text(if (currentLanguage == AppLanguage.ENGLISH) "Courses" else "കോഴ്സുകൾ", color = TextPrimary) },
                leadingIcon = { Icon(Icons.Default.Class, contentDescription = null, tint = FocusCyanLight) },
                onClick = {
                  showMobileNavMenu = false
                  onNavigateCourses()
                }
              )
              DropdownMenuItem(
                text = { Text(if (currentLanguage == AppLanguage.ENGLISH) "What You Learn" else "പഠനവിഷയങ്ങൾ", color = TextPrimary) },
                leadingIcon = { Icon(Icons.Default.AutoStories, contentDescription = null, tint = FocusCyanLight) },
                onClick = {
                  showMobileNavMenu = false
                  onNavigateWhatStudentsLearn()
                }
              )
              DropdownMenuItem(
                text = { Text(if (currentLanguage == AppLanguage.ENGLISH) "Ethics & Safety" else "ധാർമ്മികത & സുരക്ഷ", color = TextPrimary) },
                leadingIcon = { Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = WisdomAmber) },
                onClick = {
                  showMobileNavMenu = false
                  onNavigateEthicsSafety()
                }
              )
              DropdownMenuItem(
                text = { Text(if (currentLanguage == AppLanguage.ENGLISH) "Course Intro" else "ആമുഖം", color = TextPrimary) },
                leadingIcon = { Icon(Icons.Default.MenuBook, contentDescription = null, tint = FocusCyanLight) },
                onClick = {
                  showMobileNavMenu = false
                  onNavigateAbout()
                }
              )
              DropdownMenuItem(
                text = { Text(if (currentLanguage == AppLanguage.ENGLISH) "My Classes" else "എന്റെ ക്ലാസ്സുകൾ", color = TextPrimary) },
                leadingIcon = { Icon(Icons.Default.School, contentDescription = null, tint = FocusCyanLight) },
                onClick = {
                  showMobileNavMenu = false
                  onNavigateMyClasses()
                }
              )
              HorizontalDivider(color = SlateCardBorder)
              DropdownMenuItem(
                text = { Text(AppStrings.joinClass(currentLanguage), color = Color.White, fontWeight = FontWeight.Bold) },
                leadingIcon = { Icon(Icons.Default.AddCircle, contentDescription = null, tint = FocusCyan) },
                onClick = {
                  showMobileNavMenu = false
                  onNavigateJoinClass()
                }
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun NavButton(
  text: String,
  onClick: () -> Unit
) {
  TextButton(
    onClick = onClick,
    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Text(
      text = text,
      color = TextSecondary,
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium
    )
  }
}
