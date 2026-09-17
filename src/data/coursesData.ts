import { Course, Lesson } from '../types';

export const INITIAL_COURSES: Course[] = [
  {
    id: 'hypno-ml',
    titleEn: 'Complete Hypnotism Masterclass (Malayalam)',
    titleMl: 'കംപ്ലീറ്റ് ഹിപ്നോട്ടിസം മാസ്റ്റർക്ലാസ്സ് (മലയാളം)',
    subtitleEn: 'Comprehensive educational program in Malayalam',
    subtitleMl: 'മലയാളത്തിലുള്ള സമഗ്ര പഠന പരിപാടി',
    languageType: 'Malayalam',
    authorizedEmails: ['davoodhakeemm@gmail.com'],
    lessonsCount: 4,
    price: '₹4,999',
    duration: '8 Weeks',
    level: 'Comprehensive'
  },
  {
    id: 'hypno-en',
    titleEn: 'Scientific Hypnotherapy & Focus Training',
    titleMl: 'സയന്റിഫിക് ഹിപ്നോതെറാപ്പി & ഫോക്കസ് ട്രെയിനിംഗ്',
    subtitleEn: 'Complete scientific focus and hypnosis curriculum',
    subtitleMl: 'ശാസ്ത്രീയ പഠനവും പരിശീലനവും',
    languageType: 'English',
    authorizedEmails: ['davoodhakeemm@gmail.com'],
    lessonsCount: 4,
    price: '₹5,499',
    duration: '8 Weeks',
    level: 'Advanced'
  },
  {
    id: 'hypno-bi',
    titleEn: 'Foundational Principles of Subconscious Mind',
    titleMl: 'സബ്കോൺഷ്യസ് മൈൻഡ് ഫൗണ്ടേഷൻ',
    subtitleEn: 'Bilingual educational course covering theory and practical ethics',
    subtitleMl: 'തിയറിയും ധാർമ്മിക നിയമങ്ങളും ഉൾക്കൊള്ളുന്ന ദ്വിഭാഷാ കോഴ്സ്',
    languageType: 'Bilingual (Malayalam + English)',
    authorizedEmails: ['davoodhakeemm@gmail.com'],
    lessonsCount: 3,
    price: '₹3,999',
    duration: '6 Weeks',
    level: 'Foundational'
  }
];

