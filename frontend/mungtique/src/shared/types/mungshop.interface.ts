export interface MungShop {
  mungShopId?: number;
  latitude: number;
  longitude: number;
  storeName: string;
  storeAddress: string;
  breeds: string;
  businessHours: string;
  closingDays: string;
  filePath: string;
}

export interface MungShopLike {
  mungShopLikeId: number;
  mungShop: MungShop;
  userId: number;
}
