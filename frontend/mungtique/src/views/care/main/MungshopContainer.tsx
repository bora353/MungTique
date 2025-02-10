import { useEffect, useState } from "react";
import { useCalculateDistance } from "./hook/useCalculateDistance.hook";
import { useNaverMapHook } from "./hook/useNaverMap.hook";
import { MungShop } from "../../../shared/types/mungshop.interface";
import MapMarkerInfo from "./MapMarkerInfo";
import NaverMap from "./NaverMap";

export default function MungshopContainer() {
  // TODO : hook으로 빼고 전체적으로 정리하기!!
  const [selectedMarker, setSelectedMarker] = useState<MungShop | null>(null);
  const { mungShops, currentPosition, getCurrentPosition } = useNaverMapHook();
  const { distance } = useCalculateDistance(
    selectedMarker?.latitude,
    selectedMarker?.longitude,
    currentPosition?.coords.latitude,
    currentPosition?.coords.longitude
  );

  useEffect(() => {
    getCurrentPosition();
  }, [mungShops]);

  return (
    <div>
      <div style={{ height: "91vh", display: "flex" }}>
        {selectedMarker && (
          <MapMarkerInfo selectedMarker={selectedMarker} distance={distance} />
        )}

        <div style={{ width: selectedMarker ? "75%" : "100%" }}>
          <NaverMap
            currentPosition={currentPosition}
            mungShops={mungShops}
            setSelectedMarker={setSelectedMarker}
          />
        </div>
      </div>
    </div>
  );
}
