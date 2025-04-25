import { Join } from "../types/join.interface";
import { UserEntity } from "../types/user.interface";
import { Login } from "../types/login.interface";
import { MailCheck } from "../types/mailcheck.interface";
import { api } from "./apiInterceptor";

const USER_BASE_URL = '/user-service';

export const userApi = {
  join: (joinDTO: Join) =>
    api().post<UserEntity>(`${USER_BASE_URL}/join`, joinDTO),

  login: (loginDTO: Login) => {
    const formData = new FormData();
    formData.append("email", loginDTO.email);
    formData.append("password", loginDTO.password);
    return api().post(`${USER_BASE_URL}/login`, formData, {
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
    });
  },

  localLogout: () => api().post(`${USER_BASE_URL}/logout`),

  oauth2Logout: () => api().post(`${USER_BASE_URL}/oauth2/logout`),

  sendMail: (mailDTO: MailCheck) =>
    api().post<string>(`${USER_BASE_URL}/mail-send`, mailDTO),

  checkMail: (email: string, providedVerificationCode: string) =>
    api().get<boolean>(`${USER_BASE_URL}/mail-check`, {
      params: { email, providedVerificationCode },
    }),
};
