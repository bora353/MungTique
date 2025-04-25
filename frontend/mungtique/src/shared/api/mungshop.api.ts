import { api } from "./apiInterceptor";
import { MungShop } from "../types/mungshop.interface";

export const mungshopApi = {
  getMungShops: () => api().get<MungShop[]>("/mungshop-service/mungshops"),

  getLikeStatus: (mungShopId: number, userId: number) => 
    api().get<boolean>(`/mungshop-service/mungshops/${mungShopId}/like-status/${userId}`),

  getLikeCount: (mungShopId: number) => 
    api().get<number>(`/mungshop-service/mungshops/like-status?mungShopId=${mungShopId}`),

  likeMungShop: (mungShopId: number, userId: number) =>
    api().post<number>(`/mungshop-service/mungshops/${mungShopId}/like/${userId}`),
  
  unlikeMungShop: (mungShopId: number, userId: number) =>
    api().delete<number>(`/mungshop-service/mungshops/${mungShopId}/unlike/${userId}`),
};
