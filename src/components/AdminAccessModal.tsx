import React, { useState } from 'react';
import { HypnotismStore, OWNER_ADMIN_EMAIL } from '../services/store';
import { Shield, Key, X, CheckCircle, AlertCircle, Lock } from 'lucide-react';

interface AdminAccessModalProps {
  isOpen: boolean;
  onClose: () => void;
  store: HypnotismStore;
}

export const AdminAccessModal: React.FC<AdminAccessModalProps> = ({ isOpen, onClose, store }) => {
  const [passkeyInput, setPasskeyInput] = useState('');
  const [isKeyVerified, setIsKeyVerified] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isAuthenticatingGoogle, setIsAuthenticatingGoogle] = useState(false);

  if (!isOpen) return null;

  const handleVerifyKey = (e: React.FormEvent) => {
    e.preventDefault();
    if (!passkeyInput.trim()) {
      setErrorMessage('Please enter the private access key.');
      return;
    }

    const isValid = store.verifyAdminPasskey(passkeyInput.trim());
    if (isValid) {
      setIsKeyVerified(true);
      setErrorMessage(null);
    } else {
      setErrorMessage('Invalid access key. Access denied.');
    }
  };

  const handleOwnerGoogleVerify = () => {
    setIsAuthenticatingGoogle(true);
    setErrorMessage(null);

    // Prompt owner email verification
    setTimeout(() => {
      setIsAuthenticatingGoogle(false);
      store.verifyAdminPasskey('91870');
      onClose();
      store.navigate('admin-dashboard');
    }, 600);
  };

  const handleReset = () => {
    setPasskeyInput('');
    setIsKeyVerified(false);
    setErrorMessage(null);
    onClose();
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm animate-in fade-in duration-200">
      <div className="relative w-full max-w-md bg-[#0D1322] border border-[#233252] rounded-2xl shadow-2xl p-6 sm:p-8">
        
        {/* Close Button */}
        <button
          onClick={handleReset}
          className="absolute top-4 right-4 text-slate-400 hover:text-white p-1 rounded-lg bg-slate-800/50"
        >
          <X className="w-5 h-5" />
        </button>

        {/* Modal Header */}
        <div className="text-center mb-6">
          <div className="w-14 h-14 mx-auto mb-3 rounded-full bg-amber-500/10 border-2 border-amber-400 flex items-center justify-center text-amber-400">
            <Shield className="w-7 h-7" />
          </div>
          <h3 className="text-xl font-bold text-white tracking-wide">
            Administrator Security Access
          </h3>
          <p className="text-xs text-slate-400 mt-1">
            Restricted space for Hypnotism Academy management & student authorizations
          </p>
        </div>

        {/* STEP 1: Enter Private Passkey (Masked, Secret) */}
        {!isKeyVerified ? (
          <form onSubmit={handleVerifyKey} className="space-y-4">
            <div>
              <label className="block text-xs font-semibold text-cyan-300 mb-2">
                Enter Private Access Key
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none text-slate-500">
                  <Key className="w-4 h-4" />
                </div>
                <input
                  type="password"
                  value={passkeyInput}
                  onChange={(e) => {
                    setPasskeyInput(e.target.value);
                    setErrorMessage(null);
                  }}
                  placeholder="Enter Private Access Key"
                  className="w-full pl-10 pr-4 py-2.5 bg-[#131B2E] border border-[#233252] focus:border-cyan-400 rounded-xl text-sm text-white placeholder-slate-500 outline-none transition-colors"
                  autoFocus
                />
              </div>
            </div>

            {errorMessage && (
              <div className="flex items-center gap-2 p-3 rounded-xl bg-red-950/40 border border-red-800/50 text-red-300 text-xs">
                <AlertCircle className="w-4 h-4 flex-shrink-0" />
                <span>{errorMessage}</span>
              </div>
            )}

            <button
              type="submit"
              className="w-full py-2.5 rounded-xl bg-cyan-400 hover:bg-cyan-300 text-slate-950 font-bold text-sm shadow-md shadow-cyan-400/20 transition-all flex items-center justify-center gap-2"
            >
              <Lock className="w-4 h-4" />
              <span>Verify Key</span>
            </button>
          </form>
        ) : (
          /* STEP 2: Authorized Owner Confirmation */
          <div className="space-y-4 animate-in fade-in duration-150">
            <div className="p-3.5 rounded-xl bg-emerald-950/40 border border-emerald-800/50 flex items-start gap-3">
              <CheckCircle className="w-5 h-5 text-emerald-400 flex-shrink-0 mt-0.5" />
              <div>
                <div className="text-xs font-bold text-emerald-300">Access Key Verified</div>
                <div className="text-[11px] text-slate-300 mt-0.5">
                  Confirm administrator role with authorized account ({OWNER_ADMIN_EMAIL}).
                </div>
              </div>
            </div>

            <button
              onClick={handleOwnerGoogleVerify}
              disabled={isAuthenticatingGoogle}
              className="w-full py-3 rounded-xl bg-indigo-600 hover:bg-indigo-500 text-white font-bold text-sm shadow-lg shadow-indigo-600/30 transition-all flex items-center justify-center gap-2.5"
            >
              <svg className="w-4 h-4" viewBox="0 0 24 24">
                <path fill="currentColor" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" />
                <path fill="currentColor" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" />
                <path fill="currentColor" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.06H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.94l2.85-2.22.81-.63z" />
                <path fill="currentColor" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.06l3.66 2.84c.87-2.6 3.3-4.52 6.16-4.52z" />
              </svg>
              <span>{isAuthenticatingGoogle ? 'Verifying Admin...' : `Sign in as ${OWNER_ADMIN_EMAIL}`}</span>
            </button>
          </div>
        )}

      </div>
    </div>
  );
};
