import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { Join } from "../types/join.interface";
import { userApi } from "../apis/user.api";
import { Login } from "../types/login.interface";
import { MailCheck } from "../types/mailcheck.interface";

/* export const joinQuery = () => {
    return useQuery<Join[]>({
        queryKey: ["join"],
        queryFn: async () => {
            //const { data } = await joinApi.getJoinData(); 
            //return data;
        },
    });
}; */

export const useJoinMutation = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (joinDTO: Join) => {
      const { data } = await userApi.join(joinDTO);
      return data;
    },
    /* onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["join"] });
    }, */
  });
};

export const useLoginMutation = () => {
  return useMutation({
    mutationFn: async (loginDTO: Login) => {
      const response = await userApi.login(loginDTO);
      return response;
    },
  });
};

export const useLogoutMutation = () => {
  return useMutation({
    mutationFn: async () => {
      const response = await userApi.logout();
      return response;
    },
  });
};

export const useMailCheckMutation = () => {
  return useMutation({
    mutationFn: async (mailDTO: MailCheck) => {
      const response = await userApi.mailCheck(mailDTO);
      // TODO : response값을 못가져와서 일단 보류
      return response;
    },
  });
};
