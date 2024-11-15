import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import { Button } from "@mui/material";
import { useEffect, useState } from "react";
import { MungShopLike } from "../../../shared/types/mungshop.interface";
import { api } from "../../../shared/api/ApiInterceptor";

interface ShopLikeProps {
  mungShopId: number;
}

export default function ShopLike({ mungShopId }: ShopLikeProps) {
  const [isLiked, setIsLiked] = useState(false);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    const fetchLikeStatus = async () => {
      try {
        const response = await api().get<boolean>(
          "/care-service/mungshop-like-status",
          {
            params: { mungShopId, userId },
          }
        );
        setIsLiked(response.data);
      } catch (error) {
        console.error("Error fetching like status:", error);
      }
    };

    fetchLikeStatus();
  }, [mungShopId, userId]);

  const handleLikeClick = async () => {
    try {
      setIsLiked(!isLiked);

      const endpoint = isLiked
        ? "/care-service/mungshop-unlike"
        : "/care-service/mungshop-like";
      await api().post<MungShopLike>(endpoint, {
        mungShopId,
        userId,
      });
    } catch (error) {
      console.error("Error handling like/unlike:", error);
    }
  };

  return (
    <div>
      <Button onClick={handleLikeClick}>
        {isLiked ? (
          <FavoriteIcon sx={{ color: "tomato" }} />
        ) : (
          <FavoriteBorderIcon sx={{ color: "tomato" }} />
        )}
      </Button>
    </div>
  );
}