export const INITIAL_LESSONS: Record<string, Lesson[]> = {
  'hypno-ml': [
    {
      id: 'l-ml-01',
      courseId: 'hypno-ml',
      lessonNumber: 1,
      titleEn: 'Lesson 1: Introduction to Hypnotism & Misconceptions',
      titleMl: 'പാഠം 1: ഹിപ്നോട്ടിസം ആമുഖവും തെറ്റിദ്ധാരണകളും',
      duration: '32 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
      descriptionEn: 'Explore the scientific origin of hypnosis from James Braid to modern cognitive neurobiology. Dispel widespread myths regarding mind control or black magic.',
      descriptionMl: 'ജെയിംസ് ബ്രെയ്ഡ് മുതൽ ആധുനിക ന്യൂറോസയൻസ് വരെയുള്ള ഹിപ്നോട്ടിസത്തിന്റെ ശാസ്ത്രീയ ഉത്ഭവം. മനസ്സ് നിയന്ത്രിക്കപ്പെടും എന്ന തെറ്റിദ്ധാരണകൾ ഇല്ലാതാക്കുന്നു.',
      keyTakeawaysEn: [
        'Hypnosis is focused attention and heightened receptivity, not unconsciousness.',
        'A subject remains completely conscious and in control throughout the session.',
        'Stage illusions are performances, distinct from scientific practice.'
      ],
      keyTakeawaysMl: [
        'ഹിപ്നോസിസ് എന്നത് ബോധം നഷ്ടപ്പെടലല്ല, മറിച്ച് ഏകാഗ്രമായ ശ്രദ്ധയും സ്വീകാര്യതയുമാണ്.',
        'വ്യക്തിക്ക് എപ്പോഴും സ്വയം നിയന്ത്രണവും തിരിച്ചറിവും ഉണ്ടായിരിക്കും.',
        'സ്റ്റേജ് ഷോകൾ വെറും വിനോദപ്രകടനങ്ങളാണ്; ശാസ്ത്രീയ പരിശീലനവുമായി ഇതിന് വ്യത്യാസമുണ്ട്.'
      ],
      pdfAttachmentUrl: 'https://example.com/materials/hypno-ml-01.pdf',
      pdfAttachmentName: 'Lesson_1_Malayalam_Guide.pdf',
      isCompleted: true
    },
    {
      id: 'l-ml-02',
      courseId: 'hypno-ml',
      lessonNumber: 2,
      titleEn: 'Lesson 2: Conscious vs. Subconscious Mind',
      titleMl: 'പാഠം 2: കോൺഷ്യസ് & സബ്കോൺഷ്യസ് മനസ്സ്',
      duration: '45 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4',
      descriptionEn: 'Understand how the analytical conscious critical factor filters external suggestions and how subconscious neural pathways store habit patterns.',
      descriptionMl: 'യുക്തിസഹമായ ചിന്തകളും ബാഹ്യ നിർദ്ദേശങ്ങളും അബോധമനസ്സിൽ എങ്ങനെ പ്രവർത്തിക്കുന്നു എന്നും ശീലങ്ങൾ എങ്ങനെ രൂപപ്പെടുന്നു എന്നും മനസ്സിലാക്കുക.',
      keyTakeawaysEn: [
        'Critical factor acts as an analytical firewall.',
        'Subconscious mind processes emotional associations and automated behaviors.',
        'Rapport directly facilitates cognitive bypass of the critical filter.'
      ],
      keyTakeawaysMl: [
        'ക്രിട്ടിക്കൽ ഫാക്ടർ ഒരു അനലിറ്റിക്കൽ ഫയർവാൾ പോലെ പ്രവർത്തിക്കുന്നു.',
        'വികാരപരമായ ബന്ധങ്ങളും യാന്ത്രിക സ്വഭാവങ്ങളും അബോധമനസ്സ് നിയന്ത്രിക്കുന്നു.',
        'റാപ്പോർട്ട് (വിശ്വാസ്യത) നിർദ്ദേശങ്ങൾ ഫലപ്രദമായി സ്വീകരിക്കാൻ സഹായിക്കുന്നു.'
      ],
      pdfAttachmentUrl: 'https://example.com/materials/hypno-ml-02.pdf',
      pdfAttachmentName: 'Subconscious_Mind_Model_Malayalam.pdf',
      isCompleted: false
    },
    {
      id: 'l-ml-03',
      courseId: 'hypno-ml',
      lessonNumber: 3,
      titleEn: 'Lesson 3: Suggestibility Testing & Eye Fixation',
      titleMl: 'പാഠം 3: സജസ്റ്റബിലിറ്റി ടെസ്റ്റിംഗ് & ഐ ഫിക്സേഷൻ',
      duration: '38 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4',
      descriptionEn: 'Hands-on practical calibration of client readiness. Includes lemon test, postural sway test, and standard Braidian eye fixation methodologies.',
      descriptionMl: 'ക്ലയന്റിന്റെ തയ്യാറെടുപ്പ് അളക്കുന്നതിനുള്ള രീതികൾ: ലെമൺ ടെസ്റ്റ്, സ്വേ ടെസ്റ്റ്, ജെയിംസ് ബ്രെയ്ഡിന്റെ ഐ ഫിക്സേഷൻ രീതികൾ.',
      keyTakeawaysEn: [
        'Pre-talk sets expectations and eliminates apprehension.',
        'Eye-fixation fatigues ocular muscles and triggers natural relaxation.',
        'Physical feedback builds mutual confidence.'
      ],
      keyTakeawaysMl: [
        'പ്രീ-ടോക്ക് ആശയക്കുഴപ്പങ്ങൾ നീക്കി ആത്മവിശ്വാസം വളർത്തുന്നു.',
        'ഐ-ഫിക്സേഷൻ കണ്ണിന്റെ പേശികളെ വിശ്രമത്തിലേക്ക് നയിക്കുന്നു.',
        'ശരീരത്തിന്റെ പ്രതികരണങ്ങൾ പരസ്പര വിശ്വാസം ഉറപ്പാക്കുന്നു.'
      ],
      pdfAttachmentUrl: 'https://example.com/materials/hypno-ml-03.pdf',
      pdfAttachmentName: 'Suggestibility_Worksheet_ML.pdf',
      isCompleted: false
    },
    {
      id: 'l-ml-04',
      courseId: 'hypno-ml',
      lessonNumber: 4,
      titleEn: 'Lesson 4: Safe Awakening & Emergence Protocol',
      titleMl: 'പാഠം 4: സുരക്ഷിതമായ റിട്ടേൺ & എമർജൻസ് പ്രോട്ടോക്കോൾ',
      duration: '28 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4',
      descriptionEn: 'The critical exit protocol: counting from 1 to 5, anchoring calmness, and verifying the participant is fully alert and reoriented.',
      descriptionMl: 'ഹിപ്നോട്ടിക് അവസ്ഥയിൽ നിന്ന് സാധാരണ അവസ്ഥയിലേക്ക് സുരക്ഷിതമായി തിരിച്ചുകൊണ്ടുവരുന്ന 1 മുതൽ 5 വരെയുള്ള എണ്ണൽ രീതിയും ഉന്മേഷം ഉറപ്പാക്കലും.',
      keyTakeawaysEn: [
        'Never abruptly end an induction without systematic de-induction.',
        'Count 1 to 5 with progressive sensory cues.',
        'Always confirm motor alertness and clear cognitive orientation.'
      ],
      keyTakeawaysMl: [
        'കൃത്യമായ ഡി-ഇൻഡക്ഷൻ ഇല്ലാതെ സെഷൻ അവസാനിപ്പിക്കരുത്.',
        '1 മുതൽ 5 വരെ ക്രമമായി എണ്ണി ഉന്മേഷം നൽകുക.',
        'പൂർണ്ണ ബോധവും സന്തുലിതാവസ്ഥയും തിരിച്ചെത്തിയെന്ന് ഉറപ്പാക്കുക.'
      ],
      pdfAttachmentUrl: 'https://example.com/materials/hypno-ml-04.pdf',
      pdfAttachmentName: 'Awakening_Protocol_Guidelines.pdf',
      isCompleted: false
    }
  ],
  'hypno-en': [
    {
      id: 'l-en-01',
      courseId: 'hypno-en',
      lessonNumber: 1,
      titleEn: 'Module 1: Foundations of Clinical Hypnotherapy',
      titleMl: 'മൊഡ്യൂൾ 1: ക്ലിനിക്കൽ ഹിപ്നോതെറാപ്പിയുടെ അടിസ്ഥാനം',
      duration: '40 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
      descriptionEn: 'Neurobiological basis of altered states of awareness, cortical inhibition, and therapeutic suggestion science.',
      descriptionMl: 'ശ്രദ്ധയും ബോധനിലകളും സംബന്ധിച്ച ന്യൂറോബയോളജിക്കൽ അടിസ്ഥാനം.',
      keyTakeawaysEn: [
        'Beta to Alpha/Theta brainwave modulation during trance.',
        'Therapeutic alliance is the bedrock of clinical efficacy.',
        'Understanding scope of practice and legal boundaries.'
      ],
      keyTakeawaysMl: [
        'ബീറ്റയിൽ നിന്ന് ആൽഫ/തീറ്റ തരംഗങ്ങളിലേക്കുള്ള മാറ്റം.',
        'തെറാപ്പിസ്റ്റ്-ക്ലയന്റ് ബന്ധത്തിന്റെ പ്രാധാന്യം.',
        'നിയമപരമായ പരിധികളും അറിവും.'
      ],
      isCompleted: false
    },
    {
      id: 'l-en-02',
      courseId: 'hypno-en',
      lessonNumber: 2,
      titleEn: 'Module 2: Progressive Muscle Relaxation & Induction',
      titleMl: 'മൊഡ്യൂൾ 2: പ്രോഗ്രസ്സീവ് മസിൽ റിലാക്സേഷൻ',
      duration: '35 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4',
      descriptionEn: 'Jacobson progressive relaxation adapted for hypnosis. Physiological release of neuromuscular tension.',
      descriptionMl: 'ശരീരത്തിലെ പേശികളുടെ പിരിമുറുക്കം ഒഴിവാക്കാനുള്ള ശാസ്ത്രീയ രീതി.',
      keyTakeawaysEn: [
        'Somatic feedback enhances hypnotic depth.',
        'Pacing breathing rate with vocal rhythm.',
        'Deepening mechanisms via fractionalization.'
      ],
      keyTakeawaysMl: [
        'ശരീര പേശികളുടെ അയവ് മനസ്സിനെ ശാന്തമാക്കുന്നു.',
        'ശ്വാസോച്ഛ്വാസവും ശബ്ദ താളവും തമ്മിലുള്ള പൊരുത്തം.',
        'ഫ്രാക്ഷനലൈസേഷൻ രീതികൾ.'
      ],
      isCompleted: false
    },
    {
      id: 'l-en-03',
      courseId: 'hypno-en',
      lessonNumber: 3,
      titleEn: 'Module 3: Anchoring and Post-Hypnotic Suggestions',
      titleMl: 'മൊഡ്യൂൾ 3: ആങ്കറിംഗ് & പോസ്റ്റ് ഹിപ്നോട്ടിക് നിർദ്ദേശങ്ങൾ',
      duration: '42 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4',
      descriptionEn: 'Pavlovian conditioning applied to emotional stabilization. Creating instant stress-relief tactile triggers.',
      descriptionMl: 'മാനസിക പിരിമുറുക്കം കുറയ്ക്കാൻ സഹായിക്കുന്ന ആങ്കറിംഗ് വിദ്യകൾ.',
      keyTakeawaysEn: [
        'Anchors must be unique, replicable, and timed at peak state.',
        'Clean, affirmative language formulations.',
        'Future pacing to validate real-world trigger effectiveness.'
      ],
      keyTakeawaysMl: [
        'ആങ്കറിംഗ് കൃത്യമായ സമയത്ത് നൽകണം.',
        'പോസിറ്റീവായ വാക്കുകൾ മാത്രം ഉപയോഗിക്കുക.',
        'ഭാവിയിൽ ഇത് എങ്ങിനെ പ്രയോജനപ്പെടും എന്ന് ബോധ്യപ്പെടുത്തുക.'
      ],
      isCompleted: false
    },
    {
      id: 'l-en-04',
      courseId: 'hypno-en',
      lessonNumber: 4,
      titleEn: 'Module 4: Professional Ethics & Practice Boundaries',
      titleMl: 'മൊഡ്യൂൾ 4: പ്രൊഫഷണൽ എത്തിക്സ് & നിയമപരിധികൾ',
      duration: '30 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4',
      descriptionEn: 'Informed consent, absolute confidentiality, handling abreactions, and referral criteria for psychiatric care.',
      descriptionMl: 'ക്ലയന്റിന്റെ സമ്മതം, സ്വകാര്യത, അടിയന്തിര ഘട്ടങ്ങളിലെ സുരക്ഷാ രീതികൾ.',
      keyTakeawaysEn: [
        'Never diagnose or treat clinical psychiatric illness without medical license.',
        'Keep meticulous written records and consent documents.',
        'Safety first: immediate ground-out protocol for unintended emotional release.'
      ],
      keyTakeawaysMl: [
        'മെഡിക്കൽ ലൈസൻസ് ഇല്ലാതെ രോഗചികിത്സ നടത്തരുത്.',
        'രേഖകളും സമ്മതപത്രങ്ങളും കൃത്യമായി സൂക്ഷിക്കുക.',
        'സുരക്ഷയ്ക്ക് പ്രഥമ മുൻഗണന.'
      ],
      isCompleted: false
    }
  ],
  'hypno-bi': [
    {
      id: 'l-bi-01',
      courseId: 'hypno-bi',
      lessonNumber: 1,
      titleEn: 'Part 1: The Human Mind & Trance Phenomenon',
      titleMl: 'ഭാഗം 1: മനുഷ്യ മനസ്സ് & ട്രാൻസ് അവസ്ഥ',
      duration: '25 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
      descriptionEn: 'Daily natural trance states such as highway hypnosis, daydreaming, and reading absorption.',
      descriptionMl: 'നമ്മുടെ നിത്യജീവിതത്തിൽ സംഭവിക്കുന്ന സ്വാഭാവിക ട്രാൻസ് അവസ്ഥകൾ.',
      keyTakeawaysEn: [
        'Trance is a natural, organic neurological state.',
        'Everyone experiences mild hypnosis multiple times daily.'
      ],
      keyTakeawaysMl: [
        'ട്രാൻസ് എന്നത് സ്വാഭാവികമായ ഒരു മാനസികാവസ്ഥയാണ്.',
        'എല്ലാ മനുഷ്യരും ദിവസേന ഇത്തരം അവസ്ഥകളിലൂടെ കടന്നുപോകുന്നുണ്ട്.'
      ],
      isCompleted: false
    },
    {
      id: 'l-bi-02',
      courseId: 'hypno-bi',
      lessonNumber: 2,
      titleEn: 'Part 2: Ethics, Consent & Misuse Prevention',
      titleMl: 'ഭാഗം 2: ധാർമ്മികതയും അനുവാദവും',
      duration: '34 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4',
      descriptionEn: 'Why voluntary informed consent is essential and why no person can be hypnotized against their core moral principles.',
      descriptionMl: 'സമ്മതമില്ലാതെ ആരെയും ഹിപ്നോട്ടൈസ് ചെയ്യാൻ സാധിക്കില്ല എന്ന യാഥാർത്ഥ്യം.',
      keyTakeawaysEn: [
        'The subconscious mind rejects commands violating moral integrity.',
        'Honesty and integrity define professional hypnosis practitioners.'
      ],
      keyTakeawaysMl: [
        'സ്വന്തം മൂല്യങ്ങൾക്ക് വിരുദ്ധമായ നിർദ്ദേശങ്ങൾ അബോധമനസ്സ് നിരസിക്കും.',
        'സത്യസന്ധതയും സദാചാരവും നിർബന്ധമാണ്.'
      ],
      isCompleted: false
    },
    {
      id: 'l-bi-03',
      courseId: 'hypno-bi',
      lessonNumber: 3,
      titleEn: 'Part 3: Self-Hypnosis for Daily Stress & Focus',
      titleMl: 'ഭാഗം 3: ദൈനംദിന ജീവിതത്തിനായുള്ള സ്വയം ഹിപ്നോസിസ്',
      duration: '40 mins',
      videoUrl: 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4',
      descriptionEn: 'Step-by-step method to practice safe self-hypnosis for personal concentration and calming.',
      descriptionMl: 'ഏകാഗ്രതയും ശാന്തതയും വർദ്ധിപ്പിക്കാൻ സ്വയം ഹിപ്നോസിസ് ശീലിക്കുന്ന വിധം.',
      keyTakeawaysEn: [
        'Self-hypnosis requires simple cues and daily 10-minute repetition.',
        'Positive affirmations programmed before sleep create optimal neural change.'
      ],
      keyTakeawaysMl: [
        'ദിവസവും 10 മിനിറ്റ് സമയം പരിശീലനം നടത്തുക.',
        'ഉറങ്ങുന്നതിന് മുൻപ് പോസിറ്റീവ് ചിന്തകൾ നൽകുക.'
      ],
      isCompleted: false
    }
  ]
};

