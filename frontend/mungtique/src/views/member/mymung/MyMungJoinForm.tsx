import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { MyMungJoin } from "../../../shared/types/mungjoin.interface";

interface MyMungJoinProps {
  onsubmit: (mungJoinDTO: MyMungJoin) => void;
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

    const mungJoinDTO: MyMungJoin = {
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
    <div className="p-8 max-w-md mx-auto bg-white rounded-3xl shadow-lg border-2 border-pink-200">
      <div className="flex flex-col items-center mb-6">
        <h1 className="text-3xl font-bold text-pink-500">
          My Mung 등록
          <span className="ml-2" role="img" aria-label="dog">
            🐶
          </span>
        </h1>
        <div className="w-16 h-1 bg-pink-300 rounded-full mt-2"></div>
      </div>

      {/* 폼 입력 */}
      <form onSubmit={handleSubmit} className="mt-6">
        {/* 이름 입력 */}
        <div className="mb-5">
          <label className="block text-pink-600 text-sm font-medium mb-2">
            이름 <span className="text-pink-400">♥</span>
          </label>
          <input
            type="text"
            name="dogName"
            className="w-full p-3 border-2 border-pink-200 rounded-xl focus:outline-none focus:border-pink-400 transition-colors"
            maxLength={10}
            value={mungJoinForm.dogName}
            onChange={(e) => handleChange("dogName", e.target.value)}
            placeholder="멍멍이 이름을 알려주세요"
          />
        </div>

        {/* 품종 선택 */}
        <div className="mb-5">
          <label className="block text-pink-600 text-sm font-medium mb-2">
            품종 <span className="text-pink-400">♥</span>
          </label>
          <select
            name="breedType"
            className="w-full p-3 border-2 border-pink-200 rounded-xl bg-white focus:outline-none focus:border-pink-400 transition-colors"
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

        {/* 몸무게와 나이 입력 (2열 배치) */}
        <div className="grid grid-cols-2 gap-4 mb-5">
          {/* 몸무게 입력 */}
          <div>
            <label className="block text-pink-600 text-sm font-medium mb-2">
              몸무게(kg) <span className="text-pink-400">♥</span>
            </label>
            <input
              type="number"
              name="weight"
              className="w-full p-3 border-2 border-pink-200 rounded-xl focus:outline-none focus:border-pink-400 transition-colors"
              value={mungJoinForm.weight}
              onChange={(e) => handleChange("weight", e.target.value)}
              placeholder="0.0"
            />
          </div>

          {/* 나이 입력 */}
          <div>
            <label className="block text-pink-600 text-sm font-medium mb-2">
              나이 <span className="text-pink-400">♥</span>
            </label>
            <input
              type="number"
              name="age"
              className="w-full p-3 border-2 border-pink-200 rounded-xl focus:outline-none focus:border-pink-400 transition-colors"
              value={mungJoinForm.age}
              onChange={(e) => handleChange("age", e.target.value)}
              placeholder="0"
            />
          </div>
        </div>

        {/* 성별 선택 */}
        <div className="mb-5">
          <label className="block text-pink-600 text-sm font-medium mb-2">
            성별 <span className="text-pink-400">♥</span>
          </label>
          <div className="flex gap-4 mt-1">
            <button
              type="button"
              className={`p-3 border-2 rounded-xl w-full transition-colors ${
                mungJoinForm.gender === "MALE"
                  ? "bg-blue-100 border-blue-300 text-blue-600 font-medium"
                  : "bg-white border-gray-200 text-gray-500"
              }`}
              onClick={() => handleChange("gender", "MALE")}
            >
              남아 🧢
            </button>
            <button
              type="button"
              className={`p-3 border-2 rounded-xl w-full transition-colors ${
                mungJoinForm.gender === "FEMALE"
                  ? "bg-pink-100 border-pink-300 text-pink-600 font-medium"
                  : "bg-white border-gray-200 text-gray-500"
              }`}
              onClick={() => handleChange("gender", "FEMALE")}
            >
              여아 🎀
            </button>
          </div>
        </div>

        {/* 중성화 여부 선택 */}
        <div className="mb-6">
          <label className="block text-pink-600 text-sm font-medium mb-2">
            중성화 여부 <span className="text-pink-400">♥</span>
          </label>
          <div className="flex gap-4 mt-1">
            <button
              type="button"
              className={`p-3 border-2 rounded-xl w-full transition-colors ${
                mungJoinForm.fixed === "YES"
                  ? "bg-green-100 border-green-300 text-green-600 font-medium"
                  : "bg-white border-gray-200 text-gray-500"
              }`}
              onClick={() => handleChange("fixed", "YES")}
            >
              네 ✓
            </button>
            <button
              type="button"
              className={`p-3 border-2 rounded-xl w-full transition-colors ${
                mungJoinForm.fixed === "NO"
                  ? "bg-red-100 border-red-300 text-red-600 font-medium"
                  : "bg-white border-gray-200 text-gray-500"
              }`}
              onClick={() => handleChange("fixed", "NO")}
            >
              아니오 ✗
            </button>
          </div>
        </div>

        {/* 제출 버튼 */}
        <div className="mt-8">
          <button
            type="submit"
            className="w-full p-4 bg-pink-500 hover:bg-pink-600 text-white font-medium rounded-xl shadow-md transition-colors flex items-center justify-center"
          >
            <span className="mr-2">마이뭉 등록 완료</span>
            <span role="img" aria-label="paw">
              🐾
            </span>
          </button>
        </div>
      </form>

      {/* Snackbar 메시지 */}
      {openSnackbar && (
        <div className="mt-4 p-1.5 bg-green-500 text-white text-center rounded-md">
          {snackbarMessage}
        </div>
      )}
    </div>
  );
}
