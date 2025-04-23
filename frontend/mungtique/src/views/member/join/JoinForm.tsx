import { Form, Formik } from "formik";
import { useState } from "react";
import * as Yup from "yup";
import { MailCheck } from "../../../shared/types/mailcheck.interface";
import { Join } from "./join.interface";
import { userApi } from "./user.api";
import { useSnackbar } from "notistack";
import useNotificationRedirect from "../../../components/snackbar/useNotificationRedirect";

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
  const { enqueueSnackbar } = useSnackbar();
  const { showNotificationAndRedirect } = useNotificationRedirect();
  const [isEmailVerified, setIsEmailVerified] = useState(false);

  const handleMailCheck = async (email: string) => {
    try {
      if (!email) {
        enqueueSnackbar("이메일을 입력해주세요.", {
          variant: "error",
        });
        return;
      }

      const mailDTO: MailCheck = { email: email };
      const result = await userApi.mailSend(mailDTO);
      console.log("이메일 인증 요청 완료", result);

      if (result.data === "duplicate email") {
        enqueueSnackbar("이미 가입된 이메일입니다.", {
          variant: "error",
        });
        return;
      }

      enqueueSnackbar("인증 번호가 이메일로 전송되었습니다.", {
        variant: "info",
      });

      setIsEmailVerified(true);
    } catch (error) {
      console.error("이메일 인증 요청 실패:", error);
      enqueueSnackbar("이메일 인증 요청 중 오류가 발생했습니다.", {
        variant: "error",
      });
    }
  };

  const handleMailCheckOK = async (email: string, emailVerify: string) => {
    try {
      if (!emailVerify) {
        enqueueSnackbar("인증 코드를 입력해주세요.", {
          variant: "error",
        });
        return;
      }

      const response = await userApi.mailCheck(email, emailVerify);

      if (response.data === true) {
        enqueueSnackbar("메일 인증이 완료되었습니다 :)", {
          variant: "success",
        });
        setIsEmailVerified(true);
      } else {
        enqueueSnackbar("올바르지 않은 인증 코드입니다.", {
          variant: "error",
        });
      }
    } catch (error) {
      console.error("인증 코드 확인 실패:", error);
      enqueueSnackbar("인증 코드 확인 중 오류가 발생했습니다.", {
        variant: "error",
      });
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
          enqueueSnackbar("이메일 인증을 완료해야 합니다.", {
            variant: "error",
          });
          setSubmitting(false);
          return;
        }

        try {
          onsubmit(values);

          showNotificationAndRedirect(
            "회원가입 성공", 
            "success",     
            "/joinsuccess",      
            2000        
          );

        } catch (error) {
          enqueueSnackbar("회원 가입에 실패했습니다.", {
            variant: "error",
          });
        } finally {
          setSubmitting(false);
        }
      }}
    >
      {({
        values,
        handleChange,
        handleBlur,
        isSubmitting,
        errors,
        touched,
      }) => (
        <Form className="space-y-5">
          <div className="space-y-1">
            <div className="relative">
              <input
                id="username"
                name="username"
                type="text"
                value={values.username}
                onChange={handleChange}
                onBlur={handleBlur}
                className={`peer w-full border-0 border-b-2 ${
                  touched.username && errors.username
                    ? "border-red-500"
                    : "border-gray-300"
                } focus:border-blue-500 focus:ring-0 focus:outline-none bg-transparent py-2 px-1 placeholder-transparent`}
                placeholder="이름"
              />
              <label
                htmlFor="username"
                className="absolute left-1 -top-5 text-sm text-gray-600 transition-all peer-placeholder-shown:text-base peer-placeholder-shown:top-2 peer-focus:-top-5 peer-focus:text-sm peer-focus:text-blue-500"
              >
                이름
              </label>
            </div>
            {touched.username && errors.username && (
              <p className="text-red-500 text-xs">{errors.username}</p>
            )}
          </div>

          <div className="space-y-1">
            <div className="relative">
              <input
                id="phone"
                name="phone"
                type="text"
                value={values.phone}
                onChange={handleChange}
                onBlur={handleBlur}
                className={`peer w-full border-0 border-b-2 ${
                  touched.phone && errors.phone
                    ? "border-red-500"
                    : "border-gray-300"
                } focus:border-blue-500 focus:ring-0 focus:outline-none bg-transparent py-2 px-1 placeholder-transparent`}
                placeholder="휴대폰 번호"
              />
              <label
                htmlFor="phone"
                className="absolute left-1 -top-5 text-sm text-gray-600 transition-all peer-placeholder-shown:text-base peer-placeholder-shown:top-2 peer-focus:-top-5 peer-focus:text-sm peer-focus:text-blue-500"
              >
                휴대폰 번호
              </label>
            </div>
            {touched.phone && errors.phone && (
              <p className="text-red-500 text-xs">{errors.phone}</p>
            )}
          </div>

          <div className="space-y-1">
            <div className="flex gap-2">
              <div className="relative flex-1">
                <input
                  id="email"
                  name="email"
                  type="email"
                  value={values.email}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={`peer w-full border-0 border-b-2 ${
                    touched.email && errors.email
                      ? "border-red-500"
                      : "border-gray-300"
                  } focus:border-blue-500 focus:ring-0 focus:outline-none bg-transparent py-2 px-1 placeholder-transparent`}
                  placeholder="이메일"
                />
                <label
                  htmlFor="email"
                  className="absolute left-1 -top-5 text-sm text-gray-600 transition-all peer-placeholder-shown:text-base peer-placeholder-shown:top-2 peer-focus:-top-5 peer-focus:text-sm peer-focus:text-blue-500"
                >
                  이메일
                </label>
              </div>
              <button
                type="button"
                onClick={() => handleMailCheck(values.email)}
                className="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded transition duration-200 min-w-[100px]"
              >
                인증하기
              </button>
            </div>
            {touched.email && errors.email && (
              <p className="text-red-500 text-xs">{errors.email}</p>
            )}
          </div>

          <div>
            {isEmailVerified && (
              <div className="space-y-1">
                <div className="flex gap-2">
                  <div className="relative flex-1">
                    <input
                      id="emailVerify"
                      name="emailVerify"
                      type="text"
                      value={values.emailVerify}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      className={`peer w-full border-0 border-b-2 ${
                        touched.emailVerify && errors.emailVerify
                          ? "border-red-500"
                          : "border-gray-300"
                      } focus:border-blue-500 focus:ring-0 focus:outline-none bg-transparent py-2 px-1 placeholder-transparent`}
                      placeholder="인증코드"
                    />
                    <label
                      htmlFor="emailVerify"
                      className="absolute left-1 -top-5 text-sm text-gray-600 transition-all peer-placeholder-shown:text-base peer-placeholder-shown:top-2 peer-focus:-top-5 peer-focus:text-sm peer-focus:text-blue-500"
                    >
                      인증코드
                    </label>
                  </div>
                  <button
                    type="button"
                    onClick={() =>
                      handleMailCheckOK(values.email, values.emailVerify)
                    }
                    className="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded transition duration-200 min-w-[100px]"
                  >
                    확인
                  </button>
                </div>
                {touched.emailVerify && errors.emailVerify && (
                  <p className="text-red-500 text-xs">{errors.emailVerify}</p>
                )}
              </div>
            )}
          </div>

          <div className="space-y-1">
            <div className="relative">
              <input
                id="password"
                name="password"
                type="password"
                value={values.password}
                onChange={handleChange}
                onBlur={handleBlur}
                className={`peer w-full border-0 border-b-2 ${
                  touched.password && errors.password
                    ? "border-red-500"
                    : "border-gray-300"
                } focus:border-blue-500 focus:ring-0 focus:outline-none bg-transparent py-2 px-1 placeholder-transparent`}
                placeholder="비밀번호"
              />
              <label
                htmlFor="password"
                className="absolute left-1 -top-5 text-sm text-gray-600 transition-all peer-placeholder-shown:text-base peer-placeholder-shown:top-2 peer-focus:-top-5 peer-focus:text-sm peer-focus:text-blue-500"
              >
                비밀번호
              </label>
            </div>
            {touched.password && errors.password && (
              <p className="text-red-500 text-xs">{errors.password}</p>
            )}
          </div>

          <div className="space-y-1">
            <div className="relative">
              <input
                id="passwordCheck"
                name="passwordCheck"
                type="password"
                value={values.passwordCheck}
                onChange={handleChange}
                onBlur={handleBlur}
                className={`peer w-full border-0 border-b-2 ${
                  touched.passwordCheck && errors.passwordCheck
                    ? "border-red-500"
                    : "border-gray-300"
                } focus:border-blue-500 focus:ring-0 focus:outline-none bg-transparent py-2 px-1 placeholder-transparent`}
                placeholder="비밀번호 확인"
              />
              <label
                htmlFor="passwordCheck"
                className="absolute left-1 -top-5 text-sm text-gray-600 transition-all peer-placeholder-shown:text-base peer-placeholder-shown:top-2 peer-focus:-top-5 peer-focus:text-sm peer-focus:text-blue-500"
              >
                비밀번호 확인
              </label>
            </div>
            {touched.passwordCheck && errors.passwordCheck && (
              <p className="text-red-500 text-xs">{errors.passwordCheck}</p>
            )}
          </div>

          <button
            type="submit"
            disabled={isSubmitting || !isEmailVerified}
            className={`w-full py-3 px-4 mt-6 rounded-md font-medium text-white transition duration-200 ${
              isSubmitting || !isEmailVerified
                ? "bg-gray-400 cursor-not-allowed"
                : "bg-blue-500 hover:bg-blue-600"
            }`}
          >
            가입하기
          </button>
        </Form>
      )}
    </Formik>
  );
}
