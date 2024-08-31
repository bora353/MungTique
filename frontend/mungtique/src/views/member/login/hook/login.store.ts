import { create } from "zustand";

interface AuthState {
  isLogin: boolean;
  setIsLogin: (loggedIn: boolean) => void;
}

const AUTH_TOKEN_KEY = "auth_token";

export const useAuthStore = create<AuthState>((set) => ({
  isLogin: !!localStorage.getItem(AUTH_TOKEN_KEY),

  setIsLogin: (logIn: boolean) => {
    if (logIn) {
      const token = "example-token";
      localStorage.setItem(AUTH_TOKEN_KEY, token);
    } else {
      localStorage.removeItem(AUTH_TOKEN_KEY);
    }
    set({ isLogin: logIn });
  },
}));