export const WHAT_STUDENTS_LEARN_ITEMS = [
  {
    en: '1. Fundamentals of Scientific Hypnotism (James Braid history & neurobiology)',
    ml: '1. ശാസ്ത്രീയ ഹിപ്നോട്ടിസത്തിന്റെ അടിസ്ഥാന തത്ത്വങ്ങൾ (ചരിത്രവും ന്യൂറോബയോളജിയും)'
  },
  {
    en: '2. Conscious vs. Subconscious Mind Architecture & Cognitive Filter',
    ml: '2. കോൺഷ്യസ് & സബ്കോൺഷ്യസ് മനസ്സിന്റെ ഘടനയും ക്രിട്ടിക്കൽ ഫിൽറ്ററും'
  },
  {
    en: '3. Myths vs. Scientific Reality (Debunking Black Magic & Total Mind Control)',
    ml: '3. അന്ധവിശ്വാസങ്ങളും ശാസ്ത്ര സത്യങ്ങളും (മാന്ത്രികതയും ബ്ലാക്ക് മാജിക്കും തള്ളിക്കളയൽ)'
  },
  {
    en: '4. Suggestibility Testing (Lemon Test, Postural Sway & Hand Clasp)',
    ml: '4. സജസ്റ്റബിലിറ്റി ടെസ്റ്റിംഗ് രീതികൾ (ലെമൺ ടെസ്റ്റ്, സ്വേ ടെസ്റ്റ്)'
  },
  {
    en: '5. Relaxation & Trance Induction Techniques (Eye Fixation, PMR)',
    ml: '5. വിശ്രമവും ഇൻഡക്ഷൻ വിദ്യകളും (ഐ ഫിക്സേഷൻ, പ്രോഗ്രസ്സീവ് റിലാക്സേഷൻ)'
  },
  {
    en: '6. Deepening Techniques & Mental Imagery Exercises',
    ml: '6. ആഴത്തിലുള്ള റിലാക്സേഷൻ രീതികളും മെന്റൽ ഇമേജറി വ്യായാമങ്ങളും'
  },
  {
    en: '7. Positive Suggestion Formulation & Semantic Structure',
    ml: '7. ഫലപ്രദമായ പോസിറ്റീവ് നിർദ്ദേശങ്ങൾ രൂപപ്പെടുത്തുന്ന രീതി'
  },
  {
    en: '8. Safe Awakening & Emergence Protocol (The 1-to-5 Count Method)',
    ml: '8. സുരക്ഷിതമായി സാധാരണ ബോധാവസ്ഥയിലേക്ക് തിരിച്ചെത്തിക്കുന്ന രീതി'
  },
  {
    en: '9. Self-Hypnosis for Daily Focus, Mind Calmness & Exam Anxiety',
    ml: '9. ഏകാഗ്രതയ്ക്കും പരീക്ഷാ ഭയം അകറ്റാനുമുള്ള സ്വയം ഹിപ്നോസിസ്'
  },
  {
    en: '10. Mandatory Ethics, Informed Consent & Privacy Standards',
    ml: '10. നിർബന്ധിത ധാർമ്മിക നിയമങ്ങൾ, സമ്മതപത്രം, സ്വകാര്യതാ സംരക്ഷണം'
  },
  {
    en: '11. The Difference Between Educational, Clinical & Stage Hypnosis',
    ml: '11. വിദ്യാഭ്യാസ, ക്ലിനിക്കൽ, സ്റ്റേജ് ഹിപ്നോസിസുകൾ തമ്മിലുള്ള വ്യത്യാസം'
  },
  {
    en: '12. Identifying Medical Boundaries: When to Refer to Licensed Doctors',
    ml: '12. മെഡിക്കൽ പരിധികൾ: ഡോക്ടറിലേക്ക് റഫർ ചെയ്യേണ്ട സന്ദർഭങ്ങൾ'
  }
];

