import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { StaticDatePicker } from "@mui/x-date-pickers/StaticDatePicker";
import dayjs from "dayjs";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useReservationStore } from "../../shared/store/reservation.store";
import { api } from "../../shared/api/apiInterceptor";
import { useSnackbar } from "notistack";

export default function ReservationContainer() {
  const { enqueueSnackbar } = useSnackbar();
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
    if (!selectedDate) {
      setSelectedDate(dayjs().add(1, "day"));
    }
  }, [selectedDate, setSelectedDate]);

  useEffect(() => {
    // TODO : 추후 매달마다 정보 가져오게 변경하기
    api()
      .get(
        `/mungshop-service/mungshops/reservation/${selectedMungShopId}/available`
      )
      .then((response) => {
        console.log(response.data);
        const times = response.data.map(
          (reservation: {
            reservationDate: string;
            reservationTime: string;
          }) => {
            return {
              date: reservation.reservationDate,
              time: reservation.reservationTime,
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
      enqueueSnackbar("날짜와 시간을 선택해주세요!", {
        variant: "info",
      });
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
              onChange={(newDate) => {
                setSelectedDate(newDate);
                setSelectedTime(null);
              }}
              shouldDisableDate={(date) =>
                date.isBefore(dayjs(), "day") || date.isSame(dayjs(), "day")
              }
              slots={{ actionBar: () => null }}
            />
          </LocalizationProvider>
        </div>
      </div>
      {/* 시간 선택 */}
      <div className="w-full max-w-md bg-white p-4 mt-6 rounded-lg shadow-md">
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
