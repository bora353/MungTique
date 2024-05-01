import MuiAppBar from "../../components/atomic/bar/MuiAppBar";

export default function ReservationContainer() {
  // TODO : Hook, mutate 사용

  return (
    <div>
      <MuiAppBar />
      <div className="flex justify-center items-center min-h-screen bg-blue-100">
        <p>현재 위치 기반의 지도와 애견미용샵 위치 나타내기</p>
        <p>현재 위치로 부터 거리 / 가게 주소 / 가능한 견종 / 휴무 / 영업시간</p>
        <p>내부로 들어가면 별점 / 하트 / 공유하기(?) </p>
        <p>실제 강아지들 미용한 사진 / 후기</p>
        <p>예약하기 버튼!</p>
      </div>
    </div>
  );
}
