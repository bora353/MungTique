import { api } from "./apiInterceptor";

export const paymentApi = {
  getPayment: (paymentId: number) =>
    api().get(`/payment-service/payments/${paymentId}`),
};
