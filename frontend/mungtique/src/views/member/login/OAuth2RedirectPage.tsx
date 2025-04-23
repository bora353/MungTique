import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../shared/api/apiInterceptor";
import { useAuthStore } from "../../../shared/store/login.store";
import useNotificationRedirect from "../../../hooks/useNotificationRedirect";

export default function OAuth2RedirectPage() {
  const { showNotificationAndRedirect } = useNotificationRedirect();

  const navigate = useNavigate();
  const { setIsOauth2Login } = useAuthStore();
  const AUTH_TOKEN_KEY = "access";

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const accessToken = params.get("accessToken");

    console.log(accessToken);

    if (!accessToken) {
      showNotificationAndRedirect(
        "로그인에 실패했습니다. 다시 시도해주세요.",
        "error",
        "/login",
        2000
      );
      return;
    }

    localStorage.setItem(AUTH_TOKEN_KEY, accessToken);

    api()
      .get("/user-service/oauth2/me", { withCredentials: true })
      .then((response) => {
        if (response.status === 200) {
          localStorage.setItem("userId", response.data);
          setIsOauth2Login(true);
          navigate("/");
        }
      })
      .catch(() => {
        showNotificationAndRedirect(
          "로그인 세션이 만료되었거나 오류가 발생했습니다.",
          "error",
          "/login",
          2000
        );

        localStorage.removeItem("access");
        localStorage.removeItem("userId");
        setIsOauth2Login(false);
      });
  }, [navigate, setIsOauth2Login]);

  return <></>;
}
