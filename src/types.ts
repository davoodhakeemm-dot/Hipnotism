export type AppLanguage = 'ENGLISH' | 'MALAYALAM';

export type RegistrationStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface StudentUser {
  id: string;
  fullName: string;
  age: number;
  phoneNumber: string;
  whatsAppNumber: string;
  profilePhotoUri: string | null;
  gmailAddress: string;
  verifiedGoogleEmail: string;
  address: string;
  selectedCourseId: string;
  registrationDate: string;
  status: RegistrationStatus;
  lastLogin: string;
}

export interface Lesson {
  id: string;
  courseId: string;
  lessonNumber: number;
  titleEn: string;
  titleMl: string;
  duration: string;
  videoUrl: string;
  descriptionEn: string;
  descriptionMl: string;
  keyTakeawaysEn: string[];
  keyTakeawaysMl: string[];
  pdfAttachmentUrl?: string;
  pdfAttachmentName?: string;
  isCompleted?: boolean;
}

export interface Course {
  id: string;
  titleEn: string;
  titleMl: string;
  subtitleEn: string;
  subtitleMl: string;
  languageType: string;
  authorizedEmails: string[];
  lessonsCount: number;
  price?: string;
  duration?: string;
  level?: string;
}

export type PageRoute = 
  | 'home'
  | 'courses'
  | 'course-intro'
  | 'what-students-learn'
  | 'ethics-safety'
  | 'register'
  | 'student-dashboard'
  | 'lesson-player'
  | 'admin-dashboard';
