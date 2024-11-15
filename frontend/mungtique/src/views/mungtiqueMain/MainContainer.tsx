import { useState } from "react";
import { api } from "../../shared/api/ApiInterceptor";
import axios from "axios";

export default function MainContainer() {
  // TODO : 로그인 시에만 로그아웃이 가능하게 변경
  /*   const test = async () => {
    try {
      const response = await api().get("/care/mungshops");

      console.log("/user/admin : " + response.data);
    } catch (error) {
      console.error("Login failed", error);
    }
  };
 */

  return (
    <div>
      <div className="flex justify-center items-center min-h-screen">
        <h1 className="text-xl">메인 페이지</h1>
        {/*         <button onClick={test}>Test</button>
         */}
 
      </div>
    </div>
  );
}
