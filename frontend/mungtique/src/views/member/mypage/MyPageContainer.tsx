import { useLogoutViewModelHook } from "../login/hook/useLogoutViewModel.hook";
import { useAuthStore } from "../login/hook/login.store";
import MuiButton from "../../../components/buttons/MuiButton";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import Sidebar from "./Sidebar";
import MyMungCard from "../mymung/MyMungCard";
import MyReservationList from "./MyReservationList";

export default function MyPageContainer() {
  const navigate = useNavigate();
  const AUTH_TOKEN_KEY = "access";
  const OAUTH2_LOGIN_KEY = "oauth2";

  const { setIsLocalLogin, isOauth2Login, setIsOauth2Login } = useAuthStore();
  const { localLogoutData, oauth2LogoutData } = useLogoutViewModelHook();
  const [selectedMenu, setSelectedMenu] = useState<string>("home");

  const handleLogout = async () => {
    try {
      const logoutResult = await localLogoutData();

      if (logoutResult?.status === 200) {
        localStorage.removeItem(AUTH_TOKEN_KEY);
        localStorage.removeItem("userId");
        setIsLocalLogin(false);
        navigate("/");
      } else {
        alert("로그아웃 실패");
        console.error("로그아웃 실패: 서버 응답 없음");
      }
    } catch (error) {
      console.error("로그아웃 중 오류 발생:", error);
    }
  };

  const handleOauth2Logout = async () => {
    try {
      const logoutResult = await oauth2LogoutData();

      if (logoutResult?.status === 200) {
        localStorage.removeItem(OAUTH2_LOGIN_KEY);
        localStorage.removeItem("userId");
        setIsOauth2Login(false);
        navigate("/");
      } else {
        alert("로그아웃 실패");
        console.error("OAuth2 로그아웃 실패: 서버 응답 없음");
      }
    } catch (error) {
      console.error("OAuth2 로그아웃 중 오류 발생:", error);
    }
  };

  const handleMyMung = () => {
    navigate("/mymung/register");
  };

  return (
    <div className="flex bg-gray-100 min-h-screen">
      {/* 사이드바 */}
      <Sidebar setSelectedMenu={setSelectedMenu} />

      {/* 메인 콘텐츠 영역 */}
      <div className="flex-1 p-6">
        {/* 헤더 영역 */}
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
            {isOauth2Login ? (
              <MuiButton
                value="로그아웃"
                color="success"
                type="button"
                variant="outlined"
                onClick={handleOauth2Logout}
              />
            ) : (
              <MuiButton
                value="로그아웃"
                color="success"
                type="button"
                variant="outlined"
                onClick={handleLogout}
              />
            )}
          </div>
        </div>

        {/* 콘텐츠 영역 */}
        <div>
          {/* 메뉴 버튼들 */}
          {selectedMenu === "home" && <MyMungCard />}
          {selectedMenu === "reservation" && <MyReservationList />}
          {selectedMenu === "favorites" && <p>단골샵(찜하기) 내용</p>}
          {selectedMenu === "points" && <p>포인트 정보</p>}
        </div>
      </div>
    </div>
  );
}
