import { Outlet } from "react-router-dom";
import MuiAppBar from "./components/bar/MuiAppBar";

export default function HomeAppBar() {
  return (
    <div>
      <MuiAppBar />
      <main style={{ marginTop: "80px" }}>
        <Outlet />
      </main>
    </div>
  );
}
