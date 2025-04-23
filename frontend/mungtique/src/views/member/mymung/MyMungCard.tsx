import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import CardActionArea from "@mui/material/CardActionArea";
import CardMedia from "@mui/material/CardMedia";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../shared/api/apiInterceptor";
import { MyMung } from "../../../shared/types/mungjoin.interface";
import MyMungImageUploadForm from "./MyMungImageUploadForm";
import { Box, Button } from "@mui/material";

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
    setSelectedDogId(dogId);
    setOpen(true);
  };

  return (
    <div className="p-6 bg-gray-50 rounded-lg shadow-md">
      <Grid container spacing={3}>
        {myMungs.length === 0 ? (
          <Grid item xs={12} className="flex justify-center">
            <Card
              sx={{
                width: 240,
                height: 180,
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                border: "2px dashed #ccc",
              }}
            >
              <CardContent className="text-center">
                <Typography variant="body1" color="text.secondary">
                  강아지 정보를 등록하세요! 🐾
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ) : (
          myMungs.map((mung) => (
            <Grid item xs={12} sm={6} md={5} lg={4} key={mung.dogId}>
              <Card sx={{ borderRadius: 3, boxShadow: 3, overflow: "hidden" }}>
                <CardActionArea onClick={() => handleCardClick(mung.dogId)}>
                  {/* <CardMedia
                    component="img"
                    height="180"
                    width="100%"
                    image={mung.imageUrl || "https://placehold.co/200x150"}
                    alt={mung.dogName}
                    sx={{ objectFit: "cover" }}
                  /> */}
                  <Box
                    sx={{
                      width: "100%",
                      height: "200px",
                      overflow: "hidden",
                      position: "relative",
                    }}
                  >
                    <CardMedia
                      component="img"
                      image={mung.imageUrl || "https://placehold.co/200x150"}
                      alt={mung.dogName}
                      sx={{
                        position: "absolute",
                        top: 0,
                        left: 0,
                        width: "100%",
                        height: "100%",
                        objectFit: "cover",
                        objectPosition: "center",
                      }}
                    />
                  </Box>
                  <CardContent>
                    <Typography variant="h6" fontWeight="bold" color="primary">
                      {mung.dogName}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      🐾 견종: {mung.breedType}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      ⚖ 몸무게: {mung.weight}kg
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      🎂 나이: {mung.age}살
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      🚻 성별: {mung.gender}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      ✂️ 중성화 여부: {mung.fixed}
                    </Typography>
                  </CardContent>
                </CardActionArea>
                <CardContent className="flex justify-center">
                  <Button
                    color="info"
                    type="button"
                    variant="contained"
                    onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                      e.stopPropagation();
                      handleImageUploadClick(mung.dogId);
                    }}
                  >
                    이미지 등록
                  </Button>
                </CardContent>
              </Card>
            </Grid>
          ))
        )}
      </Grid>

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
