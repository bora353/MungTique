import MuiButton from "../../../components/atomic/buttons/MuiButton";
import { useLogoutViewModelHook } from "../../../shared/hooks/useLogoutViewModel.hook";

export default function MyPageContainer() {
  // TODO : Hook, mutate 사용
  const { logoutMutation } = useLogoutViewModelHook();
  const handleLogout = () => logoutMutation.mutate();

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen">
        {/* <Link to="/login">
          <MuiButton
            color="info"
            type="button"
            value="로그인"
            variant="outlined"
          />
        </Link>
        <p>로그인은 로그인 상태일때 안 보이게~~</p>
        <p>로그아웃은 로그인 상태일 때만 보이게!</p> */}
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