export const ETHICS_DIRECTIVES = [
  {
    en: '1. Explicit Informed Consent: Hypnotic procedures must never be initiated without voluntary, documented written/verbal consent.',
    ml: '1. പൂർണ്ണ സമ്മതം: ബന്ധപ്പെട്ട വ്യക്തിയുടെ സ്വമേധയാ ഉള്ള രേഖാമൂലമുള്ള അനുവാദമില്ലാതെ ഒരുവിധ ഹിപ്നോട്ടിക് പ്രക്രിയകളും ആരംഭിക്കാൻ പാടില്ല.'
  },
  {
    en: '2. Total Dignity & Respect: The subject must be treated with unwavering moral dignity. No degrading suggestions or jokes permitted.',
    ml: '2. ആദരവും മര്യാദയും: പങ്കെടുക്കുന്ന വ്യക്തിയുടെ മാന്യതയും വ്യക്തിത്വവും സംരക്ഷിക്കപ്പെടണം. യാതൊരുവിധ മോശം നിർദ്ദേശങ്ങളും നൽകരുത്.'
  },
  {
    en: '3. Absolute Confidentiality: All personal information, discussions, and session contents are strictly private and legally protected.',
    ml: '3. സ്വകാര്യത: സെഷനിൽ പങ്കുവെക്കുന്ന എല്ലാ വിവരങ്ങളും തികച്ചും സ്വകാര്യമായി സൂക്ഷിക്കേണ്ടതാണ്.'
  },
  {
    en: '4. Non-Medical Boundary: Never claim to diagnose, treat, or cure clinical diseases, severe psychiatric disorders, or medical illnesses without licensed healthcare authority.',
    ml: '4. മെഡിക്കൽ പരിധി: ലൈസൻസുള്ള ഡോക്ടറുടെ അനുമതിയില്ലാതെ മാനസിക രോഗങ്ങളോ മറ്റ് ശാരീരിക അസുഖങ്ങളോ ചികിത്സിക്കാൻ ഇത് ഉപയോഗിക്കരുത്.'
  },
  {
    en: '5. Mandatory Safe Emergence: Always guide participants through the complete 1-to-5 emergence protocol, ensuring full motor awareness and mental equilibrium.',
    ml: '5. സുരക്ഷിതമായ തിരിച്ചുവരവ്: സെഷൻ അവസാനിപ്പിക്കുമ്പോൾ വ്യക്തി പൂർണ്ണ ഉന്മേഷത്തിലും സാധാരണ ബോധത്തിലും എത്തിയെന്ന് ഉറപ്പാക്കുക.'
  },
  {
    en: '6. No Coercion / Will-Override: Never attempt to induce trance against someone\'s expressed will. Hypnosis is inherently collaborative.',
    ml: '6. നിർബന്ധപൂർവ്വമായ ഇടപെടൽ പാടില്ല: ഒരാളുടെ അനുവാദമില്ലാതെയോ ഇഷ്ടത്തിന് വിരുദ്ധമായോ ഹിപ്നോസിസ് നടത്താൻ പാടില്ല.'
  }
];
