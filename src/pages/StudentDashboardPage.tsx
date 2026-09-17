import React, { useState } from 'react';
import { HypnotismStore } from '../services/store';
import { 
  User, 
  Mail, 
  CheckCircle, 
  Clock, 
  AlertCircle, 
  LogOut, 
  BookOpen, 
  ShieldCheck, 
  Play, 
  Lock, 
  ArrowRight,
  GraduationCap
} from 'lucide-react';

interface StudentDashboardPageProps {
  store: HypnotismStore;
}

export const StudentDashboardPage: React.FC<StudentDashboardPageProps> = ({ store }) => {
  const { 
    language, 
    currentUser, 
    courses, 
    lessons, 
    loginStudentByEmail, 
    logoutStudent, 
    navigate, 
    checkCourseAccess,
    registrations 
  } = store;

  const isEnglish = language === 'ENGLISH';
  const [emailInput, setEmailInput] = useState('');
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    if (!emailInput.trim() || !emailInput.includes('@')) {
      setErrorMessage(isEnglish ? 'Please enter a valid Gmail address.' : 'സാധുവായ ഒരു ജിമെയിൽ വിലാസം നൽകുക.');
      return;
    }

    const result = loginStudentByEmail(emailInput);
    if (!result.success) {
      setErrorMessage(result.message);
    } else {
      setErrorMessage(null);
      setEmailInput('');
    }
  };

  // If not logged in, show login card
  if (!currentUser) {
    return (
      <div className="max-w-md mx-auto px-4 py-16">
        <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-8 shadow-2xl text-center space-y-6">
          
          <div className="w-16 h-16 mx-auto rounded-full bg-cyan-500/10 border-2 border-cyan-400 flex items-center justify-center text-cyan-400">
            <User className="w-8 h-8" />
          </div>

          <div className="space-y-1">
            <h2 className="text-2xl font-bold text-white">
              {isEnglish ? 'Student Portal Sign In' : 'വിദ്യാർത്ഥി പോർട്ടൽ ലോഗിൻ'}
            </h2>
            <p className="text-xs text-slate-400">
              {isEnglish ? 'Sign in with your registered Gmail account' : 'രജിസ്റ്റർ ചെയ്ത ജിമെയിൽ ഉപയോഗിച്ച് ലോഗിൻ ചെയ്യുക'}
            </p>
          </div>

          {errorMessage && (
            <div className="p-3.5 rounded-xl bg-red-950/40 border border-red-800 text-red-300 text-xs text-left flex items-start gap-2.5">
              <AlertCircle className="w-4 h-4 flex-shrink-0 mt-0.5" />
              <span>{errorMessage}</span>
            </div>
          )}

          <form onSubmit={handleLogin} className="space-y-4 text-left">
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-slate-300 flex items-center gap-1.5">
                <Mail className="w-3.5 h-3.5 text-cyan-400" />
                <span>{isEnglish ? 'Registered Gmail' : 'ജിമെയിൽ വിലാസം'}</span>
              </label>
              <input
                type="email"
                value={emailInput}
                onChange={(e) => {
                  setEmailInput(e.target.value);
                  setErrorMessage(null);
                }}
                placeholder="your.email@gmail.com"
                className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] focus:border-cyan-400 rounded-xl text-sm text-white placeholder-slate-500 outline-none transition-colors"
                required
              />
            </div>

            <button
              type="submit"
              className="w-full py-3 rounded-xl bg-gradient-to-r from-cyan-400 to-cyan-500 hover:from-cyan-300 hover:to-cyan-400 text-slate-950 font-bold text-sm shadow-md shadow-cyan-500/20 transition-all flex items-center justify-center gap-2"
            >
              <span>{isEnglish ? 'Sign In with Gmail' : 'ജിമെയിൽ വഴി ലോഗിൻ ചെയ്യുക'}</span>
              <ArrowRight className="w-4 h-4" />
            </button>
          </form>

          {/* Quick select if registrations exist */}
          {registrations.length > 0 && (
            <div className="pt-2 border-t border-slate-800 text-left">
              <div className="text-[11px] text-slate-500 font-semibold mb-2 uppercase tracking-wider">
                {isEnglish ? 'Registered Students:' : 'രജിസ്റ്റർ ചെയ്ത വിദ്യാർത്ഥികൾ:'}
              </div>
              <div className="space-y-1.5 max-h-36 overflow-y-auto">
                {registrations.map(r => (
                  <button
                    key={r.id}
                    onClick={() => loginStudentByEmail(r.gmailAddress)}
                    className="w-full p-2 rounded-lg bg-[#080B12] hover:bg-slate-800 border border-slate-800 text-xs text-slate-300 flex items-center justify-between"
                  >
                    <span className="font-medium truncate">{r.fullName}</span>
                    <span className="text-[10px] text-cyan-400">{r.gmailAddress}</span>
                  </button>
                ))}
              </div>
            </div>
          )}

          <div className="pt-4 border-t border-slate-800">
            <p className="text-xs text-slate-400">
              {isEnglish ? "Haven't applied for a course yet?" : 'ഇതുവരെ കോഴ്സിൽ ചേർന്നിട്ടില്ലേ?'}
            </p>
            <button
              onClick={() => navigate('register')}
              className="mt-2 text-xs font-bold text-cyan-400 hover:underline"
            >
              {isEnglish ? 'Submit Student Application →' : 'ഇവിടെ അപേക്ഷിക്കുക →'}
            </button>
          </div>

        </div>
      </div>
    );
  }

  // If user is logged in
  const enrolledCourse = courses.find(c => c.id === currentUser.selectedCourseId) || courses[0];
  const courseLessons = lessons[enrolledCourse?.id || 'hypno-ml'] || [];
  const completedCount = courseLessons.filter(l => l.isCompleted).length;
  const progressPercent = courseLessons.length > 0 ? Math.round((completedCount / courseLessons.length) * 100) : 0;
  const access = checkCourseAccess(enrolledCourse.id, currentUser);

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      
      {/* Student Top Profile Header */}
      <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-8 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-6 shadow-xl">
        <div className="flex items-center gap-4">
          <div className="w-16 h-16 rounded-2xl bg-indigo-600/20 border-2 border-indigo-500/40 flex items-center justify-center text-indigo-300 font-bold text-2xl">
            {currentUser.fullName.charAt(0)}
          </div>
          <div>
            <div className="flex items-center gap-3">
              <h1 className="text-xl sm:text-2xl font-bold text-white">
                {currentUser.fullName}
              </h1>
              <span
                className={`px-3 py-0.5 rounded-full text-xs font-bold ${
                  currentUser.status === 'APPROVED'
                    ? 'bg-emerald-950/80 text-emerald-300 border border-emerald-700'
                    : currentUser.status === 'PENDING'
                    ? 'bg-amber-950/80 text-amber-300 border border-amber-700'
                    : 'bg-red-950/80 text-red-300 border border-red-700'
                }`}
              >
                {currentUser.status}
              </span>
            </div>
            <p className="text-xs text-slate-400 mt-1 flex items-center gap-2">
              <Mail className="w-3.5 h-3.5 text-cyan-400" />
              <span>{currentUser.gmailAddress}</span>
              <span className="text-slate-600">•</span>
              <span>{isEnglish ? `Enrolled: ${currentUser.registrationDate}` : `ചേർന്നത്: ${currentUser.registrationDate}`}</span>
            </p>
          </div>
        </div>

        <button
          onClick={logoutStudent}
          className="flex items-center gap-2 px-4 py-2 rounded-xl bg-slate-800/80 hover:bg-slate-700 text-slate-300 hover:text-white border border-slate-700 text-xs font-semibold transition-colors"
        >
          <LogOut className="w-4 h-4 text-red-400" />
          <span>{isEnglish ? 'Log Out' : 'ലോഗൗട്ട്'}</span>
        </button>
      </div>

      {/* Main Course Progress Dashboard */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Left 2 Columns: Enrolled Course & Lessons */}
        <div className="lg:col-span-2 space-y-6">
          <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-8 space-y-6 shadow-xl">
            
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-6 border-b border-slate-800">
              <div>
                <span className="text-xs font-bold text-cyan-400 uppercase tracking-wider">
                  {isEnglish ? 'Enrolled Program' : 'പഠിക്കുന്ന കോഴ്സ്'}
                </span>
                <h2 className="text-xl font-bold text-white mt-1">
                  {isEnglish ? enrolledCourse.titleEn : enrolledCourse.titleMl}
                </h2>
                <p className="text-xs text-slate-400 mt-0.5">
                  {isEnglish ? enrolledCourse.subtitleEn : enrolledCourse.subtitleMl}
                </p>
              </div>

              {access.hasAccess ? (
                <button
                  onClick={() => navigate('lesson-player', enrolledCourse.id, courseLessons[0]?.id)}
                  className="px-5 py-2.5 rounded-xl bg-cyan-400 hover:bg-cyan-300 text-slate-950 font-bold text-xs shadow-md shadow-cyan-400/20 transition-all flex items-center gap-2"
                >
                  <Play className="w-4 h-4 fill-current" />
                  <span>{isEnglish ? 'Continue Learning' : 'പഠനം തുടരുക'}</span>
                </button>
              ) : (
                <div className="px-4 py-2 rounded-xl bg-amber-950/30 border border-amber-800/40 text-amber-300 text-xs flex items-center gap-2">
                  <Lock className="w-4 h-4" />
                  <span>{isEnglish ? 'Pending Approval' : 'അംഗീകാരം ലഭിക്കാനുണ്ട്'}</span>
                </div>
              )}
            </div>

            {/* Progress Bar */}
            <div className="space-y-2">
              <div className="flex items-center justify-between text-xs font-semibold">
                <span className="text-slate-300">{isEnglish ? 'Course Progress' : 'പഠന പുരോഗതി'}</span>
                <span className="text-cyan-400">{completedCount} of {courseLessons.length} Completed ({progressPercent}%)</span>
              </div>
              <div className="w-full h-2.5 rounded-full bg-[#131B2E] overflow-hidden">
                <div 
                  className="h-full bg-gradient-to-r from-cyan-400 to-indigo-500 rounded-full transition-all duration-500" 
                  style={{ width: `${progressPercent}%` }}
                />
              </div>
            </div>

            {/* Lessons List */}
            <div className="space-y-3 pt-2">
              <div className="text-xs font-bold text-slate-300 uppercase tracking-wider">
                {isEnglish ? 'Video Lectures & Modules' : 'വീഡിയോ ക്ലാസ്സുകൾ'}
              </div>

              <div className="space-y-2.5">
                {courseLessons.map((lesson, idx) => {
                  const isLocked = !access.hasAccess;

                  return (
                    <div
                      key={lesson.id}
                      onClick={() => {
                        if (!isLocked) {
                          navigate('lesson-player', enrolledCourse.id, lesson.id);
                        }
                      }}
                      className={`p-4 rounded-2xl border transition-all flex items-center justify-between gap-4 ${
                        isLocked
                          ? 'bg-[#080B12]/60 border-slate-800/60 opacity-70 cursor-not-allowed'
                          : 'bg-[#080B12] hover:bg-slate-800/60 border-slate-800 hover:border-cyan-500/40 cursor-pointer shadow-md'
                      }`}
                    >
                      <div className="flex items-center gap-3.5 truncate">
                        <div
                          className={`w-9 h-9 rounded-xl flex items-center justify-center flex-shrink-0 text-xs font-bold ${
                            lesson.isCompleted
                              ? 'bg-emerald-500/20 text-emerald-400 border border-emerald-500/30'
                              : isLocked
                              ? 'bg-slate-800 text-slate-500'
                              : 'bg-indigo-600/20 text-indigo-300 border border-indigo-500/30'
                          }`}
                        >
                          {lesson.isCompleted ? <CheckCircle className="w-4 h-4" /> : idx + 1}
                        </div>

                        <div className="truncate">
                          <h4 className="text-xs sm:text-sm font-semibold text-slate-100 truncate">
                            {isEnglish ? lesson.titleEn : lesson.titleMl}
                          </h4>
                          <span className="text-[11px] text-slate-400">{lesson.duration}</span>
                        </div>
                      </div>

                      <div className="flex-shrink-0">
                        {isLocked ? (
                          <Lock className="w-4 h-4 text-slate-500" />
                        ) : (
                          <div className="flex items-center gap-1.5 text-xs text-cyan-400 font-semibold">
                            <span>{isEnglish ? 'Watch' : 'കാണുക'}</span>
                            <Play className="w-3.5 h-3.5 fill-current" />
                          </div>
                        )}
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>

          </div>
        </div>

        {/* Right Column: Status & Security Notices */}
        <div className="space-y-6">
          
          {/* Authorization Status Card */}
          <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 space-y-4 shadow-xl">
            <h3 className="text-sm font-bold text-white flex items-center gap-2">
              <ShieldCheck className="w-4 h-4 text-cyan-400" />
              <span>{isEnglish ? 'Enrollment Status' : 'രജിസ്ട്രേഷൻ അവസ്ഥ'}</span>
            </h3>

            {currentUser.status === 'APPROVED' ? (
              <div className="p-4 rounded-2xl bg-emerald-950/30 border border-emerald-800/40 space-y-2 text-xs text-emerald-300">
                <div className="font-bold flex items-center gap-1.5">
                  <CheckCircle className="w-4 h-4" />
                  <span>{isEnglish ? 'Verified & Authorized' : 'പരിശോധിച്ച് അംഗീകരിച്ചു'}</span>
                </div>
                <p className="text-[11px] text-slate-300 leading-relaxed">
                  {isEnglish
                    ? 'Your Gmail account is whitelisted for private video lectures and curriculum notes.'
                    : 'നിങ്ങളുടെ ഇമെയിലിന് ക്ലാസ്സുകൾ കാണാനുള്ള പൂർണ്ണ അനുമതി നൽകിയിട്ടുണ്ട്.'}
                </p>
              </div>
            ) : (
              <div className="p-4 rounded-2xl bg-amber-950/30 border border-amber-800/40 space-y-2 text-xs text-amber-300">
                <div className="font-bold flex items-center gap-1.5">
                  <Clock className="w-4 h-4" />
                  <span>{isEnglish ? 'Application Under Review' : 'അപേക്ഷ പരിശോധനയിൽ'}</span>
                </div>
                <p className="text-[11px] text-slate-300 leading-relaxed">
                  {isEnglish
                    ? 'Administration is validating your profile details. You will gain video access upon verification.'
                    : 'വിവരങ്ങൾ പരിശോധിച്ച ശേഷം ഉടൻ തന്നെ ക്ലാസ്സുകൾ ലഭ്യമാകുന്നതാണ്.'}
                </p>
              </div>
            )}

            <div className="text-[11px] text-slate-400 space-y-1 pt-2 border-t border-slate-800">
              <div><strong>Age:</strong> {currentUser.age} years</div>
              <div><strong>Phone:</strong> {currentUser.phoneNumber}</div>
              <div><strong>Location:</strong> {currentUser.address}</div>
            </div>
          </div>

          {/* Anti-Piracy Watermark Notice */}
          <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 space-y-3 shadow-xl">
            <h3 className="text-xs font-bold text-amber-400 uppercase tracking-wider">
              {isEnglish ? 'Security & Copyright' : 'സുരക്ഷാ മാനദണ്ഡങ്ങൾ'}
            </h3>
            <p className="text-xs text-slate-300 leading-relaxed">
              {isEnglish
                ? `Lectures are protected by real-time anti-piracy watermarking displaying your registered email (${currentUser.gmailAddress}). Screen recording or redistributing course video violates copyright laws.`
                : `കോഴ്സ് വീഡിയോകളിൽ നിങ്ങളുടെ ഇമെയിൽ (${currentUser.gmailAddress}) വാട്ടർമാർക്കായി കാണിക്കുന്നതാണ്. അനധികൃതമായി റെക്കോർഡ് ചെയ്യുകയോ പങ്കുവെക്കുകയോ ചെയ്യുന്നത് കുറ്റകരമാണ്.`}
            </p>
          </div>

        </div>

      </div>

    </div>
  );
};
