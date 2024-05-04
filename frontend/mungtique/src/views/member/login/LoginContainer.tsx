import LoginButton from "./LoginSignInButton";
import LoginForm from "./LoginForm";
import { Login } from "../../../shared/types/login.interface";
import { useLoginViewModelHook } from "../../../shared/hooks/useLoginViewModel.hook";
import MuiAppBar from "../../../components/atomic/bar/MuiAppBar";

export default function LoginContainer() {
  // TODO : Hook, mutate 사용
  const { loginMutation } = useLoginViewModelHook();
  const handleLoginSubmit = (loginDTO: Login) => loginMutation.mutate(loginDTO);

  return (
    <div>
      <MuiAppBar />
      <div className="flex justify-center items-center m-10">
        <div className="text-center">
          <div className="text-4xl text-blue-500 m-5 ">
            <img
              src="/images/logo2.png"
              alt="뭉티끄"
              width="250"
              height="250"
              className="mx-auto"
            />
          </div>
          <LoginForm onsubmit={handleLoginSubmit} />
          <LoginButton />
        </div>
      </div>
    </div>
  );
}
