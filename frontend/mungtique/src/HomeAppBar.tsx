import { Outlet } from "react-router-dom";
import MuiAppBar from "./components/atomic/bar/MuiAppBar";

export default function HomeAppBar() {
  return (
    <div>
      <MuiAppBar />
      <Outlet />
    </div>
  );
}
