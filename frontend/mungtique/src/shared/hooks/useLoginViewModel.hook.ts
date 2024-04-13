import { useLoginMutation } from "../queries/user.query";

export const useLoginViewModelHook = () => {
  const loginMutation = useLoginMutation();

  return { loginMutation };
};
