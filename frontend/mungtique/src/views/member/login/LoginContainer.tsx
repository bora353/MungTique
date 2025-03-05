import LoginOptions from "./LoginOptions";
import LoginForm from "./LoginForm";
import { Login } from "../../../shared/types/login.interface";
import { useLoginViewModelHook } from "./hook/useLoginViewModel.hook";
import { useAuthStore } from "./hook/login.store";

export default function LoginContainer() {
  const AUTH_TOKEN_KEY = "access";
  const { setIsLocalLogin } = useAuthStore();
  const { loginData } = useLoginViewModelHook();

  const handleLocalLoginSubmit = async (loginDTO: Login) => {
    try {
      const { userId, accessToken } = await loginData(loginDTO);

      if (userId) {
        localStorage.setItem("userId", userId);
      }

      if (accessToken) {
        localStorage.setItem(AUTH_TOKEN_KEY, accessToken);
        setIsLocalLogin(true, accessToken);
      }
    } catch (error) {
      alert("로그인 중 오류 발생");
      console.error("로그인 중 오류 발생:", error);
    }
  };

  return (
    <div>
      <div className="flex justify-center items-center mt-3">
        <div className="text-center">
          <img
            src="/images/logo2.png"
            alt="뭉티끄"
            width="250"
            className="mx-auto"
          />

          <LoginForm onsubmit={handleLocalLoginSubmit} />
          <LoginOptions />
        </div>
      </div>
    </div>
  );
}
