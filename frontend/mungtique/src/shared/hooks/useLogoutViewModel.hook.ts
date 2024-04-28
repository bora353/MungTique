import { useLogoutMutation } from "../queries/user.query";

export const useLogoutViewModelHook = () => {
  const logoutMutation = useLogoutMutation();

  return { logoutMutation };
};
