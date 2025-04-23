import { useNavigate } from "react-router-dom";
import { useReservationStore } from "../../shared/store/reservation.store";
import { useEffect, useState } from "react";
import { api } from "../../shared/api/apiInterceptor";
import { MyMung } from "../../shared/types/mungjoin.interface";
import useNotificationRedirect from "../../hooks/useNotificationRedirect";
import { useSnackbar } from "notistack";

export default function ReservationMungContainer() {
  const { enqueueSnackbar } = useSnackbar();
  const { showNotificationAndRedirect } = useNotificationRedirect();

  const navigate = useNavigate();
  const {
    selectedDog,
    breedType,
    selectedService,
    setSelectedDogId,
    setSelectedDog,
    setBreedType,
    setSelectedService,
  } = useReservationStore();
  const [dogs, setDogs] = useState<
    { dogId: number; dogName: string; breedType: string }[]
  >([]);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    if (!userId) {
      showNotificationAndRedirect(
        "로그인이 필요합니다.",
        "warning",
        "/login",
        2000
      );
      return;
    }

    api()
      .get<MyMung[]>(`/dog-service/dogs/${userId}`)
      .then((response) => {
        const dogData = response.data.map((dog: MyMung) => ({
          dogId: dog.dogId,
          dogName: dog.dogName,
          breedType: dog.breedType,
        }));
        setDogs(dogData);
      })
      .catch((error) => {
        console.error("강아지 정보를 불러오는데 실패했습니다:", error);
      });
  }, [userId]);

  const handleNext = () => {
    if (!selectedDog || !selectedService) {
      enqueueSnackbar("강아지와 서비스를 선택해주세요!", {
        variant: "info",
      });
      return;
    }

    console.log("선택된 강아지:", selectedDog);
    console.log("선택된 서비스:", selectedService);
    console.log("선택된 서비스:", breedType);

    navigate("/reservation-confirm");
  };

  return (
    <div className="fixed inset-0 bg-gray-50 flex justify-center items-center">
      <div className="bg-white w-full max-w-md rounded-lg shadow-lg p-6 relative">
        {/* 상단 헤더 */}
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold">예약하기</h2>
          <button className="text-gray-500 hover:text-gray-700">❌</button>
        </div>

        <p className="text-gray-500 text-sm">
          예약할 강아지와 서비스를 선택하세요.
        </p>

        {/* 강아지 선택 */}
        <div className="mt-4">
          <h3 className="text-lg font-semibold">강아지 선택</h3>
          <div className="grid grid-cols-3 gap-2 mt-2">
            {dogs.map((dog) => (
              <button
                key={dog.dogId}
                className={`p-3 border rounded-lg text-center ${
                  selectedDog === dog.dogName
                    ? "bg-blue-500 text-white"
                    : "bg-gray-100 text-gray-700"
                }`}
                onClick={() => {
                  setSelectedDog(dog.dogName);
                  setSelectedDogId(dog.dogId);
                  setBreedType(dog.breedType);
                }}
              >
                {dog.dogName}
              </button>
            ))}
          </div>
        </div>

        {/* 서비스 선택 */}
        <div className="mt-6">
          <h3 className="text-lg font-semibold">서비스 선택</h3>
          <div className="grid grid-cols-3 gap-2 mt-2">
            {[
              { label: "목욕", value: "WASH" },
              { label: "커트", value: "CUT" },
              { label: "목욕+커트", value: "FULL" },
            ].map(({ label, value }) => (
              <button
                key={value}
                className={`p-3 border rounded-lg text-center ${
                  selectedService === value
                    ? "bg-blue-500 text-white"
                    : "bg-gray-100 text-gray-700"
                }`}
                onClick={() => setSelectedService(value)}
              >
                {label}
              </button>
            ))}
          </div>
        </div>

        {/* 다음 버튼 */}
        <div className="mt-6 flex justify-end">
          <button
            className={`px-4 py-2 rounded-lg ${
              selectedDog && selectedService
                ? "bg-blue-500 text-white"
                : "bg-gray-300 text-gray-500 cursor-not-allowed"
            }`}
            disabled={!selectedDog || !selectedService}
            onClick={handleNext}
          >
            다음
          </button>
        </div>
      </div>
    </div>
  );
}
