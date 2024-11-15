import { MungShop } from "../../../shared/types/mungshop.interface";
import { api } from "../../../shared/api/ApiInterceptor";

const getMungShops = async () =>
  await api().get<MungShop[]>(`/care-service/mungshops`);

export const careApi = {
  getMungShops,
};
