import { Dayjs } from "dayjs";
import { create } from "zustand";

interface ReservationStore {
  selectedMungShopId: number | null;
  selectedMungShop: string | null;
  selectedDog: string | null;
  selectedService: string | null;
  selectedDate: Dayjs | null;
  selectedTime: string | null;
  availableTimes: Array<{ dateTime: string; time: string; date: string }>;
  setSelectedMungShopId: (id: number) => void;
  setSelectedMungShop: (shop: string) => void;
  setSelectedDog: (name: string) => void;
  setSelectedService: (service: string) => void;
  setSelectedDate: (date: Dayjs | null) => void;
  setSelectedTime: (time: string | null) => void;
  setAvailableTimes: (
    times: Array<{ dateTime: string; time: string; date: string }>
  ) => void;
}

export const useReservationStore = create<ReservationStore>((set) => ({
  selectedMungShopId: null,
  selectedMungShop: null,
  selectedDog: null,
  selectedService: null,
  selectedDate: null,
  selectedTime: null,
  availableTimes: [],
  setSelectedMungShopId: (id) => set({ selectedMungShopId: id }),
  setSelectedMungShop: (shop) => set({ selectedMungShop: shop }),
  setSelectedDog: (name) => set({ selectedDog: name }),
  setSelectedService: (service) => set({ selectedService: service }),
  setSelectedDate: (date) => set({ selectedDate: date }),
  setSelectedTime: (time) => set({ selectedTime: time }),
  setAvailableTimes: (times) => set({ availableTimes: times }),
}));
