import { useState } from "react";
import { useNavigate } from "react-router-dom";

const dogs = [
  { id: 1, name: "코코", image: "https://placehold.co/50x50" },
  { id: 2, name: "바비", image: "https://placehold.co/50x50" },
  { id: 3, name: "초코", image: "https://placehold.co/50x50" },
];

export default function ReservationMungContainer() {
  const navigate = useNavigate();

  const [selectedDog, setSelectedDog] = useState<number | null>(null);
  const [selectedService, setSelectedService] = useState<string | null>(null);

  const handleNext = () => {
    if (!selectedDog || !selectedService) {
      alert("강아지와 서비스를 선택해주세요!");
      return;
    }

    console.log("선택된 강아지:", selectedDog);
    console.log("선택된 서비스:", selectedService);

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
          <div className="flex space-x-2 mt-2">
            {dogs.map((dog) => (
              <button
                key={dog.id}
                className={`flex items-center p-2 border rounded-lg ${
                  selectedDog === dog.id
                    ? "bg-green-500 text-white"
                    : "bg-gray-100 text-gray-700"
                }`}
                onClick={() => setSelectedDog(dog.id)}
              >
                <img
                  src={dog.image}
                  alt={dog.name}
                  className="w-10 h-10 rounded-full mr-2"
                />
                {dog.name}
              </button>
            ))}
          </div>
        </div>

        {/* 서비스 선택 */}
        <div className="mt-6">
          <h3 className="text-lg font-semibold">서비스 선택</h3>
          <div className="grid grid-cols-3 gap-2 mt-2">
            {["목욕", "커트", "전체"].map((service) => (
              <button
                key={service}
                className={`p-3 border rounded-lg text-center ${
                  selectedService === service
                    ? "bg-green-500 text-white"
                    : "bg-gray-100 text-gray-700"
                }`}
                onClick={() => setSelectedService(service)}
              >
                {service}
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
