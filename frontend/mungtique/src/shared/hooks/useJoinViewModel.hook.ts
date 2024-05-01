import { useJoinMutation, useMailCheckMutation } from "../queries/user.query";

export const useJoinViewModelHook = () => {
  const joinMutation = useJoinMutation();
  const { data } = useMailCheckMutation();

  return { joinMutation, mailCheck: data };
};
