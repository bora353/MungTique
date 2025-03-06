import { useMungJoinViewModelHook } from "./hook/useMungJoinViewModel.hook";
import { MyMung } from "../../../shared/types/mungjoin.interface";
import MyMungJoinForm from "./MyMungJoinForm";

export default function MyMungJoinContainer() {
  const { mungJoinData } = useMungJoinViewModelHook();
  const handleMungJoinSubmit = (mungJoinDTO: MyMung) =>
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
