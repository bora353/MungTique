import { useLocation } from "react-router-dom";
import PaymentOptions from "./PaymentOptions";
import ReservationSummary from "./ReservationSummary";
import { useEffect, useState } from "react";
import { ReservationInfo } from "./payment.interface";
import { api } from "../../../shared/api/ApiInterceptor";

export default function PaymentContainer() {
  const location = useLocation();
  const reservationId = location.state?.reservationId ?? "예약번호 없음";
  const [reservation, setReservation] = useState<ReservationInfo>();
  const [amount, setAmount] = useState<number>();

  useEffect(() => {
    console.log("결제 페이지 예약번호 : ", reservationId);

    api()
      .get<ReservationInfo>(
        `/reservation-service/reservations/${reservationId}`
      )
      .then((response) => {
        const result = response.data;
        console.log(result);
        setReservation(result);
      })
      .catch((error) => {
        console.error("예약 정보를 불러오는데 실패했습니다:", error);
      });
  }, [reservationId]);

  useEffect(() => {
    if (reservation) {
      api()
        .get<number>(
          `/mungshop-service/mungshops/price?mungShopId=${reservation.mungShopId}&breedType=${reservation.breedType}&serviceType=${reservation.serviceType}`
        )
        .then((response) => {
          const result = response.data;
          console.log(result);
          setAmount(result);
        })
        .catch((error) => {
          console.error("가격 정보를 불러오는데 실패했습니다:", error);
        });
    }
  }, [reservation]);

  return (
    <div className="flex flex-col items-center p4 bg-gray-50 min-h-screen rounded-lg">
      <div className="w-full max-w-md bg-white p-4 mt-6 rounded-lg shadow-md">
        <h1 className="text-2xl font-bold text-center text-blue-600 mb-6">
          결제 정보
        </h1>

        <ReservationSummary reservation={reservation} />

        {/* 결제 금액 */}
        <div className="bg-blue-50 p-4 rounded-lg mb-6">
          <div className="flex justify-between items-center">
            <h2 className="text-xl font-semibold">결제 금액</h2>
            <p className="text-2xl font-bold text-blue-700">{amount}원</p>
          </div>
        </div>

        <PaymentOptions
          reservationId={reservation?.reservationId}
          amount={amount}
        />
      </div>
    </div>
  );
}
