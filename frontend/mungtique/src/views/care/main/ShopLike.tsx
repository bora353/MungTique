import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import { Button } from "@mui/material";
import { useEffect, useState } from "react";
import { MungShopLike } from "../../../shared/types/mungshop.interface";
import { api } from "../../../shared/api/ApiInterceptor";
import MuiSnackbar from "../../../components/snackbar/MuiSnackbar";

interface ShopLikeProps {
  mungShopId: number;
}

export default function ShopLike({ mungShopId }: ShopLikeProps) {
  const [isLiked, setIsLiked] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarType, setSnackbarType] = useState<
    "error" | "warning" | "info" | "success"
  >("error");

  const userId = localStorage.getItem("userId");

  const params: Record<string, string | number> = { mungShopId };
  if (userId) params.userId = userId;

  const fetchLikeStatus = async () => {
    try {
      const response = await api().get<boolean>(
        "/mungshop-service/mungshops/like-status",
        {
          params,
        }
      );
      setIsLiked(response.data);
    } catch (error) {
      console.error("Error fetching like status:", error);
    }
  };

  const handleLikeClick = async () => {
    if (!userId) {
      setSnackbarMessage("로그인이 필요한 서비스입니다.");
      setSnackbarType("warning");
      setOpenSnackbar(true);
      return;
    }

    try {
      const endpoint = isLiked
        ? `/mungshop-service/mungshops/${mungShopId}/unlike`
        : `/mungshop-service/mungshops/${mungShopId}/like`;

      await api().post<MungShopLike>(endpoint, {
        userId,
      });

      setIsLiked(!isLiked);
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
      </Button>
      <MuiSnackbar
        message={snackbarMessage}
        severity={snackbarType}
        open={openSnackbar}
        onClose={() => setOpenSnackbar(false)}
      />
    </div>
  );
}
