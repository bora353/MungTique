import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { Join } from "../types/join.interface";
import { joinApi } from "../apis/join.api";

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
      const { data } = await joinApi.join(joinDTO);
      return data;
    },
    /* onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["join"] });
    }, */
  });
};
