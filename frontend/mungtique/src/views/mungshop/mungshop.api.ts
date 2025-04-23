import { api } from "../../shared/api/ApiInterceptor";
import { MungShop } from "../../shared/types/mungshop.interface";


const getMungShops = async () =>
  await api().get<MungShop[]>(`/mungshop-service/mungshops`);

export const mungshopApi = {
  getMungShops,
};
