import React, { useState } from "react";
import MuiInput from "../../../components/atomic/inputs/MuiInput";
import MuiButton from "../../../components/atomic/buttons/MuiButton";
import { Login } from "../../../shared/types/login.interface";
import MuiSnackbar from "../../../components/atomic/snackbar/MuiSnackbar";
import { useNavigate } from "react-router-dom";

interface LoginProps {
  onsubmit: (loginDTO: Login) => void;
}

export default function LoginForm({ onsubmit }: LoginProps) {
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
      navigate("/");
    } catch (error) {
      setSnackbarMessage("로그인에 실패했습니다.");
      setOpenSnackbar(true);
    }
  };

  return (
    <div className="m-5">
      <form onSubmit={handleSubmit}>
        <MuiInput
          name="email"
          placeholder="이메일을 입력해주세요"
          value={email}
          onChange={handleEmailChange}
        />
        <MuiInput
          name="password"
          type="password"
          placeholder="비밀번호를 입력해주세요"
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
      <MuiSnackbar
        message={snackbarMessage}
        severity={"error"}
        open={openSnackbar}
        onClose={() => setOpenSnackbar(false)}
      />
    </div>
  );
}
