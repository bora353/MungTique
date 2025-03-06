export default function LoginOptions() {
  const naverUrl = import.meta.env.VITE_AUTH_SERVER_NAVER;
  const kakaoUrl = import.meta.env.VITE_AUTH_SERVER_KAKAO;

  const onNaverLogin = () => {
    window.location.href = naverUrl;
  };
  const onKakaoLogin = () => {
    window.location.href = kakaoUrl;
  };

  return (
      <div className="flex justify-center my-1 mt-3">
        <div className="mx-1">
          <img
            src="/images/naver_login.png"
            alt="Naver Login"
            onClick={onNaverLogin}
            style={{ cursor: "pointer", width: "170px", height: "50px" }}
          />
        </div>
        <div className="mx-1">
          <img
            src="/images/kakao_login.png"
            alt="Kakao Login"
            onClick={onKakaoLogin}
            style={{ cursor: "pointer", width: "170px", height: "50px" }}
          />
        </div>
      </div>
  );
}
