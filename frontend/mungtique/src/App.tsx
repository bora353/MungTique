import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import CareContainer from "./views/care/CareContainer";
import MainContainer from "./views/main/MainContainer";
import JoinContainer from "./views/member/join/JoinContainer";
import LoginContainer from "./views/member/login/LoginContainer";
import ShopContainer from "./views/shop/ShopContainer";
import JoinSuccessContainer from "./views/member/join/JoinSuccessContainer";
import FindInfoContainer from "./views/member/findinfo/FindInfoContainer";
import MyPageContainer from "./views/member/mypage/MyPageContainer";

function App() {
  // TODO : zustand로 변경!
  const isLoggedIn = !!localStorage.getItem("access");
  console.log("App.tsx 로그인체크 :" + isLoggedIn);

  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="*" element={<Navigate to="/login" />} />
          <Route path="/" element={<MainContainer />} />
          <Route path="/login" element={<LoginContainer />} />
          <Route path="/join" element={<JoinContainer />} />
          <Route path="/care" element={<CareContainer />} />
          <Route path="/shop" element={<ShopContainer />} />

          {isLoggedIn ? (
            <>
              <Route path="/joinsuccess" element={<JoinSuccessContainer />} />
              <Route path="/findinfo" element={<FindInfoContainer />} />
              <Route path="/mypage" element={<MyPageContainer />} />
            </>
          ) : null}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
