import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import HomeAppBar from "./views/main/HomeAppBar";
import FindInfoContainer from "./views/member/findinfo/FindInfoContainer";
import JoinContainer from "./views/member/join/JoinContainer";
import JoinSuccessContainer from "./views/member/join/JoinSuccessContainer";
import { useAuthInit, useAuthStore } from "./shared/store/login.store";
import LoginContainer from "./views/member/login/LoginContainer";
import OAuth2RedirectPage from "./views/member/login/OAuth2RedirectPage";
import MyMungJoinContainer from "./views/member/mymung/MyMungJoinContainer";
import MyMungUpdateContainer from "./views/member/mymung/MyMungUpdateContainer";
import MyPageContainer from "./views/member/mypage/MyPageContainer";
import { ReactNode } from "react";
import LoadingScreen from "./views/common/LoadingScreen";
import NotFound from "./views/common/NotFound";
import MainContainer from "./views/main/MainContainer";
import MungshopContainer from "./views/mungshop/MungshopContainer";
import PaymentCompleteContainer from "./views/payment/PaymentCompleteContainer";
import PaymentContainer from "./views/payment/PaymentContainer";
import ReservationContainer from "./views/reservation/ReservationContainer";
import ReservationMungContainer from "./views/reservation/ReservationMungContainer";
import ReservationConfirmContainer from "./views/reservation/ReservationConfirmContainer";

const PrivateRoute = ({ children }: { children: ReactNode }) => {
  const { isLocalLogin, isOauth2Login } = useAuthStore();
  const isAuthenticated = isLocalLogin || isOauth2Login;

  if (!isAuthenticated) {
    return <Navigate to="/login" />;
  }

  return <>{children}</>;
};

function App() {
  useAuthInit();
  const { isInitialized } = useAuthStore();

  console.log("initialized : ", isInitialized);

  if (!isInitialized) {
    return <LoadingScreen />;
  }

  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeAppBar />}>
            {/* 인증이 필요하지 않은 페이지 */}
            <Route path="/" element={<MainContainer />} />
            <Route path="/login" element={<LoginContainer />} />
            <Route path="/join" element={<JoinContainer />} />
            <Route path="/joinsuccess" element={<JoinSuccessContainer />} />
            <Route path="/mungshop" element={<MungshopContainer />} />
            <Route path="/oauth2/redirect" element={<OAuth2RedirectPage />} />

            {/* 인증이 필요한 페이지 */}
            <Route
              path="/findinfo"
              element={
                <PrivateRoute>
                  <FindInfoContainer />
                </PrivateRoute>
              }
            />
            <Route
              path="/mypage"
              element={
                <PrivateRoute>
                  <MyPageContainer />
                </PrivateRoute>
              }
            />
            <Route
              path="/mymung/register"
              element={
                <PrivateRoute>
                  <MyMungJoinContainer />
                </PrivateRoute>
              }
            />
            <Route
              path="/mymung/:dogId"
              element={
                <PrivateRoute>
                  <MyMungUpdateContainer />
                </PrivateRoute>
              }
            />
            <Route
              path="/reservation"
              element={
                <PrivateRoute>
                  <ReservationContainer />
                </PrivateRoute>
              }
            />
            <Route
              path="/reservation-mung"
              element={
                <PrivateRoute>
                  <ReservationMungContainer />
                </PrivateRoute>
              }
            />
            <Route
              path="/reservation-confirm"
              element={
                <PrivateRoute>
                  <ReservationConfirmContainer />
                </PrivateRoute>
              }
            />
            <Route
              path="/payment"
              element={
                <PrivateRoute>
                  <PaymentContainer />
                </PrivateRoute>
              }
            />
            <Route
              path="/payment/complete"
              element={
                <PrivateRoute>
                  <PaymentCompleteContainer />
                </PrivateRoute>
              }
            />

            <Route path="*" element={<NotFound />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
