import { Join } from "./join.interface";
import { UserEntity } from "../../../shared/types/user.interface";
import { Login } from "../../../shared/types/login.interface";
import { MailCheck } from "../../../shared/types/mailcheck.interface";
import { api } from "../../../shared/api/ApiInterceptor";

const join = async (joinDTO: Join) =>
  await api().post<UserEntity>(`/user-service/join`, joinDTO);

const login = async (loginDTO: Login) => {
  const formData = new FormData();
  formData.append("email", loginDTO.email);
  formData.append("password", loginDTO.password);
  return await api().post(`/user-service/login`, formData, {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    withCredentials: true,
  });
};

const localLogout = async () => await api().post(`/user-service/logout`, null);
const oauth2Logout = async () => {
  return await api().post(`/user-service/oauth2/logout`, null, {
    withCredentials: true,
  });
};

const mailSend = async (mailDTO: MailCheck) => {
  console.log("mailDTO", mailDTO);
  return await api().post<string>(`/user-service/mail-send`, mailDTO);
};

const mailCheck = async (email: string, providedVerificationCode: string) =>
  await api().get<boolean>(`/user-service/mail-check`, {
    params: { email, providedVerificationCode },
  });

export const userApi = {
  join,
  login,
  localLogout,
  oauth2Logout,
  mailSend,
  mailCheck,
};
