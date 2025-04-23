import { api } from "./apiInterceptor";
import { MungShop } from "../types/mungshop.interface";

const getMungShops = async () =>
  await api().get<MungShop[]>(`/mungshop-service/mungshops`);

export const mungshopApi = {
  getMungShops,
};
