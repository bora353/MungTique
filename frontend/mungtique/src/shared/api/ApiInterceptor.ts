import axios, { AxiosInstance } from "axios";
import { useAuthStore } from "../store/login.store";

const serverUrl = import.meta.env.VITE_GATEWAY_SERVER;
const AUTH_TOKEN_KEY = "access";
const OAUTH2_LOGIN_KEY = "oauth2";

const apiClient = (baseUrl: string): AxiosInstance => {
  const accessToken = localStorage.getItem(AUTH_TOKEN_KEY);

  const instance = axios.create({
    baseURL: `${baseUrl}/api/v1/`,
    withCredentials: true,
    headers: {
      "Content-Type": "application/json",
      Authorization: accessToken ? `Bearer ${accessToken}` : undefined,
    },
    timeout: 5000,
  });

  return instance;
};

const apiInterceptor = (baseUrl: string) => {
  const axiosInstance = apiClient(baseUrl);
  console.log("apiInterceptor start");

  axiosInstance.interceptors.response.use(
    (response) => response,
    async (error) => {
      console.error(error);
      if (error.response?.status === 401) {
        const { message } = error.response.data;
        console.log("401 error message! : " + message);

        // accessToken 만료시 재발급
        if (
          message === "Invalid or expired JWT token" &&
          !error.config._retry
        ) {
          error.config._retry = true;

          try {
            const response = await axiosInstance.post(
              "/user-service/reissue",
              {},
              {
                withCredentials: true,
              }
            );

            const authorizationHeader = response.headers["authorization"];
            if (authorizationHeader) {
              const accessToken = authorizationHeader.replace("Bearer ", "");
              localStorage.setItem(AUTH_TOKEN_KEY, accessToken);
              console.log("Received new access token");

              axiosInstance.defaults.headers.common[
                "Authorization"
              ] = `Bearer ${accessToken}`;
              error.config.headers["Authorization"] = `Bearer ${accessToken}`;

              return axiosInstance(error.config); // 토큰 발행 후 원래 요청 다시
            }
          } catch (err) {
            console.error("Error during token reissue", err);
            alert("로그인 세션이 만료되었습니다. 다시 로그인해주세요.");
            signOut();
          }
        } else if (message === "No Authorization header") {
          alert("로그인 세션이 만료되었습니다. 다시 로그인해주세요.");
          signOut();
        }
      }
      return Promise.reject(error); // 401이 아닌 오류에 대한 처리
    }
  );

  return axiosInstance;
};

function signOut() {
  const { setIsLocalLogin, setIsOauth2Login } = useAuthStore();
  const axiosInstance = apiClient(serverUrl);
  const isOauth2Login = localStorage.getItem(OAUTH2_LOGIN_KEY) === "true";

  // 공통 로컬 클리어
  localStorage.removeItem(AUTH_TOKEN_KEY);
  localStorage.removeItem("userId");

  if (isOauth2Login) localStorage.removeItem(OAUTH2_LOGIN_KEY);

  // 상태 업데이트
  setIsLocalLogin(false);
  setIsOauth2Login(false);

  // 서버 로그아웃 시도
  try {
    if (isOauth2Login) {
      axiosInstance.post("/user-service/oauth2/logout");
    } else {
      axiosInstance.post("/user-service/logout");
    }
  } catch (err) {
    console.error("로그아웃 실패", err); // 실패해도 무시 가능
  } finally {
    window.location.replace("/login"); // 뒤로가기 방지
  }
}

export const api = () => apiInterceptor(serverUrl);
