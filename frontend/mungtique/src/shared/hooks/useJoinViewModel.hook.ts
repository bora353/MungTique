import { useJoinMutation } from "../queries/user.query";

export const useJoinViewModelHook = () => {
  const joinMutation = useJoinMutation();

  return { joinMutation };
};
