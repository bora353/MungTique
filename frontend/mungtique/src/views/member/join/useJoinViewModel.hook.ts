import { Join } from "./join.interface";
import { userApi } from "./user.api";

export const useJoinViewModelHook = () => {
  const joinData = async (joinDTO: Join) => {
    const { data } = await userApi.join(joinDTO);

    if (!data) {
      console.error("No data returned from the join request.");
      return;
    }

    console.log("Join response:", data);
  };

  return { joinData };
};

