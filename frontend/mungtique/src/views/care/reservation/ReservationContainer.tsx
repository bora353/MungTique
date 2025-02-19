import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { StaticDatePicker } from "@mui/x-date-pickers/StaticDatePicker";
import dayjs from "dayjs";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../shared/api/ApiInterceptor";
import { useReservationStore } from "./reservation.store";

export default function ReservationContainer() {
  const navigate = useNavigate();

  const {
    selectedMungShopId,
    selectedDate,
    selectedTime,
    availableTimes,
    setSelectedDate,
    setSelectedTime,
    setAvailableTimes,
  } = useReservationStore();

  useEffect(() => {
    api()
      .get(`/mungshop-service/mungshops/reservation/${selectedMungShopId}/available`)
      .then((response) => {
        const times = response.data.map(
          (reservation: { reservationDateTime: string }) => {
            return {
              dateTime: reservation.reservationDateTime,
              time: dayjs(reservation.reservationDateTime).format("HH:mm"),
              date: dayjs(reservation.reservationDateTime).format("YYYY-MM-DD"),
            };
          }
        );
        setAvailableTimes(times);
      })
      .catch((error) => {
        console.error("Error fetching available times:", error);
      });
  }, [selectedMungShopId]);

  const allTimes = Array.from({ length: 10 }, (_, i) =>
    dayjs()
      .startOf("day")
      .add(9 + i, "hour")
      .format("HH:mm")
  );

  const filteredAvailableTimes = availableTimes.filter(
    (timeSlot) => timeSlot.date === selectedDate?.format("YYYY-MM-DD")
  );

  const handleNext = () => {
    if (!selectedDate || !selectedTime) {
      alert("날짜와 시간을 선택해주세요!");
      return;
    }

    console.log("선택된 날짜:", selectedDate.format("YYYY-MM-DD"));
    console.log("선택된 시간:", selectedTime);

    navigate("/reservation-mung");
  };

  const handleTimeClick = (time: string) => {
    if (filteredAvailableTimes.some((available) => available.time === time)) {
      setSelectedTime(time);
    }
  };

  const getButtonStyle = (time: string) => {
    // 선택된 시간
    if (selectedTime === time) {
      return "bg-blue-500 text-white";
    }
    // 불가능한 시간
    else if (
      !filteredAvailableTimes.some((available) => available.time === time)
    ) {
      return "bg-gray-300 text-gray-500 cursor-not-allowed";
    }
    // 기본 상태
    else {
      return "bg-blue-100 text-gray-700";
    }
  };

  return (
    <div className="flex flex-col items-center p-6 bg-gray-50 min-h-screen">
      {/* 날짜 선택 */}
      <div className="w-full max-w-md bg-white p-4 mt-6 rounded-lg shadow-md">
        <h2 className="text-lg font-semibold flex items-center">
          📅 <span className="ml-2">날짜/시간을 선택해 주세요</span>
        </h2>

        <div>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <StaticDatePicker
              value={selectedDate}
              onChange={(newDate) => setSelectedDate(newDate)}
              slots={{ actionBar: () => null }}
            />
          </LocalizationProvider>
        </div>
      </div>
      {/* 시간 선택 */}
      <div className="w-full max-w-md bg-white p-4 mt-6 rounded-lg shadow-md">
        <h2 className="text-lg font-semibold">오후</h2>
        <div className="grid grid-cols-4 gap-2 mt-2">
          {allTimes.map((time) => (
            <button
              key={time} // 고유한 key 값 설정
              className={`p-3 border rounded-lg text-center ${getButtonStyle(
                time
              )}`}
              onClick={() => handleTimeClick(time)}
              disabled={
                !filteredAvailableTimes.some(
                  (available) => available.time === time
                )
              } // 불가능한 시간 비활성화
            >
              {time}
            </button>
          ))}
        </div>
      </div>
      <div className="mt-6 flex justify-end ">
        <button
          className="w-full px-4 py-2 rounded-lg bg-blue-500 text-white hover:bg-blue-600 transition-all"
          onClick={handleNext}
        >
          다음
        </button>
      </div>
    </div>
  );
}
