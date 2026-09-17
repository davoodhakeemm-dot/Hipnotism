package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = MindIndigo,
  onPrimary = Color.White,
  primaryContainer = SlateCard,
  onPrimaryContainer = FocusCyanLight,
  secondary = FocusCyan,
  onSecondary = Color.Black,
  secondaryContainer = MidnightSurface,
  onSecondaryContainer = TextPrimary,
  tertiary = WisdomAmber,
  onTertiary = Color.Black,
  background = DeepObsidian,
  onBackground = TextPrimary,
  surface = MidnightSurface,
  onSurface = TextPrimary,
  surfaceVariant = SlateCard,
  onSurfaceVariant = TextSecondary,
  error = ErrorRed,
  onError = Color.White
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = DarkColorScheme,
    typography = Typography,
    content = content
  )
}

