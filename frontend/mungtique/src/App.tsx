import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import HomeAppBar from "./HomeAppBar";
import { useAuthStore } from "./views/member/login/hook/login.store";
import ReservationContainer from "./views/care/reservation/ReservationContainer";
import MainContainer from "./views/mungtiqueMain/MainContainer";
import FindInfoContainer from "./views/member/findinfo/FindInfoContainer";
import JoinContainer from "./views/member/join/JoinContainer";
import JoinSuccessContainer from "./views/member/join/JoinSuccessContainer";
import LoginContainer from "./views/member/login/LoginContainer";
import MyMungJoinContainer from "./views/member/mymung/MyMungJoinContainer";
import MyPageContainer from "./views/member/mypage/MyPageContainer";
import ShopContainer from "./views/shop/ShopContainer";
import MungshopContainer from "./views/care/main/MungshopContainer";
import MyMungUpdateContainer from "./views/member/mymung/MyMungUpdateContainer";
import ReservationConfirmContainer from "./views/care/reservation/ReservationConfirmContainer";
import ReservationMungContainer from "./views/care/reservation/ReservationMungContainer";

function App() {
  const isLogin = useAuthStore((state) => state.isLogin);
  // console.log("login status : ", isLogin);

  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeAppBar />}>
            <Route path="*" element={<Navigate to="/login" />} />
            <Route path="/" element={<MainContainer />} />
            <Route path="/login" element={<LoginContainer />} />
            <Route path="/join" element={<JoinContainer />} />
            <Route path="/mungshop" element={<MungshopContainer />} />
            <Route path="/shop" element={<ShopContainer />} />
            <Route path="/joinsuccess" element={<JoinSuccessContainer />} />

            {isLogin ? (
              <>
                <Route path="/findinfo" element={<FindInfoContainer />} />
                <Route path="/mypage" element={<MyPageContainer />} />
                <Route
                  path="/mymung/register"
                  element={<MyMungJoinContainer />}
                />
                <Route
                  path="/mymung/:dogId"
                  element={<MyMungUpdateContainer />}
                />
                <Route
                  path="/reservation"
                  element={<ReservationContainer />}
                />
                <Route
                  path="/reservation-mung"
                  element={<ReservationMungContainer />}
                />
                <Route
                  path="/reservation-confirm"
                  element={<ReservationConfirmContainer />}
                />
                {/* <Route
                  path="/mungimage/:dogId"
                  element={<MyMungImageUploadContainer />}
                /> */}
              </>
            ) : (
              <Route path="*" element={<Navigate to="/login" />} />
            )}
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
