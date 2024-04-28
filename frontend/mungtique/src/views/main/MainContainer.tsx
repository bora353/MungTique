import MuiButton from "../../components/atomic/buttons/MuiButton";
import { useLogoutViewModelHook } from "../../shared/hooks/useLogoutViewModel.hook";

export default function MainContainer() {
  const { logoutMutation } = useLogoutViewModelHook();
  const handleLogout = () => logoutMutation.mutate();

  return (
    <div className="flex justify-center items-center min-h-screen bg-blue-100">
      <h1 className="text-xl">뭉티끄 메인 페이지</h1>
      <MuiButton
        value="로그아웃"
        variant="contained"
        color="primary"
        type="button"
        onClick={handleLogout}
      />
    </div>
  );
}
