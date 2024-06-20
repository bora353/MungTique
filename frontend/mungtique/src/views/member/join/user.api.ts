import axios from "axios";
import { Join } from "./join.interface";
import { UserEntity } from "../../../shared/types/user.interface";
import { Login } from "../../../shared/types/login.interface";
import { MailCheck } from "../../../shared/types/mailcheck.interface";

const basePath = import.meta.env.VITE_BACKEND_SERVER;

// TODO : 반환타입 백엔드에서 dto로 변경해야함 (UserEntity X)

const join = async (joinDTO: Join) =>
  await axios.post<UserEntity>(basePath + "/join", joinDTO);

const login = async (loginDTO: Login) => {
  const formData = new FormData();
  formData.append("email", loginDTO.email);
  formData.append("password", loginDTO.password);

  try {
    axios.defaults.withCredentials = true;
    const response = await axios.post(`${basePath}/login`, formData, {
      withCredentials: true,
    });

    if (response.status === 200) {
      console.log(response);
      const accessToken = response.headers["access"];
      console.log("access Token:", accessToken);
      localStorage.setItem("access", accessToken);

      // 서버 응답에서 userId 추출
      const userId = response.data.userId;
      localStorage.setItem("userId", userId);
    }
  } catch (error) {
    console.log(error);
  }
};

const logout = async () => {
  axios.defaults.withCredentials = true;

  console.log("access 삭제 예정 : " + localStorage.getItem("access"));
  localStorage.removeItem("access");
  localStorage.removeItem("userId");
  localStorage.clear();

  const response = await axios.post(`${basePath}/logout`, null, {
    withCredentials: true,
  });

  console.log(response);
  return response.data;
};

const mailCheck = async (mailDTO: MailCheck) => {
  try {
    const response = await axios.post<string>(`${basePath}/mail-send`, mailDTO);
    return response.data;
  } catch (error) {
    // 에러 처리
    console.error("이메일 인증 요청 중 오류가 발생했습니다:", error);
    throw new Error("이메일 인증 요청에 실패했습니다.");
  }
};

export const userApi = {
  join,
  login,
  logout,
  mailCheck,
};
