import { useState } from "react";
import { useCareInfoQuery } from "../queries/care.query";

export const useNaverMapHook = () => {
  const { isLoading, isError, data } = useCareInfoQuery();

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

  return { mungShops: data, currentPosition, getCurrentPosition };
};
