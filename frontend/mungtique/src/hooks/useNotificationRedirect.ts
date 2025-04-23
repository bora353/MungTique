import { useSnackbar } from "notistack";
import { useNavigate } from "react-router-dom";

const useNotificationRedirect = () => {
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();

  const showNotificationAndRedirect = (
    message: string,
    variant: "success" | "error" | "warning" | "info" = "info",
    redirectPath: string,
    autoHideDuration: number = 2000
  ) => {
    // 알림 띄우기
    enqueueSnackbar(message, {
      variant: variant,
      autoHideDuration: autoHideDuration, // 알림이 사라지는 시간
    });

    // 알림 후 페이지 이동
    setTimeout(() => {
      navigate(redirectPath); // 지정된 경로로 이동
    }, autoHideDuration); // 알림이 사라진 후에 이동
  };

  return { showNotificationAndRedirect };
};

export default useNotificationRedirect;
