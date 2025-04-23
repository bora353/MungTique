import { useNavigate } from "react-router-dom";

export default function NotFound() {
  const navigate = useNavigate();

  const handleGoBack = () => {
    navigate(-1);
  };

  const handleGoHome = () => {
    navigate("/");
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-b from-blue-50 to-purple-50 px-4">
      <div className="text-center">
        <h1 className="text-9xl font-extrabold text-transparent bg-clip-text bg-gradient-to-r from-purple-400 to-pink-600">
          404
        </h1>
        <div className="animate-bounce mt-4">
          <svg
            className="mx-auto h-16 w-16 text-purple-500"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"
            />
          </svg>
        </div>
        <p className="text-2xl font-semibold md:text-3xl mt-4 mb-8 text-gray-700">
          페이지를 찾을 수 없습니다
        </p>
        <p className="text-gray-500 mb-8">
          찾으시는 페이지가 존재하지 않거나 이동되었을 수 있습니다.
        </p>
        <div className="flex flex-col space-y-3 sm:flex-row sm:space-y-0 sm:space-x-3 justify-center">
          <button
            onClick={handleGoBack}
            className="px-6 py-2 text-sm font-semibold rounded-md shadow-sm text-white bg-purple-500 hover:bg-purple-600 focus:outline-none focus:ring-2 focus:ring-purple-300 transition duration-300"
          >
            이전 페이지로
          </button>
          <button
            onClick={handleGoHome}
            className="px-6 py-2 text-sm font-semibold rounded-md border border-purple-200 text-purple-500 hover:text-purple-700 hover:border-purple-300 focus:outline-none focus:ring-2 focus:ring-purple-300 transition duration-300"
          >
            홈으로 이동
          </button>
        </div>
      </div>
    </div>
  );
}
