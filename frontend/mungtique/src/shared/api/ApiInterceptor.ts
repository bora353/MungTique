import axios, { AxiosInstance } from "axios";

export interface ApiInterceptor {}

const serverUrl = import.meta.env.VITE_GATEWAY_SERVER;
const AUTH_TOKEN_KEY = "access";
const token = localStorage.getItem(AUTH_TOKEN_KEY);

const apiClient = (baseUrl: string): AxiosInstance => {
  const instance = axios.create({
    baseURL: `${baseUrl}/api/v1/`,
    withCredentials: true,
    headers: {
      "Content-Type": "application/json",
      Authorization: token ? `Bearer ${token}` : undefined,
    },
    // timeout: 3000,
  });

  console.log("API Client created with configuration:", {
    baseURL: instance.defaults.baseURL,
    headers: instance.defaults.headers,
    responseType: instance.defaults.responseType,
    timeout: instance.defaults.timeout,
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
        if (message === "Invalid or expired JWT token") {
          try {
            const response = await axiosInstance.post("/user-service/reissue", {
              withCredentials: true,
            });

            const authorizationHeader = response.headers["authorization"];
            if (authorizationHeader) {
              const accessToken = authorizationHeader.replace("Bearer ", "");
              console.log("Received new access token: ", accessToken);
              localStorage.setItem(AUTH_TOKEN_KEY, accessToken);
            }
            return axiosInstance(error.config);
          } catch (err) {
            console.error("Error during token reissue", err);
            alert("Session expired. Please log in again.");
            signOut(); // 로그아웃 처리
          }
        } else if (message === "Refresh token not found in the database") {
          alert("Session expired. Please log in again.");
          signOut();
        }
      } else {
        return Promise.reject(error); // 401이 아닌 오류에 대한 처리
      }
    }
  );

  return axiosInstance;
};

function signOut() {
  const axiosInstance = apiClient(serverUrl);

  localStorage.removeItem(AUTH_TOKEN_KEY);

  axiosInstance
    .post("/user-service/logout")
    .then(() => console.log("Logout from server"))
    .catch((err) => console.error("Server logout failed", err));

  window.location.replace("/login"); // 뒤로 가기 방지
}

export const api = () => apiInterceptor(serverUrl);
