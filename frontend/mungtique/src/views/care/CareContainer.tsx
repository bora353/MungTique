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
import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import { Button } from "@mui/material";
import { useNaverMapHook } from "../../shared/hooks/useNaverMap.hook";
import NaverMap from "./NaverMap";

export default function CareContainer() {
  // TODO : hook으로 빼고 전체적으로 정리하기!!
  const [selectedMarker, setSelectedMarker] = useState<string | null>(null);
  const [mungShops, setMungShops] = useState<MungShop[]>([]);
  const [currentPosition, setCurrentPosition] =
    useState<GeolocationPosition | null>(null);

  const { distance } = useNaverMapHook(
    selectedMarker?.latitude,
    selectedMarker?.longitude,
    currentPosition?.coords.latitude,
    currentPosition?.coords.longitude
  );

  const shopLikeHandler = () => {
    alert("조아욧!");
    // api 연결 및 하트 색 있고 없게 변경 필요
  };

  const basePath = import.meta.env.VITE_BACKEND_SERVER_CARE;

  const getMungShops = async () => {
    try {
      const response = await axios.get(`${basePath}/care/mungshops`);
      setMungShops(response.data);
    } catch (error) {
      console.error("Error fetching mung shops:", error);
    }
  };

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

  useEffect(() => {
    getMungShops();
    getCurrentPosition();
  }, []);

  return (
    <div>
      <MuiAppBar />
      <div style={{ height: "91vh", display: "flex" }}>
        {selectedMarker && (
          <div style={{ width: "25%", overflowY: "auto", maxHeight: "100%" }}>
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
                    {distance}m
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
                <Button onClick={shopLikeHandler}>
                  <FavoriteIcon sx={{ color: "tomato" }} />
                </Button>
                <FavoriteBorderIcon sx={{ color: "tomato" }} />
              </CardActions>
            </Card>
          </div>
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
