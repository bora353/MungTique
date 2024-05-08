import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import MuiButton from "../../../components/atomic/buttons/MuiButton";
import axios from "axios";

export default function LoginSignInButton() {
  const onNaverLogin = () => {
    window.location.href = "http://localhost:8082/oauth2/authorization/naver";
  };
  const onKakaoLogin = () => {
    window.location.href = "http://localhost:8082/oauth2/authorization/kakao";
  };

  return (
    <>
      <div className="flex my-3 text-sm">
        <div className="mx-8">
          <input type="checkbox" />
          로그인 상태 유지
        </div>
        <div>
          <Link to="/findinfo">
            <Button color="primary">아이디/비밀번호 찾기</Button>
          </Link>
        </div>
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
            value="10초 회원가입"
            variant="text"
          />
        </Link>
      </div>
    </>
  );
}
