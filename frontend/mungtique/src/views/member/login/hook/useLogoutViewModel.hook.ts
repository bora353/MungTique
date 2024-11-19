import { userApi } from "../../join/user.api";

export const useLogoutViewModelHook = () => {
  const logoutData = async () => {
    const { data, status } = await userApi.logout();

    console.log("logout data : ", data);
    if (!data) {
      console.error("No data returned from the logout request.");
      return;
    }
    return { status };
  };

  return { logoutData };
};
