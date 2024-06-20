import { useMungJoinViewModelHook } from "./hook/useMungJoinViewModel.hook";
import { MyMung } from "../../../shared/types/mungjoin.interface";
import MyMungJoinForm from "./MyMungJoinForm";

export default function MyMungJoinContainer() {
  const { mungJoinData } = useMungJoinViewModelHook();
  const handleMungJoinSubmit = (mungJoinDTO: MyMung) =>
    mungJoinData(mungJoinDTO);

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen bg-blue-100">
        <div className="text-center">
          <h1 className="text-4xl text-blue-500 mb-10">My Mung 등록</h1>
          <MyMungJoinForm onsubmit={handleMungJoinSubmit} />
        </div>
      </div>
    </div>
  );
}
