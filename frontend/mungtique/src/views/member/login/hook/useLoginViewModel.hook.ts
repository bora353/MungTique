import { Login } from "../../../../shared/types/login.interface";
import { userApi } from "../../../../shared/api/user.api";

export const useLoginViewModelHook = () => {
  const loginData = async (loginDTO: Login) => {
    try {
      const { data, headers, status } = await userApi.login(loginDTO);

      if (status === 200) {
        const authorizationHeader = headers["authorization"];
        let accessToken = "";
        let userId = "";

        if (authorizationHeader) {
          accessToken = authorizationHeader.replace("Bearer ", "");
        } else {
          console.error("Authorization header missing");
        }

        userId = data.userId;
        return { accessToken, userId };
      } else {
        throw new Error("로그인 실패: 잘못된 응답 상태");
      }
    } catch (error: any) {
      console.error("로그인 중 오류 발생:", error);
      return { accessToken: null, userId: null };
    }
  };

  return { loginData };
};
