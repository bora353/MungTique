import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import LoginContainer from "./views/member/login/LoginContainer";
import FindInfoContainer from "./views/member/findinfo/FindInfoContainer";
import MainContainer from "./views/main/MainContainer";
import JoinSuccessContainer from "./views/member/join/JoinSuccessContainer";
import JoinContainer from "./views/member/join/JoinContainer";
import MyPageContainer from "./views/member/mypage/MyPageContainer";
import ShopContainer from "./views/shop/ShopContainer";
import CareContainer from "./views/care/CareContainer";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainContainer />} />
          <Route path="/login" element={<LoginContainer />} />
          <Route path="/join" element={<JoinContainer />} />
          <Route path="/joinsuccess" element={<JoinSuccessContainer />} />
          <Route path="/findinfo" element={<FindInfoContainer />} />
          <Route path="/mypage" element={<MyPageContainer />} />
          <Route path="/care" element={<CareContainer />} />
          <Route path="/shop" element={<ShopContainer />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
