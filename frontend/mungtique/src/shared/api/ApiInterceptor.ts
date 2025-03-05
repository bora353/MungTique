import axios, { AxiosInstance } from "axios";

export interface ApiInterceptor {}

const serverUrl = import.meta.env.VITE_GATEWAY_SERVER;
const AUTH_TOKEN_KEY = "access";

const apiClient = (baseUrl: string): AxiosInstance => {
  const accessToken = localStorage.getItem(AUTH_TOKEN_KEY);

  const instance = axios.create({
    baseURL: `${baseUrl}/api/v1/`,
    withCredentials: true,
    headers: {
      "Content-Type": "application/json",
      Authorization: accessToken ? `Bearer ${accessToken}` : undefined,
    },
    // timeout: 3000,
  });

  // console.log("API Client created with configuration:", {
  //   baseURL: instance.defaults.baseURL,
  //   headers: instance.defaults.headers,
  //   responseType: instance.defaults.responseType,
  //   timeout: instance.defaults.timeout,
  // });

  return instance;
};

const apiInterceptor = (baseUrl: string) => {
  const axiosInstance = apiClient(baseUrl);
  //console.log("apiInterceptor start");

  axiosInstance.interceptors.response.use(
    (response) => response,
    async (error) => {
      console.error(error);
      if (error.response?.status === 401) {
        const { message } = error.response.data;
        console.log("401 error message! : " + message);

        // accessToken 만료시 재발급
        if (message === "Invalid or expired JWT token") {
          try {
            const response = await axiosInstance.post("/user-service/reissue", {
              withCredentials: true,
            });

            const authorizationHeader = response.headers["authorization"];
            if (authorizationHeader) {
              const accessToken = authorizationHeader.replace("Bearer ", "");
              localStorage.setItem(AUTH_TOKEN_KEY, accessToken);
              console.log("Received new access token");

              axiosInstance.defaults.headers.common[
                "Authorization"
              ] = `Bearer ${accessToken}`;
              error.config.headers["Authorization"] = `Bearer ${accessToken}`;
            }
            return axiosInstance(error.config); // 토큰 발행 후 원래 요청 다시
          } catch (err) {
            console.error("Error during token reissue", err);
            alert("Session expired. Please log in again.");
            signOut();
          }
        } else if (message === "No Authorization header") {
          alert("Session expired. Please log in again.");
          signOut();
        }
      } else {
        signOut();
        return Promise.reject(error); // 401이 아닌 오류에 대한 처리
      }
    }
  );

  return axiosInstance;
};

function signOut() {
  const axiosInstance = apiClient(serverUrl);
  localStorage.removeItem(AUTH_TOKEN_KEY);
  localStorage.removeItem("userId");

  axiosInstance
    .post("/user-service/logout")
    .then(() => {
      console.log("Logout from server");
    })
    .catch((err) => console.error("Server logout failed", err));

  window.location.replace("/login"); // 뒤로 가기 방지
}

export const api = () => apiInterceptor(serverUrl);
