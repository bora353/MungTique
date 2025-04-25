import { reservationApi } from "../shared/api/reservation.api";

export const useReservationApi = () => {
  const fetchReservationById = async (id: number) => {
    try {
      const { data } = await reservationApi.getReservationById(id);
      return data;
    } catch (error) {
      console.error("예약 정보 조회 실패:", error);
      return null;
    }
  };

  const fetchReservationByUserId = async (id: number) => {
    try {
      const { data } = await reservationApi.getReservationsByUserId(id);
      return data;
    } catch (error) {
      console.error("예약 정보 조회 실패:", error);
      return null;
    }
  };

  const createReservation = async (data: any) => {
    try {
      const response = await reservationApi.createReservation(data);
      return response.data;
    } catch (error) {
      console.error("예약 생성 실패:", error);
      throw error;
    }
  };

  return { fetchReservationById, fetchReservationByUserId, createReservation };
};
