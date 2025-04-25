import { mungshopApi } from "../shared/api/mungshop.api";

export const useMungshopApi = () => {
  const fetchMungShops = async () => {
    try {
      const { data } = await mungshopApi.getMungShops();
      return data;
    } catch (error) {
      console.error("뭉샵 목록 조회 실패:", error);
      return [];
    }
  };

  const getLikeStatus = async (mungShopId: number, userId: number) => {
    try {
      const { data } = await mungshopApi.getLikeStatus(mungShopId, userId);
      return data;
    } catch (error) {
      console.error("좋아요 상태 조회 실패:", error);
      return false; 
    }
  };

  const getLikeCount = async (mungShopId: number) => {
    try {
      const { data } = await mungshopApi.getLikeCount(mungShopId);
      return data;
    } catch (error) {
      console.error("좋아요 수 조회 실패:", error);
      return 0; 
    }
  };

  const toggleLikeStatus = async (mungShopId: number, userId: number, isLiked: boolean) => {
    try {
      let response;
      if (isLiked) {
        response = await mungshopApi.unlikeMungShop(mungShopId, userId);
      } else {
        response = await mungshopApi.likeMungShop(mungShopId, userId);
      }
      return response;
    } catch (error) {
      console.error("좋아요 상태 변경 실패:", error);
      return null;
    }
  };

  return { fetchMungShops, getLikeStatus, getLikeCount, toggleLikeStatus };
};
