import LoginButton from "./LoginSignInButton";
import LoginForm from "./LoginForm";
import { Login } from "../../../shared/types/login.interface";
import { useLoginViewModelHook } from "../../../shared/hooks/useLoginViewModel.hook";

export default function LoginContainer() {
  // TODO : Hook, mutate 사용
  const { loginMutation } = useLoginViewModelHook();
  const handleLoginSubmit = (loginDTO: Login) => loginMutation.mutate(loginDTO);

  return (
    <div className="flex justify-center items-center min-h-screen bg-blue-100">
      <div className="text-center">
        <h1 className="text-4xl text-blue-500 m-5">뭉티끄</h1>
        <LoginForm onsubmit={handleLoginSubmit} />
        <LoginButton />
      </div>
    </div>
  );
}
