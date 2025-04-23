import { useEffect } from "react";
import { create } from "zustand";

interface AuthState {
  isLocalLogin: boolean;
  isOauth2Login: boolean;
  isInitialized: boolean; // 인증 상태 초기화 완료 여부

  setIsLocalLogin: (loggedIn: boolean, token?: string) => void;
  setIsOauth2Login: (loggedIn: boolean, token?: string) => void;
  setInitialized: (initialized: boolean) => void;
}

const AUTH_TOKEN_KEY = "access";
const OAUTH2_LOGIN_KEY = "oauth2";

export const useAuthStore = create<AuthState>((set) => ({
  isLocalLogin: false,
  isOauth2Login: false,
  isInitialized: false,

  setIsLocalLogin: (login: boolean, token?: string) => {
    if (login) {
      if (token) {
        localStorage.setItem(AUTH_TOKEN_KEY, token);
      }
      set({ isLocalLogin: true });
    } else {
      // 로그아웃 시
      localStorage.removeItem(AUTH_TOKEN_KEY);
      localStorage.removeItem("userId");
      set({ isLocalLogin: false });
    }
  },

  setIsOauth2Login: (login: boolean) => {
    if (login) {
      localStorage.setItem(OAUTH2_LOGIN_KEY, "true");
      set({ isOauth2Login: true });
    } else {
      localStorage.removeItem(OAUTH2_LOGIN_KEY);
      set({ isOauth2Login: false });
    }
  },

  setInitialized: (initialized: boolean) => {
    set({ isInitialized: initialized });
  }
}));

export const useAuthInit = () => {
  const { setIsLocalLogin, setIsOauth2Login, setInitialized } = useAuthStore();

  useEffect(() => {
    const localAccessToken = localStorage.getItem(AUTH_TOKEN_KEY);
    const oauth2Check = localStorage.getItem(OAUTH2_LOGIN_KEY);

    // console.log("localAccessToken : ", localAccessToken);
    // console.log("oauth2Check : ", oauth2Check);
  
    if (localAccessToken) {
      setIsLocalLogin(true, localAccessToken);
    }
    if (oauth2Check) {
      setIsOauth2Login(true);
    }

    setInitialized(true);

  }, [setIsLocalLogin, setIsOauth2Login, setInitialized]);
};