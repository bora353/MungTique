import { useState } from "react";

export default function ReservationConfirm({ onNext }: { onNext: () => void }) {
  const [selectedRequest, setSelectedRequest] = useState("");
  const [additionalInfo, setAdditionalInfo] = useState("");

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
          <h3 className="font-semibold">🐶 멍샬롬 방문 예약</h3>
          <p className="text-sm text-gray-600">
            일정 <span className="font-semibold">2.28(금) · 오후 5:00</span>
          </p>
          <p className="text-sm text-gray-600">
            강아지 <span className="font-semibold">코코</span>
          </p>
          <p className="text-sm text-gray-600">
            서비스 <span className="font-semibold">목욕 + 커트</span>
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
        <div className="mt-6">
          <h3 className="text-lg font-semibold">요청사항</h3>
          <select
            className="mt-2 w-full border border-gray-300 rounded-lg p-2 text-gray-700"
            value={selectedRequest}
            onChange={(e) => setSelectedRequest(e.target.value)}
          >
            <option value="">요청사항을 선택해 주세요.</option>
            <option value="조용한 공간">조용한 공간</option>
            <option value="빠른 서비스">빠른 서비스</option>
            <option value="디자인 스타일 요청">디자인 스타일 요청</option>
          </select>
        </div>
        {/* 추가 정보 입력 */}
        <div className="mt-4">
          <textarea
            className="w-full border border-gray-300 rounded-lg p-2 text-gray-700"
            placeholder="추가 요청 사항이 있으면 입력해 주세요."
            value={additionalInfo}
            onChange={(e) => setAdditionalInfo(e.target.value)}
          />
        </div>
        {/* 예약하기 버튼 */}
        <div className="mt-6 flex justify-end">
          <button
            className="bg-blue-500 text-white px-4 py-2 rounded-lg"
            onClick={onNext}
          >
            예약하기
          </button>
        </div>
      </div>
    </div>
  );
}
