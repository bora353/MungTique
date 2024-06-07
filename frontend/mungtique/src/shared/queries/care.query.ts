import { useMutation, useQuery } from "@tanstack/react-query";
import { MungShop } from "../types/mungshop.interface";
import { careApi } from "../apis/care.api";

export const useCareInfoQuery = () => {
  return useQuery<MungShop[]>({
    // useQuery마다 부여되는 고유 Key
    queryKey: ["mungShop"],
    // 서버에 API 요청하는 코드
    queryFn: async () => {
      const { data } = await careApi.getMungShops();
      console.log(data);
      return data;
    },
  });
};

/* export const useLikeMungShopMutation = () => {
  return useMutation({
    mutationFn: async (mungShopId: number, userId: number) => {
      const { data } = await careApi.postLikeMungShop(mungShopId, userId);
      return data;
    },
  });
}; */
