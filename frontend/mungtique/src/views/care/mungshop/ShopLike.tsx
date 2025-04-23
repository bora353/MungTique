import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import { Button } from "@mui/material";
import { useEffect, useState } from "react";
import { api } from "../../../shared/api/apiInterceptor";
import { useSnackbar } from "notistack";

interface ShopLikeProps {
  mungShopId: number;
}

export default function ShopLike({ mungShopId }: ShopLikeProps) {
  const { enqueueSnackbar } = useSnackbar();
  const [isLiked, setIsLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(0);
  const userId = localStorage.getItem("userId");

  const fetchLikeStatus = async () => {
    if (!userId) return;

    try {
      const statusResponse = await api().get<boolean>(
        `/mungshop-service/mungshops/${mungShopId}/like-status/${userId}`
      );
      setIsLiked(statusResponse.data);

      const countResponse = await api().get<number>(
        `/mungshop-service/mungshops/like-status?mungShopId=${mungShopId}`
      );
      setLikeCount(countResponse.data);
    } catch (error) {
      console.error("Error fetching like status:", error);
    }
  };

  const handleLikeClick = async () => {
    if (!userId) {
      enqueueSnackbar("로그인이 필요한 서비스입니다.", {
        variant: "warning",
      });
      return;
    }

    try {
      let response;

      if (isLiked) {
        response = await api().delete<number>(
          `/mungshop-service/mungshops/${mungShopId}/unlike/${userId}`
        );
      } else {
        response = await api().post<number>(
          `/mungshop-service/mungshops/${mungShopId}/like/${userId}`
        );
      }
      setIsLiked((prev) => !prev);
      setLikeCount(response.data);
    } catch (error) {
      console.error("Error handling like/unlike:", error);
    }
  };

  useEffect(() => {
    fetchLikeStatus();
  }, [mungShopId, userId]);

  return (
    <div className="size-10 mx-2">
      <Button onClick={handleLikeClick}>
        {isLiked ? (
          <FavoriteIcon sx={{ color: "tomato" }} />
        ) : (
          <FavoriteBorderIcon sx={{ color: "tomato" }} />
        )}
        <span className="ml-2 text-sm text-[tomato]">{likeCount} </span>
      </Button>
    </div>
  );
}
