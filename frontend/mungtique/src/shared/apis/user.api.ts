import axios from "axios";
import { Join } from "../types/join.interface";
import { UserEntity } from "../types/user.interface";
import { Login } from "../types/login.interface";

const basePath = import.meta.env.VITE_BACKEND_SERVER;
console.log(basePath);

// TODO : 반환타입 백엔드에서 dto로 변경해야함 (UserEntity X)

const join = async (joinDTO: Join) =>
  await axios.post<UserEntity>(basePath + "/join", joinDTO);

// const login = async (loginDTO: Login) =>
//   await axios.post(basePath + "/login", loginDTO);

const login = async (loginDTO: Login) => {
  const formData = new FormData();
  formData.append("email", loginDTO.email);
  formData.append("password", loginDTO.password);

  try {
    const response = await axios.post(basePath + "/login", formData);
    return response.data; // 서버에서 받은 데이터를 반환
  } catch (error) {
    throw error; // 에러가 발생하면 예외를 던짐
  }
};

export const userApi = {
  join,
  login,
};
