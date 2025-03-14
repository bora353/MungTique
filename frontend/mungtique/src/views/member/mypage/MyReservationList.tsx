import { useEffect, useState } from "react";
import { ReservationInfo } from "../../care/payment/payment.interface";
import { api } from "../../../shared/api/ApiInterceptor";

export default function MyReservationList() {
    const [reservations, setReservations] = useState<ReservationInfo[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [activeTab, setActiveTab] = useState<string>("upcoming");
    const [expandedReservation, setExpandedReservation] = useState<number | null>(
      null
    );
  
    useEffect(() => {
      const userId = localStorage.getItem("userId");
  
      const fetchReservations = async () => {
        try {
          setIsLoading(true);
          const response = await api().get(
            `/reservation-service/reservations/${userId}`
          );
          
          // 응답 데이터가 배열인지 확인하고 배열로 변환
          if (Array.isArray(response.data)) {
            setReservations(response.data);
            console.log("API 응답 데이터(배열):", response.data);
          } else {
            // 단일 객체인 경우 배열로 변환
            const dataArray = [response.data];
            setReservations(dataArray);
            console.log("API 응답 데이터(단일 객체를 배열로 변환):", dataArray);
          }
        } catch (error) {
          console.error("예약 내역을 불러오는데 실패했습니다:", error);
          setReservations([]); // 오류 발생 시 빈 배열로 설정
        } finally {
          setIsLoading(false);
        }
      };
  
      fetchReservations();
    }, []);
  
    // 예약 상태에 따른 배경색 결정
    const getStatusColor = (status: string) => {
      switch (status) {
        case "WAITING_FOR_PAYMENT":
          return "bg-yellow-100 text-yellow-800";
        case "CANCELED":
          return "bg-red-100 text-red-800";
        case "PAID":
          return "bg-green-100 text-green-800";
        default:
          return "bg-gray-100 text-gray-800";
      }
    };
  
    // 예약 상태 한글 변환
    const getStatusKorean = (status: string) => {
      const statusMap: Record<string, string> = {
        WAITING_FOR_PAYMENT: "예약 대기",
        CANCELED: "예약 취소",
        PAID: "결제 완료",
      };
      return statusMap[status] || status;
    };
  
    // 날짜 형식 변환
    const formatDate = (dateStr: string) => {
      const date = new Date(dateStr);
      return `${date.getFullYear()}년 ${
        date.getMonth() + 1
      }월 ${date.getDate()}일`;
    };
  
    // 예약을 예정된 예약과 지난 예약으로 필터링
    const filterReservations = (type: string) => {
      const today = new Date();
      today.setHours(0, 0, 0, 0);
  
      if (!Array.isArray(reservations)) {
        console.error("reservations는 배열이어야 합니다.");
        return []; 
      }
  
      return reservations.filter((reservation) => {
        const reservationDate = new Date(reservation.reservationDate);
        reservationDate.setHours(0, 0, 0, 0);
  
        if (type === "upcoming") {
          return (
            reservationDate >= today &&
            reservation.reservationStatus !== "CANCELED"
          );
        } else {
          return (
            reservationDate < today ||
            reservation.reservationStatus === "CANCELED" ||
            reservation.reservationStatus === "COMPLETED"
          );
        }
      });
    };
  
    // 예약 상세 정보 토글
    const toggleReservationDetails = (reservationId: number) => {
      if (expandedReservation === reservationId) {
        setExpandedReservation(null);
      } else {
        setExpandedReservation(reservationId);
      }
    };
  
    // 예약 취소 기능
    const handleCancelReservation = async (reservationId: number) => {
      if (window.confirm("예약을 취소하시겠습니까?")) {
        try {
          await api().patch(
            `/reservation-service/reservations/${reservationId}/cancel`
          );
  
          setReservations(
            reservations.map((reservation) =>
              reservation.reservationId === reservationId
                ? { ...reservation, reservationStatus: "CANCELED" }
                : reservation
            )
          );
        } catch (error) {
          console.error("예약 취소에 실패했습니다:", error);
          alert("예약 취소에 실패했습니다. 다시 시도해주세요.");
        }
      }
    };
  
    const upcomingReservations = filterReservations("upcoming");
    const pastReservations = filterReservations("past");
  
    return (
      <div className="max-w-4xl mx-auto p-4">
        <div className="mb-6">
          <h1 className="text-2xl font-bold text-gray-800">나의 예약 내역</h1>
          <p className="text-gray-500 text-sm mt-1">
            현재까지의 모든 예약 내역을 확인할 수 있습니다
          </p>
        </div>
  
        {/* 탭 네비게이션 */}
        <div className="flex border-b border-gray-200 mb-6">
          <button
            className={`py-2 px-4 font-medium text-sm ${
              activeTab === "upcoming"
                ? "text-pink-500 border-b-2 border-pink-500"
                : "text-gray-500 hover:text-gray-700"
            }`}
            onClick={() => setActiveTab("upcoming")}
          >
            예정된 예약 ({upcomingReservations.length})
          </button>
          <button
            className={`py-2 px-4 font-medium text-sm ${
              activeTab === "past"
                ? "text-pink-500 border-b-2 border-pink-500"
                : "text-gray-500 hover:text-gray-700"
            }`}
            onClick={() => setActiveTab("past")}
          >
            지난 예약 ({pastReservations.length})
          </button>
        </div>
  
        {isLoading ? (
          <div className="flex items-center justify-center py-12">
            <div className="animate-bounce flex space-x-1">
              <div className="w-3 h-3 bg-pink-400 rounded-full"></div>
              <div className="w-3 h-3 bg-pink-500 rounded-full animation-delay-200"></div>
              <div className="w-3 h-3 bg-pink-600 rounded-full animation-delay-400"></div>
            </div>
          </div>
        ) : (
          <div>
            {(activeTab === "upcoming" ? upcomingReservations : pastReservations)
              .length > 0 ? (
              (activeTab === "upcoming"
                ? upcomingReservations
                : pastReservations
              ).map((reservation) => (
                <div
                  key={reservation.reservationId}
                  className="bg-white border border-gray-200 rounded-lg shadow-sm overflow-hidden mb-4"
                >
                  <div
                    className="p-4 flex justify-between items-center cursor-pointer hover:bg-gray-50"
                    onClick={() =>
                      toggleReservationDetails(reservation.reservationId)
                    }
                  >
                    <div className="flex-1">
                      <div className="flex items-center mb-1">
                        <span className="text-sm font-medium">
                          {formatDate(reservation.reservationDate)}{" "}
                          {reservation.reservationTime}
                        </span>
                        <span
                          className={`ml-2 text-xs px-2 py-1 rounded-full ${getStatusColor(
                            reservation.reservationStatus
                          )}`}
                        >
                          {getStatusKorean(reservation.reservationStatus)}
                        </span>
                      </div>
                      <h3 className="font-medium">{reservation.storeName}</h3>
                      <p className="text-gray-500 text-sm">
                        {reservation.serviceType} • {reservation.dogName}
                      </p>
                    </div>
                    <div className="flex items-center">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className={`h-5 w-5 text-gray-400 transition-transform ${
                          expandedReservation === reservation.reservationId
                            ? "transform rotate-180"
                            : ""
                        }`}
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth={2}
                          d="M19 9l-7 7-7-7"
                        />
                      </svg>
                    </div>
                  </div>
  
                  {expandedReservation === reservation.reservationId && (
                    <div className="p-4 bg-gray-50 border-t border-gray-200">
                      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 mb-4">
                        <div>
                          <p className="text-sm text-gray-500">예약 정보</p>
                          <div className="mt-2 space-y-2">
                            <div className="flex justify-between">
                              <span className="text-sm text-gray-500">날짜</span>
                              <span className="text-sm">
                                {formatDate(reservation.reservationDate)}
                              </span>
                            </div>
                            <div className="flex justify-between">
                              <span className="text-sm text-gray-500">시간</span>
                              <span className="text-sm">
                                {reservation.reservationTime}
                              </span>
                            </div>
                            <div className="flex justify-between">
                              <span className="text-sm text-gray-500">
                                서비스
                              </span>
                              <span className="text-sm">
                                {reservation.serviceType}
                              </span>
                            </div>
                          </div>
                        </div>
                        <div>
                          <p className="text-sm text-gray-500">반려견 정보</p>
                          <div className="mt-2 space-y-2">
                            <div className="flex justify-between">
                              <span className="text-sm text-gray-500">이름</span>
                              <span className="text-sm">
                                {reservation.dogName}
                              </span>
                            </div>
                            <div className="flex justify-between">
                              <span className="text-sm text-gray-500">품종</span>
                              <span className="text-sm">
                                {reservation.breedType}
                              </span>
                            </div>
                          </div>
                        </div>
                      </div>
  
                      {reservation.requestMessage && (
                        <div className="mb-4">
                          <p className="text-sm text-gray-500">요청사항</p>
                          <p className="mt-1 text-sm p-2 bg-white border border-gray-200 rounded">
                            {reservation.requestMessage}
                          </p>
                        </div>
                      )}
  
                      <div className="flex justify-end space-x-2 mt-4">
                        {reservation.reservationStatus !== "CANCELED" && (
                          <button
                            className="px-3 py-1 text-sm bg-red-50 text-red-600 border border-red-200 rounded-full hover:bg-red-100"
                            onClick={(e) => {
                              e.stopPropagation();
                              handleCancelReservation(reservation.reservationId);
                            }}
                          >
                            예약 취소
                          </button>
                        )}
                      </div>
                    </div>
                  )}
                </div>
              ))
            ) : (
              <div className="text-center py-12">
                <div className="inline-flex items-center justify-center w-16 h-16 bg-gray-100 rounded-full mb-4">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    className="h-8 w-8 text-gray-400"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
                    />
                  </svg>
                </div>
                <p className="text-lg text-gray-600 font-medium">
                  {activeTab === "upcoming"
                    ? "예정된 예약이 없습니다"
                    : "지난 예약이 없습니다"}
                </p>
                <p className="text-gray-500 text-sm mt-1">
                  새로운 예약을 추가해보세요!
                </p>
                <button className="mt-4 px-4 py-2 bg-pink-500 text-white rounded-full hover:bg-pink-600 transition-colors shadow-sm">
                  새로운 예약하기
                </button>
              </div>
            )}
          </div>
        )}
      </div>
    );
  }