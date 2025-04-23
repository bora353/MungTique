import { useLogoutViewModelHook } from "../login/hook/useLogoutViewModel.hook";
import { useAuthStore } from "../login/hook/login.store";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import Sidebar from "./Sidebar";
import MyMungCard from "../mymung/MyMungCard";
import MyReservationList from "./MyReservationList";
import { Button } from "@mui/material";
import useNotificationRedirect from "../../../components/snackbar/useNotificationRedirect";

export default function MyPageContainer() {
  const { showNotificationAndRedirect } = useNotificationRedirect();
  const navigate = useNavigate();
  const AUTH_TOKEN_KEY = "access";
  const OAUTH2_LOGIN_KEY = "oauth2";

  const { setIsLocalLogin, isOauth2Login, setIsOauth2Login } = useAuthStore();
  const { localLogoutData, oauth2LogoutData } = useLogoutViewModelHook();
  const [selectedMenu, setSelectedMenu] = useState<string>("home");

  const clientLogoutCleanup = () => {
    localStorage.removeItem(AUTH_TOKEN_KEY);
    localStorage.removeItem(OAUTH2_LOGIN_KEY);
    localStorage.removeItem("userId");
    setIsLocalLogin(false);
    setIsOauth2Login(false);
  };

const handleLocalLogout = async () => {
  let logoutSuccess = false;

  try {
    const result = await localLogoutData();
    logoutSuccess = result?.status === 200;
  } catch (error) {
    console.error("로컬 로그아웃 중 오류 발생:", error);
  } finally {
    clientLogoutCleanup();
    if (logoutSuccess) {
      showNotificationAndRedirect("로그아웃되었습니다.", "success", "/", 2000);
    } else {
      showNotificationAndRedirect("로그아웃되었습니다.", "info", "/", 2000);
    }
  }
};

const handleOauth2Logout = async () => {
  let logoutSuccess = false;

  try {
    const result = await oauth2LogoutData();
    logoutSuccess = result?.status === 200;
  } catch (error) {
    console.error("OAuth2 로그아웃 중 오류 발생:", error);
  } finally {
    clientLogoutCleanup();
    if (logoutSuccess) {
      showNotificationAndRedirect("로그아웃되었습니다.", "success", "/");
    } else {
      showNotificationAndRedirect("로그아웃되었습니다 (서버 응답 없음 또는 오류)", "info", "/");
    }
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
          <h1 className="text-2xl font-bold">나의 뭉티끄</h1>
          <div className="flex space-x-2">
            <Button
              color="warning"
              type="button"
              variant="contained"
              onClick={handleMyMung}
            >
              🐶 등록
            </Button>

            {isOauth2Login ? (
              <Button
                color="success"
                type="button"
                variant="outlined"
                onClick={handleOauth2Logout}
              >
                로그아웃
              </Button>
            ) : (
              <Button
                color="success"
                type="button"
                variant="outlined"
                onClick={handleLocalLogout}
              >
                로그아웃
              </Button>
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
