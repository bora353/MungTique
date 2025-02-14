import { useLogoutViewModelHook } from "../login/hook/useLogoutViewModel.hook";
import { useAuthStore } from "../login/hook/login.store";
import MyMungContainer from "../mymung/MyMungContainer";
import MuiButton from "../../../components/buttons/MuiButton";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import Sidebar from "./Sidebar";
import ReservationList from "./ReservationList";

export default function MyPageContainer() {
  const navigate = useNavigate();
  const AUTH_TOKEN_KEY = "access";
  const { setIsLogin } = useAuthStore.getState();
  const { logoutData } = useLogoutViewModelHook();
  const [selectedMenu, setSelectedMenu] = useState<string>("home");

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
    navigate("/mymung/register");
  };

  useEffect(() => {
    const storedAccessToken = localStorage.getItem("access");
    if (storedAccessToken) {
      setIsLogin(true, storedAccessToken);
    }
  }, [setIsLogin]);

  return (
    <div className="flex bg-gray-100 min-h-screen">
      {/* 사이드바 */}
      <Sidebar setSelectedMenu={setSelectedMenu} />

      {/* 메인 콘텐츠 영역 */}
      <div className="flex-1 p-6">
        {/* 헤더 영역 - My Page + 버튼들 */}
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-2xl font-bold">My Page</h1>
          <div className="flex space-x-2">
            <MuiButton
              value="반려뭉 등록"
              color="warning"
              type="button"
              variant="contained"
              onClick={handleMyMung}
            />
            <MuiButton
              value="로그아웃"
              color="success"
              type="button"
              variant="outlined"
              onClick={handleLogout}
            />
          </div>
        </div>

        {/* 콘텐츠 영역 */}
        {selectedMenu === "home" && <MyMungContainer />}
        {selectedMenu === "reservation" && <ReservationList />}
        {selectedMenu === "favorites" && <p>단골샵(찜하기) 내용</p>}
        {selectedMenu === "points" && <p>포인트 정보</p>}
      </div>
    </div>
  );
}
