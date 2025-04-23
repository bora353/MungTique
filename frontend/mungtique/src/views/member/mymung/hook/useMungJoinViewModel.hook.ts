import { MyMungJoin } from "../../../../shared/types/mungjoin.interface";
import { mungApi } from "../mung.api";

export const useMungJoinViewModelHook = () => {
  const mungJoinData = async (mungJoinDTO: MyMungJoin) => {
    const response = await mungApi.mungJoin(mungJoinDTO);
    return response.data;
  };

  return { mungJoinData };
};
