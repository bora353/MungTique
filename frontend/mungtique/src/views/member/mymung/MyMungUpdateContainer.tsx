import { useMungJoinViewModelHook } from "./hook/useMungJoinViewModel.hook";
import { MyMung } from "../../../shared/types/mungjoin.interface";
import MyMungJoinForm from "./MyMungJoinForm";
import { useParams } from "react-router-dom";

export default function MyMungUpdateContainer() {
  const { dogId } = useParams<{ dogId: string }>();
  const { mungJoinData } = useMungJoinViewModelHook();
  const handleMungJoinSubmit = (mungJoinDTO: MyMung) =>
    mungJoinData(mungJoinDTO);

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen bg-blue-100">
        <div className="text-center">
          <h1 className="text-4xl text-blue-500 mb-10">My Mung 변경</h1>
          <h3>Dog ID: {dogId}</h3>
          <MyMungJoinForm onsubmit={handleMungJoinSubmit} />
        </div>
      </div>
    </div>
  );
}
