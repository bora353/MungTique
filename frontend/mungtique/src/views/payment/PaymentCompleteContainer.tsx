import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import {
  PaymentInfo,
  PaymentMethod,
  ReservationInfo,
} from "../../shared/api/payment.interface";
import { api } from "../../shared/api/apiInterceptor";

export default function PaymentCompleteContainer() {
  const navigate = useNavigate();

  const location = useLocation();
  const { paymentId, reservationId } = location.state || {};
  const [reservation, setReservation] = useState<ReservationInfo>();
  const [payment, setPayment] = useState<PaymentInfo>();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    console.log("결제 완료 : ", paymentId);

    if (!paymentId || !reservationId) return;

    const fetchData = async () => {
      try {
        setIsLoading(true);
        const [reservationRes, paymentRes] = await Promise.all([
          api().get<ReservationInfo>(
            `/reservation-service/reservations/${reservationId}`
          ),
          api().get<PaymentInfo>(`/payment-service/payments/${paymentId}`),
        ]);

        setReservation(reservationRes.data);
        setPayment(paymentRes.data);

        console.log(payment);
      } catch (error) {
        console.error("데이터를 불러오는데 실패했습니다:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [paymentId, reservationId]);

  // 결제 방법 한글 변환
  const getPaymentMethodKorean = (method: PaymentMethod | undefined) => {
    if (!method) return "";
    const methods = {
      CARD: "카드",
      BANK_TRANSFER: "계좌이체",
      KAKAO_PAY: "카카오페이",
    };
    return methods[method];
  };

  // 날짜 형식 변환
  const formatDate = (dateStr: string | undefined) => {
    if (!dateStr) return "";
    const date = new Date(dateStr);
    return `${date.getFullYear()}년 ${
      date.getMonth() + 1
    }월 ${date.getDate()}일`;
  };

  // 결제 정보 마스킹 처리
  const maskCardNumber = (cardNumber: string | undefined) => {
    if (!cardNumber) return "";
    return cardNumber.replace(/\d(?=\d{4})/g, "*");
  };

  const handleReservation = () => {
    navigate("/mypage");
  };

  return (
    <div className="flex flex-col items-center p-4 bg-gray-50 min-h-screen">
      {isLoading ? (
        <div className="flex items-center justify-center w-full h-64">
          <div className="animate-bounce flex space-x-1">
            <div className="w-3 h-3 bg-pink-400 rounded-full"></div>
            <div className="w-3 h-3 bg-pink-500 rounded-full animation-delay-200"></div>
            <div className="w-3 h-3 bg-pink-600 rounded-full animation-delay-400"></div>
          </div>
        </div>
      ) : (
        <div className="w-full max-w-md bg-white p-6 mt-6 rounded-lg shadow-md">
          <div className="text-center mb-6">
            <div className="inline-flex items-center justify-center w-16 h-16 bg-pink-100 rounded-full mb-4">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-8 w-8 text-pink-500"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M5 13l4 4L19 7"
                />
              </svg>
            </div>
            <h1 className="text-2xl font-bold text-gray-800">결제 완료</h1>
            <p className="text-gray-500 text-sm mt-1">
              예약이 성공적으로 완료되었습니다
            </p>
          </div>

          <div className="border-t border-gray-200 pt-4 mb-4">
            <h2 className="font-medium text-lg text-gray-700 mb-3">
              예약 정보
            </h2>

            <div className="bg-gray-50 p-4 rounded-lg mb-4">
              <div className="flex justify-between text-sm mb-2">
                <span className="text-gray-500">매장명</span>
                <span className="font-medium text-gray-800">
                  {reservation?.storeName}
                </span>
              </div>
              <div className="flex justify-between text-sm mb-2">
                <span className="text-gray-500">강아지 이름</span>
                <span className="font-medium text-gray-800">
                  {reservation?.dogName} ({reservation?.breedType})
                </span>
              </div>
              <div className="flex justify-between text-sm mb-2">
                <span className="text-gray-500">서비스</span>
                <span className="font-medium text-gray-800">
                  {reservation?.serviceType}
                </span>
              </div>
              <div className="flex justify-between text-sm mb-2">
                <span className="text-gray-500">예약일</span>
                <span className="font-medium text-gray-800">
                  {formatDate(reservation?.reservationDate)}
                </span>
              </div>
              <div className="flex justify-between text-sm mb-2">
                <span className="text-gray-500">예약시간</span>
                <span className="font-medium text-gray-800">
                  {reservation?.reservationTime}
                </span>
              </div>
              <div className="flex justify-between text-sm">
                <span className="text-gray-500">요청사항</span>
                <span className="font-medium text-gray-800 break-words max-w-xs">
                  {reservation?.requestMessage || "-"}
                </span>
              </div>
            </div>
          </div>

          <div className="border-t border-gray-200 pt-4 mb-4">
            <h2 className="font-medium text-lg text-gray-700 mb-3">
              결제 정보
            </h2>

            <div className="bg-gray-50 p-4 rounded-lg">
              <div className="flex justify-between text-sm mb-2">
                <span className="text-gray-500">결제금액</span>
                <span className="font-medium text-gray-900">
                  {payment?.amount?.toLocaleString()}원
                </span>
              </div>
              <div className="flex justify-between text-sm mb-2">
                <span className="text-gray-500">결제수단</span>
                <span className="font-medium text-gray-800">
                  {getPaymentMethodKorean(payment?.paymentMethod)}
                </span>
              </div>
              {payment?.paymentMethod === "CARD" && (
                <div className="flex justify-between text-sm">
                  <span className="text-gray-500">카드번호</span>
                  <span className="font-medium text-gray-800">
                    {maskCardNumber(payment?.cardNumber)}
                  </span>
                </div>
              )}
              {payment?.paymentMethod === "BANK_TRANSFER" && (
                <>
                  <div className="flex justify-between text-sm mb-2">
                    <span className="text-gray-500">은행</span>
                    <span className="font-medium text-gray-800">
                      {payment?.bankName}
                    </span>
                  </div>
                  <div className="flex justify-between text-sm">
                    <span className="text-gray-500">계좌번호</span>
                    <span className="font-medium text-gray-800">
                      {maskCardNumber(payment?.accountNumber)}
                    </span>
                  </div>
                </>
              )}
            </div>
          </div>

          <div className="mt-6 text-center">
            <button
              className="px-6 py-2 bg-pink-500 text-white rounded-full hover:bg-pink-600 transition-colors shadow-md"
              onClick={handleReservation}
            >
              예약 내역 보기
            </button>
            <div className="mt-3">
              <a href="/" className="text-pink-500 text-sm hover:text-pink-700">
                홈으로 돌아가기
              </a>
            </div>
          </div>

          <div className="flex items-center justify-center mt-6">
            <div className="flex items-center space-x-1">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-4 w-4 text-pink-400"
                fill="currentColor"
                viewBox="0 0 24 24"
              >
                <path d="M12 2l2.4 7.4h7.6l-6 4.6 2.3 7-6.3-4.6-6.3 4.6 2.3-7-6-4.6h7.6z" />
              </svg>
              <p className="text-xs text-gray-500">
                예약 내역은 마이페이지에서도 확인 가능합니다
              </p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
