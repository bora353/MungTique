import { userApi } from "../shared/api/user.api";

export const useUserApi = () => {
  return {
    joinUser: userApi.join,
    logoutLocal: userApi.localLogout,
    logoutOauth2: userApi.oauth2Logout,
    sendVerificationMail: userApi.sendMail,
    verifyMailCode: userApi.checkMail,
  };
};

