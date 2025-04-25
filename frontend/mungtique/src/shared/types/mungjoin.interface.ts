export interface UserDog {
  dogId: number;
  imageUrl?: string;

  dogName: string;
  breedType: string;
  weight: number;
  age: number;
  gender: string;
  fixed: string;
  userId: number;
}

export interface RegisterDogDTO {
  imageUrl?: string;

  dogName: string;
  breedType: string;
  weight: number;
  age: number;
  gender: string;
  fixed: string;
  userId: number;
}
