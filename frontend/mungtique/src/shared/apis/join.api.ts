import axios from "axios";
import { Join } from "../types/join.interface";
import { UserEntity } from "../types/user.interface";

const basePath = import.meta.env.VITE_BACKEND_SERVER + "/join";
console.log(basePath);

const join = async (joinDTO: Join) =>
  await axios.post<UserEntity>(basePath, joinDTO);

export const joinApi = {
  join,
};
