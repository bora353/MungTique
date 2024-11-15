import { MyMung } from "../../../shared/types/mungjoin.interface";
import { UserEntity } from "../../../shared/types/user.interface";
import { api } from "../../../shared/api/ApiInterceptor";

const mungJoin = async (mungJoinDTO: MyMung) =>
  await api().post<UserEntity>("/care-service/join/mung", mungJoinDTO);

export const mungApi = {
  mungJoin,
};
