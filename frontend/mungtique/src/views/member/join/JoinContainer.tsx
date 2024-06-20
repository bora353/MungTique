import { useJoinViewModelHook } from "./useJoinViewModel.hook";
import { Join } from "./join.interface";
import JoinForm from "./JoinForm";

export default function JoinContainer() {
  const { joinData } = useJoinViewModelHook();
  const handleJoinSubmit = (joinDTO: Join) => joinData(joinDTO);

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
