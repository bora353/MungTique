import { create } from "zustand";
import { persist } from "zustand/middleware";

/* interface UserState {
  isLoggedIn: boolean;
}

const useStore = createStore<UserState>((set) => ({
  isLoggedIn: !!localStorage.getItem("access"),
}));

export default useStore; */

/**
 * Store Interface는 State/Reducer로 작성해 주세요.
 
* State    명명법 -> 이름State
 *      store property가 정의되는 구간입니다.
 *
 * Reducer  명명법 -> 이름Reducer
 *      store의 기능 및 함수가 정의 되는 구간입니다.
 */
export interface LoginState {
  isLogin: boolean;
}

export interface LoginReducer {
  setIsLogin: (value: boolean) => void;
}

export const useLoginStore = create(
  persist<LoginState & LoginReducer>(
    (set) => ({
      isLogin: false,
      setIsLogin: (value: boolean) => set({ isLogin: value }),
    }),
    {
      name: "login_state", // 로컬 스토리지에 사용될 키 이름
    }
  )
);
