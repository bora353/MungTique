import MuiButton from "../../../components/atomic/buttons/MuiButton";
import { useLogoutViewModelHook } from "../../../shared/hooks/useLogoutViewModel.hook";
import { useLoginStore } from "../../../shared/stores/login.store";

export default function MyPageContainer() {
  // TODO : Hook, mutate 사용
  const { logoutMutation } = useLogoutViewModelHook();
  const { setIsLogin } = useLoginStore();
  const handleLogout = () => {
    logoutMutation.mutate();
    setIsLogin(false); // 로그인 상태유지
  };

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen">
        <MuiButton
          value="로그아웃"
          color="success"
          type="button"
          variant="outlined"
          onClick={handleLogout}
        />
        <MuiButton
          value="반려뭉 등록"
          color="warning"
          type="button"
          variant="contained"
          onClick={handleLogout}
        />
      </div>
    </div>
  );
}
