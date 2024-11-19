import { Join } from "./join.interface";
import { UserEntity } from "../../../shared/types/user.interface";
import { Login } from "../../../shared/types/login.interface";
import { MailCheck } from "../../../shared/types/mailcheck.interface";
import { api } from "../../../shared/api/ApiInterceptor";

const join = async (joinDTO: Join) =>
  await api().post<UserEntity>(`/user-service/join`, joinDTO);

const login = async (loginDTO: Login) => {
  const formData = new FormData();
  formData.append("email", loginDTO.email);
  formData.append("password", loginDTO.password);
  return await api().post(`/user-service/login`, formData, {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    withCredentials: true,
  });
};

const logout = async () => await api().post(`/user-service/logout`, null);

const mailCheck = async (mailDTO: MailCheck) =>
  await api().post<string>(`/user-service/logout`, mailDTO);

// const join = async (joinDTO: Join) => {
//   try {
//     const response = await axios.post<UserEntity>(
//       basePath + "/user-service/join",
//       joinDTO
//     );

//     if (response.status === 200) {
//       console.log(response);
//       console.log("Join response:", response.data);
//       return response;
//     }
//   } catch (error) {
//     console.error("Join request failed:", error);
//   }
// };

// const login = async (loginDTO: Login) => {
//   const { setIsLogin } = useAuthStore.getState();

//   const formData = new FormData();
//   formData.append("email", loginDTO.email);
//   formData.append("password", loginDTO.password);

//   try {
//     axios.defaults.withCredentials = true;
//     const response = await axios.post(
//       basePath + `/user-service/login`,
//       formData
//     );

//     if (response.status === 200) {
//       const authorizationHeader = response.headers["authorization"];
//       if (authorizationHeader) {
//         const accessToken = authorizationHeader.replace("Bearer ", "");
//         console.log("Received new access token: ", accessToken);
//         localStorage.setItem(AUTH_TOKEN_KEY, accessToken);
//         setIsLogin(true);
//       }

//       const userId = response.headers["userId"];
//       if (userId) {
//         console.log("Received userId: ", userId);
//         localStorage.setItem("userId", userId);
//       }
//     }
//     return response;
//   } catch (error) {
//     console.error("Login failed:", error);
//     alert("로그인에 실패했습니다. 다시 시도해 주세요.");
//     throw error;
//   }
// };

// const logout = async () => {
//   const { setIsLogin } = useAuthStore.getState();

//   try {
//     const response = await axios.post(basePath + `/user-service/logout`, null, {
//       withCredentials: true,
//     });

//     if (response.status === 200) {
//       console.log("Logout successful");
//       localStorage.removeItem("access");
//       localStorage.removeItem("userId");
//       localStorage.clear();
//       setIsLogin(false);
//     }

//     return response;
//   } catch (error) {
//     console.error("Logout failed:", error);
//     alert("로그아웃에 실패했습니다.");
//     throw error;
//   }
// };

// const mailCheck = async (mailDTO: MailCheck) => {
//   try {
//     const response = await api().post<string>(
//       `/user-service/mail-send`,
//       mailDTO
//     );
//     return response.data;
//   } catch (error) {
//     // 에러 처리
//     console.error(
//       "An error occurred while requesting email verification:",
//       error
//     );
//     alert("이메일 인증 요청에 실패했습니다.");
//     throw error;
//   }
// };

export const userApi = {
  join,
  login,
  logout,
  mailCheck,
};
