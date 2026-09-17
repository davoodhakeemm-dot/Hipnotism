import React from 'react';
import { HypnotismStore, OWNER_ADMIN_EMAIL } from '../services/store';
import { PageRoute } from '../types';
import { Compass, ShieldAlert, Mail, Globe, HeartHandshake } from 'lucide-react';

interface FooterProps {
  store: HypnotismStore;
}

export const Footer: React.FC<FooterProps> = ({ store }) => {
  const { language, navigate } = store;
  const isEnglish = language === 'ENGLISH';

  return (
    <footer className="bg-[#05080E] border-t border-[#1C263B] text-slate-400 text-sm mt-20">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-14">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-10">
          
          {/* Column 1: Brand & Philosophy */}
          <div className="space-y-4">
            <div className="flex items-center gap-2.5">
              <div className="w-8 h-8 rounded-full bg-cyan-500/10 border border-cyan-400 flex items-center justify-center text-cyan-400">
                <Compass className="w-4 h-4" />
              </div>
              <span className="text-lg font-bold text-white tracking-wider">HYPNOTISM</span>
              <span className="text-xs px-2 py-0.5 rounded bg-[#131B2E] text-cyan-300 border border-cyan-500/20 font-medium">
                ഹിപ്നോട്ടിസം
              </span>
            </div>
            
            <p className="text-xs text-slate-400 leading-relaxed">
              {isEnglish
                ? 'Dedicated to the scientific demystification, ethical education, and psychological study of hypnotism and human subconscious awareness.'
                : 'ഹിപ്നോട്ടിസത്തെക്കുറിച്ചുള്ള അന്ധവിശ്വാസങ്ങൾ അകറ്റി, ആധുനിക മനശാസ്ത്രപരമായ രീതിയിൽ ശാസ്ത്രീയമായി പഠിപ്പിക്കുന്ന സമഗ്ര വിദ്യാഭ്യാസ കേന്ദ്രം.'}
            </p>

            <div className="flex items-center gap-2 text-xs text-emerald-400 bg-emerald-950/30 border border-emerald-800/40 rounded-lg p-2.5">
              <HeartHandshake className="w-4 h-4 flex-shrink-0" />
              <span>{isEnglish ? 'Scientific & Evidence-Based Curriculum' : 'ശാസ്ത്രീയവും തെളിവ് അധിഷ്ഠിതവുമായ പാഠ്യപദ്ധതി'}</span>
            </div>
          </div>

          {/* Column 2: Navigation Links */}
          <div>
            <h4 className="text-white font-bold text-sm tracking-wide uppercase mb-4">
              {isEnglish ? 'Explore Academy' : 'പ്രധാന ലിങ്കുകൾ'}
            </h4>
            <ul className="space-y-2.5 text-xs">
              <li>
                <button 
                  onClick={() => navigate('home')} 
                  className="hover:text-cyan-400 transition-colors"
                >
                  {isEnglish ? 'Home' : 'ഹോം'}
                </button>
              </li>
              <li>
                <button 
                  onClick={() => navigate('courses')} 
                  className="hover:text-cyan-400 transition-colors"
                >
                  {isEnglish ? 'Academic Courses' : 'കോഴ്സുകൾ'}
                </button>
              </li>
              <li>
                <button 
                  onClick={() => navigate('what-students-learn')} 
                  className="hover:text-cyan-400 transition-colors"
                >
                  {isEnglish ? '12-Point Curriculum' : 'പഠന വിഷയങ്ങൾ'}
                </button>
              </li>
              <li>
                <button 
                  onClick={() => navigate('ethics-safety')} 
                  className="hover:text-cyan-400 transition-colors"
                >
                  {isEnglish ? 'Ethical Directives & Safety' : 'ധാർമ്മികതയും സുരക്ഷയും'}
                </button>
              </li>
              <li>
                <button 
                  onClick={() => navigate('register')} 
                  className="hover:text-cyan-400 transition-colors"
                >
                  {isEnglish ? 'Student Admission' : 'അഡ്മിഷൻ'}
                </button>
              </li>
            </ul>
          </div>

          {/* Column 3: Medical Notice & Ethics */}
          <div>
            <h4 className="text-white font-bold text-sm tracking-wide uppercase mb-4 flex items-center gap-1.5">
              <ShieldAlert className="w-4 h-4 text-amber-400" />
              <span>{isEnglish ? 'Educational Notice' : 'നിയമപരമായ അറിയിപ്പ്'}</span>
            </h4>
            <div className="bg-[#0E1524] p-3.5 rounded-xl border border-slate-800 text-xs text-slate-400 leading-relaxed space-y-2">
              <p>
                {isEnglish
                  ? 'This program is purely educational. Hypnotism taught here is not a substitute for licensed medical or clinical psychiatric therapy.'
                  : 'ഇതൊരു വിദ്യാഭ്യാസ പരിശീലന പരിപാടി മാത്രമാണ്. ലൈസൻസുള്ള ഡോക്ടറുടെ അനുമതിയില്ലാതെ രോഗചികിത്സയ്ക്കായി ഇത് ഉപയോഗിക്കാൻ പാടില്ല.'}
              </p>
              <p className="text-[11px] text-amber-400/90 font-medium">
                {isEnglish ? 'Strict anti-piracy dynamic watermarking active.' : 'സുരക്ഷാ മാനദണ്ഡങ്ങൾ നിർബന്ധമാണ്.'}
              </p>
            </div>
          </div>

          {/* Column 4: Contact & Administration */}
          <div>
            <h4 className="text-white font-bold text-sm tracking-wide uppercase mb-4">
              {isEnglish ? 'Academy Contact' : 'ബന്ധപ്പെടാൻ'}
            </h4>
            <div className="space-y-3 text-xs">
              <div className="flex items-start gap-2">
                <Mail className="w-4 h-4 text-cyan-400 flex-shrink-0 mt-0.5" />
                <div>
                  <div className="text-slate-300 font-medium">{OWNER_ADMIN_EMAIL}</div>
                  <div className="text-[11px] text-slate-500">Official Administration</div>
                </div>
              </div>

              <div className="flex items-center gap-2">
                <Globe className="w-4 h-4 text-indigo-400 flex-shrink-0" />
                <span className="text-slate-300">Kerala, India • Global Online Portal</span>
              </div>
            </div>
          </div>

        </div>

        {/* Bottom Bar */}
        <div className="mt-12 pt-6 border-t border-slate-800/80 flex flex-col sm:flex-row items-center justify-between gap-4 text-xs text-slate-500">
          <p>© {new Date().getFullYear()} Hypnotism Scientific Academy. All Rights Reserved.</p>
          <div className="flex items-center gap-4">
            <span className="text-slate-400 font-medium">മലയാളം & English Bilingual Learning</span>
          </div>
        </div>
      </div>
    </footer>
  );
};
