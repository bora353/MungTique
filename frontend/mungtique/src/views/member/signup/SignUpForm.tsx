import React, { useState } from "react";
import MuiInput from "../../../components/atomic/inputs/MuiInput";
import { Button } from "@mui/material";

export default function SignUpForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  // TODO : 전화번호 인증, 이메일 인증 넣기(+ 이메일 중복확인도)
  // TODO : 비밀번호 시에 비밀번호 규칙확인, 일치하는지 확인 밑에 글씨

  return (
    <div className="m-5">
      <MuiInput
        placeholder="이름"
        value={password}
        onChange={handleUsernameChange}
      />
      <div className="flex">
        <MuiInput
          placeholder="휴대폰"
          value={password}
          onChange={handleUsernameChange}
        />
        <Button color="primary">전화번호 인증</Button>
      </div>
      <div className="flex">
        <MuiInput
          placeholder="이메일"
          value={username}
          onChange={handleUsernameChange}
        />
        <Button color="primary">중복확인</Button>
      </div>
      <MuiInput
        placeholder="비밀번호"
        value={password}
        onChange={handlePasswordChange}
      />
      <MuiInput
        placeholder="비밀번호확인"
        value={password}
        onChange={handlePasswordChange}
      />
    </div>
  );
}
