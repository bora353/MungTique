import { createStore } from "zustand";

interface UserState {
  isLoggedIn: boolean;
}

const useStore = createStore<UserState>((set) => ({
  isLoggedIn: !!localStorage.getItem("access"),
}));

export default useStore;
