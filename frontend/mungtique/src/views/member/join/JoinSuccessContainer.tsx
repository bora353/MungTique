export default function JoinSuccessContainer() {
  return (
    <div className="flex flex-col justify-center items-center mt-20 p-6">
      <div className="relative mb-4">
        <img 
          src="/images/logo2.png" 
          alt="뭉티끄" 
          width="250" 
        />
      </div>
      
      <div className="bg-white rounded-xl shadow-md p-8 text-center max-w-md">
        <h1 className="text-2xl font-bold text-gray-700 mb-3">회원가입 완료! 🎉</h1>
        <p className="text-lg text-gray-600">뭉티끄 회원이 되신 것을 축하드립니다</p>
      </div>
    </div>
  );
}