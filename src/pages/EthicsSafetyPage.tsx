import React from 'react';
import { HypnotismStore } from '../services/store';
import { ETHICS_DIRECTIVES } from '../data/coursesData';
import { Shield, AlertTriangle, CheckCircle2, Lock } from 'lucide-react';

interface EthicsSafetyPageProps {
  store: HypnotismStore;
}

export const EthicsSafetyPage: React.FC<EthicsSafetyPageProps> = ({ store }) => {
  const { language } = store;
  const isEnglish = language === 'ENGLISH';

  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-12">
      
      {/* Banner */}
      <div className="text-center bg-gradient-to-b from-[#131B2E] to-[#0D1322] border border-amber-500/30 rounded-3xl p-8 sm:p-10 space-y-4">
        <div className="w-16 h-16 mx-auto rounded-full bg-amber-500/10 border-2 border-amber-400 flex items-center justify-center text-amber-400">
          <Shield className="w-8 h-8" />
        </div>
        <h1 className="text-3xl sm:text-4xl font-extrabold text-white">
          {isEnglish ? 'Ethics & Safety Guidelines' : 'ധാർമ്മിക നിയമങ്ങളും സുരക്ഷാ നിർദ്ദേശങ്ങളും'}
        </h1>
        <div className="inline-block px-4 py-1 rounded-full bg-amber-500/10 border border-amber-500/30 text-amber-300 font-bold text-xs">
          {isEnglish ? 'Mandatory Ethical Directives' : 'നിർബന്ധിത ധാർമ്മിക നിർദ്ദേശങ്ങൾ'}
        </div>
        <p className="text-slate-300 text-xs sm:text-sm max-w-2xl mx-auto leading-relaxed">
          {isEnglish
            ? 'Hypnosis is a tool of profound psychological influence. Knowledge must always be accompanied by unwavering integrity, consent, and safety standards.'
            : 'മനസ്സിന്റെ ഘടനയെ സ്വാധീനിക്കാൻ കഴിയുന്ന ഒരു വിദ്യയായതിനാൽ, പൂർണ്ണമായ ആത്മാർത്ഥതയോടും സുരക്ഷാ മാനദണ്ഡങ്ങളോടും കൂടി മാത്രമേ ഇത് പ്രയോഗിക്കാൻ പാടുള്ളൂ.'}
        </p>
      </div>

      {/* Ethical Directives Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {ETHICS_DIRECTIVES.map((item, idx) => (
          <div
            key={idx}
            className="p-6 rounded-2xl bg-[#0D1322] border border-[#233252] hover:border-amber-500/40 transition-all flex items-start gap-4 shadow-lg"
          >
            <div className="w-8 h-8 rounded-full bg-amber-500/15 border border-amber-400/40 text-amber-400 font-bold flex items-center justify-center flex-shrink-0 text-xs">
              {idx + 1}
            </div>

            <div className="space-y-1.5 flex-1">
              <h3 className="text-sm font-bold text-slate-100 leading-snug">
                {isEnglish ? item.en : item.ml}
              </h3>
              <p className="text-xs text-slate-400 leading-relaxed">
                {isEnglish ? item.ml : item.en}
              </p>
            </div>
          </div>
        ))}
      </div>

      {/* Medical Disclaimer Box */}
      <div className="p-6 sm:p-8 rounded-3xl bg-red-950/20 border-2 border-red-800/40 space-y-3">
        <div className="flex items-center gap-3 text-red-400 font-bold text-base">
          <AlertTriangle className="w-6 h-6 flex-shrink-0" />
          <span>{isEnglish ? 'Critical Medical Disclaimer & Notice' : 'പ്രധാന മെഡിക്കൽ മുന്നറിയിപ്പ്'}</span>
        </div>
        <p className="text-xs sm:text-sm text-slate-300 leading-relaxed">
          {isEnglish
            ? 'Medical Notice: This is strictly an educational training program. Do not attempt to use hypnotism to treat clinical psychiatric disorders, chronic depression, severe trauma, or medical pain without certified medical licenses and legal healthcare authority. Always refer individuals experiencing clinical distress to licensed medical professionals.'
            : 'മെഡിക്കൽ അറിയിപ്പ്: ഇത് കേവലം ഒരു വിദ്യാഭ്യാസ പരിശീലന പരിപാടി മാത്രമാണ്. ലൈസൻസുള്ള ഡോക്ടറുടെയോ മെഡിക്കൽ വിദഗ്ദ്ധന്റെയോ അനുമതിയില്ലാതെ രോഗചികിത്സയ്ക്കായി ഇത് ഉപയോഗിക്കാൻ പാടില്ല. അസുഖങ്ങൾ അനുഭവിക്കുന്നവരെ നിർബന്ധമായും ഡോക്ടറുടെ അടുത്തേക്ക് റഫർ ചെയ്യേണ്ടതാണ്.'}
        </p>
      </div>

    </div>
  );
};
