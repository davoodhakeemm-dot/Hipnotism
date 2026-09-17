import React from 'react';
import { HypnotismStore } from '../services/store';
import { WHAT_STUDENTS_LEARN_ITEMS } from '../data/coursesData';
import { BookOpen, CheckCircle, ArrowRight } from 'lucide-react';

interface WhatStudentsLearnPageProps {
  store: HypnotismStore;
}

export const WhatStudentsLearnPage: React.FC<WhatStudentsLearnPageProps> = ({ store }) => {
  const { language, navigate } = store;
  const isEnglish = language === 'ENGLISH';

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-10">
      
      {/* Header */}
      <div className="text-center max-w-3xl mx-auto space-y-3">
        <div className="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-cyan-500/10 border border-cyan-500/30 text-xs font-bold text-cyan-400">
          <BookOpen className="w-4 h-4" />
          <span>{isEnglish ? 'Comprehensive Curriculum' : 'സമഗ്ര പാഠ്യപദ്ധതി'}</span>
        </div>
        <h1 className="text-3xl sm:text-4xl font-extrabold text-white">
          {isEnglish ? 'What Students Learn in This Academy' : 'വിദ്യാർത്ഥികൾ പഠിക്കുന്ന പ്രധാന കാര്യങ്ങൾ'}
        </h1>
        <p className="text-slate-400 text-sm leading-relaxed">
          {isEnglish
            ? 'A rigorous 12-point educational framework balancing psychological theory, practical suggestion methods, and professional safety.'
            : 'തിയറിയും പ്രാക്ടിക്കലും സമന്വയിപ്പിച്ചുകൊണ്ടുള്ള 12 സുപ്രധാന പഠന വിഷയങ്ങൾ.'}
        </p>
      </div>

      {/* 12-Point Curriculum Grid (2 cols on tablet/desktop, 1 on mobile) */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 sm:gap-6">
        {WHAT_STUDENTS_LEARN_ITEMS.map((item, index) => (
          <div
            key={index}
            className="p-5 rounded-2xl bg-[#0D1322] border border-[#233252] hover:border-cyan-500/40 transition-all flex items-start gap-4 shadow-lg group"
          >
            <div className="w-8 h-8 rounded-xl bg-cyan-500/10 border border-cyan-400/40 text-cyan-400 font-bold flex items-center justify-center flex-shrink-0 text-sm group-hover:bg-cyan-500 group-hover:text-slate-950 transition-colors">
              {index + 1}
            </div>

            <div className="flex-1 space-y-1.5">
              <p className="text-sm font-semibold text-slate-100 leading-snug">
                {isEnglish ? item.en : item.ml}
              </p>
              <p className="text-xs text-slate-400 leading-relaxed">
                {isEnglish ? item.ml : item.en}
              </p>
            </div>

            <CheckCircle className="w-4 h-4 text-emerald-400 flex-shrink-0 mt-1 opacity-80" />
          </div>
        ))}
      </div>

      {/* CTA Box */}
      <div className="p-8 rounded-3xl bg-gradient-to-r from-[#131B2E] via-[#0D1322] to-[#131B2E] border border-cyan-500/30 text-center max-w-2xl mx-auto space-y-4">
        <h3 className="text-xl font-bold text-white">
          {isEnglish ? 'Start Your Scientific Learning Journey' : 'ശാസ്ത്രീയ പഠനം ഇന്നുതന്നെ ആരംഭിക്കൂ'}
        </h3>
        <p className="text-xs text-slate-300 leading-relaxed">
          {isEnglish
            ? 'Submit your student enrollment application to gain access to private video lectures, study PDFs, and certification modules.'
            : 'വിദ്യാർത്ഥി അപേക്ഷ ഫോം പൂരിപ്പിച്ചു നൽകി സ്വകാര്യ വീഡിയോ ക്ലാസ്സുകളും പഠന സാമഗ്രികളും സ്വന്തമാക്കൂ.'}
        </p>
        <div>
          <button
            onClick={() => navigate('register')}
            className="px-8 py-3 rounded-xl bg-cyan-400 hover:bg-cyan-300 text-slate-950 font-bold text-sm shadow-lg shadow-cyan-400/20 transition-all inline-flex items-center gap-2"
          >
            <span>{isEnglish ? 'Apply to Join Class' : 'ക്ലാസ്സിൽ ചേരാൻ അപേക്ഷിക്കുക'}</span>
            <ArrowRight className="w-4 h-4" />
          </button>
        </div>
      </div>

    </div>
  );
};
