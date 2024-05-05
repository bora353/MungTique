import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import MuiButton from "../../../components/atomic/buttons/MuiButton";
import axios from "axios";

export default function LoginSignInButton() {
  const handleLogin = () => {};
  const onNaverLogin = () => {
    window.location.href = "http://localhost:8082/oauth2/authorization/naver";
  };

  // TODO : 추후 SNS 로그인

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

      <div className="my-1">
        <Button onClick={onNaverLogin} variant="contained" color="success">
          Naver Login
        </Button>
      </div>
      <div className="my-1">
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
