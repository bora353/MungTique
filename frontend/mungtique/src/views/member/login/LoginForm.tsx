import React, { useState } from "react";
import MuiInput from "../../../components/atomic/inputs/MuiInput";

export default function LoginForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  return (
    <div className="m-5">
      <MuiInput
        placeholder="아이디"
        value={username}
        onChange={handleUsernameChange}
      />
      <MuiInput
        placeholder="비밀번호"
        value={password}
        onChange={handlePasswordChange}
      />
    </div>
  );
}
