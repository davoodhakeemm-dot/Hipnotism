import React, { useState } from 'react';
import { HypnotismStore, OWNER_ADMIN_EMAIL } from '../services/store';
import { RegistrationStatus } from '../types';
import { 
  Shield, 
  Users, 
  BookOpen, 
  Video, 
  CheckCircle, 
  XCircle, 
  Plus, 
  Trash2, 
  LogOut, 
  Mail, 
  Phone, 
  Clock, 
  Lock,
  Search
} from 'lucide-react';

interface AdminDashboardPageProps {
  store: HypnotismStore;
}

export const AdminDashboardPage: React.FC<AdminDashboardPageProps> = ({ store }) => {
  const { 
    language, 
    registrations, 
    courses, 
    lessons, 
    updateRegistrationStatus, 
    addAuthorizedEmailToCourse, 
    removeAuthorizedEmailFromCourse, 
    addNewLesson,
    logoutAdmin, 
    navigate 
  } = store;

  const isEnglish = language === 'ENGLISH';
  const [activeTab, setActiveTab] = useState<'students' | 'whitelist' | 'lessons'>('students');
  const [searchQuery, setSearchQuery] = useState('');

  // Course Whitelist states
  const [selectedCourseForWhitelist, setSelectedCourseForWhitelist] = useState(courses[0]?.id || 'hypno-ml');
  const [newWhitelistEmail, setNewWhitelistEmail] = useState('');

  // Add Lesson Form states
  const [selectedCourseForLesson, setSelectedCourseForLesson] = useState(courses[0]?.id || 'hypno-ml');
  const [newLessonTitleEn, setNewLessonTitleEn] = useState('');
  const [newLessonTitleMl, setNewLessonTitleMl] = useState('');
  const [newLessonDuration, setNewLessonDuration] = useState('30 mins');
  const [newLessonVideoUrl, setNewLessonVideoUrl] = useState('');
  const [newLessonDescEn, setNewLessonDescEn] = useState('');
  const [newLessonDescMl, setNewLessonDescMl] = useState('');
  const [lessonAddedSuccess, setLessonAddedSuccess] = useState(false);

  const filteredRegistrations = registrations.filter(r => 
    r.fullName.toLowerCase().includes(searchQuery.toLowerCase()) ||
    r.gmailAddress.toLowerCase().includes(searchQuery.toLowerCase()) ||
    r.phoneNumber.includes(searchQuery)
  );

  const currentCourse = courses.find(c => c.id === selectedCourseForWhitelist) || courses[0];

  const handleAddWhitelist = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newWhitelistEmail.trim() || !newWhitelistEmail.includes('@')) return;
    addAuthorizedEmailToCourse(selectedCourseForWhitelist, newWhitelistEmail.trim());
    setNewWhitelistEmail('');
  };

  const handleCreateLesson = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newLessonTitleEn.trim() || !newLessonVideoUrl.trim()) return;

    addNewLesson(selectedCourseForLesson, {
      titleEn: newLessonTitleEn.trim(),
      titleMl: newLessonTitleMl.trim() || newLessonTitleEn.trim(),
      duration: newLessonDuration.trim() || '30 mins',
      videoUrl: newLessonVideoUrl.trim(),
      descriptionEn: newLessonDescEn.trim(),
      descriptionMl: newLessonDescMl.trim() || newLessonDescEn.trim(),
      keyTakeawaysEn: ['Comprehensive practical review.'],
      keyTakeawaysMl: ['സമഗ്രമായ പ്രായോഗിക വിലയിരുത്തൽ.'],
      isCompleted: false
    });

    setLessonAddedSuccess(true);
    setNewLessonTitleEn('');
    setNewLessonTitleMl('');
    setNewLessonVideoUrl('');
    setNewLessonDescEn('');
    setNewLessonDescMl('');
    setTimeout(() => setLessonAddedSuccess(false), 3000);
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      
      {/* Top Banner */}
      <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-8 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-6 shadow-xl">
        <div className="flex items-center gap-4">
          <div className="w-14 h-14 rounded-2xl bg-amber-500/10 border-2 border-amber-400 flex items-center justify-center text-amber-400">
            <Shield className="w-7 h-7" />
          </div>
          <div>
            <div className="flex items-center gap-2">
              <h1 className="text-xl sm:text-2xl font-bold text-white">Academy Administration</h1>
              <span className="text-[10px] uppercase font-bold tracking-wider px-2 py-0.5 rounded-full bg-amber-500/10 text-amber-300 border border-amber-500/30">
                Master Security Mode
              </span>
            </div>
            <p className="text-xs text-slate-400 mt-1">
              Logged in as: <strong className="text-cyan-400">{OWNER_ADMIN_EMAIL}</strong>
            </p>
          </div>
        </div>

        <button
          onClick={() => {
            logoutAdmin();
            navigate('home');
          }}
          className="flex items-center gap-2 px-4 py-2 rounded-xl bg-red-950/40 hover:bg-red-900/40 text-red-300 border border-red-800 text-xs font-semibold transition-colors"
        >
          <LogOut className="w-4 h-4" />
          <span>Exit Admin Space</span>
        </button>
      </div>

      {/* Stats Quick Cards */}
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <div className="p-5 rounded-2xl bg-[#0D1322] border border-[#233252]">
          <div className="text-xs text-slate-400">Total Applicants</div>
          <div className="text-2xl font-extrabold text-white mt-1">{registrations.length}</div>
        </div>
        <div className="p-5 rounded-2xl bg-[#0D1322] border border-[#233252]">
          <div className="text-xs text-slate-400">Approved Students</div>
          <div className="text-2xl font-extrabold text-emerald-400 mt-1">
            {registrations.filter(r => r.status === 'APPROVED').length}
          </div>
        </div>
        <div className="p-5 rounded-2xl bg-[#0D1322] border border-[#233252]">
          <div className="text-xs text-slate-400">Pending Review</div>
          <div className="text-2xl font-extrabold text-amber-400 mt-1">
            {registrations.filter(r => r.status === 'PENDING').length}
          </div>
        </div>
        <div className="p-5 rounded-2xl bg-[#0D1322] border border-[#233252]">
          <div className="text-xs text-slate-400">Active Courses</div>
          <div className="text-2xl font-extrabold text-cyan-400 mt-1">{courses.length}</div>
        </div>
      </div>

      {/* Navigation Tabs */}
      <div className="flex items-center gap-2 border-b border-slate-800 pb-2">
        <button
          onClick={() => setActiveTab('students')}
          className={`px-4 py-2 rounded-xl text-xs font-bold transition-all flex items-center gap-2 ${
            activeTab === 'students'
              ? 'bg-cyan-500 text-slate-950 shadow-sm shadow-cyan-500/20'
              : 'bg-[#131B2E] text-slate-400 hover:text-white border border-[#233252]'
          }`}
        >
          <Users className="w-4 h-4" />
          <span>Student Registrations ({registrations.length})</span>
        </button>

        <button
          onClick={() => setActiveTab('whitelist')}
          className={`px-4 py-2 rounded-xl text-xs font-bold transition-all flex items-center gap-2 ${
            activeTab === 'whitelist'
              ? 'bg-cyan-500 text-slate-950 shadow-sm shadow-cyan-500/20'
              : 'bg-[#131B2E] text-slate-400 hover:text-white border border-[#233252]'
          }`}
        >
          <BookOpen className="w-4 h-4" />
          <span>Course Whitelist Access</span>
        </button>

        <button
          onClick={() => setActiveTab('lessons')}
          className={`px-4 py-2 rounded-xl text-xs font-bold transition-all flex items-center gap-2 ${
            activeTab === 'lessons'
              ? 'bg-cyan-500 text-slate-950 shadow-sm shadow-cyan-500/20'
              : 'bg-[#131B2E] text-slate-400 hover:text-white border border-[#233252]'
          }`}
        >
          <Video className="w-4 h-4" />
          <span>Add Lecture Video</span>
        </button>
      </div>

      {/* TAB 1: Student Registrations Management */}
      {activeTab === 'students' && (
        <div className="space-y-4">
          <div className="flex flex-col sm:flex-row items-center justify-between gap-4">
            <div className="relative w-full sm:w-72">
              <Search className="w-4 h-4 text-slate-500 absolute left-3 top-3" />
              <input
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search student or email..."
                className="w-full pl-9 pr-4 py-2 bg-[#131B2E] border border-[#233252] rounded-xl text-xs text-white placeholder-slate-500 outline-none"
              />
            </div>
            <span className="text-xs text-slate-400">
              Showing {filteredRegistrations.length} students
            </span>
          </div>

          {filteredRegistrations.length === 0 ? (
            <div className="p-12 text-center rounded-3xl bg-[#0D1322] border border-slate-800 space-y-2">
              <Users className="w-12 h-12 text-slate-600 mx-auto" />
              <h3 className="text-base font-bold text-slate-300">No Student Registrations Found</h3>
              <p className="text-xs text-slate-500 max-w-sm mx-auto">
                Applications submitted by students through the public admission form will appear here for verification.
              </p>
            </div>
          ) : (
            <div className="space-y-3">
              {filteredRegistrations.map((student) => {
                const course = courses.find(c => c.id === student.selectedCourseId);

                return (
                  <div
                    key={student.id}
                    className="p-5 rounded-2xl bg-[#0D1322] border border-[#233252] flex flex-col md:flex-row items-start md:items-center justify-between gap-4 shadow-lg"
                  >
                    <div className="space-y-1 flex-1">
                      <div className="flex items-center gap-3">
                        <span className="text-sm font-bold text-white">{student.fullName}</span>
                        <span className="text-xs text-slate-400">({student.age} yrs)</span>
                        <span
                          className={`px-2.5 py-0.5 rounded-full text-[10px] font-bold ${
                            student.status === 'APPROVED'
                              ? 'bg-emerald-950 text-emerald-300 border border-emerald-700'
                              : student.status === 'PENDING'
                              ? 'bg-amber-950 text-amber-300 border border-amber-700'
                              : 'bg-red-950 text-red-300 border border-red-700'
                          }`}
                        >
                          {student.status}
                        </span>
                      </div>

                      <div className="flex flex-wrap items-center gap-4 text-xs text-slate-400 pt-1">
                        <span className="flex items-center gap-1 text-cyan-300">
                          <Mail className="w-3.5 h-3.5" />
                          {student.gmailAddress}
                        </span>
                        <span className="flex items-center gap-1">
                          <Phone className="w-3.5 h-3.5" />
                          {student.phoneNumber}
                        </span>
                        <span>
                          <strong>Course:</strong> {course?.titleEn || student.selectedCourseId}
                        </span>
                        <span>
                          <strong>Applied:</strong> {student.registrationDate}
                        </span>
                      </div>

                      <div className="text-[11px] text-slate-500">
                        Address: {student.address}
                      </div>
                    </div>

                    {/* Action Buttons */}
                    <div className="flex items-center gap-2 self-end md:self-center">
                      {student.status !== 'APPROVED' && (
                        <button
                          onClick={() => updateRegistrationStatus(student.id, 'APPROVED')}
                          className="px-3.5 py-1.5 rounded-xl bg-emerald-600 hover:bg-emerald-500 text-white font-bold text-xs flex items-center gap-1.5 transition-colors shadow-sm"
                        >
                          <CheckCircle className="w-3.5 h-3.5" />
                          <span>Approve & Authorize</span>
                        </button>
                      )}

                      {student.status !== 'REJECTED' && (
                        <button
                          onClick={() => updateRegistrationStatus(student.id, 'REJECTED')}
                          className="px-3 py-1.5 rounded-xl bg-red-950/60 hover:bg-red-900/60 text-red-300 border border-red-800 font-semibold text-xs flex items-center gap-1 transition-colors"
                        >
                          <XCircle className="w-3.5 h-3.5" />
                          <span>Reject</span>
                        </button>
                      )}
                    </div>
                  </div>
                );
              })}
            </div>
          )}
        </div>
      )}

      {/* TAB 2: Course Whitelist Manager */}
      {activeTab === 'whitelist' && (
        <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-8 space-y-6">
          <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
            <div>
              <h2 className="text-lg font-bold text-white">Course Video Whitelist Manager</h2>
              <p className="text-xs text-slate-400">
                Directly manage which student Gmail accounts have authorized access to lectures
              </p>
            </div>

            <select
              value={selectedCourseForWhitelist}
              onChange={(e) => setSelectedCourseForWhitelist(e.target.value)}
              className="px-3 py-2 rounded-xl bg-[#131B2E] border border-[#233252] text-xs text-white outline-none"
            >
              {courses.map(c => (
                <option key={c.id} value={c.id}>
                  {c.titleEn}
                </option>
              ))}
            </select>
          </div>

          {/* Add email to course */}
          <form onSubmit={handleAddWhitelist} className="flex gap-2">
            <input
              type="email"
              value={newWhitelistEmail}
              onChange={(e) => setNewWhitelistEmail(e.target.value)}
              placeholder="student.email@gmail.com"
              className="flex-1 px-4 py-2.5 bg-[#131B2E] border border-[#233252] rounded-xl text-xs text-white placeholder-slate-500 outline-none focus:border-cyan-400"
            />
            <button
              type="submit"
              className="px-5 py-2.5 rounded-xl bg-cyan-400 hover:bg-cyan-300 text-slate-950 font-bold text-xs flex items-center gap-1.5 transition-colors"
            >
              <Plus className="w-4 h-4" />
              <span>Authorize Gmail</span>
            </button>
          </form>

          {/* Whitelisted emails list */}
          <div className="space-y-2 pt-2">
            <h3 className="text-xs font-bold text-slate-300 uppercase tracking-wider">
              Whitelisted Accounts ({currentCourse.authorizedEmails.length})
            </h3>

            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-3">
              {currentCourse.authorizedEmails.map((email) => (
                <div
                  key={email}
                  className="p-3 rounded-xl bg-[#080B12] border border-slate-800 flex items-center justify-between gap-2 text-xs"
                >
                  <span className="text-slate-200 truncate">{email}</span>
                  {email.toLowerCase() !== OWNER_ADMIN_EMAIL.toLowerCase() ? (
                    <button
                      onClick={() => removeAuthorizedEmailFromCourse(currentCourse.id, email)}
                      className="text-slate-500 hover:text-red-400 p-1"
                      title="Revoke access"
                    >
                      <Trash2 className="w-3.5 h-3.5" />
                    </button>
                  ) : (
                    <span className="text-[10px] text-amber-400 font-bold">Owner</span>
                  )}
                </div>
              ))}
            </div>
          </div>
        </div>
      )}

      {/* TAB 3: Add Lecture Video */}
      {activeTab === 'lessons' && (
        <div className="bg-[#0D1322] border border-[#233252] rounded-3xl p-6 sm:p-8 space-y-6">
          <div>
            <h2 className="text-lg font-bold text-white">Publish New Video Lecture</h2>
            <p className="text-xs text-slate-400">
              Upload course video link and study description for enrolled students
            </p>
          </div>

          {lessonAddedSuccess && (
            <div className="p-4 rounded-xl bg-emerald-950/40 border border-emerald-800 text-emerald-300 text-xs font-bold">
              New lecture added to course successfully!
            </div>
          )}

          <form onSubmit={handleCreateLesson} className="space-y-4">
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div className="space-y-1.5">
                <label className="text-xs font-semibold text-slate-300">Target Course</label>
                <select
                  value={selectedCourseForLesson}
                  onChange={(e) => setSelectedCourseForLesson(e.target.value)}
                  className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] rounded-xl text-xs text-white outline-none"
                >
                  {courses.map(c => (
                    <option key={c.id} value={c.id}>
                      {c.titleEn}
                    </option>
                  ))}
                </select>
              </div>

              <div className="space-y-1.5">
                <label className="text-xs font-semibold text-slate-300">Duration</label>
                <input
                  type="text"
                  value={newLessonDuration}
                  onChange={(e) => setNewLessonDuration(e.target.value)}
                  placeholder="35 mins"
                  className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] rounded-xl text-xs text-white outline-none"
                  required
                />
              </div>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div className="space-y-1.5">
                <label className="text-xs font-semibold text-slate-300">Lesson Title (English)</label>
                <input
                  type="text"
                  value={newLessonTitleEn}
                  onChange={(e) => setNewLessonTitleEn(e.target.value)}
                  placeholder="e.g. Lesson 5: Advanced Suggestion Mechanics"
                  className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] rounded-xl text-xs text-white outline-none"
                  required
                />
              </div>

              <div className="space-y-1.5">
                <label className="text-xs font-semibold text-slate-300">Lesson Title (Malayalam)</label>
                <input
                  type="text"
                  value={newLessonTitleMl}
                  onChange={(e) => setNewLessonTitleMl(e.target.value)}
                  placeholder="പാഠം 5: സജഷൻ രീതികൾ"
                  className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] rounded-xl text-xs text-white outline-none"
                />
              </div>
            </div>

            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-slate-300">Video Stream URL (MP4 / HLS / Firebase Storage)</label>
              <input
                type="url"
                value={newLessonVideoUrl}
                onChange={(e) => setNewLessonVideoUrl(e.target.value)}
                placeholder="https://commondatastorage.googleapis.com/.../video.mp4"
                className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] rounded-xl text-xs text-white outline-none"
                required
              />
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div className="space-y-1.5">
                <label className="text-xs font-semibold text-slate-300">Description (English)</label>
                <textarea
                  rows={3}
                  value={newLessonDescEn}
                  onChange={(e) => setNewLessonDescEn(e.target.value)}
                  placeholder="Educational summary..."
                  className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] rounded-xl text-xs text-white outline-none"
                />
              </div>

              <div className="space-y-1.5">
                <label className="text-xs font-semibold text-slate-300">Description (Malayalam)</label>
                <textarea
                  rows={3}
                  value={newLessonDescMl}
                  onChange={(e) => setNewLessonDescMl(e.target.value)}
                  placeholder="മലയാളത്തിലുള്ള വിശദീകരണം..."
                  className="w-full px-4 py-2.5 bg-[#131B2E] border border-[#233252] rounded-xl text-xs text-white outline-none"
                />
              </div>
            </div>

            <button
              type="submit"
              className="px-6 py-3 rounded-xl bg-cyan-400 hover:bg-cyan-300 text-slate-950 font-bold text-xs flex items-center gap-2 transition-colors shadow-md"
            >
              <Plus className="w-4 h-4" />
              <span>Publish Lecture to Course</span>
            </button>
          </form>
        </div>
      )}

    </div>
  );
};
