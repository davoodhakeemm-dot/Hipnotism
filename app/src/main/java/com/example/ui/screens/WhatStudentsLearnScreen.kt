package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.components.AppFooter
import com.example.ui.theme.*
import com.example.util.AppStrings

@Composable
fun WhatStudentsLearnScreen(
  currentLanguage: AppLanguage
) {
  val items = AppStrings.whatStudentsLearnItems(currentLanguage)

  BoxWithConstraints(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .testTag("what_students_learn_screen")
  ) {
    val isDesktop = maxWidth >= 960.dp
    val isTablet = maxWidth in 650.dp..959.dp
    val columns = if (isDesktop || isTablet) 2 else 1
    val hPadding = if (isDesktop) 40.dp else 16.dp

    LazyColumn(
      contentPadding = PaddingValues(top = 20.dp, bottom = 24.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = hPadding)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .widthIn(max = 1100.dp)
              .align(Alignment.Center)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                  fontSize = 20.sp,
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
        }
      }

      // Chunk items into responsive rows (2 columns on wide, 1 column on mobile)
      val indexedItems = items.mapIndexed { idx, title -> Pair(idx, title) }
      val chunked = indexedItems.chunked(columns)

      items(chunked) { rowPairs ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = hPadding)
        ) {
          Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .widthIn(max = 1100.dp)
              .align(Alignment.Center)
          ) {
            for ((index, topic) in rowPairs) {
              Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SlateCard),
                border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(SlateCardBorder)),
                modifier = Modifier.weight(1f)
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
                    fontSize = 13.sp,
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
            if (columns > rowPairs.size) {
              Spacer(modifier = Modifier.weight(1f))
            }
          }
        }
      }

      item {
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
}
