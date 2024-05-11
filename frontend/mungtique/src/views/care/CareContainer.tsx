import { useState, useEffect, useRef } from "react";
import MuiAppBar from "../../components/atomic/bar/MuiAppBar";
import axios from "axios";
import MuiButton from "../../components/atomic/buttons/MuiButton";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { MungShop } from "../../shared/types/mungshop.interface";

export default function CareContainer() {
  // TODO : hook으로 빼고 전체적으로 정리하기!!
  const mapRef = useRef<HTMLDivElement>(null);
  const [selectedMarker, setSelectedMarker] = useState<string | null>(null);
  const [mungShops, setMungShops] = useState<MungShop[]>([]);
  const [currentLatitude, setCurrentLatitude] = useState<number | null>(null);
  const [currentLongitude, setCurrentLongitude] = useState<number | null>(null);

  function calculateDistance(lat1, lon1, lat2, lon2) {
    const R = 6371e3;
    const dLat = deg2rad(lat2 - lat1);
    const dLon = deg2rad(lon2 - lon1);
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(deg2rad(lat1)) *
        Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c;
    return Math.round(distance);
  }

  function deg2rad(deg) {
    return deg * (Math.PI / 180);
  }

  // TODO : 지도 로딩 시 default 위치에 있다가 현재 위치로 넘어가는 것 수정 필요
  const basePath = import.meta.env.VITE_BACKEND_SERVER_CARE;

  const getMungShops = async () => {
    try {
      const response = await axios.get(`${basePath}/care/mungshops`);
      setMungShops(response.data);
    } catch (error) {
      console.error("Error fetching mung shops:", error);
    }
  };

  useEffect(() => {
    getMungShops();
  }, []);

  useEffect(() => {
    if (mapRef.current) {
      const map = new naver.maps.Map(mapRef.current, {
        center: new naver.maps.LatLng(37.5666805, 126.9784147),
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

      function onSuccessGeolocation(position: GeolocationPosition) {
        const location = new naver.maps.LatLng(
          position.coords.latitude,
          position.coords.longitude
        );
        map.setCenter(location);
        map.setZoom(16);

        setCurrentLatitude(position.coords.latitude);
        setCurrentLongitude(position.coords.longitude);

        infowindow.setContent(
          '<div style="padding:10px;">' + "현재 위치" + "</div>"
        );
        infowindow.open(map, location);
        console.log("Coordinates: " + location.toString());
      }

      function onErrorGeolocation() {
        const center = map.getCenter();

        infowindow.setContent(
          '<div style="padding:20px;"><h5 style="margin-bottom:5px;color:#f00;">Geolocation failed!</h5>' +
            "latitude: " +
            center.lat() +
            "<br />longitude: " +
            center.lng() +
            "</div>"
        );
        infowindow.open(map, center);
      }

      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          onSuccessGeolocation,
          onErrorGeolocation
        );
      } else {
        const center = map.getCenter();
        infowindow.setContent(
          '<div style="padding:20px;"><h5 style="margin-bottom:5px;color:#f00;">Geolocation not supported</h5></div>'
        );
        infowindow.open(map, center);
      }
    }
  }, [mungShops]);

  return (
    <div>
      <MuiAppBar />
      <div style={{ display: "flex" }}>
        {selectedMarker && (
          <div style={{ width: "30%", overflowY: "auto", maxHeight: "700px" }}>
            <Card>
              <CardMedia
                component="img"
                alt="green iguana"
                image="/images/mungtique.jpg"
              />
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  {selectedMarker?.storeName}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  <p style={{ fontWeight: "bold", color: "skyblue" }}>
                    {calculateDistance(
                      selectedMarker.latitude,
                      selectedMarker.longitude,
                      currentLatitude,
                      currentLongitude
                    )}
                    m
                  </p>
                  <p>{selectedMarker.starRating} ⭐⭐⭐⭐⭐ 하지말까</p>
                  <p style={{ fontWeight: "bold" }}>
                    주소: {selectedMarker.storeAddress}
                  </p>
                  <p style={{ fontWeight: "bold" }}>
                    영업 시간: {selectedMarker.businessHours}
                  </p>
                  <p style={{ fontWeight: "bold" }}>
                    휴무일: {selectedMarker.closingDays}
                  </p>{" "}
                  <p>가능한 견종: {selectedMarker.breeds}</p>
                  <p>실제 강아지들 미용한 사진 / 후기</p>
                  <p>가격(따로 DB??)</p>
                </Typography>
              </CardContent>
              <CardActions>
                <MuiButton
                  type="button"
                  color="warning"
                  variant="contained"
                  value="예약하기"
                />
                <p style={{ color: "pink" }}>❤️ (DB? localStorage?)</p>
              </CardActions>
            </Card>
          </div>
        )}

        <div style={{ width: selectedMarker ? "70%" : "100%" }}>
          <div
            ref={mapRef}
            style={{
              width: "100%",
              height: "700px",
            }}
          ></div>
        </div>
      </div>
    </div>
  );
}
