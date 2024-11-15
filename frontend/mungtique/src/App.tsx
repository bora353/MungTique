import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import HomeAppBar from "./HomeAppBar";
import { useAuthStore } from "./views/member/login/hook/login.store";
import CareContainer from "./views/care/main/CareContainer";
import ReservationContainer from "./views/care/reservation/ReservationContainer";
import MainContainer from "./views/mungtiqueMain/MainContainer";
import FindInfoContainer from "./views/member/findinfo/FindInfoContainer";
import JoinContainer from "./views/member/join/JoinContainer";
import JoinSuccessContainer from "./views/member/join/JoinSuccessContainer";
import LoginContainer from "./views/member/login/LoginContainer";
import MyMungImageUploadContainer from "./views/member/mymung/MyMungImageUploadContainer";
import MyMungJoinContainer from "./views/member/mymung/MyMungJoinContainer";
import MyPageContainer from "./views/member/mypage/MyPageContainer";
import ShopContainer from "./views/shop/ShopContainer";

function App() {
  const isLogin = useAuthStore((state) => state.isLogin);
  console.log("login status : ", isLogin);

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
                {<Route path="/mypage" element={<MyPageContainer />} />}
                <Route path="/mymung" element={<MyMungJoinContainer />} />
                {
                  <Route
                    path="/reservation"
                    element={<ReservationContainer />}
                  />
                }
                {<Route path="/mungimage" element={<MyMungImageUploadContainer />} />}
              </>
            ) : null}
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
