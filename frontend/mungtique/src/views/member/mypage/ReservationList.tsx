import ReservationItem from "./ReservationItem";

interface Reservation {
  id: number;
  date: string;
  status: string;
  title: string;
  price: string;
  quantity: string;
  reservationNumber: string;
  seller: string;
  image: string;
}

const reservations: Reservation[] = [
  {
    id: 1,
    date: "2025.02.09",
    status: "예약완료",
    title: "멍샬롬",
    price: "7,000원",
    quantity: "1개",
    reservationNumber: "4262684058",
    seller: "COOPMARKETING",
    image: "https://placehold.co/100x60",
  },
];

export default function ReservationList() {
  return (
    <>
    <div className="flex items-center space-x-3">
      <h2 className="text-xl font-semibold">예약내역</h2>
    </div>

    {/* 필터 */}
    <div className="flex items-center mt-4 space-x-2">
      <input
        type="text"
        placeholder="예약 정보를 입력하세요"
        className="border px-4 py-2 rounded w-64"
      />
      <button className="px-4 py-2 bg-green-500 text-white rounded">
        조회하기
      </button>
    </div>
    <div className="mt-6">
      {reservations.map((reservation) => (
        <ReservationItem key={reservation.id} reservation={reservation} />
      ))}
    </div>
    </>
  );
}
