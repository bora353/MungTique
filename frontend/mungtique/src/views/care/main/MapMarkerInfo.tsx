import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { MungShop } from "../../../shared/types/mungshop.interface";
import ShopLike from "./ShopLike";
import SearchBar from "./SearchBar";
import { useState } from "react";
import MapTabMenu from "./MapTabMenu";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import StorefrontIcon from "@mui/icons-material/Storefront"; 

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
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState("홈");

  return (
    <div style={{ width: "27%", overflowY: "auto", maxHeight: "100%" }}>
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
              <div className="flex items-center justify-between">
                <span className="text-lg font-bold">
                  {selectedMarker.storeName} &nbsp;
                </span>
                <span className="text-sm text-blue-400">{distance}m</span>
                &nbsp;
                <span className="text-sm">⭐ 4.5</span>
                <ShopLike mungShopId={selectedMarker.mungShopId} />
              </div>

              <CardActions>
                <Button
                  type="button"
                  color="warning"
                  variant="contained"
                  fullWidth
                  onClick={() => navigate("/reservation")}
                >
                  예약하기
                </Button>
              </CardActions>

              <MapTabMenu activeTab={activeTab} setActiveTab={setActiveTab} />

              {activeTab === "가격" && (
                <div className="p-3 text-gray-600">
                  <div className="flex flex-col gap-3">
                    {selectedMarker?.mungShopPrices.map((price) => (
                      <div
                        key={price.mungShopPriceId}
                        className="flex flex-col bg-white shadow-sm rounded-md p-3"
                      >
                        <div className="flex justify-between items-center mb-1">
                          <div className="flex items-center gap-1">
                            <span className="text-sm text-gray-700">
                              {price.breedType}
                            </span>
                          </div>
                          <span className="text-xs text-gray-400">
                            {price.serviceType}
                          </span>
                        </div>
                        <div className="flex justify-between items-center">
                          <span className="text-lg font-semibold text-blue-500 ml-auto">
                            {price.price}원
                          </span>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              )}

              {activeTab === "홈" && (
                <div className="p-4 text-gray-500">
                  <div className="flex items-center gap-2 mt-2">
                    <span>
                      <span>📍</span> 주소: {selectedMarker.storeAddress}
                    </span>
                  </div>
                  <div className="flex items-center gap-2 mt-2">
                    <span>
                      <span>⏰</span> 영업 시간: {selectedMarker.businessHours}
                    </span>
                  </div>
                  <div className="flex items-center gap-2 mt-2">
                    <span>
                      <span>🚫</span> 휴무일: {selectedMarker.closingDays}
                    </span>
                  </div>
                  <div className="flex items-center gap-2 mt-2">
                    <span>
                      <span>📍</span> 가능한 견종: {selectedMarker.breedType}
                    </span>
                  </div>
                </div>
              )}
            </CardContent>
          </>
        ) : (
          <CardContent className="flex flex-col items-center justify-center text-center py-10">
            <StorefrontIcon sx={{ fontSize: 50, color: "#A0A0A0" }} />
            <Typography variant="h6" color="text.primary" className="mt-2">
              매장을 선택해 주세요
            </Typography>
            <Typography variant="body2" color="text.secondary" className="mt-1">
              원하는 매장을 클릭하면 정보가 표시됩니다.
            </Typography>
          </CardContent>
        )}
      </Card>
    </div>
  );
}
