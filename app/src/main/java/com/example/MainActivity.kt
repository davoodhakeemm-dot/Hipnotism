package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.data.HypnotismRepository
import com.example.model.AppLanguage
import com.example.model.Course
import com.example.ui.components.AppTopBar
import com.example.ui.screens.*
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.MyApplicationTheme

enum class ScreenState {
  HOME,
  COURSE_INTRO,
  WHAT_STUDENTS_LEARN,
  ETHICS_SAFETY,
  REGISTRATION,
  STUDENT_DASHBOARD,
  LESSON_PLAYER,
  ADMIN_DASHBOARD
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        HypnotismApp()
      }
    }
  }
}

@Composable
fun HypnotismApp() {
  val repository = remember { HypnotismRepository.getInstance() }

  // Global App States
  val currentLanguage by repository.currentLanguage.collectAsState()
  val currentUser by repository.currentUser.collectAsState()
  val isAdminLoggedIn by repository.isAdminAuthenticated.collectAsState()
  val courses by repository.courses.collectAsState()

  // Navigation State
  var currentScreen by remember { mutableStateOf(ScreenState.HOME) }
  var selectedCourseForPlayer by remember { mutableStateOf<Course?>(null) }

  // Dialog States
  var showFirstVisitLanguageDialog by remember { mutableStateOf(false) }
  var showAdminAccessDialog by remember { mutableStateOf(false) }

  // Back Button Navigation Handling
  BackHandler(enabled = currentScreen != ScreenState.HOME) {
    currentScreen = when (currentScreen) {
      ScreenState.LESSON_PLAYER -> ScreenState.STUDENT_DASHBOARD
      ScreenState.ADMIN_DASHBOARD -> ScreenState.HOME
      ScreenState.REGISTRATION -> ScreenState.HOME
      ScreenState.STUDENT_DASHBOARD -> ScreenState.HOME
      ScreenState.COURSE_INTRO -> ScreenState.HOME
      ScreenState.WHAT_STUDENTS_LEARN -> ScreenState.HOME
      ScreenState.ETHICS_SAFETY -> ScreenState.HOME
      ScreenState.HOME -> ScreenState.HOME
    }
  }

  Scaffold(
    topBar = {
      if (currentScreen != ScreenState.ADMIN_DASHBOARD) {
        AppTopBar(
          currentLanguage = currentLanguage,
          currentUser = currentUser,
          isAdminLoggedIn = isAdminLoggedIn,
          canNavigateBack = currentScreen != ScreenState.HOME,
          onNavigateBack = {
            currentScreen = when (currentScreen) {
              ScreenState.LESSON_PLAYER -> ScreenState.STUDENT_DASHBOARD
              ScreenState.STUDENT_DASHBOARD -> ScreenState.HOME
              else -> ScreenState.HOME
            }
          },
          onToggleLanguage = { lang ->
            repository.setLanguage(lang)
          },
          onOpenProfileOrDashboard = {
            if (isAdminLoggedIn) {
              currentScreen = ScreenState.ADMIN_DASHBOARD
            } else {
              currentScreen = ScreenState.STUDENT_DASHBOARD
            }
          },
          onTripleTapLogo = {
            // Hidden Admin Entry triggered by 3 taps on logo
            showAdminAccessDialog = true
          }
        )
      }
    },
    containerColor = DeepObsidian,
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(DeepObsidian)
    ) {
      when (currentScreen) {
        ScreenState.HOME -> {
          HomeScreen(
            currentLanguage = currentLanguage,
            currentUser = currentUser,
            courses = courses,
            onJoinClassClick = { currentScreen = ScreenState.REGISTRATION },
            onStudentLoginClick = { currentScreen = ScreenState.STUDENT_DASHBOARD },
            onOpenCourseIntro = { currentScreen = ScreenState.COURSE_INTRO },
            onOpenWhatStudentsLearn = { currentScreen = ScreenState.WHAT_STUDENTS_LEARN },
            onOpenEthicsSafety = { currentScreen = ScreenState.ETHICS_SAFETY },
            onSelectCourse = { course ->
              selectedCourseForPlayer = course
              currentScreen = ScreenState.LESSON_PLAYER
            },
            onTripleTapLogo = {
              showAdminAccessDialog = true
            }
          )
        }

        ScreenState.COURSE_INTRO -> {
          CourseIntroScreen(currentLanguage = currentLanguage)
        }

        ScreenState.WHAT_STUDENTS_LEARN -> {
          WhatStudentsLearnScreen(currentLanguage = currentLanguage)
        }

        ScreenState.ETHICS_SAFETY -> {
          EthicsSafetyScreen(currentLanguage = currentLanguage)
        }

        ScreenState.REGISTRATION -> {
          RegistrationScreen(
            currentLanguage = currentLanguage,
            courses = courses,
            onRegistrationSuccess = { newUser ->
              currentScreen = ScreenState.STUDENT_DASHBOARD
            },
            onCancel = {
              currentScreen = ScreenState.HOME
            }
          )
        }

        ScreenState.STUDENT_DASHBOARD -> {
          StudentDashboardScreen(
            currentLanguage = currentLanguage,
            currentUser = currentUser,
            courses = courses,
            onOpenCourse = { course ->
              selectedCourseForPlayer = course
              currentScreen = ScreenState.LESSON_PLAYER
            },
            onJoinAnotherCourse = {
              currentScreen = ScreenState.REGISTRATION
            },
            onLogout = {
              repository.logoutStudent()
            },
            onSwitchStudent = { student ->
              repository.setCurrentUser(student)
            }
          )
        }

        ScreenState.LESSON_PLAYER -> {
          selectedCourseForPlayer?.let { course ->
            LessonPlayerScreen(
              course = course,
              currentUser = currentUser,
              currentLanguage = currentLanguage,
              onBackToDashboard = {
                currentScreen = ScreenState.STUDENT_DASHBOARD
              }
            )
          } ?: run {
            currentScreen = ScreenState.HOME
          }
        }

        ScreenState.ADMIN_DASHBOARD -> {
          AdminDashboardScreen(
            currentLanguage = currentLanguage,
            onExitAdmin = {
              currentScreen = ScreenState.HOME
            }
          )
        }
      }

      // Dialog 1: Language selection on first visit
      if (showFirstVisitLanguageDialog) {
        LanguageFirstVisitDialog(
          onSelectLanguage = { lang ->
            repository.setLanguage(lang)
            showFirstVisitLanguageDialog = false
          }
        )
      }

      // Dialog 2: Hidden Admin Entry triggered by 3 taps on logo
      if (showAdminAccessDialog) {
        AdminAccessDialog(
          onDismiss = { showAdminAccessDialog = false },
          onAdminAuthenticated = {
            showAdminAccessDialog = false
            currentScreen = ScreenState.ADMIN_DASHBOARD
          }
        )
      }
    }
  }
}
