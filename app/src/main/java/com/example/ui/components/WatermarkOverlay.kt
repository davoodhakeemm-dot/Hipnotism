package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.WatermarkColor

@Composable
fun WatermarkOverlay(
  studentName: String,
  studentEmail: String,
  modifier: Modifier = Modifier
) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .fillMaxSize()
      .testTag("student_watermark_overlay")
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .rotate(-25f)
        .padding(16.dp)
    ) {
      Text(
        text = "Licensed to: $studentName",
        color = WatermarkColor,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = studentEmail,
        color = WatermarkColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center
      )
    }
  }
}
