
export default function LoadingScreen () {
  return (
    <div className="flex items-center justify-center h-screen bg-gray-50">
      <div className="flex flex-col items-center gap-4">
        <div className="relative">
          <div className="w-16 h-16 border-4 border-blue-200 border-solid rounded-full"></div>
          <div className="absolute top-0 w-16 h-16 border-4 border-blue-500 border-solid rounded-full border-t-transparent animate-spin"></div>
        </div>
        <p className="text-lg font-medium text-gray-700">잠시만 기다려주세요...</p>
      </div>
    </div>
  );
};

