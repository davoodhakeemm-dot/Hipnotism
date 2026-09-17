package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.FocusCyan
import com.example.ui.theme.MindIndigo

@Composable
fun HypnoticLogo(
  size: Dp = 64.dp,
  animated: Boolean = true,
  onTripleTap: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  // Triple tap tracking: exactly 3 taps within approximately 2 seconds
  var tapCount by remember { mutableIntStateOf(0) }
  var firstTapTime by remember { mutableLongStateOf(0L) }

  // Subtle rotation for hypnotic focus rings
  val infiniteTransition = rememberInfiniteTransition(label = "hypnotic_spin")
  val angle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = if (animated) 360f else 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(20000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "logo_rotation"
  )

  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .size(size)
      .testTag("hypnotic_logo")
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
      ) {
        if (onTripleTap != null) {
          val now = System.currentTimeMillis()
          if (tapCount == 0 || now - firstTapTime > 2000) {
            tapCount = 1
            firstTapTime = now
          } else {
            tapCount++
            if (tapCount == 3) {
              tapCount = 0
              onTripleTap()
            }
          }
        }
      }
  ) {
    // Outer glowing concentric ring
    Box(
      modifier = Modifier
        .fillMaxSize()
        .rotate(angle)
        .border(
          width = 1.5.dp,
          brush = Brush.sweepGradient(
            colors = listOf(
              MindIndigo,
              FocusCyan,
              Color.Transparent,
              MindIndigo
            )
          ),
          shape = CircleShape
        )
    )

    // Inner logo image
    Image(
      painter = painterResource(id = R.drawable.hypnotism_logo),
      contentDescription = "Hypnotism Logo",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(size - 8.dp)
        .clip(CircleShape)
        .border(1.dp, Brush.radialGradient(listOf(FocusCyan, Color.Transparent)), CircleShape)
    )
  }
}
