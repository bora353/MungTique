import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import MuiButton from "../../../components/buttons/MuiButton";

export default function LoginOptions() {
  const naverUrl = import.meta.env.VITE_AUTH_SERVER_NAVER;
  const kakaoUrl = import.meta.env.VITE_AUTH_SERVER_KAKAO;

  const onNaverLogin = () => {
    window.location.href = naverUrl;
  };
  const onKakaoLogin = () => {
    window.location.href = kakaoUrl;
  };

  return (
    <>
      <div className="flex my-3 justify-end">
        <Link to="/findinfo">
          <Button color="primary" style={{ fontSize: "0.75rem" }}>
            아이디 찾기
          </Button>
        </Link>
        <Link to="/findinfo">
          <Button color="primary" style={{ fontSize: "0.75rem" }}>
            비밀번호 찾기
          </Button>
        </Link>
      </div>

      <div className="flex justify-center my-1">
        <div className="mx-1">
          <img
            src="/images/naver_login.png"
            alt="Naver Login"
            onClick={onNaverLogin}
            style={{ cursor: "pointer", width: "170px", height: "50px" }}
          />
        </div>
        <div className="mx-1">
          <img
            src="/images/kakao_login.png"
            alt="Kakao Login"
            onClick={onKakaoLogin}
            style={{ cursor: "pointer", width: "170px", height: "50px" }}
          />
        </div>
      </div>
      <div className="my-5">
        <Link to="/join">
          <MuiButton
            color="primary"
            type="button"
            value="뭉티끄 10초 회원가입"
            variant="text"
          />
        </Link>
      </div>
    </>
  );
}
