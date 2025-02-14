import { useEffect } from "react";
import { useCalculateDistance } from "./hook/useCalculateDistance.hook";
import { useNaverMapHook } from "./hook/useNaverMap.hook";
import { useSearchQueryHook } from "./hook/useSearchQuery.hook";
import MapMarkerInfo from "./MapMarkerInfo";
import NaverMap from "./NaverMap";

export default function MungshopContainer() {
  const { mungShops, currentPosition, getCurrentPosition } = useNaverMapHook();

  const {
    setSearchQuery,
    filteredMungShops,
    selectedMarker,
    setSelectedMarker,
  } = useSearchQueryHook(mungShops);

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
        <MapMarkerInfo
          selectedMarker={selectedMarker}
          distance={distance}
          onSearch={setSearchQuery}
        />

        <div style={{ width: selectedMarker ? "73%" : "100%" }}>
          <NaverMap
            currentPosition={currentPosition}
            mungShops={filteredMungShops}
            setSelectedMarker={setSelectedMarker}
            selectedMarker={selectedMarker}
          />
        </div>
      </div>
    </div>
  );
}
