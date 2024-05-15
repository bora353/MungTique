import axios from "axios";
import { MungShop, MungShopLike } from "../types/mungshop.interface";

const basePath = import.meta.env.VITE_BACKEND_SERVER_CARE;

const getMungShops = async () =>
  await axios.get<MungShop[]>(`${basePath}/care/mungshops`);

const postLikeMungShop = async (mungShopId: number, userId: number) =>
  await axios.post<MungShopLike>(basePath + "/care/mungshop-like", {
    mungShopId,
    userId,
  });

export const careApi = {
  getMungShops,
  postLikeMungShop,
};
