import { useEffect, useRef } from "react";
import { MungShop } from "../../../shared/types/mungshop.interface";

interface NaverMapProps {
  currentPosition: GeolocationPosition | null;
  mungShops: MungShop[] | undefined;
  setSelectedMarker: (shop: MungShop) => void;
  selectedMarker?: MungShop | null;
}

export default function NaverMap({
  currentPosition,
  mungShops,
  setSelectedMarker,
  selectedMarker,
}: NaverMapProps) {
  const mapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (mapRef.current && currentPosition) {
      const map = new naver.maps.Map(mapRef.current, {
        center: new naver.maps.LatLng(
          // currentPosition.coords.latitude,
          // currentPosition.coords.longitude
          37.206967589057925, 127.05491620429956
        ),
        zoom: 16,
        mapTypeId: naver.maps.MapTypeId.NORMAL,
      });

      mungShops?.forEach((shop) => {
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(shop.latitude, shop.longitude),
          map: map,
          title: shop.storeName,
        });

        naver.maps.Event.addListener(marker, "click", function () {
          setSelectedMarker(shop);
        });
      });

      if (selectedMarker?.latitude && selectedMarker?.longitude) {
        const selectedLatLng = new naver.maps.LatLng(
          selectedMarker.latitude,
          selectedMarker.longitude
        );
        map.setCenter(selectedLatLng);
        map.setZoom(16);
      }

      const infowindow = new naver.maps.InfoWindow({
        content: '<div style="padding:5px;">' + "현위치" + "</div>",
      });

      if (currentPosition) {
        const location = new naver.maps.LatLng(
          currentPosition.coords.latitude,
          currentPosition.coords.longitude
        );
        map.setCenter(location);
        map.setZoom(16);

        infowindow.open(map, location);
        //console.log("Coordinates: " + location.toString());
      }
    }
  }, [currentPosition, mapRef, mungShops, setSelectedMarker]);

  return (
    <div
      ref={mapRef}
      style={{
        width: "100%",
        height: "100%",
      }}
    ></div>
  );
}
