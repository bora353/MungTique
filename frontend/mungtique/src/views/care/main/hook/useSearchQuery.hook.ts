import { useEffect, useState } from "react";
import { MungShop } from "../../../../shared/types/mungshop.interface";
import { api } from "../../../../shared/api/ApiInterceptor";

export const useSearchQueryHook = (mungShops : MungShop[]) => {
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedMarker, setSelectedMarker] = useState<MungShop | null>(null);
  const [filteredMungShops, setFilteredMungShops] =
    useState<MungShop[]>(mungShops);

  useEffect(() => {
    fetchFilteredMungShops();
  }, [searchQuery, mungShops]);

  const fetchFilteredMungShops = async () => {
    console.log("검색어:", searchQuery);
    if (searchQuery) {
      const response = await api().get<MungShop[]>(
        `/mungshop-service/mungshops/search?searchQuery=${encodeURIComponent(
          searchQuery
        )}`
      );
      setFilteredMungShops(response.data);
      setSelectedMarker(response.data[0]);
    } else {
      setFilteredMungShops(mungShops);
    }
  };

  return { mungShops, setSearchQuery, filteredMungShops, selectedMarker, setSelectedMarker };
};
