import { useState, useEffect } from 'react';
import { AppLanguage, Course, Lesson, PageRoute, RegistrationStatus, StudentUser } from '../types';
import { INITIAL_COURSES, INITIAL_LESSONS } from '../data/coursesData';

const STORAGE_KEYS = {
  LANGUAGE: 'hypno_web_language',
  COURSES: 'hypno_web_courses',
  LESSONS: 'hypno_web_lessons',
  REGISTRATIONS: 'hypno_web_registrations',
  CURRENT_USER: 'hypno_web_current_user',
  ADMIN_AUTH: 'hypno_web_admin_auth'
};

export const OWNER_ADMIN_EMAIL = 'davoodhakeemm@gmail.com';
export const PRIVATE_ADMIN_KEY = '91870';

export function useHypnotismStore() {
  const [language, setLanguage] = useState<AppLanguage>(() => {
    return (localStorage.getItem(STORAGE_KEYS.LANGUAGE) as AppLanguage) || 'MALAYALAM';
  });

  const [currentRoute, setCurrentRoute] = useState<PageRoute>('home');
  const [activeCourseId, setActiveCourseId] = useState<string>('hypno-ml');
  const [activeLessonId, setActiveLessonId] = useState<string>('l-ml-01');

  const [courses, setCourses] = useState<Course[]>(() => {
    const saved = localStorage.getItem(STORAGE_KEYS.COURSES);
    if (saved) {
      try { return JSON.parse(saved); } catch (e) { /* fallback */ }
    }
    return INITIAL_COURSES;
  });

  const [lessons, setLessons] = useState<Record<string, Lesson[]>>(() => {
    const saved = localStorage.getItem(STORAGE_KEYS.LESSONS);
    if (saved) {
      try { return JSON.parse(saved); } catch (e) { /* fallback */ }
    }
    return INITIAL_LESSONS;
  });

  const [registrations, setRegistrations] = useState<StudentUser[]>(() => {
    const saved = localStorage.getItem(STORAGE_KEYS.REGISTRATIONS);
    if (saved) {
      try { return JSON.parse(saved); } catch (e) { /* fallback */ }
    }
    return [];
  });

  const [currentUser, setCurrentUser] = useState<StudentUser | null>(() => {
    const saved = localStorage.getItem(STORAGE_KEYS.CURRENT_USER);
    if (saved) {
      try { return JSON.parse(saved); } catch (e) { /* fallback */ }
    }
    return null;
  });

  const [isAdminAuthenticated, setIsAdminAuthenticated] = useState<boolean>(() => {
    return localStorage.getItem(STORAGE_KEYS.ADMIN_AUTH) === 'true';
  });

  // Sync with localStorage
  useEffect(() => {
    localStorage.setItem(STORAGE_KEYS.LANGUAGE, language);
  }, [language]);

  useEffect(() => {
    localStorage.setItem(STORAGE_KEYS.COURSES, JSON.stringify(courses));
  }, [courses]);

  useEffect(() => {
    localStorage.setItem(STORAGE_KEYS.LESSONS, JSON.stringify(lessons));
  }, [lessons]);

  useEffect(() => {
    localStorage.setItem(STORAGE_KEYS.REGISTRATIONS, JSON.stringify(registrations));
  }, [registrations]);

  useEffect(() => {
    if (currentUser) {
      localStorage.setItem(STORAGE_KEYS.CURRENT_USER, JSON.stringify(currentUser));
    } else {
      localStorage.removeItem(STORAGE_KEYS.CURRENT_USER);
    }
  }, [currentUser]);

  useEffect(() => {
    localStorage.setItem(STORAGE_KEYS.ADMIN_AUTH, String(isAdminAuthenticated));
  }, [isAdminAuthenticated]);

  // Navigation
  const navigate = (route: PageRoute, courseId?: string, lessonId?: string) => {
    if (courseId) setActiveCourseId(courseId);
    if (lessonId) setActiveLessonId(lessonId);
    setCurrentRoute(route);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const toggleLanguage = () => {
    setLanguage(prev => prev === 'ENGLISH' ? 'MALAYALAM' : 'ENGLISH');
  };

  // Student Registration
  const registerStudent = (data: {
    fullName: string;
    age: number;
    phoneNumber: string;
    whatsAppNumber: string;
    gmailAddress: string;
    address: string;
    selectedCourseId: string;
  }): { success: boolean; message: string } => {
    const normalizedEmail = data.gmailAddress.trim().toLowerCase();
    
    // Check if already registered
    const exists = registrations.some(r => r.gmailAddress.toLowerCase() === normalizedEmail);
    if (exists) {
      return {
        success: false,
        message: language === 'ENGLISH' 
          ? 'This Gmail address is already registered. Please login through Student Portal.' 
          : 'ഈ ജിമെയിൽ വിലാസം ഇതിനകം രജിസ്റ്റർ ചെയ്തിട്ടുണ്ട്. ദയവായി സ്റ്റുഡന്റ് പോർട്ടൽ വഴി ലോഗിൻ ചെയ്യുക.'
      };
    }

    const newStudent: StudentUser = {
      id: `stud-${Date.now()}`,
      fullName: data.fullName.trim(),
      age: data.age,
      phoneNumber: data.phoneNumber.trim(),
      whatsAppNumber: data.whatsAppNumber.trim(),
      profilePhotoUri: null,
      gmailAddress: normalizedEmail,
      verifiedGoogleEmail: normalizedEmail,
      address: data.address.trim(),
      selectedCourseId: data.selectedCourseId,
      registrationDate: new Date().toLocaleDateString('en-GB', { day: '2-digit', month: 'short', year: 'numeric' }),
      status: 'PENDING',
      lastLogin: 'Never'
    };

    setRegistrations(prev => [newStudent, ...prev]);
    return {
      success: true,
      message: language === 'ENGLISH'
        ? 'Application submitted successfully! Administrator review is pending.'
        : 'അപേക്ഷ വിജയകരമായി സമർപ്പിച്ചു! അഡ്മിനിസ്ട്രേറ്ററുടെ അംഗീകാരത്തിനായി കാത്തിരിക്കുന്നു.'
    };
  };

  // Student Login
  const loginStudentByEmail = (email: string): { success: boolean; message: string; student?: StudentUser } => {
    const normalized = email.trim().toLowerCase();
    const student = registrations.find(r => 
      r.gmailAddress.toLowerCase() === normalized || 
      r.verifiedGoogleEmail.toLowerCase() === normalized
    );

    if (!student) {
      return {
        success: false,
        message: language === 'ENGLISH'
          ? `No student registration found for '${email}'. Please apply to join class.`
          : `'${email}' എന്ന ഇമെയിലിൽ രജിസ്ട്രേഷൻ കണ്ടെത്തിയില്ല. ദയവായി ക്ലാസ്സിൽ ചേരാൻ അപേക്ഷിക്കുക.`
      };
    }

    const updatedStudent: StudentUser = {
      ...student,
      lastLogin: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) + ', Today'
    };

    setCurrentUser(updatedStudent);
    setRegistrations(prev => prev.map(s => s.id === student.id ? updatedStudent : s));

    return {
      success: true,
      message: language === 'ENGLISH' ? 'Logged in successfully!' : 'വിജയകരമായി ലോഗിൻ ചെയ്തു!',
      student: updatedStudent
    };
  };

  const logoutStudent = () => {
    setCurrentUser(null);
  };

  // Course Access Check
  const checkCourseAccess = (courseId: string, student: StudentUser | null): { hasAccess: boolean; reason: string } => {
    if (!student) {
      return {
        hasAccess: false,
        reason: language === 'ENGLISH' ? 'Please log in with your verified Google account.' : 'ദയവായി നിങ്ങളുടെ ജിമെയിൽ അക്കൗണ്ട് ഉപയോഗിച്ച് ലോഗിൻ ചെയ്യുക.'
      };
    }

    if (student.status !== 'APPROVED') {
      return {
        hasAccess: false,
        reason: language === 'ENGLISH' ? 'Your registration is currently pending admin approval.' : 'നിങ്ങളുടെ രജിസ്ട്രേഷൻ അഡ്മിൻ പരിശോധനയിലാണ്. ഉടൻ അംഗീകാരം ലഭിക്കും.'
      };
    }

    const course = courses.find(c => c.id === courseId);
    if (!course) {
      return { hasAccess: false, reason: 'Course not found' };
    }

    const email = student.gmailAddress.toLowerCase();
    const isAuthorized = course.authorizedEmails.some(e => e.toLowerCase() === email) || email === OWNER_ADMIN_EMAIL.toLowerCase();

    if (!isAuthorized) {
      return {
        hasAccess: false,
        reason: language === 'ENGLISH' 
          ? 'Your account is not authorized for this specific course. Contact administration.' 
          : 'ഈ കോഴ്സ് പഠിക്കുന്നതിനായി നിങ്ങളുടെ ഇമെയിലിന് പ്രത്യേക അനുമതി നൽകിയിട്ടില്ല. അഡ്മിനുമായി ബന്ധപ്പെടുക.'
      };
    }

    return { hasAccess: true, reason: 'Access granted' };
  };

  // Admin Actions
  const verifyAdminPasskey = (passkey: string): boolean => {
    if (passkey.trim() === PRIVATE_ADMIN_KEY) {
      setIsAdminAuthenticated(true);
      return true;
    }
    return false;
  };

  const logoutAdmin = () => {
    setIsAdminAuthenticated(false);
  };

  const updateRegistrationStatus = (studentId: string, newStatus: RegistrationStatus) => {
    setRegistrations(prev => prev.map(s => {
      if (s.id === studentId) {
        const updated = { ...s, status: newStatus };
        // If approved, automatically authorize student email in selected course
        if (newStatus === 'APPROVED') {
          addAuthorizedEmailToCourse(s.selectedCourseId, s.gmailAddress);
        }
        return updated;
      }
      return s;
    }));
  };

  const addAuthorizedEmailToCourse = (courseId: string, email: string) => {
    const cleanEmail = email.trim().toLowerCase();
    setCourses(prev => prev.map(course => {
      if (course.id === courseId) {
        if (!course.authorizedEmails.some(e => e.toLowerCase() === cleanEmail)) {
          return {
            ...course,
            authorizedEmails: [...course.authorizedEmails, cleanEmail]
          };
        }
      }
      return course;
    }));
  };

  const removeAuthorizedEmailFromCourse = (courseId: string, email: string) => {
    const cleanEmail = email.trim().toLowerCase();
    setCourses(prev => prev.map(course => {
      if (course.id === courseId) {
        return {
          ...course,
          authorizedEmails: course.authorizedEmails.filter(e => e.toLowerCase() !== cleanEmail)
        };
      }
      return course;
    }));
  };

  const toggleLessonCompletion = (courseId: string, lessonId: string) => {
    setLessons(prev => {
      const courseLessons = prev[courseId] || [];
      const updated = courseLessons.map(l => {
        if (l.id === lessonId) {
          return { ...l, isCompleted: !l.isCompleted };
        }
        return l;
      });
      return { ...prev, [courseId]: updated };
    });
  };

  const addNewLesson = (courseId: string, newLessonData: Omit<Lesson, 'id' | 'courseId' | 'lessonNumber'>) => {
    setLessons(prev => {
      const existing = prev[courseId] || [];
      const newLesson: Lesson = {
        id: `l-${Date.now()}`,
        courseId,
        lessonNumber: existing.length + 1,
        ...newLessonData
      };
      const updated = [...existing, newLesson];
      // update course lesson count
      setCourses(cList => cList.map(c => c.id === courseId ? { ...c, lessonsCount: updated.length } : c));
      return { ...prev, [courseId]: updated };
    });
  };

  return {
    language,
    toggleLanguage,
    setLanguage,
    currentRoute,
    navigate,
    courses,
    lessons,
    activeCourseId,
    activeLessonId,
    setActiveCourseId,
    setActiveLessonId,
    registrations,
    currentUser,
    setCurrentUser,
    registerStudent,
    loginStudentByEmail,
    logoutStudent,
    checkCourseAccess,
    isAdminAuthenticated,
    verifyAdminPasskey,
    logoutAdmin,
    updateRegistrationStatus,
    addAuthorizedEmailToCourse,
    removeAuthorizedEmailFromCourse,
    toggleLessonCompletion,
    addNewLesson
  };
}

export type HypnotismStore = ReturnType<typeof useHypnotismStore>;
