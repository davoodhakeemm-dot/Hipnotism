import React, { useState } from 'react';
import { HypnotismStore } from '../services/store';
import { 
  UserPlus, 
  CheckCircle, 
  AlertCircle, 
  ShieldCheck, 
  Mail, 
  Phone, 
  MapPin, 
  User, 
  BookOpen, 
  Lock 
} from 'lucide-react';

interface RegistrationPageProps {
  store: HypnotismStore;
}

export const RegistrationPage: React.FC<RegistrationPageProps> = ({ store }) => {
  const { language, courses, activeCourseId, registerStudent, navigate } = store;
  const isEnglish = language === 'ENGLISH';

  const [fullName, setFullName] = useState('');
  const [age, setAge] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [whatsAppNumber, setWhatsAppNumber] = useState('');
  const [gmailAddress, setGmailAddress] = useState('');
  const [address, setAddress] = useState('');
  const [selectedCourseId, setSelectedCourseId] = useState(activeCourseId || courses[0]?.id || 'hypno-ml');
  const [agreeEthics, setAgreeEthics] = useState(false);

  const [statusMessage, setStatusMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!fullName.trim()) {
      setStatusMessage({ type: 'error', text: isEnglish ? 'Please enter your full name.' : 'ദയവായി നിങ്ങളുടെ പൂർണ്ണ പേര് നൽകുക.' });
      return;
    }

    const ageNum = parseInt(age);
    if (isNaN(ageNum) || ageNum < 16 || ageNum > 90) {
      setStatusMessage({ type: 'error', text: isEnglish ? 'Please enter a valid age (minimum 16 years).' : 'ദയവായി സാധുവായ പ്രായം നൽകുക (കുറഞ്ഞത് 16 വയസ്സ്).' });
      return;
    }

    if (!phoneNumber.trim()) {
      setStatusMessage({ type: 'error', text: isEnglish ? 'Please enter your contact phone number.' : 'ദയവായി ഫോൺ നമ്പർ നൽകുക.' });
      return;
    }

    if (!gmailAddress.trim() || !gmailAddress.includes('@')) {
      setStatusMessage({ type: 'error', text: isEnglish ? 'Please provide a valid Gmail address.' : 'ദയവായി സാധുവായ ഒരു ജിമെയിൽ വിലാസം നൽകുക.' });
      return;
    }

    if (!address.trim()) {
      setStatusMessage({ type: 'error', text: isEnglish ? 'Please enter your residential location / city.' : 'ദയവായി നിങ്ങളുടെ സ്ഥലം / വിലാസം നൽകുക.' });
      return;
    }

    if (!agreeEthics) {
      setStatusMessage({ type: 'error', text: isEnglish ? 'You must accept the ethical directives to continue.' : 'തുടരുന്നതിനായി നിങ്ങൾ ധാർമ്മിക നിയമങ്ങൾ അംഗീകരിക്കണം.' });
      return;
    }

    const result = registerStudent({
      fullName,
      age: ageNum,
      phoneNumber,
      whatsAppNumber: whatsAppNumber.trim() || phoneNumber.trim(),
      gmailAddress,
      address,
      selectedCourseId
    });

    if (result.success) {
      setStatusMessage({ type: 'success', text: result.message });
      // Reset fields
      setFullName('');
      setAge('');
      setPhoneNumber('');
      setWhatsAppNumber('');
      setAddress('');
      setAgreeEthics(false);
    } else {
      setStatusMessage({ type: 'error', text: result.message });
    }
  };

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      
      {/* Header */}
      <div className="text-center space-y-3">
        <div className="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-cyan-500/10 border border-cyan-500/30 text-xs font-bold text-cyan-400">
          <UserPlus className="w-4 h-4" />
          <span>{isEnglish ? 'Student Admission Form' : 'വിദ്യാർത്ഥി പ്രവേശന ഫോം'}</span>
        </div>
        <h1 className="text-3xl font-extrabold text-white">
          {isEnglish ? 'Apply to Join Hypnotism Class' : 'ക്ലാസ്സിൽ ചേരാൻ അപേക്ഷിക്കുക'}
        </h1>
        <p className="text-slate-400 text-xs sm:text-sm max-w-xl mx-auto">
          {isEnglish
            ? 'Complete your profile information. Once approved by the administration, you will receive full access to lectures, study materials, and certification.'
            : 'വിവരങ്ങൾ പൂരിപ്പിച്ച് അപേക്ഷ സമർപ്പിക്കുക. അഡ്മിൻ പരിശോധനയ്ക്ക് ശേഷം നിങ്ങളുടെ ജിമെയിൽ അക്കൗണ്ടിലേക്ക് കോഴ്സ് അനുമതി ലഭിക്കുന്നതാണ്.'}
        </p>
      </div>

      {/* Form Card */}
      <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-10 shadow-2xl">
        
        {statusMessage && (
          <div
            className={`p-4 rounded-2xl mb-6 text-xs flex items-start gap-3 border ${
              statusMessage.type === 'success'
                ? 'bg-emerald-950/40 border-emerald-800 text-emerald-300'
                : 'bg-red-950/40 border-red-800 text-red-300'
            }`}
          >
            {statusMessage.type === 'success' ? (
              <CheckCircle className="w-5 h-5 flex-shrink-0 mt-0.5" />
            ) : (
              <AlertCircle className="w-5 h-5 flex-shrink-0 mt-0.5" />
            )}
            <div className="space-y-1">
              <div className="font-bold">{statusMessage.text}</div>
              {statusMessage.type === 'success' && (
                <button
                  type="button"
                  onClick={() => navigate('student-dashboard')}
                  className="text-cyan-400 underline font-semibold mt-1 inline-block"
                >
                  {isEnglish ? 'Go to Student Portal to check approval status →' : 'സ്റ്റാറ്റസ് പരിശോധിക്കാൻ സ്റ്റുഡന്റ് പോർട്ടലിലേക്ക് പോകുക →'}
                </button>
              )}
            </div>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          
          {/* Full Name & Age */}
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <div className="sm:col-span-2 space-y-1.5">
              <label className="text-xs font-semibold text-slate-300 flex items-center gap-1.5">
                <User className="w-3.5 h-3.5 text-cyan-400" />
                <span>{isEnglish ? 'Full Name' : 'പൂർണ്ണ പേര്'} *</span>
              </label>
              <input
                type="text"
                value={fullName}
                onChange={(e) => setFullName(e.target.value)}
                placeholder={isEnglish ? 'e.g. Rahul Krishnan' : 'ഉദാ: രാഹുൽ കൃഷ്ണൻ'}
                className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] focus:border-cyan-400 rounded-xl text-sm text-white placeholder-slate-500 outline-none transition-colors"
                required
              />
            </div>

            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-slate-300">
                {isEnglish ? 'Age' : 'പ്രായം'} *
              </label>
              <input
                type="number"
                min="16"
                max="90"
                value={age}
                onChange={(e) => setAge(e.target.value)}
                placeholder="24"
                className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] focus:border-cyan-400 rounded-xl text-sm text-white placeholder-slate-500 outline-none transition-colors"
                required
              />
            </div>
          </div>

          {/* Contact Details (Phone & WhatsApp) */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-slate-300 flex items-center gap-1.5">
                <Phone className="w-3.5 h-3.5 text-cyan-400" />
                <span>{isEnglish ? 'Phone Number' : 'ഫോൺ നമ്പർ'} *</span>
              </label>
              <input
                type="tel"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                placeholder="+91 98470 12345"
                className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] focus:border-cyan-400 rounded-xl text-sm text-white placeholder-slate-500 outline-none transition-colors"
                required
              />
            </div>

            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-slate-300 flex items-center gap-1.5">
                <Phone className="w-3.5 h-3.5 text-emerald-400" />
                <span>{isEnglish ? 'WhatsApp Number (Optional)' : 'വാട്സ്ആപ്പ് നമ്പർ'}</span>
              </label>
              <input
                type="tel"
                value={whatsAppNumber}
                onChange={(e) => setWhatsAppNumber(e.target.value)}
                placeholder="+91 98470 12345"
                className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] focus:border-cyan-400 rounded-xl text-sm text-white placeholder-slate-500 outline-none transition-colors"
              />
            </div>
          </div>

          {/* Gmail & Location */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-slate-300 flex items-center gap-1.5">
                <Mail className="w-3.5 h-3.5 text-cyan-400" />
                <span>{isEnglish ? 'Registered Gmail Account' : 'ജിമെയിൽ വിലാസം'} *</span>
              </label>
              <input
                type="email"
                value={gmailAddress}
                onChange={(e) => setGmailAddress(e.target.value)}
                placeholder="your.email@gmail.com"
                className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] focus:border-cyan-400 rounded-xl text-sm text-white placeholder-slate-500 outline-none transition-colors"
                required
              />
              <p className="text-[11px] text-slate-500">
                {isEnglish ? 'Used for course video authorization and anti-piracy watermark.' : 'വീഡിയോ കാണാനും സുരക്ഷിത ലോഗിനുമായി ഈ ഇമെയിൽ ഉപയോഗിക്കും.'}
              </p>
            </div>

            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-slate-300 flex items-center gap-1.5">
                <MapPin className="w-3.5 h-3.5 text-cyan-400" />
                <span>{isEnglish ? 'Location / Address' : 'സ്ഥലം / വിലാസം'} *</span>
              </label>
              <input
                type="text"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                placeholder={isEnglish ? 'City, State' : 'സ്ഥലം, ജില്ല'}
                className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] focus:border-cyan-400 rounded-xl text-sm text-white placeholder-slate-500 outline-none transition-colors"
                required
              />
            </div>
          </div>

          {/* Course Selection */}
          <div className="space-y-1.5">
            <label className="text-xs font-semibold text-slate-300 flex items-center gap-1.5">
              <BookOpen className="w-3.5 h-3.5 text-cyan-400" />
              <span>{isEnglish ? 'Select Academic Course' : 'പഠിക്കാൻ ആഗ്രഹിക്കുന്ന കോഴ്സ്'} *</span>
            </label>
            <select
              value={selectedCourseId}
              onChange={(e) => setSelectedCourseId(e.target.value)}
              className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] focus:border-cyan-400 rounded-xl text-sm text-white outline-none transition-colors"
            >
              {courses.map((course) => (
                <option key={course.id} value={course.id} className="bg-[#0D1322] text-white">
                  {isEnglish ? course.titleEn : course.titleMl} ({course.languageType})
                </option>
              ))}
            </select>
          </div>

          {/* Ethical Commitment Checkbox */}
          <div className="p-4 rounded-2xl bg-[#080B12] border border-amber-500/30 space-y-3">
            <div className="flex items-start gap-3">
              <input
                type="checkbox"
                id="ethics"
                checked={agreeEthics}
                onChange={(e) => setAgreeEthics(e.target.checked)}
                className="mt-1 w-4 h-4 rounded border-slate-700 text-cyan-500 focus:ring-cyan-400"
              />
              <label htmlFor="ethics" className="text-xs text-slate-300 leading-relaxed cursor-pointer select-none">
                <span className="font-bold text-amber-400 block mb-1">
                  {isEnglish ? 'Ethical Pledge & Non-Medical Agreement' : 'ധാർമ്മിക പ്രതിജ്ഞയും സമ്മതവും'}
                </span>
                {isEnglish
                  ? 'I solemnly swear to adhere to all scientific ethics, respect individual informed consent, maintain client privacy, and never attempt to replace certified psychiatric or medical care.'
                  : 'ഞാൻ എല്ലാ ശാസ്ത്രീയ ധാർമ്മികതയും പാലിക്കുമെന്നും, സമ്മതമില്ലാതെ ആർക്കും നിർദ്ദേശങ്ങൾ നൽകില്ലെന്നും, ഡോക്ടറുടെ അനുമതിയില്ലാതെ ചികിത്സയ്ക്ക് ഇത് ഉപയോഗിക്കില്ലെന്നും ഉറപ്പ് നൽകുന്നു.'}
              </label>
            </div>
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="w-full py-3.5 rounded-xl bg-gradient-to-r from-cyan-400 to-cyan-500 hover:from-cyan-300 hover:to-cyan-400 text-slate-950 font-bold text-sm sm:text-base shadow-lg shadow-cyan-500/20 transition-all flex items-center justify-center gap-2"
          >
            <UserPlus className="w-5 h-5" />
            <span>{isEnglish ? 'Submit Application' : 'അപേക്ഷ സമർപ്പിക്കുക'}</span>
          </button>

        </form>

      </div>

    </div>
  );
};
