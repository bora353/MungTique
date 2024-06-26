import LoginButton from "./LoginSignInButton";
import LoginForm from "./LoginForm";
import { Login } from "../../../shared/types/login.interface";
import { useLoginViewModelHook } from "./hook/useLoginViewModel.hook";

export default function LoginContainer() {
  // TODO : Hook, mutate 사용
  const { loginData } = useLoginViewModelHook();
  const handleLoginSubmit = (loginDTO: Login) => loginData(loginDTO);

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
