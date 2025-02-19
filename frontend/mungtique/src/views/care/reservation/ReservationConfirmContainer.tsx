import { useEffect, useState } from "react";
import { useReservationStore } from "./reservation.store";
import { api } from "../../../shared/api/ApiInterceptor";
import { useNavigate } from "react-router-dom";

export default function ReservationConfirm() {
  const navigate = useNavigate();
  const [additionalInfo, setAdditionalInfo] = useState("");
  const {
    selectedMungShop,
    setSelectedMungShopId,
    selectedDog,
    selectedService,
    selectedDate,
    selectedTime,
    availableTimes,
  } = useReservationStore();
  const userId = localStorage.getItem("userId");

  // TODO : 예약자정보 user에서 api 로 가져오기
  // TODO : 예약정보 전체 reservation 서비스로 보내기
  // TODO : zustand에 로컬스토리지 추가

  useEffect(() => {
    if (!userId) {
      alert("로그인이 필요합니다.");
      navigate("/login");
      return;
    }

    api()
      .get(`/user-service/users/${userId}`)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error("예약자 정보를 불러오는데 실패했습니다:", error);
      });
  }, [userId]);

  return (
    <div className="fixed inset-0  bg-gray-50 flex justify-center items-center">
      <div className="bg-white w-full max-w-md rounded-lg shadow-lg p-6 relative mt-6">
        {/* 상단 헤더 */}
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold">예약하기</h2>
          <button className="text-gray-500 hover:text-gray-700">❌</button>
        </div>
        <p className="text-gray-500 text-sm">
          아래 내용이 맞는지 확인해 주세요.
        </p>
        {/* 예약 정보 */}
        <div className="bg-gray-100 rounded-lg p-4 mt-3">
          <h3 className="font-semibold">🐶 {selectedMungShop} 방문 예약</h3>
          <p className="text-sm text-gray-600">
            일정
            <span className="text-gray-800">
              {selectedDate?.format("M월 D일(ddd)")} {selectedTime}
            </span>
          </p>
          <p className="text-sm text-gray-600">
            강아지 <span className="text-gray-800">{selectedDog}</span>
          </p>
          <p className="text-sm text-gray-600">
            서비스 <span className="text-gray-800">{selectedService}</span>
          </p>
        </div>
        {/* 예약자 정보 */}
        <div className="mt-6">
          <h3 className="text-lg font-semibold">예약자 정보</h3>
          <div className="flex justify-between items-center mt-2">
            <div>
              <p className="font-semibold">보라</p>
              <p className="text-sm text-gray-600">010-12**-34**</p>
            </div>
            <button className="text-blue-500 border border-blue-500 px-3 py-1 rounded-lg text-sm">
              변경
            </button>
          </div>
        </div>
        {/* 요청사항 선택 */}
        <div className="mt-4">
          <textarea
            className="w-full border border-gray-300 rounded-lg p-2 text-gray-700"
            placeholder="요청 사항이 있으면 입력해 주세요."
            value={additionalInfo}
            onChange={(e) => setAdditionalInfo(e.target.value)}
          />
        </div>
        {/* 예약하기 버튼 */}
        <div className="mt-6 flex justify-end">
          <button
            className="bg-blue-500 text-white px-4 py-2 rounded-lg"
            //onClick={onNext}
          >
            예약하기
          </button>
        </div>
      </div>
    </div>
  );
}
