import { userApi } from "../../join/user.api";

export const useLogoutViewModelHook = () => {
  const logoutData = async () => {
    const response = await userApi.logout();
    return response.data;
  };

  return { logoutData };
};
