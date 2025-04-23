import { userApi } from "../../../../shared/api/user.api";

export const useLogoutViewModelHook = () => {
  const localLogoutData = async () => {
    const { data, status } = await userApi.localLogout();

    if (!data) {
      console.error("No data returned from the logout request.");
      return;
    }
    return { status };
  };
  1;
  const oauth2LogoutData = async () => {
    const { data, status } = await userApi.oauth2Logout();

    if (!data) {
      console.error("No data returned from the logout request.");
      return;
    }
    return { status };
  };

  return { localLogoutData, oauth2LogoutData };
};
