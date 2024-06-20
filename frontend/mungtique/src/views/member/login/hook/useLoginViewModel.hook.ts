import { Login } from "../../../../shared/types/login.interface";
import { userApi } from "../../join/user.api";

export const useLoginViewModelHook = () => {
  const loginData = async (loginDTO: Login) => {
    const response = await userApi.login(loginDTO);
    return response.data;
  };

  return { loginData };
};
