import axios from "axios";
import { Join } from "../types/join.interface";
import { UserEntity } from "../types/user.interface";

// TODO : 환경변수로 변경
//const basePath = process.env.REACT_APP_BACK_END_SERVER + "/join";
const basePath = "http://localhost:8082/api/v1" + "/join";
console.log(basePath);

const join = async (joinDTO: Join) =>
  await axios.post<UserEntity>(basePath, joinDTO);

export const joinApi = {
  join,
};
