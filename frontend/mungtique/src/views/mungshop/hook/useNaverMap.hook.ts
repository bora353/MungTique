import { useEffect, useState } from "react";
import { MungShop } from "../../../../shared/types/mungshop.interface";
import { mungshopApi } from "../mungshop.api";

export const useNaverMapHook = () => {
  const [mungShops, setMungShops] = useState<MungShop[]>([]);

  useEffect(() => {
    fetchMungShops();
  }, []);

  const fetchMungShops = async () => {
    const response = await mungshopApi.getMungShops();
    setMungShops(response.data);
  };

  const [currentPosition, setCurrentPosition] =
    useState<GeolocationPosition | null>(null);

  const getCurrentPosition = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setCurrentPosition(position);
        },
        (error) => {
          console.error("Error getting current position:", error);
        }
      );
    } else {
      console.error("Geolocation is not supported.");
    }
  };

  return { mungShops, currentPosition, getCurrentPosition };
};
