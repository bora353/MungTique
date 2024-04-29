import MuiAppBar from "../../components/atomic/bar/MuiAppBar";
import MuiButton from "../../components/atomic/buttons/MuiButton";
import { useLogoutViewModelHook } from "../../shared/hooks/useLogoutViewModel.hook";

export default function MainContainer() {
  const { logoutMutation } = useLogoutViewModelHook();
  const handleLogout = () => logoutMutation.mutate();

  // TODO : 로그인 시에만 로그아웃이 가능하게 변경

  return (
    <div>
      <MuiAppBar />
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
    </div>
  );
}
