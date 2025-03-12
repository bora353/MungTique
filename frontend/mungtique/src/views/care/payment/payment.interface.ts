export interface ReservationData {
  reservationId: number;
  mungShopId: number;
  storeName: string;
  dogId: number;
  dogName: string;
  serviceType: string;
  breedType: string;
  reservationDate: string;
  reservationTime: string;
  userId: number;
  username: string;
  phone: string;
  requestMessage: string;
  reservationStatus: string;
}

export type PaymentMethod = 'CARD' | 'BANK_TRANSFER' | 'MOBILE' | 'KAKAO_PAY';

export interface PaymentInfo {
  reservationId: number;
  userId: string;
  amount: number;
  paymentMethod: PaymentMethod;
  cardNumber?: string;
  cardExpiry?: string;
  cardCVC?: string;
  accountHolder?: string;
  bankName?: string;
  accountNumber?: string;
}