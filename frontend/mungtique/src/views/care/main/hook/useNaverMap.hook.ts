import { useState } from "react";
import { careApi } from "../../../member/mymung/care.api";

export const useNaverMapHook = () => {
  const mungShops = async () => {
    const response = await careApi.getMungShops();
    return response.data;
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
