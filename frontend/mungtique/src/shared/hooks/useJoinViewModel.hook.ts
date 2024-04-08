import { useJoinMutation } from "../queries/join.query";

export const useJoinViewModelHook = () => {
  const joinMutation = useJoinMutation();

  return { joinMutation };
};
