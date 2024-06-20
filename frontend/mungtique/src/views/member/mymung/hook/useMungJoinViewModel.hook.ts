import { MyMung } from "../../../../shared/types/mungjoin.interface";
import { mungApi } from "../mung.api";

export const useMungJoinViewModelHook = () => {
  const mungJoinData = async (mungJoinDTO: MyMung) => {
    const response = await mungApi.mungJoin(mungJoinDTO);
    return response.data;
  };

  return { mungJoinData };
};
