import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../shared/api/ApiInterceptor";
import { useAuthStore } from "./hook/login.store";

export default function OAuth2RedirectPage() {
  const navigate = useNavigate();
  const { setIsOauth2Login } = useAuthStore();
  const OAUTH2_LOGIN_KEY = "oauth2";

  useEffect(() => {
    api()
      .get("/user-service/oauth2/me", { withCredentials: true })
      .then((response) => {
        if (response.status === 200) {
          const message = response.data;

          if (message === "expired") {
            handleAuthFailure("expired");
          } else if (message === "no access token") {
            handleAuthFailure("no access token");
          } else if (message === "error") {
            handleAuthFailure("error");
          } else {
            console.log("OAuth2 로그인 상태 유지됨");

            const userId = message;
            localStorage.setItem("userId", userId);
            localStorage.setItem(OAUTH2_LOGIN_KEY, "true");
            setIsOauth2Login(true); // OAuth2 로그인 상태로 설정

            console.log("userId: ", userId);
          }
        } else {
          handleAuthFailure("error");
        }
      })
      .catch(() => {
        handleAuthFailure("error");
      });
  }, [setIsOauth2Login]);

  const handleAuthFailure = (reason: string) => {
    console.log(`OAuth2 인증 실패: ${reason}`);

    localStorage.removeItem("userId");
    setIsOauth2Login(false);

    if (reason === "expired") {
      alert("로그인 세션이 만료되었습니다. 다시 로그인해주세요.");
    }
  };

  // 로그인 후 리다이렉트
  useEffect(() => {
    navigate("/"); // 로그인 후 메인 페이지로 리다이렉트
  }, [navigate]);

  return <></>;
}
