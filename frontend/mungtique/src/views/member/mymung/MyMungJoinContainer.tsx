import { useDogApi } from "../../../hooks/useDogApi";
import { RegisterDogDTO } from "../../../shared/types/mungjoin.interface";
import MyMungJoinForm from "./MyMungJoinForm";

export default function MyMungJoinContainer() {
  const { registerDog } = useDogApi();
  const handleDogRegisterSubmit = (mungJoinDTO: RegisterDogDTO) => registerDog(mungJoinDTO);

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen bg-pink-50">
        <div className="text-center">
          <MyMungJoinForm onsubmit={handleDogRegisterSubmit} />
        </div>
      </div>
    </div>
  );
}
