package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.HypnotismRepository
import com.example.ui.theme.*

@Composable
fun AdminAccessDialog(
  onDismiss: () -> Unit,
  onAdminAuthenticated: () -> Unit
) {
  val repository = remember { HypnotismRepository.getInstance() }
  var accessKey by remember { mutableStateOf("") }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var isKeyVerified by remember { mutableStateOf(false) }

  // Step 2: Google Admin Identity Verification
  var adminEmailInput by remember { mutableStateOf(HypnotismRepository.OWNER_ADMIN_EMAIL) }
  var isVerifyingGoogleAuth by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MidnightSurface),
      border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("admin_access_dialog")
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(22.dp)
      ) {
        Icon(
          imageVector = Icons.Default.AdminPanelSettings,
          contentDescription = "Admin Access",
          tint = WisdomAmber,
          modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Admin Access",
          color = TextPrimary,
          fontSize = 19.sp,
          fontWeight = FontWeight.Bold
        )

        Text(
          text = "Private Instructor & Management Portal",
          color = TextSecondary,
          fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        if (!isKeyVerified) {
          // STEP 1: Enter Private Access Key
          Text(
            text = "Enter Access Key",
            color = FocusCyanLight,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = accessKey,
            onValueChange = {
              accessKey = it
              errorMessage = null
            },
            placeholder = { Text("Enter Private Access Key", color = TextMuted) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = WisdomAmber,
              unfocusedBorderColor = SlateCardBorder,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("admin_key_input")
          )

          if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = errorMessage ?: "",
              color = ErrorRed,
              fontSize = 12.sp,
              textAlign = TextAlign.Center
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            OutlinedButton(
              onClick = onDismiss,
              modifier = Modifier.weight(1f)
            ) {
              Text("Cancel", color = TextSecondary)
            }

            Button(
              onClick = {
                val (isValid, msg) = repository.verifyAdminAccessKey(accessKey)
                if (isValid) {
                  isKeyVerified = true
                  errorMessage = null
                } else {
                  errorMessage = msg
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = WisdomAmber),
              modifier = Modifier
                .weight(1f)
                .testTag("verify_admin_key_button")
            ) {
              Text("Verify Key", color = Color.Black, fontWeight = FontWeight.Bold)
            }
          }
        } else {
          // STEP 2: Secure Google Authentication / Role check
          // Requirement: "The key must NOT be the real security boundary. Require Firebase Authentication and a backend/admin-role check before opening Admin Space. Only the owner's authorized account can access Admin Space."
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = SlateCard,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Security, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Role & Identity Verification", color = FocusCyanLight, fontSize = 13.sp, fontWeight = FontWeight.Bold)
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "Key accepted. To prevent unauthorized client-side bypass, verify with the authorized owner Google Account.",
                color = TextSecondary,
                fontSize = 11.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          OutlinedTextField(
            value = adminEmailInput,
            onValueChange = {
              adminEmailInput = it
              errorMessage = null
            },
            label = { Text("Authorized Admin Gmail", color = TextSecondary) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = FocusCyan,
              unfocusedBorderColor = SlateCardBorder,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("admin_email_input")
          )

          if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = errorMessage ?: "",
              color = ErrorRed,
              fontSize = 12.sp,
              textAlign = TextAlign.Center
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          Button(
            onClick = {
              isVerifyingGoogleAuth = true
              val success = repository.authenticateAdmin(adminEmailInput)
              isVerifyingGoogleAuth = false
              if (success) {
                onAdminAuthenticated()
              } else {
                errorMessage = "Access Denied: Account $adminEmailInput does not have Admin / Instructor role permissions."
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("admin_google_login_button")
          ) {
            Text("Authenticate as Admin", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
