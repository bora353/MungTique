import Button from "@mui/material/Button";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";

export interface ButtonProps {
  message: string;
  severity: "error" | "warning" | "info" | "success";
  open: boolean;
  onClose: () => void;
}

export default function MuiSnackbar({
  message,
  severity,
  open,
  onClose,
}: ButtonProps) {
  return (
    <div>
      <Button onClick={onClose} />
      <Snackbar
        open={open}
        autoHideDuration={3000}
        onClose={onClose}
        anchorOrigin={{
          vertical: "top",
          horizontal: "center",
        }}
      >
        <Alert
          onClose={onClose}
          severity={severity}
          variant="filled"
          sx={{ width: "100%" }}
        >
          {message}
        </Alert>
      </Snackbar>
    </div>
  );
}
