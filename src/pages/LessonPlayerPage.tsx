import React, { useState } from 'react';
import { HypnotismStore, OWNER_ADMIN_EMAIL } from '../services/store';
import { 
  Play, 
  CheckCircle, 
  ArrowLeft, 
  ArrowRight, 
  FileText, 
  Download, 
  ShieldAlert, 
  Lock, 
  Sparkles,
  Globe,
  Clock
} from 'lucide-react';

interface LessonPlayerPageProps {
  store: HypnotismStore;
}

export const LessonPlayerPage: React.FC<LessonPlayerPageProps> = ({ store }) => {
  const { 
    language, 
    toggleLanguage,
    courses, 
    lessons, 
    activeCourseId, 
    activeLessonId, 
    setActiveLessonId, 
    currentUser, 
    navigate, 
    checkCourseAccess,
    toggleLessonCompletion 
  } = store;

  const isEnglish = language === 'ENGLISH';
  const course = courses.find(c => c.id === activeCourseId) || courses[0];
  const courseLessons = lessons[course?.id || 'hypno-ml'] || [];
  const currentLessonIndex = courseLessons.findIndex(l => l.id === activeLessonId);
  const currentLesson = currentLessonIndex !== -1 ? courseLessons[currentLessonIndex] : courseLessons[0];

  const access = checkCourseAccess(course.id, currentUser);

  // If unauthorized or not logged in
  if (!currentUser || !access.hasAccess) {
    return (
      <div className="max-w-2xl mx-auto px-4 py-20 text-center">
        <div className="bg-[#0D1322] border border-amber-500/30 rounded-3xl p-8 sm:p-12 shadow-2xl space-y-6">
          <div className="w-16 h-16 mx-auto rounded-full bg-amber-500/10 border-2 border-amber-400 flex items-center justify-center text-amber-400">
            <Lock className="w-8 h-8" />
          </div>
          <div className="space-y-2">
            <h2 className="text-2xl font-bold text-white">
              {isEnglish ? 'Private Course Content Locked' : 'ക്ലാസ്സുകൾക്ക് പ്രത്യേക അനുമതി ആവശ്യമാണ്'}
            </h2>
            <p className="text-xs sm:text-sm text-slate-300 leading-relaxed max-w-lg mx-auto">
              {access.reason}
            </p>
          </div>

          <div className="pt-4 flex flex-wrap items-center justify-center gap-4">
            <button
              onClick={() => navigate('student-dashboard')}
              className="px-6 py-2.5 rounded-xl bg-cyan-400 hover:bg-cyan-300 text-slate-950 font-bold text-xs shadow-md transition-colors"
            >
              {isEnglish ? 'Go to Student Portal' : 'വിദ്യാർത്ഥി പോർട്ടൽ കാണുക'}
            </button>
            <button
              onClick={() => navigate('register', course.id)}
              className="px-6 py-2.5 rounded-xl bg-[#131B2E] border border-slate-700 text-slate-200 hover:text-white font-semibold text-xs transition-colors"
            >
              {isEnglish ? 'Apply for Course' : 'കോഴ്സിനായി അപേക്ഷിക്കുക'}
            </button>
          </div>
        </div>
      </div>
    );
  }

  const prevLesson = currentLessonIndex > 0 ? courseLessons[currentLessonIndex - 1] : null;
  const nextLesson = currentLessonIndex < courseLessons.length - 1 ? courseLessons[currentLessonIndex + 1] : null;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-8">
      
      {/* Top Bar */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 pb-4 border-b border-slate-800">
        <button
          onClick={() => navigate('student-dashboard')}
          className="flex items-center gap-2 text-xs font-semibold text-slate-400 hover:text-cyan-400 transition-colors"
        >
          <ArrowLeft className="w-4 h-4" />
          <span>{isEnglish ? 'Back to Student Portal' : 'വിദ്യാർത്ഥി പോർട്ടലിലേക്ക് മടങ്ങുക'}</span>
        </button>

        <div className="flex items-center gap-3">
          <span className="text-xs text-slate-400 font-medium">
            {isEnglish ? course.titleEn : course.titleMl}
          </span>
          <button
            onClick={toggleLanguage}
            className="flex items-center gap-1.5 px-3 py-1 rounded-full bg-[#131B2E] border border-[#233252] text-xs font-semibold text-cyan-300 hover:border-cyan-500/50 transition-colors"
          >
            <Globe className="w-3.5 h-3.5" />
            <span>{isEnglish ? 'മലയാളം' : 'English'}</span>
          </button>
        </div>
      </div>

      {/* Main Player Area: Video on left (8 cols), Playlist on right (4 cols) */}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-8">
        
        {/* Left Video Player & Notes (8 cols) */}
        <div className="lg:col-span-8 space-y-6">
          
          {/* Responsive 16:9 Video Container with Real-Time Anti-Piracy Watermark */}
          <div className="relative aspect-video bg-black rounded-2xl overflow-hidden border border-slate-800 shadow-2xl">
            
            {/* Dynamic Anti-Piracy Watermark Overlay */}
            <div className="absolute inset-0 z-20 pointer-events-none flex flex-col justify-between p-4 sm:p-6 select-none opacity-20 hover:opacity-30 transition-opacity">
              <div className="flex items-center justify-between text-[11px] font-mono tracking-widest text-cyan-400">
                <span>HYPNOTISM ACADEMY</span>
                <span>{currentUser.gmailAddress}</span>
              </div>
              <div className="self-center rotate-[-15deg] text-xs sm:text-sm font-mono tracking-widest text-slate-300">
                LICENSED TO: {currentUser.fullName.toUpperCase()} ({currentUser.gmailAddress})
              </div>
              <div className="flex items-center justify-between text-[11px] font-mono tracking-widest text-cyan-400">
                <span>DO NOT DISTRIBUTE</span>
                <span>ID: {currentUser.id}</span>
              </div>
            </div>

            {/* Video Element */}
            {currentLesson?.videoUrl ? (
              <video
                key={currentLesson.id}
                src={currentLesson.videoUrl}
                controls
                controlsList="nodownload"
                poster="/assets/aistudio/hero_banner.png"
                className="w-full h-full object-cover"
              >
                Your browser does not support the video tag.
              </video>
            ) : (
              <div className="w-full h-full flex flex-col items-center justify-center text-slate-500 space-y-2">
                <Play className="w-12 h-12" />
                <p className="text-xs">No video stream available for this lecture.</p>
              </div>
            )}

          </div>

          {/* Lesson Details & Actions Bar */}
          <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-8 space-y-6 shadow-xl">
            
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
              <div>
                <div className="flex items-center gap-2 text-xs font-bold text-cyan-400 uppercase tracking-wider mb-1">
                  <span>Lesson {currentLesson?.lessonNumber}</span>
                  <span>•</span>
                  <span className="flex items-center gap-1 text-slate-400">
                    <Clock className="w-3.5 h-3.5" />
                    {currentLesson?.duration}
                  </span>
                </div>
                <h1 className="text-xl sm:text-2xl font-bold text-white">
                  {isEnglish ? currentLesson?.titleEn : currentLesson?.titleMl}
                </h1>
              </div>

              {/* Mark Complete Toggle */}
              <button
                onClick={() => toggleLessonCompletion(course.id, currentLesson.id)}
                className={`px-4 py-2.5 rounded-xl font-bold text-xs flex items-center gap-2 transition-all ${
                  currentLesson?.isCompleted
                    ? 'bg-emerald-500/20 text-emerald-400 border border-emerald-500/40'
                    : 'bg-[#131B2E] text-slate-300 border border-[#233252] hover:border-cyan-500/40 hover:text-white'
                }`}
              >
                <CheckCircle className={`w-4 h-4 ${currentLesson?.isCompleted ? 'text-emerald-400' : 'text-slate-500'}`} />
                <span>
                  {currentLesson?.isCompleted
                    ? (isEnglish ? 'Completed' : 'പൂർത്തിയാക്കി')
                    : (isEnglish ? 'Mark as Completed' : 'പൂർത്തിയാക്കിയതായി അടയാളപ്പെടുത്തുക')}
                </span>
              </button>
            </div>

            {/* Description */}
            <div className="text-xs sm:text-sm text-slate-300 leading-relaxed py-2 border-t border-slate-800">
              {isEnglish ? currentLesson?.descriptionEn : currentLesson?.descriptionMl}
            </div>

            {/* Key Takeaways */}
            {((isEnglish ? currentLesson?.keyTakeawaysEn : currentLesson?.keyTakeawaysMl) || []).length > 0 && (
              <div className="space-y-3 pt-2">
                <h3 className="text-xs font-bold text-slate-300 uppercase tracking-wider flex items-center gap-1.5">
                  <Sparkles className="w-4 h-4 text-cyan-400" />
                  <span>{isEnglish ? 'Key Academic Takeaways' : 'പ്രധാന പഠന നേട്ടങ്ങൾ'}</span>
                </h3>

                <div className="space-y-2">
                  {(isEnglish ? currentLesson?.keyTakeawaysEn : currentLesson?.keyTakeawaysMl)?.map((item, idx) => (
                    <div key={idx} className="p-3 rounded-xl bg-[#080B12] border border-slate-800 text-xs text-slate-300 flex items-start gap-2.5">
                      <div className="w-1.5 h-1.5 rounded-full bg-cyan-400 flex-shrink-0 mt-1.5" />
                      <span>{item}</span>
                    </div>
                  ))}
                </div>
              </div>
            )}

            {/* PDF Study Material Download if available */}
            {currentLesson?.pdfAttachmentName && (
              <div className="p-4 rounded-2xl bg-indigo-950/30 border border-indigo-800/40 flex items-center justify-between gap-4">
                <div className="flex items-center gap-3">
                  <FileText className="w-6 h-6 text-indigo-400 flex-shrink-0" />
                  <div>
                    <div className="text-xs font-bold text-white">{currentLesson.pdfAttachmentName}</div>
                    <div className="text-[11px] text-slate-400">Official Educational Handout</div>
                  </div>
                </div>

                <a
                  href={currentLesson.pdfAttachmentUrl || '#'}
                  target="_blank"
                  rel="noreferrer"
                  className="px-3.5 py-1.5 rounded-xl bg-indigo-600 hover:bg-indigo-500 text-white font-semibold text-xs flex items-center gap-1.5 transition-colors"
                >
                  <Download className="w-3.5 h-3.5" />
                  <span>Download</span>
                </a>
              </div>
            )}

            {/* Prev / Next Navigation */}
            <div className="flex items-center justify-between pt-4 border-t border-slate-800">
              {prevLesson ? (
                <button
                  onClick={() => setActiveLessonId(prevLesson.id)}
                  className="flex items-center gap-2 px-4 py-2 rounded-xl bg-[#131B2E] border border-[#233252] text-xs font-semibold text-slate-300 hover:text-white transition-colors"
                >
                  <ArrowLeft className="w-4 h-4" />
                  <span>{isEnglish ? 'Previous Lesson' : 'മുമ്പത്തെ പാഠം'}</span>
                </button>
              ) : <div />}

              {nextLesson ? (
                <button
                  onClick={() => setActiveLessonId(nextLesson.id)}
                  className="flex items-center gap-2 px-4 py-2 rounded-xl bg-cyan-400 hover:bg-cyan-300 text-slate-950 text-xs font-bold transition-colors"
                >
                  <span>{isEnglish ? 'Next Lesson' : 'അടുത്ത പാഠം'}</span>
                  <ArrowRight className="w-4 h-4" />
                </button>
              ) : <div />}
            </div>

          </div>

        </div>

        {/* Right Sidebar: Playlist Curriculum (4 cols) */}
        <div className="lg:col-span-4 space-y-4">
          <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 shadow-xl space-y-4">
            
            <div className="flex items-center justify-between pb-3 border-b border-slate-800">
              <h3 className="text-sm font-bold text-white">
                {isEnglish ? 'Curriculum Playlist' : 'കോഴ്സ് പാഠങ്ങൾ'}
              </h3>
              <span className="text-xs text-cyan-400 font-semibold">
                {courseLessons.length} {isEnglish ? 'Lectures' : 'ക്ലാസ്സുകൾ'}
              </span>
            </div>

            <div className="space-y-2 max-h-[550px] overflow-y-auto pr-1">
              {courseLessons.map((lesson, idx) => {
                const isCurrent = lesson.id === currentLesson?.id;

                return (
                  <button
                    key={lesson.id}
                    onClick={() => setActiveLessonId(lesson.id)}
                    className={`w-full p-3.5 rounded-2xl text-left border transition-all flex items-start gap-3 ${
                      isCurrent
                        ? 'bg-[#131B2E] border-cyan-400 shadow-md'
                        : 'bg-[#080B12] border-slate-800 hover:border-slate-700'
                    }`}
                  >
                    <div
                      className={`w-7 h-7 rounded-lg flex items-center justify-center flex-shrink-0 text-xs font-bold ${
                        lesson.isCompleted
                          ? 'bg-emerald-500/20 text-emerald-400'
                          : isCurrent
                          ? 'bg-cyan-400 text-slate-950'
                          : 'bg-slate-800 text-slate-400'
                      }`}
                    >
                      {lesson.isCompleted ? <CheckCircle className="w-4 h-4" /> : idx + 1}
                    </div>

                    <div className="flex-1 truncate">
                      <div className={`text-xs font-semibold truncate ${isCurrent ? 'text-cyan-300' : 'text-slate-200'}`}>
                        {isEnglish ? lesson.titleEn : lesson.titleMl}
                      </div>
                      <div className="text-[11px] text-slate-500 mt-0.5">{lesson.duration}</div>
                    </div>
                  </button>
                );
              })}
            </div>

          </div>
        </div>

      </div>

    </div>
  );
};
