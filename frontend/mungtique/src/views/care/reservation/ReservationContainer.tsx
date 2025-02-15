import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { StaticDatePicker } from "@mui/x-date-pickers/StaticDatePicker";
import dayjs, { Dayjs } from "dayjs";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function ReservationContainer() {
  const navigate = useNavigate();

  const [selectedDate, setSelectedDate] = useState<Dayjs | null>(dayjs());
  const [selectedTime, setSelectedTime] = useState<string | null>(null);

  const availableTimes = [
    "5:00",
    "5:30",
    "6:00",
    "6:30",
    "7:00",
    "7:30",
    "8:00",
    "8:30",
  ];

  const handleNext = () => {
    if (!selectedDate || !selectedTime) {
      alert("날짜와 시간을 선택해주세요!");
      return;
    }

    console.log("선택된 날짜:", selectedDate.format("YYYY-MM-DD"));
    console.log("선택된 시간:", selectedTime);

    navigate("/reservation-mung");
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
            />
          </LocalizationProvider>
        </div>
      </div>
      {/* 시간 선택 */}
      <div className="w-full max-w-md bg-white p-4 mt-6 rounded-lg shadow-md">
        <h2 className="text-lg font-semibold">오후</h2>
        <div className="grid grid-cols-4 gap-2 mt-2">
          {availableTimes.map((time) => (
            <button
              key={time}
              className={`p-3 border rounded-lg text-center ${
                selectedTime === time
                  ? "bg-green-500 text-white"
                  : "bg-gray-100 text-gray-700"
              }`}
              onClick={() => setSelectedTime(time)}
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
