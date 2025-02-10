import { useLogoutViewModelHook } from "../login/hook/useLogoutViewModel.hook";
import { useAuthStore } from "../login/hook/login.store";
import MyMungContainer from "../mymung/MyMungContainer";
import MuiButton from "../../../components/buttons/MuiButton";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export default function MyPageContainer() {
  const navigate = useNavigate();

  const AUTH_TOKEN_KEY = "access";
  const { setIsLogin } = useAuthStore.getState();
  const { logoutData } = useLogoutViewModelHook();

  const handleLogout = async () => {
    const logoutResult = await logoutData();

    if (logoutResult && logoutResult.status === 200) {
      localStorage.removeItem(AUTH_TOKEN_KEY);
      localStorage.removeItem("userId");
      setIsLogin(false);
      navigate("/login");
    }
  };

  const handleMyMung = () => {
    navigate("/mymung");
  };

  useEffect(() => {
    const storedAccessToken = localStorage.getItem("access");
    if (storedAccessToken) {
      setIsLogin(true, storedAccessToken);
    }
  }, [setIsLogin]);

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen">
        <div className="flex flex-col items-center">
          <div>
            <MyMungContainer />
          </div>
          <MuiButton
            value="반려뭉 등록"
            color="warning"
            type="button"
            variant="contained"
            onClick={handleMyMung}
          />

          <p>예약내역</p>
          <p>주문내역(쇼핑몰 한다면)</p>
          <p>포인트...?</p>
          <p>단골샵(찜하기)</p>
          <MuiButton
            value="로그아웃"
            color="success"
            type="button"
            variant="outlined"
            onClick={handleLogout}
          />
        </div>
      </div>
    </div>
  );
}
