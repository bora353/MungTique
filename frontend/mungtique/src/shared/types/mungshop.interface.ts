export interface MungShop {
  id?: number;
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
  mungShopId: number;
  userId: number;
}
