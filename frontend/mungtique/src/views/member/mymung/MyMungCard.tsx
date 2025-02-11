import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { Button, CardActionArea } from "@mui/material";
import { useEffect, useState } from "react";
import { MyMung } from "../../../shared/types/mungjoin.interface";
import MuiButton from "../../../components/buttons/MuiButton";
import { Link } from "react-router-dom";
import { api } from "../../../shared/api/ApiInterceptor";

export default function MyMungCard() {
  const [myMungs, setMyMungs] = useState<MyMung[]>([]);

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
  }, []);

  return (
    <div style={{ display: "flex", flexWrap: "wrap" }}>
      {myMungs.length === 0 ? (
        <div style={{ width: "100%", textAlign: "center", padding: "20px" }}>
          <Typography variant="h6" color="text.secondary">
            뭉정보를 등록하세요 <br />
            <Link to="/mungimage">
              <MuiButton
                color="info"
                type="button"
                value="이미지 등록"
                variant="outlined"
              />
            </Link>
          </Typography>
        </div>
      ) : (
        myMungs.map((mung) => (
          <Card
            key={mung.myMungId}
            sx={{ maxWidth: 345, marginBottom: 2, marginRight: 2 }}
          >
            <CardActionArea>
              <Link to="/mungimage">
                <MuiButton
                  color="info"
                  type="button"
                  value="이미지등록"
                  variant="outlined"
                />
              </Link>
              <div
                style={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  height: "200px",
                }}
              >
                <CardMedia
                  component="img"
                  style={{ width: "100px", height: "auto" }} // 너비를 100px로 설정하고 높이는 자동 조정
                  image={mung.imageUrl}
                  alt={mung.mungName}
                />
              </div>
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  {mung.mungName}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  견종: {mung.breed}
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
            </CardActionArea>
          </Card>
        ))
      )}
    </div>
  );
}
