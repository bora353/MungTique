import SignUpButton from "./SignUpButton";
import SignUpForm from "./SignUpForm";

export default function SignUpContainer() {
  return (
    <div className="flex justify-center items-center min-h-screen bg-blue-100">
      <div className="text-center">
        <h1 className="text-4xl text-blue-500">회원가입</h1>
        <SignUpForm />
        <SignUpButton />
      </div>
    </div>
  );
}
