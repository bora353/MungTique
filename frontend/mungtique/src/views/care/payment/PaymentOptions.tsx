import { useState } from "react";
import { PaymentInfo, PaymentMethod } from "./payment.interface";
import { useNavigate } from "react-router-dom";
import { api } from "../../../shared/api/ApiInterceptor";

interface PaymentOptionsProps {
  reservationId: number | undefined;
  amount: number | undefined;
}

export default function PaymentOptions({
  reservationId,
  amount,
}: PaymentOptionsProps) {
  const navigate = useNavigate();

  // 결제 정보 상태
  const [paymentInfo, setPaymentInfo] = useState<PaymentInfo>({
    reservationId: reservationId ?? 0,
    amount: amount ?? 0,
    paymentMethod: "CARD",
    userId: localStorage.getItem("userId") ?? "",
  });

  // 카드 입력 상태
  const [cardInfo, setCardInfo] = useState({
    cardNumber: "",
    cardExpiry: "",
    cardCVC: "",
  });

  // 계좌이체 입력 상태
  const [bankInfo, setBankInfo] = useState({
    accountHolder: "",
    bankName: "",
    accountNumber: "",
  });

  // 결제 방법 변경 핸들러
  const handlePaymentMethodChange = (method: PaymentMethod) => {
    setPaymentInfo({
      ...paymentInfo,
      paymentMethod: method,
    });
  };

  // 카드 정보 변경 핸들러
  const handleCardInfoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setCardInfo({
      ...cardInfo,
      [name]: value,
    });
  };

  // 계좌이체 정보 변경 핸들러
  const handleBankInfoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setBankInfo({
      ...bankInfo,
      [name]: value,
    });
  };

  // 결제 처리
  const handlePayment = () => {
    // 결제 수단별 유효성 검사
    if (paymentInfo.paymentMethod === "CARD") {
      if (!cardInfo.cardNumber || !cardInfo.cardExpiry || !cardInfo.cardCVC) {
        alert("카드 정보를 모두 입력해주세요.");
        return;
      }

      // 카드 정보 추가
      setPaymentInfo({
        ...paymentInfo,
        cardNumber: cardInfo.cardNumber,
        cardExpiry: cardInfo.cardExpiry,
        cardCVC: cardInfo.cardCVC,
      });
    } else if (paymentInfo.paymentMethod === "BANK_TRANSFER") {
      if (
        !bankInfo.accountHolder ||
        !bankInfo.bankName ||
        !bankInfo.accountNumber
      ) {
        alert("계좌 정보를 모두 입력해주세요.");
        return;
      }

      // 계좌 정보 추가
      setPaymentInfo({
        ...paymentInfo,
        accountHolder: bankInfo.accountHolder,
        bankName: bankInfo.bankName,
        accountNumber: bankInfo.accountNumber,
      });
    }

    // 결제 API 호출 (실제 구현 필요)
    console.log("결제 정보:", {
      ...paymentInfo,
      ...(paymentInfo.paymentMethod === "CARD" ? cardInfo : {}),
      ...(paymentInfo.paymentMethod === "BANK_TRANSFER" ? bankInfo : {}),
    });

    // TODO :
    // '예약하기 & 결제하기' 버튼 클릭 시 결제 진행
    // 결제가 완료되면 예약 확정
    api()
      .post<number>(`/payment-service/payments`, {
        ...paymentInfo,
        ...(paymentInfo.paymentMethod === "CARD" ? cardInfo : {}),
        ...(paymentInfo.paymentMethod === "BANK_TRANSFER" ? bankInfo : {}),
      })
      .then((response) => {
        const paymentId = response.data;
        console.log("paymentId", paymentId);

        alert("결제가 완료되었습니다.");
        navigate("/payment/complete", {
          state: {
            paymentId: paymentId,
            reservationId: reservationId,
            amount: paymentInfo.amount,
            paymentMethod: paymentInfo.paymentMethod,
          },
        });
      })
      .catch((error) => {
        console.error("예약 정보를 불러오는데 실패했습니다:", error);
      });
  };

  const handleCancel = () => {
    if (window.confirm("결제를 취소하시겠습니까?")) {
      navigate("/mungshop");
    }
  };

  return (
    <>
      {/* 결제 수단 선택 */}
      <div className="mb-6">
        <h2 className="text-xl font-semibold mb-4">결제 수단</h2>
        <div className="grid grid-cols- gap-4 mb-4 sm:grid-cols-3">
          <button
            className={`p-3 border rounded-lg flex flex-col items-center ${
              paymentInfo.paymentMethod === "CARD"
                ? "bg-blue-100 border-blue-500"
                : "border-gray-300"
            }`}
            onClick={() => handlePaymentMethodChange("CARD")}
          >
            <span className="text-xl mb-1">💳</span>
            <span>신용카드</span>
          </button>
          <button
            className={`p-3 border rounded-lg flex flex-col items-center ${
              paymentInfo.paymentMethod === "BANK_TRANSFER"
                ? "bg-blue-100 border-blue-500"
                : "border-gray-300"
            }`}
            onClick={() => handlePaymentMethodChange("BANK_TRANSFER")}
          >
            <span className="text-xl mb-1">🏦</span>
            <span>계좌이체</span>
          </button>
          <button
            className={`p-3 border rounded-lg flex flex-col items-center ${
              paymentInfo.paymentMethod === "KAKAO_PAY"
                ? "bg-blue-100 border-blue-500"
                : "border-gray-300"
            }`}
            onClick={() => handlePaymentMethodChange("KAKAO_PAY")}
          >
            <span className="text-xl mb-1">🟡</span>
            <span>카카오페이</span>
          </button>
        </div>
        {/* 카드 결제 폼 */}
        {paymentInfo.paymentMethod === "CARD" && (
          <div className="bg-gray-50 p-4 rounded-lg">
            <div className="mb-4">
              <label className="block text-gray-700 mb-1">카드번호</label>
              <input
                type="text"
                name="cardNumber"
                placeholder="0000-0000-0000-0000"
                className="w-full p-2 border rounded"
                value={cardInfo.cardNumber}
                onChange={handleCardInfoChange}
              />
            </div>
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-gray-700 mb-1">
                  유효기간 (MM/YY)
                </label>
                <input
                  type="text"
                  name="cardExpiry"
                  placeholder="MM/YY"
                  className="w-full p-2 border rounded"
                  value={cardInfo.cardExpiry}
                  onChange={handleCardInfoChange}
                />
              </div>
              <div>
                <label className="block text-gray-700 mb-1">CVC</label>
                <input
                  type="text"
                  name="cardCVC"
                  placeholder="000"
                  className="w-full p-2 border rounded"
                  value={cardInfo.cardCVC}
                  onChange={handleCardInfoChange}
                />
              </div>
            </div>
          </div>
        )}{" "}
        {/* 계좌이체 폼 */}
        {paymentInfo.paymentMethod === "BANK_TRANSFER" && (
          <div className="bg-gray-50 p-4 rounded-lg">
            <div className="mb-4">
              <label className="block text-gray-700 mb-1">예금주</label>
              <input
                type="text"
                name="accountHolder"
                placeholder="예금주명"
                className="w-full p-2 border rounded"
                value={bankInfo.accountHolder}
                onChange={handleBankInfoChange}
              />
            </div>
            <div className="mb-4">
              <label className="block text-gray-700 mb-1">은행</label>
              <select
                name="bankName"
                className="w-full p-2 border rounded"
                value={bankInfo.bankName}
                onChange={(e) =>
                  setBankInfo({ ...bankInfo, bankName: e.target.value })
                }
              >
                <option value="">은행 선택</option>
                <option value="KB국민은행">KB국민은행</option>
                <option value="신한은행">신한은행</option>
                <option value="우리은행">우리은행</option>
                <option value="하나은행">하나은행</option>
                <option value="농협은행">농협은행</option>
                <option value="카카오뱅크">카카오뱅크</option>
                <option value="토스뱅크">토스뱅크</option>
              </select>
            </div>
            <div>
              <label className="block text-gray-700 mb-1">계좌번호</label>
              <input
                type="text"
                name="accountNumber"
                placeholder="'-' 없이 입력"
                className="w-full p-2 border rounded"
                value={bankInfo.accountNumber}
                onChange={handleBankInfoChange}
              />
            </div>
          </div>
        )}
        {/* 카카오페이 안내 */}
        {paymentInfo.paymentMethod === "KAKAO_PAY" && (
          <div className="bg-gray-50 p-4 rounded-lg">
            <p className="text-center">
              결제하기 버튼을 누르면 카카오페이로 이동합니다.
            </p>
          </div>
        )}
      </div>

      {/* 결제 버튼 */}
      <div className="flex justify-between">
        <button
          className="px-6 py-3 bg-gray-300 text-gray-700 rounded-lg font-medium hover:bg-gray-400"
          onClick={handleCancel}
        >
          취소
        </button>
        <button
          className="px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 disabled:bg-gray-400 disabled:cursor-not-allowed"
          onClick={handlePayment}
        >
          {paymentInfo.amount}원 결제하기
        </button>
      </div>
    </>
  );
}
