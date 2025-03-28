import { useNavigate } from "react-router-dom";

export default function JoinSuccessContainer() {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 flex flex-col justify-center items-center p-6">
      <div className="w-full max-w-md">
        <div className="bg-white rounded-2xl shadow-2xl overflow-hidden">
          <div className="p-6 flex justify-center items-center">
            <img 
              src="/images/logo2.png" 
              alt="뭉티끄" 
              className="w-40 h-auto object-contain"
            />
          </div>
          
          <div className="p-8 text-center">            
            <h1 className="text-3xl font-bold text-gray-800 mb-4">회원가입 완료! 🎉</h1>
            <p className="text-lg text-gray-600 leading-relaxed">
              뭉티끄 회원이 되신 것을 진심으로 축하드립니다. 
            </p>
            
            <button className="mt-6 w-full bg-blue-500 text-white py-3 rounded-lg hover:bg-blue-600 transition-colors duration-300"
            onClick={()=> navigate("/login")}>
              로그인
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}