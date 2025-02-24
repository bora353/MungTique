import { Dayjs } from "dayjs";
import { create } from "zustand";
import { persist } from "zustand/middleware";

interface ReservationStore {
  selectedMungShopId: number | null;
  selectedMungShop: string | null;
  selectedDog: string | null;
  selectedService: string | null;
  selectedDate: Dayjs | null;
  selectedTime: string | null;
  availableTimes: Array<{ dateTime: string; time: string; date: string }>;
  reserveUserName: string | null;
  reserveUserPhone: string | null;
  requestMessage: string | null;

  setSelectedMungShopId: (id: number) => void;
  setSelectedMungShop: (shop: string) => void;
  setSelectedDog: (name: string) => void;
  setSelectedService: (service: string) => void;
  setSelectedDate: (date: Dayjs | null) => void;
  setSelectedTime: (time: string | null) => void;
  setAvailableTimes: (
    times: Array<{ dateTime: string; time: string; date: string }>
  ) => void;
  setReserveUserName: (name: string) => void;
  setReserveUserPhone: (phone: string) => void;
  setRequestMessage: (request: string) => void;
}

export const useReservationStore = create<ReservationStore>()(
  persist(
    (set) => ({
      selectedMungShopId: null,
      selectedMungShop: null,
      selectedDog: null,
      selectedService: null,
      selectedDate: null,
      selectedTime: null,
      availableTimes: [],
      reserveUserName: null,
      reserveUserPhone: null,
      requestMessage: null,
      setSelectedMungShopId: (id) => set({ selectedMungShopId: id }),
      setSelectedMungShop: (shop) => set({ selectedMungShop: shop }),
      setSelectedDog: (name) => set({ selectedDog: name }),
      setSelectedService: (service) => set({ selectedService: service }),
      setSelectedDate: (date) => set({ selectedDate: date }),
      setSelectedTime: (time) => set({ selectedTime: time }),
      setAvailableTimes: (times) => set({ availableTimes: times }),
      setReserveUserName: (name) => set({ reserveUserName: name }),
      setReserveUserPhone: (phone) => set({ reserveUserPhone: phone }),
      setRequestMessage: (messeage) => set({ requestMessage: messeage }),
    }),
    {
      name: "reservation-storage", // localStorage에 저장될 키 이름
      partialize: (state) => ({
        // 저장할 데이터
        selectedMungShopId: state.selectedMungShopId,
        selectedMungShop: state.selectedMungShop,
        selectedDog: state.selectedDog,
        selectedService: state.selectedService,
        selectedTime: state.selectedTime,  
        reserveUserName: state.reserveUserName,
        reserveUserPhone: state.reserveUserPhone,
        requestMessage: state.requestMessage
      }),
    }
  )
);