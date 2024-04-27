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

const login = async (loginDTO: Login) => {
  // const [cookies, setCookie] = useCookies(["refresh"]);

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
      console.log("Access Token:", accessToken);

      //const refreshCookieValue = cookies.refresh;
      //console.log("Refresh Token from Cookie:", refreshCookieValue);
    }
  } catch (error) {
    console.log(error);
  }
};

export const userApi = {
  join,
  login,
};
