import axios from "axios";
import { MungShop } from "../../../shared/types/mungshop.interface";

const basePath = import.meta.env.VITE_BACKEND_SERVER_CARE;

const getMungShops = async () =>
  await axios.get<MungShop[]>(`${basePath}/care/mungshops`);

export const careApi = {
  getMungShops,
};
