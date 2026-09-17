package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.theme.*
import com.example.util.AppStrings

@Composable
fun WhatStudentsLearnScreen(
  currentLanguage: AppLanguage
) {
  val items = AppStrings.whatStudentsLearnItems(currentLanguage)

  LazyColumn(
    contentPadding = PaddingValues(20.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp),
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .testTag("what_students_learn_screen")
  ) {
    item {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(FocusCyan.copy(alpha = 0.2f))
            .border(1.dp, FocusCyan, CircleShape)
        ) {
          Icon(Icons.Default.MenuBook, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
          Text(
            text = AppStrings.whatStudentsLearnTitle(currentLanguage),
            color = TextPrimary,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = if (currentLanguage == AppLanguage.ENGLISH)
              "Core curriculum modules & practical knowledge"
            else
              "പ്രധാന പഠന വിഷയങ്ങളും പ്രായോഗിക രീതികളും",
            color = FocusCyanLight,
            fontSize = 12.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
    }

    itemsIndexed(items) { index, topic ->
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SlateCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(14.dp)
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(MindIndigo.copy(alpha = 0.2f))
          ) {
            Text(
              text = "${index + 1}",
              color = FocusCyanLight,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Text(
            text = topic,
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
          )

          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = SuccessGreen,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}
