import { Link } from "react-router-dom";
import MuiButton from "../../../components/buttons/MuiButton";
import { useLogoutViewModelHook } from "../login/hook/useLogoutViewModel.hook";
import { useLoginStore } from "../login/hook/login.store";
import MyMungContainer from "../mymung/MyMungContainer";

export default function MyPageContainer() {
  const { logoutData } = useLogoutViewModelHook();
  const { setIsLogin } = useLoginStore();

  const handleLogout = () => {
    logoutData();
    setIsLogin(false); // 로그인 상태유지
  };

  const handleMyMung = () => {};

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen">
        <div className="flex flex-col items-center">
          <div>
            <MyMungContainer />
          </div>
          <Link to="/mymung">
            <MuiButton
              value="반려뭉 등록"
              color="warning"
              type="button"
              variant="contained"
              onClick={handleMyMung}
            />
          </Link>

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
