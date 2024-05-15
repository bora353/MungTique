import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import HomeAppBar from "./HomeAppBar";
import CareContainer from "./views/care/CareContainer";
import MainContainer from "./views/main/MainContainer";
import FindInfoContainer from "./views/member/findinfo/FindInfoContainer";
import JoinContainer from "./views/member/join/JoinContainer";
import JoinSuccessContainer from "./views/member/join/JoinSuccessContainer";
import LoginContainer from "./views/member/login/LoginContainer";
import MyPageContainer from "./views/member/mypage/MyPageContainer";
import ShopContainer from "./views/shop/ShopContainer";
import { useLoginStore } from "./shared/stores/login.store";

function App() {
  /*  // TODO : zustand로 변경!
  // TODO : 지금 새로고침 해야만 적용이 됨...
  const isLoggedIn = !!localStorage.getItem("access");
  console.log("App.tsx 로그인체크 :" + isLoggedIn); */

  // TODO : 카카오 네이버 로그인은 또 따로 설정해줘야겠다!!
  const { isLogin, setIsLogin } = useLoginStore();
  console.log("현재 로그인 상태:", isLogin);

  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeAppBar />}>
            <Route path="*" element={<Navigate to="/login" />} />
            <Route path="/" element={<MainContainer />} />
            <Route path="/login" element={<LoginContainer />} />
            <Route path="/join" element={<JoinContainer />} />
            <Route path="/care" element={<CareContainer />} />
            <Route path="/shop" element={<ShopContainer />} />

            {isLogin ? (
              <>
                <Route path="/joinsuccess" element={<JoinSuccessContainer />} />
                <Route path="/findinfo" element={<FindInfoContainer />} />
                <Route path="/mypage" element={<MyPageContainer />} />
              </>
            ) : null}
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
