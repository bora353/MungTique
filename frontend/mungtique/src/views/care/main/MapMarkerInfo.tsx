import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import MuiButton from "../../../components/buttons/MuiButton";
import { MungShop } from "../../../shared/types/mungshop.interface";
import ShopLike from "./ShopLike";
import SearchBar from "./SearchBar";

interface MarkerInfoProps {
  selectedMarker: MungShop | null;
  distance: number | null;
  onSearch: (query: string) => void;
}

export default function MapMarkerInfo({
  selectedMarker,
  distance,
  onSearch,
}: MarkerInfoProps) {
  console.log(selectedMarker);

  return (
    <div style={{ width: "25%", overflowY: "auto", maxHeight: "100%" }}>
      <SearchBar onSearch={onSearch} />

      <Card>
        {selectedMarker ? (
          <>
            <CardMedia
              component="img"
              image={selectedMarker.storeImageUrl}
              sx={{ height: 350 }}
            />
            <CardContent>
              <Typography gutterBottom variant="h5" component="div">
                {selectedMarker.storeName}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                <p style={{ fontWeight: "bold", color: "skyblue" }}>
                  {distance}m
                </p>
                <p> ⭐⭐⭐⭐⭐ 하지말까</p>
                <p style={{ fontWeight: "bold" }}>
                  주소: {selectedMarker.storeAddress}
                </p>
                <p style={{ fontWeight: "bold" }}>
                  영업 시간: {selectedMarker.businessHours}
                </p>
                <p style={{ fontWeight: "bold" }}>
                  휴무일: {selectedMarker.closingDays}
                </p>
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
              <ShopLike mungShopId={selectedMarker.mungShopId} />
            </CardActions>
          </>
        ) : (
          <CardContent style={{ textAlign: "center", padding: "20px" }}>
            <Typography variant="body2" color="text.secondary">
              매장을 선택하면 정보가 보여요 ~ !
            </Typography>
          </CardContent>
        )}
      </Card>
    </div>
  );
}
