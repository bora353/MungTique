import { Button, TextField, Typography } from "@mui/material";
import { ErrorMessage, Form, Formik } from "formik";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as Yup from "yup";
import MuiSnackbar from "../../../components/snackbar/MuiSnackbar";
import { MailCheck } from "../../../shared/types/mailcheck.interface";
import { Join } from "./join.interface";
import { userApi } from "./user.api";

interface JoinProps {
  onsubmit: (joinDTO: Join) => void;
}

const validationSchema = Yup.object().shape({
  username: Yup.string().required("이름을 입력해주세요"),
  phone: Yup.string()
    .required("휴대폰 번호를 입력해주세요")
    .matches(/^\d{10,11}$/, "유효하지 않은 번호입니다."),
  email: Yup.string()
    .email("유효하지 않은 이메일 형식입니다")
    .required("이메일을 입력해주세요"),
  password: Yup.string()
    .required("비밀번호를 입력해주세요")
    .min(6, "비밀번호는 최소 6자 이상이어야 합니다")
    .matches(/^[a-zA-Z0-9!@#$%^&*]+$/, "유효하지 않은 비밀번호 형식입니다"),
  passwordCheck: Yup.string()
    .oneOf([Yup.ref("password")], "비밀번호가 일치하지 않습니다")
    .required("비밀번호를 입력해주세요"),
});

export default function JoinForm({ onsubmit }: JoinProps) {
  const navigate = useNavigate();
  const [isEmailVerified, setIsEmailVerified] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarType, setSnackbarType] = useState<
    "error" | "warning" | "info" | "success"
  >("error");

  const showSnackbar = (message: string, type: "info" | "error") => {
    setSnackbarMessage(message);
    setSnackbarType(type);
    setOpenSnackbar(true);
  };

  const handleMailCheck = async (email: string) => {
    try {
      if (!email) {
        showSnackbar("이메일을 입력해주세요.", "error");
        return;
      }

      const mailDTO: MailCheck = { email: email };
      const result = await userApi.mailSend(mailDTO);
      console.log("이메일 인증 요청 완료", result);

      if (result.data === "duplicate email") {
        showSnackbar("이미 사용된 이메일입니다.", "error");
        return;
      }

      showSnackbar("인증 번호가 이메일로 전송되었습니다.", "info");
      setIsEmailVerified(true);
    } catch (error) {
      console.error("이메일 인증 요청 실패:", error);
      showSnackbar("이메일 인증 요청 중 오류가 발생했습니다.", "error");
    }
  };

  const handleMailCheckOK = async (email: string, emailVerify: string) => {
    try {
      if (!emailVerify) {
        showSnackbar("인증 코드를 입력해주세요.", "error");
        return;
      }

      const response = await userApi.mailCheck(email, emailVerify);

      if (response.data === true) {
        showSnackbar("메일 인증이 완료되었습니다 :)", "info");
        setIsEmailVerified(true);
      } else {
        showSnackbar("올바르지 않은 인증 코드입니다.", "error");
      }
    } catch (error) {
      console.error("인증 코드 확인 실패:", error);
      showSnackbar("인증 코드 확인 중 오류가 발생했습니다.", "error");
    }
  };

  return (
    <Formik
      initialValues={{
        username: "",
        phone: "",
        email: "",
        emailVerify: "",
        password: "",
        passwordCheck: "",
      }}
      validationSchema={validationSchema}
      onSubmit={(values, { setSubmitting }) => {
        if (!isEmailVerified) {
          showSnackbar("이메일 인증을 완료해야 합니다.", "error");
          setSubmitting(false);
          return;
        }

        try {
          onsubmit(values);
          showSnackbar("회원 가입 성공!", "info");
          navigate("/joinsuccess");
        } catch (error) {
          showSnackbar("회원 가입에 실패했습니다.", "error");
        } finally {
          setSubmitting(false);
        }
      }}
    >
      {({ values, handleChange, handleBlur, isSubmitting }) => (
        <Form>
          <div>
            <TextField
              id="username"
              name="username"
              label="이름"
              variant="standard"
              value={values.username}
              onChange={handleChange}
              onBlur={handleBlur}
              sx={{ width: 330 }}
              helperText={
                <Typography sx={{ color: "red", fontSize: "0.75rem" }}>
                  <ErrorMessage name="username" />
                </Typography>
              }
              error={Boolean(ErrorMessage.name === "username")}
            />
          </div>
          <div>
            <TextField
              id="phone"
              name="phone"
              label="휴대폰 번호"
              variant="standard"
              value={values.phone}
              onChange={handleChange}
              onBlur={handleBlur}
              sx={{ width: 330 }}
              helperText={
                <Typography sx={{ color: "red", fontSize: "0.75rem" }}>
                  <ErrorMessage name="phone" />
                </Typography>
              }
              error={Boolean(ErrorMessage.name === "phone")}
            />
          </div>
          <div className="flex" style={{ gap: "8px", marginLeft: "110px" }}>
            <TextField
              id="email"
              name="email"
              label="이메일"
              variant="standard"
              value={values.email}
              onChange={handleChange}
              onBlur={handleBlur}
              sx={{ width: 330 }}
              helperText={
                <Typography sx={{ color: "red", fontSize: "0.75rem" }}>
                  <ErrorMessage name="email" />
                </Typography>
              }
              error={Boolean(ErrorMessage.name === "email")}
            />
            <Button
              type="button"
              variant="outlined"
              color="primary"
              onClick={() => handleMailCheck(values.email)}
              sx={{ width: 100, height: 40 }}
            >
              인증하기
            </Button>
          </div>
          {isEmailVerified && (
            <div className="flex" style={{ gap: "8px", marginLeft: "110px" }}>
              <TextField
                id="emailVerify"
                name="emailVerify"
                label="인증코드"
                variant="standard"
                value={values.emailVerify}
                onChange={handleChange}
                onBlur={handleBlur}
                sx={{ width: 330 }}
                helperText={<ErrorMessage name="emailVerify" />}
                error={Boolean(ErrorMessage.name === "emailVerify")}
              />
              <Button
                type="button"
                variant="outlined"
                color="primary"
                onClick={() =>
                  handleMailCheckOK(values.email, values.emailVerify)
                }
                sx={{ width: 100, height: 40 }}
              >
                확인
              </Button>
            </div>
          )}
          <div>
            <TextField
              id="password"
              name="password"
              label="비밀번호"
              type="password"
              variant="standard"
              value={values.password}
              onChange={handleChange}
              onBlur={handleBlur}
              sx={{ width: 330 }}
              helperText={
                <Typography sx={{ color: "red", fontSize: "0.75rem" }}>
                  <ErrorMessage name="password" />
                </Typography>
              }
              error={Boolean(ErrorMessage.name === "password")}
            />
          </div>
          <div>
            <TextField
              id="passwordCheck"
              name="passwordCheck"
              label="비밀번호 확인"
              type="password"
              variant="standard"
              value={values.passwordCheck}
              onChange={handleChange}
              onBlur={handleBlur}
              sx={{ width: 330 }}
              helperText={
                <Typography sx={{ color: "red", fontSize: "0.75rem" }}>
                  <ErrorMessage name="passwordCheck" />
                </Typography>
              }
              error={Boolean(ErrorMessage.name === "passwordCheck")}
            />
          </div>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            sx={{ width: 330 }}
            disabled={isSubmitting || !isEmailVerified}
          >
            가입하기
          </Button>
          <MuiSnackbar
            message={snackbarMessage}
            severity={snackbarType}
            open={openSnackbar}
            onClose={() => setOpenSnackbar(false)}
          />
        </Form>
      )}
    </Formik>
  );
}
