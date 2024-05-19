import { useJoinViewModelHook } from "../../../shared/hooks/useJoinViewModel.hook";
import { Join } from "../../../shared/types/join.interface";
import JoinForm from "./JoinForm";

export default function JoinContainer() {
  // TODO : Hook, mutate 사용
  const { joinMutation } = useJoinViewModelHook();
  const handleJoinSubmit = (joinDTO: Join) => joinMutation.mutate(joinDTO);

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen bg-blue-100">
        <div className="text-center">
          <h1 className="text-4xl text-blue-500">회원가입</h1>
          <JoinForm onsubmit={handleJoinSubmit} />
        </div>
      </div>
    </div>
  );
}
