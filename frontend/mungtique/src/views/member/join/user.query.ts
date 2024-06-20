/* import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { Join } from "./join.interface";
import { userApi } from "./user.api";
import { MailCheck } from "../../../shared/types/mailcheck.interface";

/* export const joinQuery = () => {
    return useQuery<Join[]>({
        queryKey: ["join"],
        queryFn: async () => {
            //const { data } = await joinApi.getJoinData(); 
            //return data;
        },
    });
}; */

/* export const useJoinMutation = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: 
    /* onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["join"] });
    }, */
  });
};
 */
/* export const useLoginMutation = () => {
  return useMutation({
    mutationFn: async (loginDTO: Login) => {
      const response = await userApi.login(loginDTO);
      return response;
    },
  });
}; */

/* export const useLogoutMutation = () => {
  return useMutation({
    mutationFn: async () => {
      const response = await userApi.logout();
      return response;
    },
  });
}; */

/* export const useMailCheckMutation = () => {
  return useMutation({
    mutationFn: 
  });
};
 */ */