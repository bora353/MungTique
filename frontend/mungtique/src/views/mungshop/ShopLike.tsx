import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import { Button } from "@mui/material";
import { useEffect, useState } from "react";
import { useSnackbar } from "notistack";
import { useMungshopApi } from "../../hooks/useMungshopApi";

interface ShopLikeProps {
  mungShopId: number;
}

export default function ShopLike({ mungShopId }: ShopLikeProps) {
  const { enqueueSnackbar } = useSnackbar();
  const [isLiked, setIsLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(0);
  const { getLikeStatus, getLikeCount, toggleLikeStatus } = useMungshopApi();
  const userId = localStorage.getItem("userId");

  const fetchLikeStatus = async () => {
    if (!userId) return;

    const status = await getLikeStatus(mungShopId, Number(userId));
    const count = await getLikeCount(mungShopId);
    
    if (status !== null) setIsLiked(status);
    if (count !== null) setLikeCount(count);
  };

  const handleLikeClick = async () => {
    if (!userId) {
      enqueueSnackbar("로그인이 필요한 서비스입니다.", {
        variant: "warning",
      });
      return;
    }

    const response = await toggleLikeStatus(
      mungShopId,
      Number(userId),
      isLiked
    );

    if (response !== null) {
      setIsLiked(!isLiked);
      setLikeCount(response.data);
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
