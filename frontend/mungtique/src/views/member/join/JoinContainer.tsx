import { useJoinViewModelHook } from "./hook/useJoinViewModel.hook";
import { Join } from "../../../shared/types/join.interface";
import JoinForm from "./JoinForm";

export default function JoinContainer() {
  const { joinData } = useJoinViewModelHook();
  const handleJoinSubmit = (joinDTO: Join) => joinData(joinDTO);

  return (
    <div className="flex justify-center items-center min-h-screen bg-pink-50">
      <div className="w-full max-w-md p-8 bg-white rounded-xl shadow-lg">
        <div className="text-center">
          <h1 className="text-2xl font-bold text-pink-500 mb-4">회원가입</h1>
          <JoinForm onsubmit={handleJoinSubmit} />
        </div>
      </div>
    </div>
  );
}
