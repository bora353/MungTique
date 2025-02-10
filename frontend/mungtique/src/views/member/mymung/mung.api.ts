import { MyMung } from "../../../shared/types/mungjoin.interface";
import { UserEntity } from "../../../shared/types/user.interface";
import { api } from "../../../shared/api/ApiInterceptor";

const mungJoin = async (mungJoinDTO: MyMung) =>
  await api().post<UserEntity>("/dog-service/dogs", mungJoinDTO);

export const mungApi = {
  mungJoin,
};
