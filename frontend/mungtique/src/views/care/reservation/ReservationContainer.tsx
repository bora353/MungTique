import { useState } from "react";

export default function ReservationContainer() {
  const [selectedDate, setSelectedDate] = useState<string>("14");
  const [selectedTime, setSelectedTime] = useState<string | null>(null);

  const today = "14";

  // TODO : 처음에 로그인 되어야 예약하기 버튼 누를 수 있게 !!! 아니면 로그인 페이지로 가게!!!

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

  const daysInMonth = [
    { date: "26", disabled: true },
    { date: "27", disabled: true },
    { date: "28", disabled: true },
    { date: "29", disabled: true },
    { date: "30", disabled: true },
    { date: "31", disabled: true },
    { date: "1" },
    { date: "2" },
    { date: "3" },
    { date: "4" },
    { date: "5" },
    { date: "6" },
    { date: "7" },
    { date: "8" },
    { date: "9" },
    { date: "10" },
    { date: "11" },
    { date: "12" },
    { date: "13" },
    { date: "14", isToday: true },
    { date: "15" },
    { date: "16", holiday: true },
    { date: "17", holiday: true },
    { date: "18" },
    { date: "19" },
    { date: "20" },
    { date: "21" },
    { date: "22" },
    { date: "23" },
    { date: "24", holiday: true },
    { date: "25" },
    { date: "26" },
    { date: "27" },
    { date: "28" },
    { date: "1", holiday: true },
  ];

  return (
    <div className="flex flex-col items-center p-6 bg-gray-50 min-h-screen">
      {/* 날짜 선택 */}
      <div className="w-full max-w-md bg-white p-4 mt-6 rounded-lg shadow-md">
        <h2 className="text-lg font-semibold flex items-center">
          📅 <span className="ml-2">2.14.(금) · 시간을 선택해 주세요</span>
        </h2>
        <p className="text-gray-500 text-center font-semibold text-xl mt-2">
          2025.2
        </p>

        <div className="grid grid-cols-7 gap-1 mt-3 text-center text-sm text-gray-600">
          {["일", "월", "화", "수", "목", "금", "토"].map((day) => (
            <div key={day} className="font-semibold">
              {day}
            </div>
          ))}
          {daysInMonth.map(({ date, isToday, holiday, disabled }) => (
            <button
              key={date}
              className={`p-2 rounded-lg ${
                disabled
                  ? "text-gray-300"
                  : selectedDate === date
                  ? "bg-green-500 text-white font-bold"
                  : isToday
                  ? "bg-green-100 text-green-700 font-semibold"
                  : holiday
                  ? "text-red-500 font-semibold"
                  : "hover:bg-gray-200"
              }`}
              onClick={() => !disabled && setSelectedDate(date)}
              disabled={disabled}
            >
              {date}
              {isToday && <span className="block text-xs">오늘</span>}
              {holiday && <span className="block text-xs">휴무</span>}
            </button>
          ))}
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
        <button className="px-4 py-2 rounded-lg bg-blue-500 text-white">
          다음
        </button>
      </div>
    </div>
  );
}
