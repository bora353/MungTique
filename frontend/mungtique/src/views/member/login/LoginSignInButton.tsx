import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import MuiButton from "../../../components/buttons/MuiButton";
import { useAuthStore } from "./hook/login.store";

export default function LoginSignInButton() {
  const setIsLogin = useAuthStore((state) => state.setIsLogin);

  // TODO : url 환경변수로 빼기
  const onNaverLogin = () => {
    window.location.href = "http://localhost:8000/oauth2/authorization/naver";
    setIsLogin(true);
  };
  const onKakaoLogin = () => {
    window.location.href = "http://localhost:8000/oauth2/authorization/kakao";
    setIsLogin(true);
  };

  return (
    <>
      <div className="flex my-3 items-center">
        <div className="mx-8">
          <input type="checkbox" />
          <span style={{ fontSize: "0.85rem" }}> 로그인 상태 유지</span>
        </div>
        <div>
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
