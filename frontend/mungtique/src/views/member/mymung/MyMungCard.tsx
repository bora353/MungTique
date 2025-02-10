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
      {myMungs.map((mung) => (
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

            <CardMedia
              component="img"
              height="140"
              image={
                mung.image
                  ? mung.image?.url
                  : "/static/images/cards/contemplative-reptile.jpg"
              }
              alt={mung.mungName}
            />
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
      ))}
    </div>
  );
}
