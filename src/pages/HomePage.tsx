import React from 'react';
import { HypnotismStore } from '../services/store';
import { 
  Sparkles, 
  Brain, 
  ShieldCheck, 
  GraduationCap, 
  ArrowRight, 
  BookOpen, 
  Lock, 
  CheckCircle2, 
  Flame, 
  HelpCircle 
} from 'lucide-react';

interface HomePageProps {
  store: HypnotismStore;
}

export const HomePage: React.FC<HomePageProps> = ({ store }) => {
  const { language, navigate, courses } = store;
  const isEnglish = language === 'ENGLISH';

  return (
    <div className="space-y-24 py-6 md:py-12">
      
      {/* 1. HERO SECTION */}
      <section className="relative overflow-hidden">
        {/* Glow background accent */}
        <div className="absolute -top-24 left-1/2 -translate-x-1/2 w-[600px] h-[350px] bg-gradient-to-tr from-cyan-500/15 to-indigo-600/15 blur-3xl -z-10 rounded-full pointer-events-none" />

        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 items-center">
            
            {/* Left Content (7 cols on lg) */}
            <div className="lg:col-span-7 space-y-6 text-center lg:text-left">
              
              <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-[#131B2E] border border-cyan-500/30 text-xs font-semibold text-cyan-300">
                <Sparkles className="w-3.5 h-3.5 text-cyan-400" />
                <span>{isEnglish ? 'Scientific Cognitive Psychology' : 'ശാസ്ത്രീയ മനോവിജ്ഞാനീയം'}</span>
              </div>

              <h1 className="text-3xl sm:text-4xl lg:text-5xl font-extrabold tracking-tight text-white leading-tight">
                {isEnglish ? (
                  <>
                    Demystifying the <span className="bg-gradient-to-r from-cyan-400 to-indigo-400 bg-clip-text text-transparent">Subconscious Mind</span> through Modern Science
                  </>
                ) : (
                  <>
                    അന്ധവിശ്വാസങ്ങളില്ലാതെ, ശാസ്ത്രീയമായി{' '}
                    <span className="bg-gradient-to-r from-cyan-400 to-indigo-400 bg-clip-text text-transparent">
                      ഹിപ്നോട്ടിസം
                    </span>{' '}
                    പഠിക്കാം
                  </>
                )}
              </h1>

              <p className="text-slate-300 text-sm sm:text-base leading-relaxed max-w-2xl mx-auto lg:mx-0">
                {isEnglish
                  ? 'A structured, evidence-based academic curriculum designed to teach the neurobiology of trance, subconscious suggestion, suggestibility testing, and strict ethical practice. Available in Malayalam and English.'
                  : 'മന്ത്രവാദവും അന്ധവിശ്വാസങ്ങളും മാറ്റിനിർത്തി, ശ്രദ്ധ, ഏകാഗ്രത, മനുഷ്യ മനസ്സിന്റെ പ്രവർത്തന രീതികൾ എന്നിവ ശാസ്ത്രീയമായി മനസ്സിലാക്കാനും സ്വയം വികസനത്തിന് ഉപയോഗിക്കാനുമുള്ള സമഗ്ര പഠന കോഴ്സ്.'}
              </p>

              {/* Action CTA Buttons */}
              <div className="flex flex-wrap items-center justify-center lg:justify-start gap-4 pt-2">
                <button
                  onClick={() => navigate('register')}
                  className="px-6 py-3.5 rounded-xl bg-gradient-to-r from-cyan-400 to-cyan-500 hover:from-cyan-300 hover:to-cyan-400 text-slate-950 font-bold text-sm sm:text-base shadow-lg shadow-cyan-500/20 transition-all flex items-center gap-2 group"
                >
                  <span>{isEnglish ? 'Apply for Admission' : 'അഡ്മിഷനായി അപേക്ഷിക്കുക'}</span>
                  <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
                </button>

                <button
                  onClick={() => navigate('courses')}
                  className="px-6 py-3.5 rounded-xl bg-[#131B2E] hover:bg-slate-800 text-slate-200 border border-[#233252] hover:border-cyan-500/40 font-semibold text-sm sm:text-base transition-all flex items-center gap-2"
                >
                  <BookOpen className="w-4 h-4 text-cyan-400" />
                  <span>{isEnglish ? 'Browse Curriculum' : 'പാഠ്യപദ്ധതി കാണുക'}</span>
                </button>
              </div>

              {/* Trust Indicators */}
              <div className="pt-4 flex flex-wrap items-center justify-center lg:justify-start gap-6 text-xs text-slate-400">
                <div className="flex items-center gap-2">
                  <CheckCircle2 className="w-4 h-4 text-cyan-400" />
                  <span>{isEnglish ? 'Bilingual Lectures (മലയാളം / English)' : 'മലയാളം & ഇംഗ്ലീഷ് ക്ലാസ്സുകൾ'}</span>
                </div>
                <div className="flex items-center gap-2">
                  <ShieldCheck className="w-4 h-4 text-emerald-400" />
                  <span>{isEnglish ? 'Mandatory Ethical Guidelines' : 'ധാർമ്മിക മാനദണ്ഡങ്ങൾ'}</span>
                </div>
              </div>

            </div>

            {/* Right Hero Badge / Interactive Preview (5 cols on lg) */}
            <div className="lg:col-span-5">
              <div className="relative mx-auto max-w-md bg-gradient-to-b from-[#131B2E] to-[#0D1322] border border-[#233252] rounded-3xl p-6 shadow-2xl shadow-cyan-500/5">
                
                <div className="flex items-center justify-between pb-4 border-b border-slate-800">
                  <div className="flex items-center gap-2">
                    <Brain className="w-5 h-5 text-cyan-400" />
                    <span className="text-xs font-bold text-slate-200 tracking-wider">ACADEMY PLATFORM</span>
                  </div>
                  <span className="text-[10px] uppercase tracking-wider font-bold px-2 py-0.5 rounded-full bg-cyan-500/10 text-cyan-400 border border-cyan-500/30">
                    Live Verified
                  </span>
                </div>

                <div className="py-6 space-y-4">
                  <div className="p-4 rounded-2xl bg-[#080B12] border border-slate-800">
                    <div className="text-xs text-slate-400 mb-1">{isEnglish ? 'Scientific Core Principle' : 'ശാസ്ത്രീയ യാഥാർത്ഥ്യം'}</div>
                    <div className="text-sm font-bold text-slate-100 leading-snug">
                      {isEnglish 
                        ? 'Hypnosis is focused awareness, not unconsciousness or sleep.' 
                        : 'ഹിപ്നോസിസ് എന്നത് ബോധം നഷ്ടപ്പെടലല്ല, മറിച്ച് അതീവ ഏകാഗ്രതയാണ്.'}
                    </div>
                  </div>

                  <div className="grid grid-cols-2 gap-3">
                    <div className="p-3.5 rounded-xl bg-slate-900/80 border border-slate-800">
                      <div className="text-cyan-400 font-extrabold text-xl">100%</div>
                      <div className="text-[11px] text-slate-400 mt-1">{isEnglish ? 'Scientific Basis' : 'ശാസ്ത്രീയം'}</div>
                    </div>
                    <div className="p-3.5 rounded-xl bg-slate-900/80 border border-slate-800">
                      <div className="text-amber-400 font-extrabold text-xl">0%</div>
                      <div className="text-[11px] text-slate-400 mt-1">{isEnglish ? 'Magic / Mysticism' : 'അന്ധവിശ്വാസം'}</div>
                    </div>
                  </div>

                  <div className="p-3.5 rounded-xl bg-emerald-950/20 border border-emerald-800/40 text-xs text-emerald-300 flex items-center gap-2.5">
                    <ShieldCheck className="w-4 h-4 flex-shrink-0" />
                    <span>{isEnglish ? 'Google Verified Student Enrollment' : 'സുരക്ഷിതമായ വിദ്യാർത്ഥി പ്രവേശനം'}</span>
                  </div>
                </div>

                <button
                  onClick={() => navigate('student-dashboard')}
                  className="w-full py-3 rounded-xl bg-indigo-600 hover:bg-indigo-500 text-white font-semibold text-xs tracking-wider uppercase transition-all shadow-md shadow-indigo-600/20"
                >
                  {isEnglish ? 'Open Student Portal' : 'വിദ്യാർത്ഥി പോർട്ടൽ തുറക്കുക'}
                </button>

              </div>
            </div>

          </div>
        </div>
      </section>

      {/* 2. THREE DOMAIN DISTINCTION (Scientific vs Clinical vs Stage) */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center max-w-3xl mx-auto mb-12">
          <h2 className="text-2xl sm:text-3xl font-extrabold text-white">
            {isEnglish ? 'The Three Clear Domains of Hypnosis' : 'ഹിപ്നോട്ടിസത്തിന്റെ മൂന്ന് പ്രധാന തലങ്ങൾ'}
          </h2>
          <p className="text-slate-400 text-sm mt-2">
            {isEnglish 
              ? 'Understanding boundaries is the cornerstone of professional practice' 
              : 'ഓരോ മേഖലയും തമ്മിലുള്ള വ്യക്തമായ വ്യത്യാസം മനസ്സിലാക്കുക'}
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          
          {/* Domain 1 */}
          <div className="p-6 rounded-2xl bg-[#0D1322] border-2 border-cyan-500/40 hover:border-cyan-400 transition-all shadow-lg shadow-cyan-500/5 flex flex-col">
            <div className="w-12 h-12 rounded-xl bg-cyan-500/10 border border-cyan-400 text-cyan-400 flex items-center justify-center mb-4">
              <Brain className="w-6 h-6" />
            </div>
            <div className="inline-block px-2.5 py-0.5 rounded-full bg-cyan-500/20 text-cyan-300 text-[11px] font-bold self-start mb-2">
              {isEnglish ? 'OUR ACADEMY FOCUS' : 'ഞങ്ങളുടെ പ്രധാന പഠനം'}
            </div>
            <h3 className="text-lg font-bold text-white mb-2">
              {isEnglish ? '1. Scientific & Educational' : '1. ശാസ്ത്രീയവും വിദ്യാഭ്യാസപരവുമായ പഠനം'}
            </h3>
            <p className="text-xs text-slate-300 leading-relaxed flex-grow">
              {isEnglish
                ? 'Academic study of the subconscious mind, cognitive mechanics, suggestibility testing, self-hypnosis for personal focus, habit conditioning, and psychological awareness.'
                : 'മനസ്സിന്റെ ഘടന, ഏകാഗ്രത, വ്യക്തിത്വ വികാസം, പഠന വൈകല്യങ്ങൾ മാറ്റൽ, സെൽഫ് ഹിപ്നോസിസ് എന്നിവ ശാസ്ത്രീയമായി പഠിക്കുന്ന രീതി.'}
            </p>
          </div>

          {/* Domain 2 */}
          <div className="p-6 rounded-2xl bg-[#0D1322] border border-[#233252] hover:border-slate-600 transition-all flex flex-col">
            <div className="w-12 h-12 rounded-xl bg-amber-500/10 border border-amber-400 text-amber-400 flex items-center justify-center mb-4">
              <ShieldCheck className="w-6 h-6" />
            </div>
            <div className="inline-block px-2.5 py-0.5 rounded-full bg-amber-500/10 text-amber-300 text-[11px] font-bold self-start mb-2">
              {isEnglish ? 'LICENSED MEDICAL ONLY' : 'മെഡിക്കൽ വിദഗ്ദ്ധർക്ക് മാത്രം'}
            </div>
            <h3 className="text-lg font-bold text-white mb-2">
              {isEnglish ? '2. Clinical & Medical Therapy' : '2. ക്ലിനിക്കൽ & മെഡിക്കൽ തെറാപ്പി'}
            </h3>
            <p className="text-xs text-slate-400 leading-relaxed flex-grow">
              {isEnglish
                ? 'Treating psychiatric disorders, clinical depression, trauma, and medical conditions. Reserved exclusively for licensed psychologists, psychiatrists, and certified healthcare practitioners.'
                : 'മാനസിക രോഗങ്ങൾ, അസുഖങ്ങൾ എന്നിവ ചികിത്സിക്കുന്ന രീതി. ഇത് ലൈസൻസുള്ള ഡോക്ടർമാർക്കും വിദഗ്ദ്ധർക്കും മാത്രമേ ചെയ്യാൻ അനുവാദമുള്ളൂ.'}
            </p>
          </div>

          {/* Domain 3 */}
          <div className="p-6 rounded-2xl bg-[#0D1322] border border-[#233252] hover:border-slate-600 transition-all flex flex-col">
            <div className="w-12 h-12 rounded-xl bg-purple-500/10 border border-purple-400 text-purple-400 flex items-center justify-center mb-4">
              <Sparkles className="w-6 h-6" />
            </div>
            <div className="inline-block px-2.5 py-0.5 rounded-full bg-purple-500/10 text-purple-300 text-[11px] font-bold self-start mb-2">
              {isEnglish ? 'ENTERTAINMENT' : 'വിനോദ പരിപാടി'}
            </div>
            <h3 className="text-lg font-bold text-white mb-2">
              {isEnglish ? '3. Stage & Performance Hypnosis' : '3. സ്റ്റേജ് & പെർഫോമൻസ്'}
            </h3>
            <p className="text-xs text-slate-400 leading-relaxed flex-grow">
              {isEnglish
                ? 'Theatrical showmanship with willing volunteers designed for public entertainment, completely distinct from rigorous scientific psychology and educational training.'
                : 'സ്റ്റേജ് ഷോകൾക്കും മാധ്യമങ്ങൾക്കും വേണ്ടി ജനങ്ങളെ രസിപ്പിക്കാൻ ഉപയോഗിക്കുന്ന പ്രകടനങ്ങൾ. ഇതിന് അക്കാദമിക് പഠനവുമായി ബന്ധമില്ല.'}
            </p>
          </div>

        </div>
      </section>

      {/* 3. FEATURED COURSES CATALOG */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex flex-col sm:flex-row items-start sm:items-end justify-between mb-8 gap-4">
          <div>
            <div className="text-xs font-bold text-cyan-400 uppercase tracking-wider mb-1">
              {isEnglish ? 'Academic Curriculum' : 'പാഠ്യപദ്ധതി'}
            </div>
            <h2 className="text-2xl sm:text-3xl font-extrabold text-white">
              {isEnglish ? 'Available Courses & Masterclasses' : 'ലഭ്യമായ കോഴ്സുകൾ'}
            </h2>
          </div>
          <button
            onClick={() => navigate('courses')}
            className="text-xs font-bold text-cyan-400 hover:text-cyan-300 flex items-center gap-1"
          >
            <span>{isEnglish ? 'View all curriculum details' : 'എല്ലാ കോഴ്സുകളും കാണുക'}</span>
            <ArrowRight className="w-3.5 h-3.5" />
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {courses.map((course) => (
            <div
              key={course.id}
              className="bg-[#0D1322] border border-[#233252] hover:border-cyan-500/50 rounded-2xl p-6 flex flex-col justify-between transition-all hover:-translate-y-1 shadow-lg"
            >
              <div>
                <div className="flex items-center justify-between text-xs mb-3">
                  <span className="px-2.5 py-1 rounded-md bg-indigo-950/60 text-indigo-300 border border-indigo-800/40 font-semibold">
                    {course.languageType}
                  </span>
                  <span className="text-slate-400 font-medium">{course.duration || '8 Weeks'}</span>
                </div>

                <h3 className="text-base font-bold text-white mb-2 leading-snug">
                  {isEnglish ? course.titleEn : course.titleMl}
                </h3>

                <p className="text-xs text-slate-400 line-clamp-2 mb-4 leading-relaxed">
                  {isEnglish ? course.subtitleEn : course.subtitleMl}
                </p>

                <div className="space-y-2 py-3 border-y border-slate-800/80 text-xs text-slate-300">
                  <div className="flex items-center gap-2">
                    <BookOpen className="w-3.5 h-3.5 text-cyan-400" />
                    <span>{course.lessonsCount} {isEnglish ? 'Structured Lessons' : 'പാഠങ്ങൾ'}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <ShieldCheck className="w-3.5 h-3.5 text-emerald-400" />
                    <span>{isEnglish ? 'Private Video Stream & Watermark' : 'സുരക്ഷിത വീഡിയോ ക്ലാസ്സുകൾ'}</span>
                  </div>
                </div>
              </div>

              <div className="pt-6">
                <button
                  onClick={() => navigate('register', course.id)}
                  className="w-full py-2.5 rounded-xl bg-cyan-500/10 hover:bg-cyan-500 text-cyan-400 hover:text-slate-950 border border-cyan-500/30 hover:border-cyan-500 font-bold text-xs tracking-wide transition-all"
                >
                  {isEnglish ? 'Apply to Join Course' : 'കോഴ്സിൽ ചേരുക'}
                </button>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* 4. ETHICAL COMMITMENT BANNER */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="bg-gradient-to-r from-amber-950/30 via-[#0D1322] to-cyan-950/30 border border-amber-500/30 rounded-3xl p-8 sm:p-12 text-center relative overflow-hidden">
          <div className="max-w-2xl mx-auto space-y-4">
            <div className="w-12 h-12 mx-auto rounded-full bg-amber-500/10 border border-amber-400 flex items-center justify-center text-amber-400">
              <ShieldCheck className="w-6 h-6" />
            </div>
            <h3 className="text-xl sm:text-2xl font-bold text-white">
              {isEnglish ? 'Ethical Directives & Student Responsibility' : 'ധാർമ്മിക പ്രതിജ്ഞയും വിദ്യാർത്ഥി ഉത്തരവാദിത്തവും'}
            </h3>
            <p className="text-xs sm:text-sm text-slate-300 leading-relaxed">
              {isEnglish
                ? 'All admitted students must commit to informed consent, client dignity, and strict non-medical boundaries before accessing course materials.'
                : 'കോഴ്സ് പാഠങ്ങൾ പഠിക്കുന്ന ഓരോ വിദ്യാർത്ഥിയും ധാർമ്മിക നിയമങ്ങളും വ്യക്തമായ സമ്മതപത്രങ്ങളും പാലിക്കാൻ ബാധ്യസ്ഥരാണ്.'}
            </p>
            <div className="pt-2">
              <button
                onClick={() => navigate('ethics-safety')}
                className="px-6 py-2.5 rounded-xl bg-[#131B2E] border border-amber-500/40 text-amber-300 hover:bg-amber-500/20 font-bold text-xs transition-colors"
              >
                {isEnglish ? 'Read Safety Guidelines' : 'സുരക്ഷാ നിർദ്ദേശങ്ങൾ വായിക്കുക'}
              </button>
            </div>
          </div>
        </div>
      </section>

    </div>
  );
};
