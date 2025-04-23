import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import HomeAppBar from "./HomeAppBar";
import MungshopContainer from "./views/care/main/MungshopContainer";
import PaymentCompleteContainer from "./views/care/payment/PaymentCompleteContainer";
import PaymentContainer from "./views/care/payment/PaymentContainer";
import ReservationConfirmContainer from "./views/care/reservation/ReservationConfirmContainer";
import ReservationContainer from "./views/care/reservation/ReservationContainer";
import ReservationMungContainer from "./views/care/reservation/ReservationMungContainer";
import FindInfoContainer from "./views/member/findinfo/FindInfoContainer";
import JoinContainer from "./views/member/join/JoinContainer";
import JoinSuccessContainer from "./views/member/join/JoinSuccessContainer";
import {
  useAuthInit,
  useAuthStore,
} from "./views/member/login/hook/login.store";
import LoginContainer from "./views/member/login/LoginContainer";
import OAuth2RedirectPage from "./views/member/login/OAuth2RedirectPage";
import MyMungJoinContainer from "./views/member/mymung/MyMungJoinContainer";
import MyMungUpdateContainer from "./views/member/mymung/MyMungUpdateContainer";
import MyPageContainer from "./views/member/mypage/MyPageContainer";
import LoadingScreen from "./views/mungtiqueMain/LoadingScreen";
import MainContainer from "./views/mungtiqueMain/MainContainer";
import NotFound from "./views/mungtiqueMain/NotFound";

function App() {
  useAuthInit();
  const { isLocalLogin, isOauth2Login, isInitialized } = useAuthStore();
  const isAuthenticated = isLocalLogin || isOauth2Login;

  console.log("login status : ", isAuthenticated);
  console.log("initialized : ", isInitialized);

  if (!isInitialized) {
    return <LoadingScreen />;
  }

  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeAppBar />}>
            {/* <Route path="*" element={<Navigate to="/login" />} /> */}
            <Route path="/" element={<MainContainer />} />
            <Route path="/login" element={<LoginContainer />} />
            <Route path="/join" element={<JoinContainer />} />
            <Route path="/mungshop" element={<MungshopContainer />} />
            <Route path="/joinsuccess" element={<JoinSuccessContainer />} />

            <Route path="/oauth2/redirect" element={<OAuth2RedirectPage />} />
            {isAuthenticated ? (
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
                <Route path="/reservation" element={<ReservationContainer />} />
                <Route
                  path="/reservation-mung"
                  element={<ReservationMungContainer />}
                />
                <Route
                  path="/reservation-confirm"
                  element={<ReservationConfirmContainer />}
                />
                <Route path="/payment" element={<PaymentContainer />} />
                <Route
                  path="/payment/complete"
                  element={<PaymentCompleteContainer />}
                />
                {/* <Route
                  path="/mungimage/:dogId"
                  element={<MyMungImageUploadContainer />}
                /> */}
              </>
            ) : (
              <Route path="/login" element={<Navigate to="/login" />} />
            )}
            <Route path="*" element={<NotFound />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
