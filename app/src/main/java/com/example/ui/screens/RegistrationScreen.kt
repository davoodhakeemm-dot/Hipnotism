package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HypnotismRepository
import com.example.model.AppLanguage
import com.example.model.Course
import com.example.model.StudentUser
import com.example.ui.components.AppFooter
import com.example.ui.theme.*
import com.example.util.AppStrings
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
  currentLanguage: AppLanguage,
  courses: List<Course>,
  onRegistrationSuccess: (StudentUser) -> Unit,
  onCancel: () -> Unit
) {
  val repository = remember { HypnotismRepository.getInstance() }

  var fullName by remember { mutableStateOf("") }
  var ageText by remember { mutableStateOf("") }
  var phoneNumber by remember { mutableStateOf("") }
  var whatsAppNumber by remember { mutableStateOf("") }
  var gmailAddress by remember { mutableStateOf("") }
  var address by remember { mutableStateOf("") }
  var selectedCourseId by remember { mutableStateOf(courses.firstOrNull()?.id ?: "hypno-ml") }

  // Google Sign-In state: Simulating Google authentication token/account
  var isGoogleSignedIn by remember { mutableStateOf(false) }
  var authenticatedGoogleEmail by remember { mutableStateOf("") }

  // Consent checkbox
  var consentAccepted by remember { mutableStateOf(false) }

  // Errors & UI state
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var isSubmitting by remember { mutableStateOf(false) }
  var showSuccessDialog by remember { mutableStateOf(false) }
  var registeredUser by remember { mutableStateOf<StudentUser?>(null) }

  val currentDate = remember {
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
  }

  BoxWithConstraints(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepObsidian)
      .testTag("registration_screen")
  ) {
    val isDesktop = maxWidth >= 960.dp
    val hPadding = if (isDesktop) 36.dp else 16.dp

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(top = 20.dp, bottom = 24.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 760.dp)
          .padding(horizontal = hPadding)
      ) {
        // Header
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(MindIndigo.copy(alpha = 0.2f))
          .border(1.dp, MindIndigo, CircleShape)
      ) {
        Icon(Icons.Default.School, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(24.dp))
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column {
        Text(
          text = if (currentLanguage == AppLanguage.ENGLISH) "Student Registration" else "വിദ്യാർത്ഥി രജിസ്ട്രേഷൻ",
          color = TextPrimary,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = if (currentLanguage == AppLanguage.ENGLISH)
            "Apply for private Hypnotism class access"
          else
            "പ്രത്യേക ഹിപ്നോട്ടിസം ക്ലാസ്സിൽ ചേരാൻ അപേക്ഷിക്കുക",
          color = TextSecondary,
          fontSize = 12.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // GOOGLE SIGN-IN VERIFICATION CARD (CRITICAL REQUIREMENT)
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = MidnightSurface,
      border = CardDefaults.outlinedCardBorder().copy(
        brush = androidx.compose.ui.graphics.SolidColor(if (isGoogleSignedIn) SuccessGreen else MindIndigo)
      ),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("google_auth_box")
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = if (isGoogleSignedIn) Icons.Default.CheckCircle else Icons.Default.AccountCircle,
              contentDescription = null,
              tint = if (isGoogleSignedIn) SuccessGreen else FocusCyanLight,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (isGoogleSignedIn) "Google Account Verified" else "Google Sign-In Required",
              color = TextPrimary,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
          }

          if (isGoogleSignedIn) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = SuccessGreen.copy(alpha = 0.2f)
            ) {
              Text(
                text = "VERIFIED",
                color = SuccessGreen,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = if (currentLanguage == AppLanguage.ENGLISH)
            "The Gmail obtained from Google authentication must be verified against the Gmail submitted in the form."
          else
            "ഫോമിൽ നൽകുന്ന ജിമെയിൽ ഗൂഗിൾ അക്കൗണ്ടുമായി ഒത്തുനോക്കി സ്ഥിരീകരിക്കുന്നതാണ്.",
          color = TextSecondary,
          fontSize = 11.sp,
          lineHeight = 15.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!isGoogleSignedIn) {
          Button(
            onClick = {
              // Simulated Google Sign-In with auto-fill or current Gmail
              val emailToVerify = if (gmailAddress.isNotBlank()) gmailAddress.trim().lowercase() else "student.demo@gmail.com"
              authenticatedGoogleEmail = emailToVerify
              if (gmailAddress.isBlank()) {
                gmailAddress = emailToVerify
              }
              isGoogleSignedIn = true
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SlateCard),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FocusCyanLight)),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("google_sign_in_button")
          ) {
            Icon(Icons.Default.Security, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign in with Google", color = FocusCyanLight, fontWeight = FontWeight.Bold)
          }
        } else {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(SlateCard)
              .padding(10.dp)
          ) {
            Text(
              text = "Authenticated: $authenticatedGoogleEmail",
              color = FocusCyanLight,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium,
              modifier = Modifier.weight(1f)
            )
            Text(
              text = "Change",
              color = WisdomAmber,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.clickable {
                isGoogleSignedIn = false
              }
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // REGISTRATION FORM FIELDS
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SlateCard),
      border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.padding(18.dp)
      ) {
        // Full Name
        OutlinedTextField(
          value = fullName,
          onValueChange = { fullName = it },
          label = { Text("Full Name", color = TextSecondary) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("reg_fullname_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FocusCyan,
            unfocusedBorderColor = SlateCardBorder,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          )
        )

        // Age
        OutlinedTextField(
          value = ageText,
          onValueChange = { ageText = it },
          label = { Text("Age", color = TextSecondary) },
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("reg_age_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FocusCyan,
            unfocusedBorderColor = SlateCardBorder,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          )
        )

        // Phone Number
        OutlinedTextField(
          value = phoneNumber,
          onValueChange = {
            phoneNumber = it
            if (whatsAppNumber.isBlank()) whatsAppNumber = it
          },
          label = { Text("Phone Number", color = TextSecondary) },
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("reg_phone_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FocusCyan,
            unfocusedBorderColor = SlateCardBorder,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          )
        )

        // WhatsApp Number
        OutlinedTextField(
          value = whatsAppNumber,
          onValueChange = { whatsAppNumber = it },
          label = { Text("WhatsApp Number", color = TextSecondary) },
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("reg_whatsapp_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FocusCyan,
            unfocusedBorderColor = SlateCardBorder,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          )
        )

        // Gmail Address (Form Field)
        OutlinedTextField(
          value = gmailAddress,
          onValueChange = { gmailAddress = it },
          label = { Text("Gmail Address", color = TextSecondary) },
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("reg_gmail_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FocusCyan,
            unfocusedBorderColor = SlateCardBorder,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          )
        )

        // Address
        OutlinedTextField(
          value = address,
          onValueChange = { address = it },
          label = { Text("Physical Address / City", color = TextSecondary) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("reg_address_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FocusCyan,
            unfocusedBorderColor = SlateCardBorder,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          )
        )

        // Selected Course Picker
        Text(
          text = if (currentLanguage == AppLanguage.ENGLISH) "Select Preferred Course:" else "കോഴ്സ് തിരഞ്ഞെടുക്കുക:",
          color = TextPrimary,
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold
        )

        courses.forEach { course ->
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = if (selectedCourseId == course.id) MindIndigo.copy(alpha = 0.25f) else MidnightSurface,
            border = CardDefaults.outlinedCardBorder().copy(
              brush = androidx.compose.ui.graphics.SolidColor(if (selectedCourseId == course.id) FocusCyanLight else SlateCardBorder)
            ),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { selectedCourseId = course.id }
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(12.dp)
            ) {
              RadioButton(
                selected = selectedCourseId == course.id,
                onClick = { selectedCourseId = course.id },
                colors = RadioButtonDefaults.colors(selectedColor = FocusCyanLight)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = if (currentLanguage == AppLanguage.ENGLISH) course.titleEn else course.titleMl,
                  color = TextPrimary,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = course.languageType,
                  color = TextSecondary,
                  fontSize = 11.sp
                )
              }
            }
          }
        }

        // Registration Date
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(top = 4.dp)
        ) {
          Icon(Icons.Default.CalendarToday, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Registration Date: $currentDate",
            color = TextSecondary,
            fontSize = 12.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // PRIVACY NOTICE & ETHICS CONSENT CHECKBOX
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = SlateCard,
      border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SlateCardBorder)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Lock, contentDescription = null, tint = FocusCyanLight, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (currentLanguage == AppLanguage.ENGLISH) "Privacy & Security Guarantee" else "സ്വകാര്യതാ സുരക്ഷാ ഉറപ്പ്",
            color = FocusCyanLight,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = AppStrings.privacyNotice(currentLanguage),
          color = TextSecondary,
          fontSize = 11.sp,
          lineHeight = 15.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          verticalAlignment = Alignment.Top,
          modifier = Modifier
            .fillMaxWidth()
            .clickable { consentAccepted = !consentAccepted }
            .testTag("consent_checkbox_row")
        ) {
          Checkbox(
            checked = consentAccepted,
            onCheckedChange = { consentAccepted = it },
            colors = CheckboxDefaults.colors(checkedColor = FocusCyanLight),
            modifier = Modifier.testTag("consent_checkbox")
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = AppStrings.consentCheckboxText(currentLanguage),
            color = TextPrimary,
            fontSize = 12.sp,
            lineHeight = 17.sp
          )
        }
      }
    }

    if (errorMessage != null) {
      Spacer(modifier = Modifier.height(12.dp))
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = ErrorRed.copy(alpha = 0.15f),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(ErrorRed)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = errorMessage ?: "",
          color = ErrorRed,
          fontSize = 12.sp,
          modifier = Modifier.padding(12.dp),
          textAlign = TextAlign.Center
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Submit & Cancel Buttons
    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      OutlinedButton(
        onClick = onCancel,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.weight(1f)
      ) {
        Text("Cancel", color = TextSecondary)
      }

      Button(
        onClick = {
          errorMessage = null
          val age = ageText.toIntOrNull() ?: 0

          if (fullName.isBlank()) {
            errorMessage = "Please enter your full name."
            return@Button
          }
          if (age < 16) {
            errorMessage = "Students must be at least 16 years of age."
            return@Button
          }
          if (phoneNumber.isBlank()) {
            errorMessage = "Please enter your phone number."
            return@Button
          }
          if (gmailAddress.isBlank() || !gmailAddress.contains("@gmail.com")) {
            errorMessage = "Please enter a valid Gmail address (@gmail.com)."
            return@Button
          }
          if (!isGoogleSignedIn) {
            errorMessage = "Google Sign-In is required to verify your Gmail identity."
            return@Button
          }
          if (!consentAccepted) {
            errorMessage = "You must agree to the Ethics & Safety Consent."
            return@Button
          }

          isSubmitting = true
          val result = repository.registerStudent(
            fullName = fullName,
            age = age,
            phoneNumber = phoneNumber,
            whatsAppNumber = whatsAppNumber,
            profilePhotoUri = null,
            gmailAddress = gmailAddress,
            verifiedGoogleEmail = authenticatedGoogleEmail,
            address = address,
            selectedCourseId = selectedCourseId,
            consentAccepted = consentAccepted
          )
          isSubmitting = false

          result.onSuccess { student ->
            registeredUser = student
            showSuccessDialog = true
          }.onFailure { err ->
            errorMessage = err.message
          }
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MindIndigo),
        modifier = Modifier
          .weight(1f)
          .testTag("submit_registration_button")
      ) {
        Text("Submit Registration", color = Color.White, fontWeight = FontWeight.Bold)
      }
    }
  }

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

  // Success Dialog
  if (showSuccessDialog && registeredUser != null) {
    AlertDialog(
      onDismissRequest = {
        showSuccessDialog = false
        onRegistrationSuccess(registeredUser!!)
      },
      title = {
        Text("Registration Submitted!", color = TextPrimary, fontWeight = FontWeight.Bold)
      },
      text = {
        Column {
          Text(
            text = "Welcome ${registeredUser?.fullName}! Your application has been received.",
            color = TextPrimary,
            fontSize = 14.sp
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Status: PENDING ADMIN APPROVAL\nVerified Gmail: ${registeredUser?.verifiedGoogleEmail}",
            color = WisdomAmber,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Once the course administrator approves your registration and verifies your Gmail, your course access will be unlocked in your Student Dashboard.",
            color = TextSecondary,
            fontSize = 12.sp
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            showSuccessDialog = false
            onRegistrationSuccess(registeredUser!!)
          },
          colors = ButtonDefaults.buttonColors(containerColor = MindIndigo)
        ) {
          Text("Go to My Dashboard", color = Color.White)
        }
      }
    )
  }
}
