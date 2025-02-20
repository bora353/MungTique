import LoginButton from "./LoginSignInButton";
import LoginForm from "./LoginForm";
import { Login } from "../../../shared/types/login.interface";
import { useLoginViewModelHook } from "./hook/useLoginViewModel.hook";
import { useAuthStore } from "./hook/login.store";

export default function LoginContainer() {
  const AUTH_TOKEN_KEY = "access";
  const { setIsLogin } = useAuthStore.getState();
  const { loginData } = useLoginViewModelHook();

  const handleLoginSubmit = async (loginDTO: Login) => {
    const { userId, accessToken } = await loginData(loginDTO);

    if (userId) {
      localStorage.setItem("userId", userId);
      console.log("User ID saved:", userId);
    }
    if (accessToken) {
      localStorage.setItem(AUTH_TOKEN_KEY, accessToken);
      console.log("Access token saved:", accessToken);
      setIsLogin(true, accessToken);
    }
  };

  // useEffect(() => {
  //   const storedAccessToken = localStorage.getItem(AUTH_TOKEN_KEY);
  //   const storedUserId = localStorage.getItem("userId");

  //   if (storedAccessToken && storedUserId) {
  //     setIsLogin(true, storedAccessToken);
  //     console.log("Stored Access token:", storedAccessToken);
  //     console.log("Stored User ID:", storedUserId);
  //   }
  // }, [setIsLogin]);

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

          <LoginForm onsubmit={handleLoginSubmit} />
          <LoginButton />
        </div>
      </div>
    </div>
  );
}
