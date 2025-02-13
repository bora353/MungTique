import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { MyMung } from "../../../shared/types/mungjoin.interface";

interface MyMungJoinProps {
  onsubmit: (mungJoinDTO: MyMung) => void;
}

export default function MyMungJoinForm({ onsubmit }: MyMungJoinProps) {
  const navigate = useNavigate();
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");

  const [mungJoinForm, setMungJoinForm] = useState({
    dogName: "",
    breedType: "",
    weight: "",
    age: "",
    gender: "",
    fixed: "",
  });

  const handleChange = (field: keyof typeof mungJoinForm, value: string) => {
    setMungJoinForm((prev) => ({ ...prev, [field]: value }));
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (
      !mungJoinForm.dogName ||
      !mungJoinForm.breedType ||
      !mungJoinForm.weight ||
      !mungJoinForm.age ||
      !mungJoinForm.gender ||
      !mungJoinForm.fixed
    ) {
      setSnackbarMessage("모든 값을 입력해주세요");
      setOpenSnackbar(true);
      return;
    }

    const mungJoinDTO: MyMung = {
      ...mungJoinForm,
      weight: Number(mungJoinForm.weight),
      age: Number(mungJoinForm.age),
      userId: Number(localStorage.getItem("userId")),
    };

    console.log("mungJoinDTO", mungJoinDTO);
    onsubmit(mungJoinDTO);
    setSnackbarMessage("등록 완료! 마이페이지로 이동합니다.");
    setOpenSnackbar(true);

    setTimeout(() => {
      navigate("/mypage");
    }, 2000);
  };

  return (
    <div className="p-6 max-w-md mx-auto bg-white shadow-md rounded-lg">
      <h1 className="text-4xl text-blue-500 mb-10">My Mung 등록</h1>

      {/* 폼 입력 */}
      <form onSubmit={handleSubmit} className="mt-4">
        {/* 이름 입력 */}
        <div className="mt-4">
          <label className="text-gray-600 text-sm font-semibold">이름</label>
          <input
            type="text"
            name="dogName"
            className="w-full mt-1 p-3 border rounded-md focus:outline-none"
            maxLength={10}
            value={mungJoinForm.dogName}
            onChange={(e) => handleChange("dogName", e.target.value)}
          />
        </div>

        {/* 품종 선택 */}
        <div className="mt-4">
          <label className="text-gray-600 text-sm font-semibold">품종</label>
          <select
            name="breedType"
            className="w-full mt-1 p-3 border rounded-md bg-white"
            value={mungJoinForm.breedType}
            onChange={(e) => handleChange("breedType", e.target.value)}
          >
            <option value="">선택하세요</option>
            <option value="GOLDEN_RETRIEVER">골든 리트리버</option>
            <option value="POODLE">푸들</option>
            <option value="CHIHUAHUA">치와와</option>
            <option value="MALTESE">말티즈</option>
            <option value="ALL">기타</option>
          </select>
        </div>

        {/* 몸무게 입력 */}
        <div className="mt-4">
          <label className="text-gray-600 text-sm font-semibold">
            몸무게(kg)
          </label>
          <input
            type="number"
            name="weight"
            className="w-full mt-1 p-3 border rounded-md focus:outline-none"
            value={mungJoinForm.weight}
            onChange={(e) => handleChange("weight", e.target.value)}
          />
        </div>

        {/* 나이 입력 */}
        <div className="mt-4">
          <label className="text-gray-600 text-sm font-semibold">나이</label>
          <input
            type="number"
            name="age"
            className="w-full mt-1 p-3 border rounded-md focus:outline-none"
            value={mungJoinForm.age}
            onChange={(e) => handleChange("age", e.target.value)}
          />
        </div>

        {/* 성별 선택 */}
        <div className="mt-4">
          <label className="text-gray-600 text-sm font-semibold">성별</label>
          <div className="flex gap-2 mt-1">
            <button
              type="button"
              className={`p-3 border rounded-md w-full ${
                mungJoinForm.gender === "MALE" ? "bg-gray-200" : "bg-white"
              }`}
              onClick={() => handleChange("gender", "MALE")}
            >
              남
            </button>
            <button
              type="button"
              className={`p-3 border rounded-md w-full ${
                mungJoinForm.gender === "FEMALE" ? "bg-gray-200" : "bg-white"
              }`}
              onClick={() => handleChange("gender", "FEMALE")}
            >
              여
            </button>
          </div>
        </div>

        {/* 중성화 여부 선택 */}
        <div className="mt-4">
          <label className="text-gray-600 text-sm font-semibold">
            중성화 여부
          </label>
          <div className="flex gap-2 mt-1">
            <button
              type="button"
              className={`p-3 border rounded-md w-full ${
                mungJoinForm.fixed === "YES" ? "bg-gray-200" : "bg-white"
              }`}
              onClick={() => handleChange("fixed", "YES")}
            >
              네
            </button>
            <button
              type="button"
              className={`p-3 border rounded-md w-full ${
                mungJoinForm.fixed === "NO" ? "bg-gray-200" : "bg-white"
              }`}
              onClick={() => handleChange("fixed", "NO")}
            >
              아니오
            </button>
          </div>
        </div>

        {/* 제출 버튼 */}
        <div className="mt-6">
          <button
            type="submit"
            className="w-full p-3 bg-blue-500 text-white rounded-md"
          >
            완료
          </button>
        </div>
      </form>

      {/* Snackbar 메시지 */}
      {openSnackbar && (
        <div className="mt-4 p-3 bg-red-500 text-white text-center rounded-md">
          {snackbarMessage}
        </div>
      )}
    </div>
  );
}
