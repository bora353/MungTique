import { useMungJoinMutation } from "../queries/mung.query";

export const useMungJoinViewModelHook = () => {
  const mungJoinMutation = useMungJoinMutation();

  return { mungJoinMutation };
};
