import axios from "axios";
import { useEffect, useState } from "react";
import MuiAppBar from "../../components/atomic/bar/MuiAppBar";
import { useCalculateDistance } from "../../shared/hooks/useCalculateDistance.hook";
import { useNaverMapHook } from "../../shared/hooks/useNaverMap.hook";
import { MungShop } from "../../shared/types/mungshop.interface";
import MapMarkerInfo from "./MapMarkerInfo";
import NaverMap from "./NaverMap";

export default function CareContainer() {
  // TODO : hook으로 빼고 전체적으로 정리하기!!
  const [selectedMarker, setSelectedMarker] = useState<MungShop | null>(null);
  const { mungShops, currentPosition, getCurrentPosition } = useNaverMapHook();
  const { distance } = useCalculateDistance(
    selectedMarker?.latitude,
    selectedMarker?.longitude,
    currentPosition?.coords.latitude,
    currentPosition?.coords.longitude
  );

  const shopLikeHandler = () => {
    alert("조아욧!");
    // api 연결 및 하트 색 있고 없게 변경 필요
  };

  useEffect(() => {
    getCurrentPosition();
  }, [mungShops]);

  return (
    <div>
      <MuiAppBar />
      <div style={{ height: "91vh", display: "flex" }}>
        {selectedMarker && (
          <MapMarkerInfo
            selectedMarker={selectedMarker}
            distance={distance}
            shopLikeHandler={shopLikeHandler}
          />
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
