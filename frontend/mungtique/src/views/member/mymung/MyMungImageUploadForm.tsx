import { useState } from "react";
import { api } from "../../../shared/api/ApiInterceptor";
import axios from "axios";

export default function MyMungImageUploadForm() {
  const [file, setFile] = useState<File | null>(null);
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);
  const [message, setMessage] = useState<string>("");

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
      return;
    }

    // TODO : mungId는 localStorage에서!!! (mungId 저장해뒀던가..;;)
    const mungId: string = "1";

    const formData = new FormData();
    formData.append("file", file);
    formData.append("dogId", mungId);

    console.log("FormData being sent:");
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

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
      console.log(response.data);
    } catch (error) {
      setMessage("Image upload failed");
      console.error(error);
    }
  };

  return (
    <div>
      <h1>Image Upload</h1>
      <form onSubmit={handleSubmit}>
        <input type="file" onChange={handleFileChange} />
        {previewUrl && (
          <img
            src={previewUrl}
            alt="Preview"
            style={{ width: "100px", height: "100px" }}
          />
        )}
        <button type="submit">Upload Button</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}
