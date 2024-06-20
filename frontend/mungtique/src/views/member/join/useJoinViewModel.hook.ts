import { Join } from "./join.interface";
import { userApi } from "./user.api";

export const useJoinViewModelHook = () => {
  const joinData = async (joinDTO: Join) => {
    const response = await userApi.join(joinDTO);
    return response.data;
  };

  return { joinData };
};
