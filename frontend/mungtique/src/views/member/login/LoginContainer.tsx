import LoginOptions from "./LoginOptions";
import LoginForm from "./LoginForm";
import { Login } from "../../../shared/types/login.interface";
import { useLoginViewModelHook } from "./hook/useLoginViewModel.hook";
import { useAuthStore } from "../../../shared/store/login.store";
import { useNavigate } from "react-router-dom";
import useNotificationRedirect from "../../../hooks/useNotificationRedirect";

export default function LoginContainer() {
  const { showNotificationAndRedirect } = useNotificationRedirect();
  const navigate = useNavigate();

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
      navigate("/");
    } catch (error) {
      console.error("로그인 중 오류 발생:", error);

      showNotificationAndRedirect(
        "아이디 또는 비밀번호가 일치하지 않습니다.",
        "warning",
        "/login",
        2000
      );
    }
  };

  return (
    <div className="flex justify-center items-center h-screen bg-pink-50">
      <div className="w-full max-w-md p-8 bg-white rounded-xl shadow-lg">
        <LoginForm onsubmit={handleLocalLoginSubmit} />
        <LoginOptions />
      </div>
    </div>
  );
}
