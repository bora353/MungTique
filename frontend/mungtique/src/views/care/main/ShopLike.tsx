import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import { Button } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import { MungShopLike } from "../../../shared/types/mungshop.interface";

export default function ShopLike({ mungShopId }) {
  const [isLiked, setIsLiked] = useState(false);
  const basePath = import.meta.env.VITE_BACKEND_SERVER_CARE;
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    const fetchLikeStatus = async () => {
      try {
        const response = await axios.get<boolean>(
          basePath + "/care/mungshop-like-status",
          {
            params: { mungShopId, userId },
          }
        );
        setIsLiked(response.data);
        console.log("과연? " + isLiked);
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
        ? "/care/mungshop-unlike"
        : "/care/mungshop-like";
      await axios.post<MungShopLike>(basePath + endpoint, {
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
