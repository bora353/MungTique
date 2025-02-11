import {
  Alert,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  Snackbar
} from "@mui/material";
import { useState } from "react";
import { api } from "../../../shared/api/ApiInterceptor";

interface MyMungImageUploadFormProps {
  open: boolean;
  onClose: () => void;
  dogId: number;
}

export default function MyMungImageUploadForm({
  open,
  onClose,
  dogId,
}: MyMungImageUploadFormProps) {
  const [file, setFile] = useState<File | null>(null);
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);
  const [message, setMessage] = useState<string>("");
  const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFile = event.target.files?.[0];
    if (selectedFile) {
      setFile(selectedFile);

      const reader = new FileReader();
      reader.onloadend = () => {
        setPreviewUrl(reader.result as string);
      };
      reader.readAsDataURL(selectedFile);
    }
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!file) {
      setMessage("No file selected");
      setOpenSnackbar(true);
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("dogId", dogId.toString());

    try {
      const response = await api().post(
        `/dog-service/dogs/upload-image`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      setMessage("Image uploaded successfully");
      setOpenSnackbar(true);
      onClose();
    } catch (error) {
      setMessage("Image upload failed");
      setOpenSnackbar(true);
    }
  };

  const handleClose = () => {
    setFile(null);
    setPreviewUrl(null);
    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogContent>
        <form onSubmit={handleSubmit}>
          <input
            type="file"
            onChange={handleFileChange}
            accept="image/*"
            style={{ display: "none" }}
            id="file-upload"
          />
          <label htmlFor="file-upload">
            <Button variant="contained" component="span" size="small">
              Select Image
            </Button>
          </label>
          {previewUrl && (
            <>
              <img
                src={previewUrl}
                alt="Preview"
                style={{ width: "100px", height: "100px", marginTop: "10px" }}
              />
              <div>
                <Button
                  type="submit"
                  variant="outlined"
                  color="primary"
                  style={{ marginTop: "10px" }}
                  size="small"
                >
                  Upload Image
                </Button>
              </div>
            </>
          )}
        </form>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="primary">
          Close
        </Button>
      </DialogActions>

      <Snackbar
        open={openSnackbar}
        autoHideDuration={6000}
        onClose={() => setOpenSnackbar(false)}
      >
        <Alert
          onClose={() => setOpenSnackbar(false)}
          severity={message.includes("failed") ? "error" : "success"}
          sx={{ width: "100%" }}
        >
          {message}
        </Alert>
      </Snackbar>
    </Dialog>
  );
}
