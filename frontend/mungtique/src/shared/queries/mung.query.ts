import { useMutation, useQueryClient } from "@tanstack/react-query";
import { MyMung } from "../types/mungjoin.interface";
import { mungApi } from "../apis/mung.api";

export const useMungJoinMutation = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (mungJoinDTO: MyMung) => {
      const { data } = await mungApi.mungJoin(mungJoinDTO);
      return data;
    },
  });
};
