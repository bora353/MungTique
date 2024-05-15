import { useEffect, useRef } from "react";
import { MungShop } from "../../../shared/types/mungshop.interface";

interface NaverMapProps {
  currentPosition: GeolocationPosition | null;
  mungShops: MungShop[] | undefined;
  setSelectedMarker: (shop: MungShop) => void;
}

export default function NaverMap({
  currentPosition,
  mungShops,
  setSelectedMarker,
}: NaverMapProps) {
  const mapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (mapRef.current && currentPosition) {
      const map = new naver.maps.Map(mapRef.current, {
        center: new naver.maps.LatLng(
          currentPosition.coords.latitude,
          currentPosition.coords.longitude
        ),
        zoom: 16,
        mapTypeId: naver.maps.MapTypeId.NORMAL,
      });

      mungShops.forEach((shop) => {
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(shop.latitude, shop.longitude),
          map: map,
          title: shop.storeName,
        });

        naver.maps.Event.addListener(marker, "click", function (e) {
          setSelectedMarker(shop);
        });
      });

      const infowindow = new naver.maps.InfoWindow();

      if (currentPosition) {
        const location = new naver.maps.LatLng(
          currentPosition.coords.latitude,
          currentPosition.coords.longitude
        );
        map.setCenter(location);
        map.setZoom(16);

        infowindow.setContent(
          '<div style="padding:10px;">' + "현위치" + "</div>"
        );
        infowindow.open(map, location);
        console.log("Coordinates: " + location.toString());
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
