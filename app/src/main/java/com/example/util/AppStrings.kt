package com.example.util

import com.example.model.AppLanguage

object AppStrings {
  fun appTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "HYPNOTISM"
    AppLanguage.MALAYALAM -> "ഹിപ്നോട്ടിസം"
  }

  fun subtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "Understand the Mind • Master Your Focus • Practice Responsibly"
    AppLanguage.MALAYALAM -> "മനസ്സിനെ മനസ്സിലാക്കുക • ശ്രദ്ധയെ നിയന്ത്രിക്കുക • ഉത്തരവാദിത്തത്തോടെ പരിശീലിക്കുക"
  }

  fun joinClass(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "Join Class"
    AppLanguage.MALAYALAM -> "ക്ലാസ്സിൽ ചേരുക"
  }

  fun login(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "Student Login"
    AppLanguage.MALAYALAM -> "വിദ്യാർത്ഥി ലോഗിൻ"
  }

  fun courseIntroTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "Introduction to Hypnotism"
    AppLanguage.MALAYALAM -> "ഹിപ്നോട്ടിസം ആമുഖം"
  }

  fun courseIntroBody(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> """
Hypnotism is a technique involving focused attention, relaxation, and increased responsiveness to suggestions.

Hypnosis is commonly described as a state involving focused attention and heightened suggestibility. It is not the same as ordinary sleep, and a person generally retains awareness and the ability to respond according to their own choices.

Hypnotism has been studied in psychological and scientific contexts and has also been used in performance and certain complementary or clinical settings by appropriately trained professionals.

The course should teach students to understand hypnotism responsibly rather than presenting it as supernatural mind control.
""".trimIndent()
    AppLanguage.MALAYALAM -> """
ഹിപ്നോട്ടിസം (Hypnotism) എന്നത് ശ്രദ്ധയെ ഒരു പ്രത്യേക കാര്യത്തിൽ കേന്ദ്രീകരിക്കൽ, വിശ്രമാവസ്ഥ, നിർദ്ദേശങ്ങളോട് കൂടുതൽ ശ്രദ്ധ പുലർത്തുന്ന അവസ്ഥ എന്നിവയുമായി ബന്ധപ്പെട്ട ഒരു സാങ്കേതികവിദ്യയാണ്.

ഹിപ്നോസിസ് സാധാരണ ഉറക്കത്തിന് തുല്യമല്ല. ഹിപ്നോട്ടിക് അവസ്ഥയിലുള്ള വ്യക്തിക്ക് ചുറ്റുപാടുകളെക്കുറിച്ച് ഒരു പരിധിവരെ ബോധവാനായിരിക്കാം. ഒരാളുടെ മനസ്സിന്റെ പൂർണ്ണ നിയന്ത്രണം മറ്റൊരാൾക്ക് ലഭിക്കുന്നു എന്നത് ഹിപ്നോസിസിന്റെ ശാസ്ത്രീയമായ വിവരണം അല്ല.

ഈ കോഴ്സ് ഹിപ്നോട്ടിസത്തെ ശാസ്ത്രീയവും ഉത്തരവാദിത്തപരവുമായ രീതിയിൽ മനസ്സിലാക്കാൻ സഹായിക്കുന്നതിനാണ്.
""".trimIndent()
  }

  fun whatStudentsLearnTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "What Students Learn"
    AppLanguage.MALAYALAM -> "വിദ്യാർത്ഥികൾക്ക് പഠിക്കാവുന്ന വിഷയങ്ങൾ"
  }

  fun whatStudentsLearnItems(lang: AppLanguage): List<String> = when (lang) {
    AppLanguage.ENGLISH -> listOf(
      "Meaning of hypnosis and hypnotism",
      "History of hypnotism",
      "Focus and concentration",
      "Relaxation",
      "Suggestion",
      "Imagination",
      "Communication",
      "Observation",
      "Hypnotic states",
      "Common myths",
      "Scientific perspectives",
      "Ethics",
      "Consent",
      "Safety",
      "Stage hypnosis",
      "Therapeutic hypnosis",
      "Responsible practice"
    )
    AppLanguage.MALAYALAM -> listOf(
      "Hypnosis, Hypnotism എന്നിവയുടെ അടിസ്ഥാന ആശയങ്ങൾ",
      "ഹിപ്നോട്ടിസത്തിന്റെ ചരിത്രം",
      "ശ്രദ്ധയും ഏകാഗ്രതയും",
      "Relaxation (വിശ്രാന്തി)",
      "Suggestion (നിർദ്ദേശങ്ങൾ)",
      "Imagination (ഭാവന)",
      "ആശയവിനിമയം (Communication)",
      "നിരീക്ഷണം (Observation)",
      "Hypnotic states (ഹിപ്നോട്ടിക് അവസ്ഥകൾ)",
      "പൊതുവായ തെറ്റിദ്ധാരണകൾ (Myths & Facts)",
      "ശാസ്ത്രീയ കാഴ്ചപ്പാടുകൾ (Scientific Perspectives)",
      "Ethics (ധാർമ്മികത)",
      "Consent (സമ്മതം)",
      "Safety (സുരക്ഷ)",
      "Stage hypnosis (സ്റ്റേജ് ഹിപ്നോസിസ്)",
      "Therapeutic hypnosis (തെറാപ്പിറ്റിക് ഹിപ്നോസിസ്)",
      "ഉത്തരവാദിത്തപരമായ സമീപനം (Responsible Practice)"
    )
  }

  fun ethicsTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "Ethics & Safety — Learn Responsibly"
    AppLanguage.MALAYALAM -> "ധാർമ്മികതയും സുരക്ഷയും — ഉത്തരവാദിത്തത്തോടെ പഠിക്കുക"
  }

  fun ethicsSub(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "Consent • Safety • Respect • Responsibility"
    AppLanguage.MALAYALAM -> "സമ്മതം • സുരക്ഷ • ആദരവ് • ഉത്തരവാദിത്തം"
  }

  fun ethicsItems(lang: AppLanguage): List<String> = when (lang) {
    AppLanguage.ENGLISH -> listOf(
      "Obtain informed consent before attempting any hypnotic exercises.",
      "Respect personal boundaries at all times.",
      "Never present hypnotism as guaranteed or supernatural mind control.",
      "Never pressure someone to participate against their free will.",
      "Do not use hypnotism to manipulate, exploit, frighten, or deceive people.",
      "Do not claim to diagnose or treat medical/mental-health conditions without appropriate professional qualifications.",
      "Clinical hypnosis should be conducted exclusively by appropriately trained healthcare professionals."
    )
    AppLanguage.MALAYALAM -> listOf(
      "ഏതൊരു ഹിപ്നോട്ടിക് പരിശീലനത്തിന് മുമ്പും വ്യക്തിയുടെ പൂർണ്ണ സമ്മതം (Informed Consent) നേടുക.",
      "വ്യക്തിപരമായ അതിരുകൾ എപ്പോഴും ആദരിക്കുക.",
      "ഹിപ്നോട്ടിസത്തെ മനസ്സിൻമേലുള്ള അമാനുഷിക നിയന്ത്രണമായി ഒരിക്കലും ചിത്രീകരിക്കരുത്.",
      "ഒരാളെയും സ്വന്തം ഇഷ്ടത്തിന് വിരുദ്ധമായി പങ്കെടുക്കാൻ നിർബന്ധിക്കരുത്.",
      "ആളുകളെ ചൂഷണം ചെയ്യാനോ, ഭയപ്പെടുത്താനോ, വഞ്ചിക്കാനോ ഹിപ്നോട്ടിസം ഉപയോഗിക്കരുത്.",
      "മതിയായ അംഗീകൃത യോഗ്യതയില്ലാതെ ശാരീരിക/മാനസിക രോഗങ്ങൾ നിർണ്ണയിക്കാനോ ചികിത്സിക്കാനോ അവകാശപ്പെടരുത്.",
      "ക്ലിനിക്കൽ ഹിപ്നോസിസ് പരിശീലനം ലഭിച്ച ആരോഗ്യ വിദഗ്ദ്ധർ മാത്രമേ നിർവഹിക്കാവൂ."
    )
  }

  fun privacyNotice(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "Student Privacy Notice: Student information is encrypted and confidential. It is only accessible by the authorized instructor/admin and will NEVER be visible to other students or third parties."
    AppLanguage.MALAYALAM -> "സ്വകാര്യതാ നയം: വിദ്യാർത്ഥികളുടെ വിവരങ്ങൾ പൂർണ്ണമായും രഹസ്യമായി സൂക്ഷിക്കപ്പെടുന്നതാണ്. അധ്യാപകന്/അഡ്മിന് മാത്രമേ ഇത് കാണാൻ സാധിക്കൂ. മറ്റ് വിദ്യാർത്ഥികൾക്ക് ഇത് ഒരിക്കലും ലഭ്യമാകില്ല."
  }

  fun consentCheckboxText(lang: AppLanguage): String = when (lang) {
    AppLanguage.ENGLISH -> "I agree to practice ethically and responsibly, adhere to safety guidelines, and consent to identity verification with my Gmail account."
    AppLanguage.MALAYALAM -> "ഞാൻ ഈ വിജ്ഞാനം ധാർമ്മികമായും ഉത്തരവാദിത്തത്തോടെയും മാത്രമേ ഉപയോഗിക്കൂ എന്നും, സുരക്ഷാ മാനദണ്ഡങ്ങൾ പാലിക്കുമെന്നും, എന്റെ ജിമെയിൽ അക്കൗണ്ട് സ്ഥിരീകരണത്തിന് സമ്മതിക്കുന്നു എന്നും ഉറപ്പുനൽകുന്നു."
  }
}
