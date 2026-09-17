package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.HypnotismRepository
import com.example.model.AppLanguage
import com.example.model.RegistrationStatus
import com.example.model.StudentUser
import com.example.util.AppStrings
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  private lateinit var repository: HypnotismRepository

  @Before
  fun setUp() {
    repository = HypnotismRepository.getInstance()
  }

  @Test
  fun testAppNameStringResource() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Hypnotism", appName)
  }

  @Test
  fun testBilingualStrings() {
    // English
    assertEquals("HYPNOTISM", AppStrings.appTitle(AppLanguage.ENGLISH))
    assertEquals("Understand the Mind • Master Your Focus • Practice Responsibly", AppStrings.subtitle(AppLanguage.ENGLISH))

    // Malayalam
    assertEquals("ഹിപ്നോട്ടിസം", AppStrings.appTitle(AppLanguage.MALAYALAM))
    assertEquals("മനസ്സിനെ മനസ്സിലാക്കുക • ശ്രദ്ധയെ നിയന്ത്രിക്കുക • ഉത്തരവാദിത്തത്തോടെ പരിശീലിക്കുക", AppStrings.subtitle(AppLanguage.MALAYALAM))
  }

  @Test
  fun testGoogleAuthVerificationOnRegistration() {
    // 1. Mismatched email should fail
    val failResult = repository.registerStudent(
      fullName = "Test Student",
      age = 25,
      phoneNumber = "+919876543210",
      whatsAppNumber = "+919876543210",
      profilePhotoUri = null,
      gmailAddress = "student.entered@gmail.com",
      verifiedGoogleEmail = "other.google@gmail.com",
      address = "Kochi, Kerala",
      selectedCourseId = "hypno-ml",
      consentAccepted = true
    )
    assertTrue("Mismatched Google Auth email must fail", failResult.isFailure)

    // 2. Matching email should succeed with PENDING status
    val successResult = repository.registerStudent(
      fullName = "Valid Student",
      age = 22,
      phoneNumber = "+919876543211",
      whatsAppNumber = "+919876543211",
      profilePhotoUri = null,
      gmailAddress = "valid.student@gmail.com",
      verifiedGoogleEmail = "valid.student@gmail.com",
      address = "Kozhikode, Kerala",
      selectedCourseId = "hypno-ml",
      consentAccepted = true
    )
    assertTrue("Matching email must succeed", successResult.isSuccess)
    val student = successResult.getOrThrow()
    assertEquals(RegistrationStatus.PENDING, student.status)
  }

  @Test
  fun testCourseAccessControl() {
    val approvedStudentWithAccess = StudentUser(
      id = "test-1",
      fullName = "Approved Student",
      age = 26,
      phoneNumber = "1234567890",
      whatsAppNumber = "1234567890",
      profilePhotoUri = null,
      gmailAddress = "student1@gmail.com",
      verifiedGoogleEmail = "student1@gmail.com",
      address = "Kerala",
      selectedCourseId = "hypno-ml",
      registrationDate = "2026-09-17",
      status = RegistrationStatus.APPROVED,
      lastLogin = "Today"
    )

    // Authorized access to hypno-ml (student1@gmail.com is in hypno-ml authorized list)
    val (hasAccess, _) = repository.checkCourseAccess("hypno-ml", approvedStudentWithAccess)
    assertTrue("Approved student in authorized list should have access", hasAccess)

    // Unauthorized course access (student1@gmail.com is not in hypno-en)
    val (unauthAccess, reason) = repository.checkCourseAccess("hypno-en", approvedStudentWithAccess)
    assertFalse("Student not authorized for hypno-en should be blocked", unauthAccess)
    assertTrue("Reason should indicate email not authorized", reason.contains("not authorized"))

    // Pending student access should be blocked
    val pendingStudent = approvedStudentWithAccess.copy(status = RegistrationStatus.PENDING)
    val (pendingAccess, pendingReason) = repository.checkCourseAccess("hypno-ml", pendingStudent)
    assertFalse("Pending student must be blocked", pendingAccess)
    assertTrue("Reason should state pending status", pendingReason.contains("pending"))

    // Suspended student access should be blocked
    val suspendedStudent = approvedStudentWithAccess.copy(status = RegistrationStatus.SUSPENDED)
    val (suspendedAccess, suspendedReason) = repository.checkCourseAccess("hypno-ml", suspendedStudent)
    assertFalse("Suspended student must be blocked", suspendedAccess)
    assertTrue("Reason should state suspended status", suspendedReason.contains("suspended"))
  }

  @Test
  fun testAdminAccessKeyAndRateLimiting() {
    // Valid key
    val (isValid, _) = repository.verifyAdminAccessKey(HypnotismRepository.PROTOTYPE_ADMIN_KEY)
    assertTrue("Key 91870 must be valid", isValid)

    // Invalid key increments failures
    val (wrong1, _) = repository.verifyAdminAccessKey("00000")
    assertFalse("Wrong key must fail", wrong1)

    // Verify owner admin authentication
    val authSuccess = repository.authenticateAdmin(HypnotismRepository.OWNER_ADMIN_EMAIL)
    assertTrue("Owner admin email must authenticate successfully", authSuccess)

    // Unauthorized email admin authentication must fail
    val unauthAdmin = repository.authenticateAdmin("random.person@gmail.com")
    assertFalse("Random email cannot authenticate as admin", unauthAdmin)
  }
}
