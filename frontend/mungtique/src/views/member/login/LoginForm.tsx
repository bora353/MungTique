import React, { useState } from "react";
import MuiInput from "../../../components/inputs/MuiInput";
import MuiButton from "../../../components/buttons/MuiButton";
import { Login } from "../../../shared/types/login.interface";
import MuiSnackbar from "../../../components/snackbar/MuiSnackbar";
import { useNavigate } from "react-router-dom";
import { useAuthStore } from "./hook/login.store";

interface LoginProps {
  onsubmit: (loginDTO: Login) => void;
}

export default function LoginForm({ onsubmit }: LoginProps) {
  const setIsLogin = useAuthStore((state) => state.setIsLogin);
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const loginDTO = {
      email,
      password,
    };

    if (!(email && password)) {
      setSnackbarMessage("모든 값을 입력해주세요");
      setOpenSnackbar(true);
      return;
    }

    try {
      console.log("loginDTO", loginDTO);
      onsubmit(loginDTO);
      setIsLogin(true); // 로그인 상태 유지
      navigate("/");
    } catch (error) {
      setSnackbarMessage("로그인에 실패했습니다.");
      setOpenSnackbar(true);
    }
  };

  return (
    <div className="m-5">
      <MuiSnackbar
        message={snackbarMessage}
        severity={"error"}
        open={openSnackbar}
        onClose={() => setOpenSnackbar(false)}
      />
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <MuiInput
            name="email"
            placeholder="email"
            value={email}
            onChange={handleEmailChange}
          />
        </div>
        <MuiInput
          name="password"
          type="password"
          placeholder="password"
          value={password}
          onChange={handlePasswordChange}
        />
        <div className="my-3">
          <MuiButton
            value="로그인"
            variant={"contained"}
            color={"primary"}
            type={"submit"}
          />
        </div>
      </form>
    </div>
  );
}
