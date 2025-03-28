import { useState } from "react";

interface SidebarProps {
  setSelectedMenu: (menu: string) => void;
}

export default function Sidebar({ setSelectedMenu }: SidebarProps) {
  const [activeMenu, setActiveMenu] = useState<string>("home");

  const handleMenuClick = (menu: string) => {
    setActiveMenu(menu);
    setSelectedMenu(menu);
  };

  return (
    <aside className="w-64 bg-white shadow-md p-4 min-h-screen">
      <nav>
        <ul className="space-y-2">
          <li
            className={`p-2 rounded cursor-pointer ${
              activeMenu === "home"
                ? "bg-gray-200 font-bold"
                : "hover:bg-gray-100"
            }`}
            onClick={() => handleMenuClick("home")}
          >
            🐶 뭉티끄
          </li>
          <li
            className={`p-2 rounded cursor-pointer ${
              activeMenu === "reservation"
                ? "bg-gray-200 font-bold"
                : "hover:bg-gray-100"
            }`}
            onClick={() => handleMenuClick("reservation")}
          >
            📦 예약내역
          </li>
          <li
            className={`p-2 rounded cursor-pointer ${
              activeMenu === "favorites"
                ? "bg-gray-200 font-bold"
                : "hover:bg-gray-100"
            }`}
            onClick={() => handleMenuClick("favorites")}
          >
            ⭐ 단골샵(찜하기)
          </li>
          <li
            className={`p-2 rounded cursor-pointer ${
              activeMenu === "points"
                ? "bg-gray-200 font-bold"
                : "hover:bg-gray-100"
            }`}
            onClick={() => handleMenuClick("points")}
          >
            🏅 포인트
          </li>
        </ul>
      </nav>
    </aside>
  );
}
