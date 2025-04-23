import { MyMungJoin } from "../types/mungjoin.interface";
import { UserEntity } from "../types/user.interface";
import { api } from "./apiInterceptor";

const mungJoin = async (mungJoinDTO: MyMungJoin) =>
  await api().post<UserEntity>("/dog-service/dogs", mungJoinDTO);

export const mungApi = {
  mungJoin,
};
