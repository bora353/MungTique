import LoginButton from "./LoginSignInButton";
import LoginForm from "./LoginForm";

export default function LoginContainer() {
  return (
    <div className="flex justify-center items-center min-h-screen bg-blue-100">
      <div className="text-center">
        <h1 className="text-4xl text-blue-500 m-5">MungTique</h1>
        <LoginForm />
        <LoginButton />
      </div>
    </div>
  );
}
