const tabs = ["홈", "가격", "리뷰", "사진"];

interface MapTabMenuProps {
  activeTab: string;
  setActiveTab: (tab: string) => void;
}

export default function MapTabMenu({
  activeTab,
  setActiveTab,
}: MapTabMenuProps) {

  return (
    <div className="flex border-b mt-4">
      {tabs.map((tab) => (
        <button
          key={tab}
          className={`p-3 flex-1 ${
            activeTab === tab
              ? "border-b-2 border-black font-semibold"
              : "text-gray-500"
          }`}
          onClick={() => setActiveTab(tab)}
        >
          {tab}
        </button>
      ))}
    </div>
  );
}
