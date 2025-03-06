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
      
      setTimeout(() => {
        onClose();
        window.location.reload();
      }, 1000);
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
    <Dialog 
      open={open} 
      onClose={onClose}
      PaperProps={{
        style: {
          borderRadius: '16px',
          padding: '8px',
          border: '2px solid #FFC0CB',
          maxWidth: '400px',
          width: '100%'
        }
      }}
    >
      <div className="text-center py-3 px-2 border-b-2 border-pink-200">
        <h2 className="text-pink-500 text-xl font-bold flex items-center justify-center">
          <span role="img" aria-label="camera" className="mr-2">📸</span>
          멍냥이 사진 올리기
          <span role="img" aria-label="paw" className="ml-2">🐾</span>
        </h2>
      </div>

      <DialogContent sx={{ padding: '20px' }}>
        <form onSubmit={handleSubmit} className="flex flex-col items-center">
          <div className="mb-4 w-full text-center">
            <p className="text-gray-600 mb-2 text-sm">
              귀여운 사진을 선택해주세요!
            </p>
            
            <input
              type="file"
              onChange={handleFileChange}
              accept="image/*"
              style={{ display: "none" }}
              id="file-upload"
            />
            
            <label htmlFor="file-upload" className="w-full">
              <Button
                variant="contained"
                component="span"
                fullWidth
                sx={{
                  backgroundColor: '#FFA0C0',
                  '&:hover': {
                    backgroundColor: '#FF80B0',
                  },
                  borderRadius: '12px',
                  textTransform: 'none',
                  padding: '10px',
                  fontWeight: 'bold'
                }}
              >
                <span role="img" aria-label="photo" className="mr-2">🖼️</span>
                사진 선택하기
              </Button>
            </label>
          </div>
          
          {previewUrl && (
            <div className="flex flex-col items-center mt-2">
              <div className="border-4 border-pink-200 rounded-lg p-1 mb-4 shadow-md">
                <img
                  src={previewUrl}
                  alt="Preview"
                  className="w-40 h-40 object-cover rounded-md"
                />
              </div>
              
              <Button
                type="submit"
                variant="contained"
                fullWidth
                sx={{
                  backgroundColor: '#82D8BD',
                  '&:hover': {
                    backgroundColor: '#6BC7AC',
                  },
                  borderRadius: '12px',
                  textTransform: 'none',
                  padding: '10px',
                  fontWeight: 'bold'
                }}
              >
                <span role="img" aria-label="upload" className="mr-2">⬆️</span>
                사진 업로드하기
              </Button>
            </div>
          )}
        </form>
      </DialogContent>
      
      <DialogActions 
        sx={{ 
          padding: '16px', 
          justifyContent: 'center',
          borderTop: '2px dashed #FFC0CB',
          marginTop: '8px'
        }}
      >
        <Button 
          onClick={handleClose} 
          sx={{
            color: '#FB6F92',
            borderRadius: '12px',
            border: '2px solid #FB6F92',
            '&:hover': {
              backgroundColor: '#FFF0F5',
              border: '2px solid #FB6F92',
            },
            textTransform: 'none',
            fontWeight: 'bold',
            padding: '6px 16px'
          }}
          variant="outlined"
        >
          닫기
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
          sx={{ 
            width: "100%", 
            borderRadius: '12px',
            fontWeight: 'bold',
            '& .MuiAlert-icon': {
              fontSize: '1.5rem'
            }
          }}
        >
          {message}
        </Alert>
      </Snackbar>
    </Dialog>
  );
}