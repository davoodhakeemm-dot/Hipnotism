import React, { useState } from 'react';
import { HypnotismStore } from '../services/store';
import { Course } from '../types';
import { GraduationCap, Clock, BookOpen, CheckCircle, Shield, ArrowRight } from 'lucide-react';

interface CoursesPageProps {
  store: HypnotismStore;
}

export const CoursesPage: React.FC<CoursesPageProps> = ({ store }) => {
  const { language, courses, lessons, navigate, currentUser, checkCourseAccess } = store;
  const isEnglish = language === 'ENGLISH';
  const [filterLang, setFilterLang] = useState<string>('ALL');

  const filteredCourses = courses.filter(c => {
    if (filterLang === 'ALL') return true;
    if (filterLang === 'MALAYALAM') return c.languageType.toLowerCase().includes('malayalam');
    if (filterLang === 'ENGLISH') return c.languageType.toLowerCase().includes('english');
    return true;
  });

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-10">
      
      {/* Header */}
      <div className="text-center max-w-3xl mx-auto space-y-3">
        <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-cyan-500/10 border border-cyan-500/30 text-xs font-bold text-cyan-400">
          <GraduationCap className="w-4 h-4" />
          <span>{isEnglish ? 'Curriculum & Certification' : 'കോഴ്സ് പാഠ്യപദ്ധതി'}</span>
        </div>
        <h1 className="text-3xl sm:text-4xl font-extrabold text-white">
          {isEnglish ? 'Academic Courses in Hypnotism' : 'ശാസ്ത്രീയ ഹിപ്നോട്ടിസം കോഴ്സുകൾ'}
        </h1>
        <p className="text-slate-400 text-sm leading-relaxed">
          {isEnglish
            ? 'Structured academic training covering cognitive psychology, suggestibility calibration, subconscious mechanics, and ethical guidelines.'
            : 'മനസ്സിന്റെ ആഴങ്ങളിലേക്ക് ശാസ്ത്രീയമായി സഞ്ചരിക്കാനും ഏകാഗ്രതയും ശ്രദ്ധയും വർദ്ധിപ്പിക്കാനും സഹായിക്കുന്ന പ്രായോഗിക പരിശീലന കോഴ്സുകൾ.'}
        </p>

        {/* Filter Pills */}
        <div className="flex items-center justify-center gap-2 pt-4">
          {['ALL', 'MALAYALAM', 'ENGLISH'].map((f) => (
            <button
              key={f}
              onClick={() => setFilterLang(f)}
              className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all ${
                filterLang === f
                  ? 'bg-cyan-500 text-slate-950 shadow-sm shadow-cyan-500/20'
                  : 'bg-[#131B2E] text-slate-400 border border-[#233252] hover:text-white'
              }`}
            >
              {f === 'ALL' ? (isEnglish ? 'All Courses' : 'എല്ലാ കോഴ്സുകളും') : f === 'MALAYALAM' ? 'മലയാളം' : 'English'}
            </button>
          ))}
        </div>
      </div>

      {/* Courses Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {filteredCourses.map((course) => {
          const courseLessons = lessons[course.id] || [];
          const accessInfo = checkCourseAccess(course.id, currentUser);

          return (
            <div
              key={course.id}
              className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 flex flex-col justify-between hover:border-cyan-500/40 transition-all shadow-xl"
            >
              <div>
                <div className="flex items-center justify-between gap-2 mb-4">
                  <span className="px-2.5 py-1 rounded-lg bg-indigo-950/70 border border-indigo-800/40 text-indigo-300 text-xs font-bold">
                    {course.languageType}
                  </span>
                  <span className="text-xs text-slate-400 flex items-center gap-1">
                    <Clock className="w-3.5 h-3.5 text-cyan-400" />
                    {course.duration || '8 Weeks'}
                  </span>
                </div>

                <h3 className="text-xl font-bold text-white mb-2 leading-snug">
                  {isEnglish ? course.titleEn : course.titleMl}
                </h3>

                <p className="text-xs text-slate-400 mb-6 leading-relaxed">
                  {isEnglish ? course.subtitleEn : course.subtitleMl}
                </p>

                {/* Lesson Outline Preview */}
                <div className="space-y-3 mb-6">
                  <div className="text-xs font-bold text-slate-300 uppercase tracking-wider flex items-center justify-between">
                    <span>{isEnglish ? 'Curriculum Outline' : 'പാഠ്യവിഷയങ്ങൾ'}</span>
                    <span className="text-cyan-400 font-semibold">{courseLessons.length} {isEnglish ? 'Lessons' : 'പാഠങ്ങൾ'}</span>
                  </div>

                  <div className="space-y-2">
                    {courseLessons.map((lesson, idx) => (
                      <div
                        key={lesson.id}
                        className="p-2.5 rounded-xl bg-[#080B12] border border-slate-800/80 flex items-start gap-2.5 text-xs text-slate-300"
                      >
                        <div className="w-5 h-5 rounded-full bg-slate-800 text-cyan-400 font-bold flex items-center justify-center flex-shrink-0 text-[10px]">
                          {idx + 1}
                        </div>
                        <div className="flex-1 truncate">
                          <span className="font-medium text-slate-200">
                            {isEnglish ? lesson.titleEn : lesson.titleMl}
                          </span>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

              </div>

              {/* Bottom Actions */}
              <div className="space-y-2 pt-4 border-t border-slate-800">
                {currentUser && accessInfo.hasAccess ? (
                  <button
                    onClick={() => navigate('lesson-player', course.id, courseLessons[0]?.id)}
                    className="w-full py-3 rounded-xl bg-gradient-to-r from-emerald-500 to-emerald-600 hover:from-emerald-400 hover:to-emerald-500 text-slate-950 font-bold text-xs tracking-wide shadow-md shadow-emerald-500/20 transition-all flex items-center justify-center gap-2"
                  >
                    <BookOpen className="w-4 h-4" />
                    <span>{isEnglish ? 'Start / Continue Lessons' : 'ക്ലാസ്സുകൾ ആരംഭിക്കുക'}</span>
                  </button>
                ) : (
                  <button
                    onClick={() => navigate('register', course.id)}
                    className="w-full py-3 rounded-xl bg-gradient-to-r from-cyan-400 to-cyan-500 hover:from-cyan-300 hover:to-cyan-400 text-slate-950 font-bold text-xs tracking-wide shadow-md shadow-cyan-500/20 transition-all flex items-center justify-center gap-2"
                  >
                    <span>{isEnglish ? 'Apply for Enrollment' : 'കോഴ്സിനായി അപേക്ഷിക്കുക'}</span>
                    <ArrowRight className="w-4 h-4" />
                  </button>
                )}
              </div>

            </div>
          );
        })}
      </div>

    </div>
  );
};
