import axios from "axios";
import { MyMung } from "../types/mungjoin.interface";
import { UserEntity } from "../types/user.interface";

const basePath = import.meta.env.VITE_BACKEND_SERVER_CARE;

const mungJoin = async (mungJoinDTO: MyMung) =>
  await axios.post<UserEntity>(basePath + "/care/join/mung", mungJoinDTO);

export const mungApi = {
  mungJoin,
};
