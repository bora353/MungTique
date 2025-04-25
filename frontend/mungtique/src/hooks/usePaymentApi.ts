import { paymentApi } from "../shared/api/payment.api";

export const usePaymentApi = () => {
  const fetchPayment = async (id: number) => {
    try {
      const { data } = await paymentApi.getPayment(id);
      return data;
    } catch (error) {
      console.error("결제 정보 조회 실패:", error);
      return null;
    }
  };

  return { fetchPayment };
};
