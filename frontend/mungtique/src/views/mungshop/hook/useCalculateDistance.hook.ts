import { useEffect, useState } from "react";

export const useCalculateDistance = (
  lat1?: number,
  lon1?: number,
  lat2?: number,
  lon2?: number
): number => {
  const [distance, setDistance] = useState<number>(0);

  function deg2rad(deg: number): number {
    return deg * (Math.PI / 180);
  }

  function calculateDistance(lat1: number, lon1: number, lat2: number, lon2: number): number {
    const R = 6371e3;
    const dLat = deg2rad(lat2 - lat1);
    const dLon = deg2rad(lon2 - lon1);
    const a =
      Math.sin(dLat / 2) ** 2 +
      Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) ** 2;
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return Math.round(R * c);
  }

  useEffect(() => {
    if (
      lat1 !== undefined &&
      lon1 !== undefined &&
      lat2 !== undefined &&
      lon2 !== undefined
    ) {
      const calculated = calculateDistance(lat1, lon1, lat2, lon2);
      setDistance(calculated);
    } else {
      setDistance(0);
    }
  }, [lat1, lon1, lat2, lon2]);

  return distance;
};
