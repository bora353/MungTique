import { create } from "zustand";

interface AuthState {
  isLogin: boolean;
  setIsLogin: (loggedIn: boolean, token?: string) => void;
}

const AUTH_TOKEN_KEY = "access";

export const useAuthStore = create<AuthState>((set) => ({
  isLogin: !!localStorage.getItem(AUTH_TOKEN_KEY),

  setIsLogin: (loggedIn: boolean, token?: string) => {
    if (loggedIn && token) {
      localStorage.setItem(AUTH_TOKEN_KEY, token);
      set({ isLogin: true });
    } else {
      localStorage.removeItem(AUTH_TOKEN_KEY);
      set({ isLogin: false });
    }
  },
}));
