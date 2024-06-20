import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import MuiButton from "../../../components/buttons/MuiButton";
import MuiInput from "../../../components/inputs/MuiInput";
import { Join } from "./join.interface";
import MuiSnackbar from "../../../components/snackbar/MuiSnackbar";
import { MailCheck } from "../../../shared/types/mailcheck.interface";
import { userApi } from "./user.api";
import axios from "axios";

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
    emailVerify: "",
    phone: "",
  });

  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [verifyNumber, setVerifyNumber] = useState("");
  const [isEmailVerified, setIsEmailVerified] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setJoinForm({ ...joinForm, [name]: value });
  };

  // TODO : 비밀번호 시에 비밀번호 규칙확인, 일치하는지 확인 밑에 글씨

  const handleMailCheck = async () => {
    const mailDTO: MailCheck = { mail: joinForm.email };
    const result = await userApi.mailCheck(mailDTO);
    console.log("이메일 인증 요청 완료", result);

    if (result) {
      setIsEmailVerified(true);
    }

    setVerifyNumber(result);
  };

  const handleMailCheckOK = async () => {
    // TODO : 이메일 중복도 체크해야 함!!!
    const basePath = import.meta.env.VITE_BACKEND_SERVER;
    const userNumber = joinForm.emailVerify;
    const sentNumber = verifyNumber;

    const response = await axios.get(`${basePath}/mail-check`, {
      params: {
        userNumber,
        sentNumber,
      },
    });

    if (response.data === true) {
      setSnackbarMessage("메일 인증되었습니다 :)");
      setOpenSnackbar(true);
      setIsEmailVerified(false);
    } else {
      setSnackbarMessage("올바르지 않은 인증코드입니다.");
      setOpenSnackbar(true);
    }
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

    // TODO : 인증번호 성공한 상태에서만 회원가입 되게 추가!

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
            value="인증하기"
            variant="text"
            color="primary"
            type="button"
            onClick={handleMailCheck}
          />
        </div>
        {isEmailVerified && (
          <div className="flex">
            <MuiInput
              name="emailVerify"
              placeholder="인증코드"
              value={joinForm.emailVerify}
              onChange={handleChange}
            />
            <MuiButton
              value="확인"
              variant="text"
              color="primary"
              type="button"
              onClick={handleMailCheckOK}
            />
          </div>
        )}
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
            value="완료"
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
