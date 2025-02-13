export interface MungShop {
  mungShopId: number;
  latitude: number;
  longitude: number;
  storeName: string;
  storeAddress: string;
  breedType: string;
  businessHours: string;
  closingDays: string;
  storeImageUrl: string;
  mungShopPrices: MungShopPrice[];
}

export interface MungShopLike {
  mungShopLikeId: number;
  mungShop: MungShop;
  userId: number;
}
  export interface MungShopPrice {
    mungShopPriceId: number;
    breedType: string;
    serviceType: string;
    price: number;
  }