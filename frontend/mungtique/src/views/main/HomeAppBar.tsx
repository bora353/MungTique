import { Outlet } from "react-router-dom";
import MuiAppBar from "../../components/bar/MuiAppBar";

export default function HomeAppBar() {
  return (
    <div className="min-h-screen flex flex-col overflow-hidden pt-[80px]">
      <MuiAppBar />
      <main className="flex-1 overflow-y-auto">
        <Outlet />
      </main>
    </div>
  );
}
