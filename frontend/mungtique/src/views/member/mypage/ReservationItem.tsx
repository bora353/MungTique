// 주문 아이템 props 타입 정의
interface ReservationItemProps {
  reservation: {
    id: number;
    date: string;
    status: string;
    title: string;
    price: string;
    quantity: string;
    reservationNumber: string;
    seller: string;
    image: string;
  };
}

export default function ReservationItem({ reservation }: ReservationItemProps) {
  return (
    <div className="border rounded-md p-4 mt-4">
      <h3 className="text-lg font-semibold">{reservation.date}</h3>
      <div className="flex mt-2">
        <img
          src={reservation.image}
          alt="주문 상품 이미지"
          className="w-24 h-14 rounded-md"
        />
        <div className="ml-4 flex-1">
          <p className="text-green-600 font-bold">{reservation.status}</p>
          <p className="text-blue-600">{reservation.title}</p>
          <p className="font-bold">
            {reservation.price} / {reservation.quantity}
          </p>
          <p className="text-gray-500">
            주문번호 {reservation.reservationNumber}
          </p>
        </div>
        <div className="flex flex-col justify-between">
          <button className="px-3 py-1 border rounded bg-gray-200">
            문의하기
          </button>
          <button className="px-3 py-1 border rounded bg-yellow-400">
            e쿠폰상세
          </button>
        </div>
      </div>
    </div>
  );
}
