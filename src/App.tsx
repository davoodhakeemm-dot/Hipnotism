import React, { useState } from 'react';
import { useHypnotismStore } from './services/store';
import { Navbar } from './components/Navbar';
import { Footer } from './components/Footer';
import { AdminAccessModal } from './components/AdminAccessModal';
import { HomePage } from './pages/HomePage';
import { CoursesPage } from './pages/CoursesPage';
import { CourseIntroPage } from './pages/CourseIntroPage';
import { WhatStudentsLearnPage } from './pages/WhatStudentsLearnPage';
import { EthicsSafetyPage } from './pages/EthicsSafetyPage';
import { RegistrationPage } from './pages/RegistrationPage';
import { StudentDashboardPage } from './pages/StudentDashboardPage';
import { LessonPlayerPage } from './pages/LessonPlayerPage';
import { AdminDashboardPage } from './pages/AdminDashboardPage';

export const App: React.FC = () => {
  const store = useHypnotismStore();
  const [isAdminModalOpen, setIsAdminModalOpen] = useState(false);

  const renderCurrentPage = () => {
    switch (store.currentRoute) {
      case 'home':
        return <HomePage store={store} />;
      case 'courses':
        return <CoursesPage store={store} />;
      case 'course-intro':
        return <CourseIntroPage store={store} />;
      case 'what-students-learn':
        return <WhatStudentsLearnPage store={store} />;
      case 'ethics-safety':
        return <EthicsSafetyPage store={store} />;
      case 'register':
        return <RegistrationPage store={store} />;
      case 'student-dashboard':
        return <StudentDashboardPage store={store} />;
      case 'lesson-player':
        return <LessonPlayerPage store={store} />;
      case 'admin-dashboard':
        return store.isAdminAuthenticated ? (
          <AdminDashboardPage store={store} />
        ) : (
          <div className="text-center py-24 space-y-4">
            <h2 className="text-xl font-bold text-white">Administrator Authentication Required</h2>
            <p className="text-xs text-slate-400">Please unlock admin space using the passkey modal.</p>
            <button
              onClick={() => setIsAdminModalOpen(true)}
              className="px-6 py-2 rounded-xl bg-cyan-400 text-slate-950 font-bold text-xs"
            >
              Enter Admin Passkey
            </button>
          </div>
        );
      default:
        return <HomePage store={store} />;
    }
  };

  return (
    <div className="min-h-screen flex flex-col bg-[#080B12] text-slate-100 selection:bg-cyan-500 selection:text-black">
      <Navbar 
        store={store} 
        onOpenAdminDialog={() => setIsAdminModalOpen(true)} 
      />

      <main className="flex-1">
        {renderCurrentPage()}
      </main>

      <Footer store={store} />

      <AdminAccessModal
        isOpen={isAdminModalOpen}
        onClose={() => setIsAdminModalOpen(false)}
        store={store}
      />
    </div>
  );
};

export default App;
