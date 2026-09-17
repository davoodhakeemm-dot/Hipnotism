import React from 'react';
import { HypnotismStore } from '../services/store';
import { Sparkles, Brain, History, CheckCircle, ShieldAlert } from 'lucide-react';

interface CourseIntroPageProps {
  store: HypnotismStore;
}

export const CourseIntroPage: React.FC<CourseIntroPageProps> = ({ store }) => {
  const { language, navigate } = store;
  const isEnglish = language === 'ENGLISH';

  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-12">
      
      {/* Title */}
      <div className="text-center space-y-3">
        <div className="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-cyan-500/10 border border-cyan-500/30 text-xs font-bold text-cyan-400">
          <Brain className="w-4 h-4" />
          <span>{isEnglish ? 'Academic Introduction' : 'കോഴ്സ് ആമുഖം'}</span>
        </div>
        <h1 className="text-3xl sm:text-4xl font-extrabold text-white">
          {isEnglish ? 'Scientific Foundation of Hypnotism' : 'ഹിപ്നോട്ടിസത്തിന്റെ ശാസ്ത്രീയ അടിസ്ഥാനം'}
        </h1>
        <p className="text-slate-400 text-sm max-w-2xl mx-auto">
          {isEnglish
            ? 'Demystifying human attention, cognitive suggestibility, and subconscious processes through evidence-based research.'
            : 'മാന്ത്രികതയുടെയോ അത്ഭുതങ്ങളുടെയോ മറയില്ലാതെ, മനുഷ്യ മനസ്സിന്റെ പ്രവർത്തനങ്ങളെക്കുറിച്ച് ശാസ്ത്രം പറയുന്ന കാര്യങ്ങൾ മനസ്സിലാക്കുക.'}
        </p>
      </div>

      {/* Main Content Articles */}
      <div className="space-y-8">
        
        {/* Card 1: What is Hypnosis? */}
        <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-8 space-y-4">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-cyan-500/10 border border-cyan-500/40 flex items-center justify-center text-cyan-400">
              <Sparkles className="w-5 h-5" />
            </div>
            <h2 className="text-xl font-bold text-white">
              {isEnglish ? 'What Really is Hypnosis?' : 'എന്താണ് യഥാർത്ഥത്തിൽ ഹിപ്നോസിസ്?'}
            </h2>
          </div>

          <p className="text-sm text-slate-300 leading-relaxed">
            {isEnglish
              ? 'In cognitive psychology, hypnosis is defined as a natural state of focused attention, heightened absorption, and increased responsiveness to suggestion. Contrary to cinema tropes, the subject is not asleep or unconscious. Modern fMRI brain imaging demonstrates that during hypnosis, regions of the brain responsible for focal concentration and sensory processing remain actively engaged while external peripheral distractions are minimized.'
              : 'മനശാസ്ത്രത്തിന്റെ വീക്ഷണത്തിൽ, ഹിപ്നോസിസ് എന്നത് അതീവ ഏകാഗ്രതയും ശ്രദ്ധയും സ്വീകാര്യതയും ഉള്ള ഒരു സ്വാഭാവിക മാനസികാവസ്ഥയാണ്. സിനിമകളിൽ കാണുന്നതുപോലെ വ്യക്തിയുടെ ബോധം നഷ്ടപ്പെടുകയോ അയാൾ ഒരു പാവയായി മാറുകയോ ചെയ്യുന്നില്ല. മറിച്ച്, ബാഹ്യമായ അലോസരങ്ങളിൽ നിന്ന് മനസ്സ് പിന്തിരിഞ്ഞ് ഒരു പ്രത്യേക വിഷയത്തിലേക്ക് മാത്രം ഏകാഗ്രമാകുന്ന അവസ്ഥയാണിത്.'}
          </p>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 pt-2">
            <div className="p-3.5 rounded-xl bg-[#080B12] border border-slate-800 text-xs flex items-start gap-2.5">
              <CheckCircle className="w-4 h-4 text-emerald-400 flex-shrink-0 mt-0.5" />
              <span className="text-slate-300">
                {isEnglish ? 'You retain 100% conscious control and ethical discretion.' : 'വ്യക്തിക്ക് പൂർണ്ണമായ ബോധവും സ്വയം നിയന്ത്രണവും ഉണ്ടായിരിക്കും.'}
              </span>
            </div>
            <div className="p-3.5 rounded-xl bg-[#080B12] border border-slate-800 text-xs flex items-start gap-2.5">
              <CheckCircle className="w-4 h-4 text-emerald-400 flex-shrink-0 mt-0.5" />
              <span className="text-slate-300">
                {isEnglish ? 'No one can be forced into hypnosis against their personal will.' : 'ആരുടെയും അനുവാദമില്ലാതെ ഹിപ്നോട്ടൈസ് ചെയ്യാൻ സാധിക്കില്ല.'}
              </span>
            </div>
          </div>
        </div>

        {/* Card 2: Historical Timeline */}
        <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-8 space-y-4">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-indigo-500/10 border border-indigo-500/40 flex items-center justify-center text-indigo-400">
              <History className="w-5 h-5" />
            </div>
            <h2 className="text-xl font-bold text-white">
              {isEnglish ? 'From Mesmerism to Neurobiology' : 'ചരിത്രപരമായ നാൾവഴികൾ'}
            </h2>
          </div>

          <div className="space-y-4 text-xs text-slate-300 leading-relaxed">
            <div className="p-4 rounded-2xl bg-[#080B12] border border-slate-800">
              <div className="font-bold text-cyan-400 text-sm mb-1">
                {isEnglish ? '1. Franz Anton Mesmer (1734–1815)' : '1. ഫ്രാൻസ് ആന്റൺ മെസ്മർ (1734–1815)'}
              </div>
              <p>
                {isEnglish
                  ? 'Proposed "Animal Magnetism", claiming an invisible bodily fluid influenced health. While his physical theories were debunked by the French Royal Commission (Benjamin Franklin, Lavoisier), his clinical trials revealed the powerful role of psychological suggestion.'
                  : 'മനുഷ്യ ശരീരത്തിൽ ഒരു കാന്തിക ദ്രാവകം ഉണ്ടെന്ന വാദം മുന്നോട്ടുവെച്ചു. അദ്ദേഹത്തിന്റെ ഈ സിദ്ധാന്തം തെറ്റാണെന്ന് തെളിയിക്കപ്പെട്ടെങ്കിലും, മാനസിക നിർദ്ദേശങ്ങളുടെ ശക്തി ആദ്യമായി ശ്രദ്ധിക്കപ്പെടാൻ ഇത് കാരണമായി.'}
              </p>
            </div>

            <div className="p-4 rounded-2xl bg-[#080B12] border border-slate-800">
              <div className="font-bold text-indigo-400 text-sm mb-1">
                {isEnglish ? '2. Dr. James Braid (1795–1860) – The Father of Modern Hypnosis' : '2. ഡോ. ജെയിംസ് ബ്രെയ്ഡ് (1795–1860) - ആധുനിക ഹിപ്നോസിസിന്റെ പിതാവ്'}
              </div>
              <p>
                {isEnglish
                  ? 'Scottish surgeon who rejected occult theories and demonstrated that prolonged physiological eye-fixation tires ocular muscles, inducing "neuro-hypnology" (nervous sleep). He coined the term "Hypnotism" and established it as a legitimate physiological science.'
                  : 'സ്കോട്ടിഷ് ഡോക്ടറായ ഇദ്ദേഹമാണ് കണ്ണിന്റെ പേശികളിലെ ക്ഷീണം മൂലമുണ്ടാകുന്ന ശാന്തതയെ ശാസ്ത്രീയമായി വിശദീകരിച്ചതും "ഹിപ്നോട്ടിസം" എന്ന പേര് നൽകിയതും.'}
              </p>
            </div>

            <div className="p-4 rounded-2xl bg-[#080B12] border border-slate-800">
              <div className="font-bold text-emerald-400 text-sm mb-1">
                {isEnglish ? '3. Modern Neuroscience & Cognitive Psychology' : '3. ആധുനിക ന്യൂറോസയൻസും മനശാസ്ത്രവും'}
              </div>
              <p>
                {isEnglish
                  ? 'Today, brain mapping technologies confirm distinct Alpha (8-12 Hz) and Theta (4-8 Hz) brainwave shifts during trance. It is widely used by sports psychologists, researchers, and trained educators worldwide.'
                  : 'ഇന്ന് fMRI, EEG തുടങ്ങിയ ആധുനിക പരിശോധനകളിലൂടെ ഹിപ്നോസിസ് സമയത്തെ തലച്ചോറിലെ ആൽഫ, തീറ്റ തരംഗങ്ങളുടെ പ്രവർത്തനം കൃത്യമായി തെളിയിക്കപ്പെട്ടിട്ടുണ്ട്.'}
              </p>
            </div>
          </div>
        </div>

        {/* Card 3: Action Callout */}
        <div className="text-center p-8 rounded-3xl bg-gradient-to-r from-cyan-950/30 to-indigo-950/30 border border-cyan-500/30 space-y-4">
          <h3 className="text-xl font-bold text-white">
            {isEnglish ? 'Ready to explore the structured curriculum?' : 'പാഠ്യപദ്ധതി കൂടുതൽ അറിയാൻ ആഗ്രഹിക്കുന്നുണ്ടോ?'}
          </h3>
          <div className="flex flex-wrap items-center justify-center gap-4">
            <button
              onClick={() => navigate('what-students-learn')}
              className="px-6 py-2.5 rounded-xl bg-cyan-400 text-slate-950 font-bold text-xs hover:bg-cyan-300 transition-colors"
            >
              {isEnglish ? 'What Students Learn (12 Points)' : '12 പ്രധാന പഠന വിഷയങ്ങൾ'}
            </button>
            <button
              onClick={() => navigate('register')}
              className="px-6 py-2.5 rounded-xl bg-[#131B2E] border border-[#233252] text-slate-200 font-semibold text-xs hover:text-white hover:border-cyan-500/50 transition-colors"
            >
              {isEnglish ? 'Join Course Today' : 'കോഴ്സിൽ ചേരുക'}
            </button>
          </div>
        </div>

      </div>

    </div>
  );
};
