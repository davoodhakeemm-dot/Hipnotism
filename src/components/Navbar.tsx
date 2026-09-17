import React, { useState } from 'react';
import { HypnotismStore } from '../services/store';
import { PageRoute } from '../types';
import { 
  Compass, 
  Globe, 
  Menu, 
  X, 
  Shield, 
  User, 
  GraduationCap, 
  BookOpen, 
  Home, 
  Sparkles 
} from 'lucide-react';

interface NavbarProps {
  store: HypnotismStore;
  onOpenAdminDialog: () => void;
}

export const Navbar: React.FC<NavbarProps> = ({ store, onOpenAdminDialog }) => {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const { language, toggleLanguage, currentRoute, navigate, currentUser, isAdminAuthenticated } = store;

  const isEnglish = language === 'ENGLISH';

  const navLinks: { route: PageRoute; labelEn: string; labelMl: string; icon: React.ReactNode }[] = [
    { route: 'home', labelEn: 'Home', labelMl: 'ഹോം', icon: <Home className="w-4 h-4" /> },
    { route: 'courses', labelEn: 'Courses', labelMl: 'കോഴ്സുകൾ', icon: <GraduationCap className="w-4 h-4" /> },
    { route: 'what-students-learn', labelEn: 'What You Learn', labelMl: 'പഠന വിഷയങ്ങൾ', icon: <BookOpen className="w-4 h-4" /> },
    { route: 'ethics-safety', labelEn: 'Ethics & Safety', labelMl: 'ധാർമ്മികത', icon: <Shield className="w-4 h-4" /> },
    { route: 'course-intro', labelEn: 'About Course', labelMl: 'ആമുഖം', icon: <Sparkles className="w-4 h-4" /> },
  ];

  const handleNav = (route: PageRoute) => {
    navigate(route);
    setMobileMenuOpen(false);
  };

  return (
    <header className="sticky top-0 z-50 bg-[#080B12]/95 backdrop-blur-md border-b border-[#233252] transition-colors">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-20">
          
          {/* Brand Logo & Name */}
          <div 
            onClick={() => handleNav('home')} 
            className="flex items-center gap-3 cursor-pointer group"
          >
            <div className="w-11 h-11 rounded-full bg-cyan-500/10 border-2 border-cyan-400 flex items-center justify-center text-cyan-400 group-hover:scale-105 transition-transform shadow-[0_0_15px_rgba(0,229,255,0.2)]">
              <Compass className="w-6 h-6 animate-pulse" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <span className="text-xl font-extrabold tracking-wider bg-gradient-to-r from-cyan-400 to-indigo-400 bg-clip-text text-transparent">
                  HYPNOTISM
                </span>
                <span className="text-xs font-semibold px-2 py-0.5 rounded-full bg-[#131B2E] text-cyan-300 border border-cyan-500/30">
                  ഹിപ്നോട്ടിസം
                </span>
              </div>
              <p className="text-[11px] font-medium text-slate-400 tracking-wide">
                {isEnglish ? 'Scientific Educational Academy' : 'ശാസ്ത്രീയ പഠന കേന്ദ്രം'}
              </p>
            </div>
          </div>

          {/* Desktop Navigation Links */}
          <nav className="hidden lg:flex items-center gap-1 xl:gap-2">
            {navLinks.map((link) => {
              const isActive = currentRoute === link.route;
              return (
                <button
                  key={link.route}
                  onClick={() => handleNav(link.route)}
                  className={`flex items-center gap-1.5 px-3 py-2 rounded-lg text-xs xl:text-sm font-semibold transition-all ${
                    isActive
                      ? 'bg-[#131B2E] text-cyan-400 border border-cyan-500/40 shadow-sm'
                      : 'text-slate-300 hover:text-white hover:bg-slate-800/40'
                  }`}
                >
                  {link.icon}
                  <span>{isEnglish ? link.labelEn : link.labelMl}</span>
                </button>
              );
            })}
          </nav>

          {/* Right Action Tools */}
          <div className="hidden sm:flex items-center gap-3">
            
            {/* Bilingual Toggle */}
            <button
              onClick={toggleLanguage}
              className="flex items-center gap-1.5 px-3 py-1.5 rounded-full bg-[#131B2E] border border-[#233252] text-xs font-semibold text-slate-300 hover:text-white hover:border-cyan-500/50 transition-colors"
              title="Toggle Language / ഭാഷ മാറ്റുക"
            >
              <Globe className="w-3.5 h-3.5 text-cyan-400" />
              <span>{isEnglish ? 'മലയാളം' : 'English'}</span>
            </button>

            {/* Join Class / Registration */}
            <button
              onClick={() => handleNav('register')}
              className={`px-3.5 py-1.5 rounded-lg text-xs font-bold transition-all ${
                currentRoute === 'register'
                  ? 'bg-amber-500 text-slate-950 shadow-md shadow-amber-500/20'
                  : 'bg-amber-500/10 text-amber-400 border border-amber-500/40 hover:bg-amber-500/20'
              }`}
            >
              {isEnglish ? 'Join Class' : 'ക്ലാസ്സിൽ ചേരുക'}
            </button>

            {/* Student Portal */}
            <button
              onClick={() => handleNav('student-dashboard')}
              className={`flex items-center gap-1.5 px-3.5 py-1.5 rounded-lg text-xs font-bold transition-all ${
                currentRoute === 'student-dashboard'
                  ? 'bg-indigo-600 text-white shadow-md shadow-indigo-500/20'
                  : 'bg-indigo-600/20 text-indigo-300 border border-indigo-500/30 hover:bg-indigo-600/30'
              }`}
            >
              <User className="w-3.5 h-3.5" />
              <span>{currentUser ? currentUser.fullName.split(' ')[0] : (isEnglish ? 'Student Portal' : 'വിദ്യാർത്ഥി')}</span>
            </button>

            {/* Admin Space */}
            <button
              onClick={() => {
                if (isAdminAuthenticated) {
                  navigate('admin-dashboard');
                } else {
                  onOpenAdminDialog();
                }
              }}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-semibold transition-all ${
                currentRoute === 'admin-dashboard'
                  ? 'bg-slate-700 text-cyan-300 border border-cyan-400'
                  : 'bg-slate-800/80 text-slate-400 border border-slate-700 hover:text-white hover:border-slate-600'
              }`}
              title="Admin Space"
            >
              <Shield className="w-3.5 h-3.5 text-amber-400" />
              <span>Admin</span>
            </button>

          </div>

          {/* Mobile menu hamburger button */}
          <div className="flex sm:hidden items-center gap-2">
            <button
              onClick={toggleLanguage}
              className="p-1.5 rounded-lg bg-[#131B2E] border border-[#233252] text-xs font-semibold text-cyan-400"
            >
              {isEnglish ? 'മല' : 'EN'}
            </button>

            <button
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
              className="p-2 rounded-lg bg-[#131B2E] border border-[#233252] text-slate-300 hover:text-white"
              aria-label="Toggle menu"
            >
              {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
            </button>
          </div>

        </div>
      </div>

      {/* Mobile Dropdown Drawer */}
      {mobileMenuOpen && (
        <div className="sm:hidden bg-[#0D1322] border-b border-[#233252] px-4 pt-3 pb-6 space-y-3 animate-in slide-in-from-top-2 duration-200">
          <div className="space-y-1">
            {navLinks.map((link) => {
              const isActive = currentRoute === link.route;
              return (
                <button
                  key={link.route}
                  onClick={() => handleNav(link.route)}
                  className={`w-full flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-semibold transition-all ${
                    isActive
                      ? 'bg-[#131B2E] text-cyan-400 border border-cyan-500/40'
                      : 'text-slate-300 hover:bg-slate-800/50'
                  }`}
                >
                  {link.icon}
                  <span>{isEnglish ? link.labelEn : link.labelMl}</span>
                </button>
              );
            })}
          </div>

          <div className="pt-3 border-t border-slate-800 flex flex-col gap-2">
            <button
              onClick={() => handleNav('register')}
              className="w-full py-2.5 rounded-lg bg-amber-500/20 text-amber-300 border border-amber-500/40 font-bold text-sm text-center"
            >
              {isEnglish ? 'Join Class' : 'ക്ലാസ്സിൽ ചേരുക'}
            </button>

            <button
              onClick={() => handleNav('student-dashboard')}
              className="w-full py-2.5 rounded-lg bg-indigo-600/30 text-indigo-300 border border-indigo-500/40 font-bold text-sm flex items-center justify-center gap-2"
            >
              <User className="w-4 h-4" />
              <span>{currentUser ? currentUser.fullName : (isEnglish ? 'Student Portal' : 'വിദ്യാർത്ഥി പോർട്ടൽ')}</span>
            </button>

            <button
              onClick={() => {
                setMobileMenuOpen(false);
                if (isAdminAuthenticated) {
                  navigate('admin-dashboard');
                } else {
                  onOpenAdminDialog();
                }
              }}
              className="w-full py-2 rounded-lg bg-slate-800 text-slate-300 border border-slate-700 text-xs font-semibold flex items-center justify-center gap-2"
            >
              <Shield className="w-3.5 h-3.5 text-amber-400" />
              <span>Admin Space (അഡ്മിൻ)</span>
            </button>
          </div>
        </div>
      )}
    </header>
  );
};
