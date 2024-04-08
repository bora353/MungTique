import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import MuiButton from "../../../components/atomic/buttons/MuiButton";
import MuiInput from "../../../components/atomic/inputs/MuiInput";
import { Join } from "../../../shared/types/join.interface";

interface JoinProps {
  onsubmit: (joinDTO: Join) => void;
}

export default function JoinForm({ onsubmit }: JoinProps) {
  const [joinForm, setJoinForm] = useState({
    username: "",
    password: "",
    passwordCheck: "",
    email: "",
    phone: "",
  });

  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setJoinForm({ ...joinForm, [name]: value });
  };

  // TODO : 비밀번호 시에 비밀번호 규칙확인, 일치하는지 확인 밑에 글씨

  const handlePhoneAuthSend = () => {
    // TODO : 전화번호 인증
    const phoneNumber = "010-1234-5678"; // 사용자가 입력한 전화번호를 가져온다고 가정

    // 전화번호를 이용하여 인증 코드를 전송하는 작업을 수행한다
    // 예를 들어, API 요청을 보내거나 다른 방법을 사용하여 인증 코드를 전송할 수 있다
  };

  const handleEmailCheck = () => {
    // TODO : 이메일 인증 (+ 이메일 중복확인도)
    const email = "user@example.com"; // 사용자가 입력한 이메일을 가져온다고 가정
    // 이메일 중복을 확인하는 작업을 수행한다
    // 예를 들어, API 요청을 보내거나 다른 방법을 사용하여 이메일 중복을 확인할 수 있다
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const joinDTO = {
      username: joinForm.username,
      password: joinForm.password,
      passwordCheck: joinForm.passwordCheck,
      email: joinForm.email,
      phone: joinForm.phone,
    };

    /* if (!(username && email && password && passwordCheck && phone)) {
      return alert('모든 값을 입력해주세요');
    }

    if (password !== passwordCheck) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    } */
    try {
      console.log("joinDTO", joinDTO);
      onsubmit(joinDTO);
      navigate("/joinsuccess");
    } catch (error) {
      alert("회원 가입에 실패했습니다.");
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
          <MuiButton
            value="인증번호 전송"
            variant="text"
            color="primary"
            type="button"
            onClick={handlePhoneAuthSend}
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
          placeholder="비밀번호"
          value={joinForm.password}
          onChange={handleChange}
        />
        <MuiInput
          name="passwordCheck"
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
    </div>
  );
}
