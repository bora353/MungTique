import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import MuiButton from "../../../components/buttons/MuiButton";
import { api } from "../../../shared/api/ApiInterceptor";
import { MyMung } from "../../../shared/types/mungjoin.interface";
import MyMungImageUploadForm from "./MyMungImageUploadForm";

export default function MyMungCard() {
  const navigate = useNavigate();
  const [myMungs, setMyMungs] = useState<MyMung[]>([]);
  const [open, setOpen] = useState(false);
  const [selectedDogId, setSelectedDogId] = useState<number | null>(null);

  const userId = localStorage.getItem("userId");

  const getMyMungs = async () => {
    try {
      const response = await api().get<MyMung[]>(`/dog-service/dogs/${userId}`);
      setMyMungs(response.data);
      console.log(response.data);
    } catch (error) {
      console.error("Error getMyMungs : ", error);
    }
  };

  useEffect(() => {
    getMyMungs();
  }, [userId]);

  const handleCardClick = (dogId: number) => {
    navigate(`/mymung/${dogId}`);
  };

  const handleImageUploadClick = (dogId: number) => {
    // navigate(`/mungimage/${dogId}`);
    setSelectedDogId(dogId); 
    setOpen(true);
  };

  return (
    <div style={{ display: "flex", flexWrap: "wrap" }}>
      {myMungs.length === 0 ? (
        <div style={{ width: "100%", textAlign: "center", padding: "20px" }}>
          <Card sx={{ width: 200, marginBottom: 2, marginRight: 2 }}>
            <CardContent>
              <Typography variant="body1" color="text.secondary">
                뭉 정보를 등록하세요 <br />
              </Typography>
            </CardContent>
          </Card>
        </div>
      ) : (
        myMungs.map((mung) => (
          <Card
            key={mung.dogId}
            sx={{ width: 200, marginBottom: 2, marginRight: 2 }}
            onClick={() => handleCardClick(mung.dogId)}
          >
            <CardContent>
              <MuiButton
                color="info"
                type="button"
                value="이미지 등록"
                variant="outlined"
                onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                  e.stopPropagation();
                  handleImageUploadClick(mung.dogId);
                }}
              />
              <div
                style={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  height: "200px",
                }}
              >
                <img
                  style={{ width: "100px", height: "auto" }}
                  src={mung.imageUrl}
                  alt={mung.dogName}
                />
              </div>
              <Typography gutterBottom variant="h5" component="div">
                {mung.dogName}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                견종: {mung.breedType}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                몸무게: {mung.weight}kg
              </Typography>
              <Typography variant="body2" color="text.secondary">
                나이: {mung.age}살
              </Typography>
              <Typography variant="body2" color="text.secondary">
                성별: {mung.gender}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                중성화여부: {mung.fixed}
              </Typography>
            </CardContent>
          </Card>
        ))
      )}
      {selectedDogId !== null && (
        <MyMungImageUploadForm
          open={open}
          onClose={() => setOpen(false)}
          dogId={selectedDogId}
        />
      )}
    </div>
  );
}
