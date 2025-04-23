import { MyMungJoin } from "../../../shared/types/mungjoin.interface";
import { useMungJoinViewModelHook } from "./hook/useMungJoinViewModel.hook";
import MyMungJoinForm from "./MyMungJoinForm";

export default function MyMungJoinContainer() {
  const { mungJoinData } = useMungJoinViewModelHook();
  const handleMungJoinSubmit = (mungJoinDTO: MyMungJoin) =>
    mungJoinData(mungJoinDTO);

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen bg-pink-50">
        <div className="text-center">
          <MyMungJoinForm onsubmit={handleMungJoinSubmit} />
        </div>
      </div>
    </div>
  );
}
