export interface MungShop {
  mungShopId: number;
  latitude: number;
  longitude: number;
  storeName: string;
  storeAddress: string;
  breeds: string;
  businessHours: string;
  closingDays: string;
  storeImageUrl: string;
}

export interface MungShopLike {
  mungShopLikeId: number;
  mungShop: MungShop;
  userId: number;
}
