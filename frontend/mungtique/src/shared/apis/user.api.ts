import axios from "axios";
import { Join } from "../types/join.interface";
import { UserEntity } from "../types/user.interface";
import { Login } from "../types/login.interface";
import { Cookies, useCookies } from "react-cookie";

const basePath = import.meta.env.VITE_BACKEND_SERVER;
console.log(basePath);

// TODO : 반환타입 백엔드에서 dto로 변경해야함 (UserEntity X)

const join = async (joinDTO: Join) =>
  await axios.post<UserEntity>(basePath + "/join", joinDTO);

/* function getCookie(name: string) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop()?.split(";").shift();
} */

const [cookies, setCookie] = useCookies(["refresh"]);

const login = async (loginDTO: Login) => {
  const formData = new FormData();
  formData.append("email", loginDTO.email);
  formData.append("password", loginDTO.password);

  try {
    const response = await axios.post(basePath + "/login", formData);

    // 응답에서 헤더와 쿠키 추출
    const accessToken = response.headers["access"];

    //const cookies = new Cookies();
    //const refreshToken = cookies.get("refresh");
    //const refreshToken = getCookie("refresh");
    const statusCode = response.status;

    // 로그인 성공 시 처리 (예: 페이지 이동 등)
    console.log("로그인 성공");
    console.log("accessToken:", accessToken);
    console.log("refreshToken:", cookies);
    console.log("statusCode:", statusCode);

    return response;
  } catch (error) {
    throw error;
  }
};

export const userApi = {
  join,
  login,
};
