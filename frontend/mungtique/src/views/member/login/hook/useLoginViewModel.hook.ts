import { Login } from "../../../../shared/types/login.interface";
import { userApi } from "../../../../shared/api/user.api";

export const useLoginViewModelHook = () => {
  const loginData = async (loginDTO: Login) => {
    const { data, headers, status } = await userApi.login(loginDTO);

    let accessToken;
    let userId;

    if (status === 200) {
      const authorizationHeader = headers["authorization"];
      if (authorizationHeader) {
        accessToken = authorizationHeader.replace("Bearer ", "");
      } else {
        console.error("Authorization header missing");
      }
      userId = data.userId;
    }

    return { accessToken, userId };
  };

  return { loginData };
};
