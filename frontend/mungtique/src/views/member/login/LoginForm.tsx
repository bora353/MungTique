import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Login } from "../../../shared/types/login.interface";
import MuiSnackbar from "../../../components/snackbar/MuiSnackbar";

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
      //console.log("loginDTO", loginDTO);
      await onsubmit(loginDTO);
      navigate("/");
    } catch (error) {
      setSnackbarMessage("로그인에 실패했습니다.");
      setOpenSnackbar(true);
    }
  };

  return (
    <div>
      <div className="text-center mb-6">
        <h2 className="text-2xl font-bold text-pink-500">반갑습니다!</h2>
      </div>

      {/* Snackbar */}
      <MuiSnackbar
        message={snackbarMessage}
        severity={"error"}
        open={openSnackbar}
        onClose={() => setOpenSnackbar(false)}
      />

      {/* Form */}
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-gray-700 text-sm font-medium mb-1">
            이메일
          </label>
          <input
            type="email"
            name="email"
            value={email}
            onChange={handleEmailChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-300 focus:border-transparent transition"
            placeholder="이메일 주소를 입력하세요"
          />
        </div>

        <div>
          <label className="block text-gray-700 text-sm font-medium mb-1">
            비밀번호
          </label>
          <input
            type="password"
            name="password"
            value={password}
            onChange={handlePasswordChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-300 focus:border-transparent transition"
            placeholder="비밀번호를 입력하세요"
          />
        </div>

        <div className="pt-2">
          <button
            type="submit"
            className="w-full bg-gradient-to-r from-pink-400 to-pink-500 text-white py-2 rounded-lg hover:from-pink-500 hover:to-pink-600 focus:outline-none focus:ring-2 focus:ring-pink-300 transition duration-200 font-medium"
          >
            로그인
          </button>
        </div>
      </form>

      {/* Additional links */}
      <div className="mt-6 text-center text-sm">
        <Link to="/findinfo" className="text-blue-500 hover:text-blue-600">
          아이디 찾기 
        </Link>
        &nbsp; | &nbsp;
        <Link to="/findinfo" className="text-blue-500 hover:text-blue-600">
          비밀번호 찾기
        </Link>

        <div className="mt-2">
          계정이 없으신가요?{" "}
            <Link
            to="/join"
            className="text-pink-500 hover:text-pink-600 font-medium"
            >
            회원가입
          </Link>
        </div>
      </div>
    </div>
  );
}
