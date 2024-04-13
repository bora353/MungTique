import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import MuiButton from "../../../components/atomic/buttons/MuiButton";
import MuiInput from "../../../components/atomic/inputs/MuiInput";
import { Join } from "../../../shared/types/join.interface";
import MuiSnackbar from "../../../components/atomic/snackbar/MuiSnackbar";

interface JoinProps {
  onsubmit: (joinDTO: Join) => void;
}

export default function JoinForm({ onsubmit }: JoinProps) {
  const navigate = useNavigate();

  const [joinForm, setJoinForm] = useState({
    username: "",
    password: "",
    passwordCheck: "",
    email: "",
    phone: "",
  });

  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setJoinForm({ ...joinForm, [name]: value });
  };

  // TODO : 비밀번호 시에 비밀번호 규칙확인, 일치하는지 확인 밑에 글씨

  const handleEmailCheck = () => {
    // TODO : 이메일 인증 (+ 이메일 중복확인도)
    // https://suhyeon-developer.tistory.com/19
    const email = joinForm.email;
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // 참고 https://jisoo-log.tistory.com/17
    const joinDTO = {
      username: joinForm.username,
      password: joinForm.password,
      passwordCheck: joinForm.passwordCheck,
      email: joinForm.email,
      phone: joinForm.phone,
    };

    if (
      !(
        joinForm.username &&
        joinForm.email &&
        joinForm.password &&
        joinForm.passwordCheck &&
        joinForm.phone
      )
    ) {
      setSnackbarMessage("모든 값을 입력해주세요");
      setOpenSnackbar(true);
      return;
    }

    if (joinForm.password !== joinForm.passwordCheck) {
      setSnackbarMessage("비밀번호가 일치하지 않습니다.");
      setOpenSnackbar(true);
      return;
    }

    try {
      console.log("joinDTO", joinDTO);
      onsubmit(joinDTO);
      navigate("/joinsuccess");
    } catch (error) {
      setSnackbarMessage("회원 가입에 실패했습니다.");
      setOpenSnackbar(true);
    }
  };

  return (
    <div className="m-5">
      <form onSubmit={handleSubmit}>
        <MuiInput
          name="username"
          placeholder="이름"
          value={joinForm.username}
          onChange={handleChange}
        />
        <div className="flex">
          <MuiInput
            name="phone"
            placeholder="휴대폰"
            value={joinForm.phone}
            onChange={handleChange}
          />
        </div>
        <div className="flex">
          <MuiInput
            name="email"
            placeholder="이메일"
            value={joinForm.email}
            onChange={handleChange}
          />
          <MuiButton
            value="중복확인"
            variant="text"
            color="primary"
            type="button"
            onClick={handleEmailCheck}
          />
        </div>
        <MuiInput
          name="password"
          type="password"
          placeholder="비밀번호"
          value={joinForm.password}
          onChange={handleChange}
        />
        <MuiInput
          name="passwordCheck"
          type="password"
          placeholder="비밀번호확인"
          value={joinForm.passwordCheck}
          onChange={handleChange}
        />
        <div className="m-3">
          <MuiButton
            value="회원가입"
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
