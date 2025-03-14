import dayjs from "dayjs";
import { ReservationInfo } from "./payment.interface";

interface ReservationSummaryProps {
  reservation: ReservationInfo | undefined;
}

export default function ReservationSummary({
  reservation,
}: ReservationSummaryProps) {
  const getServiceTypeText = (type: string) => {
    const typeMap: Record<string, string> = {
      WASH: "목욕",
      CUT: "커트",
      FULL: "목욕 + 커트",
    };
    return typeMap[type] || type;
  };

  return (
    <>
      {/* 예약 정보 요약 */}
      <div className="bg-gray-50 p-4 rounded-lg mb-6">
        <h2 className="text-xl font-semibold mb-4">예약 정보</h2>
        <div className="grid grid-cols-2 gap-4">
          <div>
            <p className="text-gray-600">매장명</p>
            <p className="font-medium">🐶 {reservation?.storeName} </p>
          </div>
          <div>
            <p className="text-gray-600">반려뭉 이름</p>
            <p className="font-medium">{reservation?.dogName}</p>
          </div>
          <div>
            <p className="text-gray-600">서비스</p>
            <p className="font-medium">
              {getServiceTypeText(reservation?.serviceType ?? "")}
            </p>
          </div>
          <div>
            <p className="text-gray-600">예약일시</p>
            <p className="font-medium">
              {/* {selectedDate?.format("M월 D일(ddd)")} */}
              {dayjs(reservation?.reservationDate).format("MM월 DD일")}{" "}
              {reservation?.reservationTime}
            </p>
          </div>
        </div>
        <div className="mt-4">
          <p className="text-gray-600">요청사항</p>
          <p className="font-medium">{reservation?.requestMessage || "없음"}</p>
        </div>
      </div>
    </>
  );
}
