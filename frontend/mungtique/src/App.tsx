import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import LoginContainer from "./views/member/login/LoginContainer";
import SignUpContainer from "./views/member/signup/SignUpContainer";
import SignUpSuccessContainer from "./views/member/signup/SignUpSuccessContainer";
import FindInfoContainer from "./views/member/findinfo/FindInfoContainer";
import MainContainer from "./views/main/MainContainer";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainContainer />} />
          <Route path="/login" element={<LoginContainer />} />
          <Route path="/signup" element={<SignUpContainer />} />
          <Route path="/signupsuccess" element={<SignUpSuccessContainer />} />
          <Route path="/findinfo" element={<FindInfoContainer />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
