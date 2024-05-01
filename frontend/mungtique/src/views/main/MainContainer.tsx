import MuiAppBar from "../../components/atomic/bar/MuiAppBar";

export default function MainContainer() {
  // TODO : 로그인 시에만 로그아웃이 가능하게 변경

  return (
    <div>
      <MuiAppBar />
      <div className="flex justify-center items-center min-h-screen bg-blue-100">
        <h1 className="text-xl">메인 페이지</h1>
      </div>
    </div>
  );
}
