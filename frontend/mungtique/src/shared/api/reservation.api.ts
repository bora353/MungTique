import { api } from "./apiInterceptor";

export const reservationApi = {
  getReservationById: (reservationId: number) =>
    api().get(`/reservation-service/reservations/${reservationId}`),
  getReservationsByUserId: (userId: number) =>
    api().get(`/reservation-service/reservations/user/${userId}`),
  createReservation: (data: any) =>
    api().post(`/reservation-service/reservations`, data),
};
