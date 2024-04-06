import React from "react";
import Button from "@mui/material/Button";
import { Link } from "react-router-dom";

export default function SignUpButton() {
  const handleLogout = () => {};

  return (
    <>
      <Link to="/signupsuccess">
        <Button variant="contained" color="primary" onClick={handleLogout}>
          회원가입하기
        </Button>
      </Link>
    </>
  );
}
