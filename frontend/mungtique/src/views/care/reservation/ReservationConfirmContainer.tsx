import { useNavigate } from "react-router-dom";
import { useReservationStore } from "./reservation.store";
import { api } from "../../../shared/api/ApiInterceptor";
import { useEffect } from "react";
import { UserDto } from "../../../shared/types/user.interface";

export default function ReservationConfirm() {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");

  const {
    selectedMungShop,
    selectedMungShopId,
    selectedDogId,
    selectedDog,
    selectedService,
    breedType,
    selectedDate,
    selectedTime,
    reserveUserName,
    reserveUserPhone,
    requestMessage,
    setReserveUserName,
    setReserveUserPhone,
    setRequestMessage,
  } = useReservationStore();

  const handleReservationSubmit = async () => {
    const reservationData = {
      mungShopId: selectedMungShopId,
      storeName: selectedMungShop,
      dogId: selectedDogId,
      dogName: selectedDog,
      breedType: breedType,
      serviceType: selectedService,
      reservationDate: selectedDate?.format("YYYY-MM-DD"),
      reservationTime: selectedTime,
      userId: userId,
      username: reserveUserName,
      phone: reserveUserPhone,
      requestMessage: requestMessage,
    };

    console.log("예약 요청 데이터:", reservationData);

    try {
      const response = await api().post(
        "/reservation-service/reservations",
        reservationData
      );
      const reservationId = response.data;
      console.log("예약 성공:", reservationId);

      localStorage.removeItem("reservation-storage");

      navigate("/payment", { state: { reservationId } });
    } catch (error) {
      console.error("예약 실패:", error);
      alert("예약에 실패했습니다. 다시 시도해주세요.");
    }
  };

  useEffect(() => {
    if (!userId) {
      alert("로그인이 필요합니다.");
      navigate("/login");
      return;
    }

    api()
      .get<UserDto>(`/user-service/users/${userId}`)
      .then((response) => {
        setReserveUserName(response.data.username);
        setReserveUserPhone(response.data.phone);
      })
      .catch((error) => {
        console.error("예약자 정보를 불러오는데 실패했습니다:", error);
      });
  }, [userId]);

  return (
    <div className="fixed inset-0  bg-gray-50 flex justify-center items-center">
      <div className="bg-white w-full max-w-md rounded-lg shadow-lg p-6 relative mt-6">
        {/* 예약자 정보 */}
        <div className="mt-6">
          <h3 className="text-lg font-semibold">예약자 정보</h3>
          <div className="flex justify-between items-center mt-2">
            <div>
              <p className="font-semibold">{reserveUserName}</p>
              <p className="text-sm text-gray-600">{reserveUserPhone}</p>
            </div>
          </div>
        </div>
        {/* 요청사항 선택 */}
        <div className="mt-4">
          <textarea
            className="w-full border border-gray-300 rounded-lg p-2 text-gray-700"
            placeholder="요청 사항이 있으면 입력해 주세요."
            value={requestMessage || ""}
            onChange={(e) => setRequestMessage(e.target.value)}
          />
        </div>
        {/* 예약하기 버튼 */}
        <div className="mt-6 flex justify-end">
          <button
            className="bg-blue-500 text-white px-4 py-2 rounded-lg"
            onClick={handleReservationSubmit}
          >
            예약하기
          </button>
        </div>
      </div>
    </div>
  );
}
