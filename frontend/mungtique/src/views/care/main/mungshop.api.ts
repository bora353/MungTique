import { MungShop } from "../../../shared/types/mungshop.interface";
import { api } from "../../../shared/api/ApiInterceptor";

const getMungShops = async () =>
  await api().get<MungShop[]>(`/mungshop-service/mungshops`);

export const mungshopApi = {
  getMungShops,
};
