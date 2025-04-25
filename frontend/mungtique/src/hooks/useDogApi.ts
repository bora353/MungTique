import { dogApi } from "../shared/api/dog.api";
import { RegisterDogDTO } from "../shared/types/mungjoin.interface";

export const useDogApi = () => {
  const registerDog = async (dogInfo: RegisterDogDTO) => {
    try {
      const { data } = await dogApi.registerDog(dogInfo);
      return data;
    } catch (error) {
      console.error("강아지 등록 실패:", error);
      return {};
    }
  };

  const getMyDogs = async (userId: number) => {
    try {
      const { data } = await dogApi.getMyDogs(userId);
      return data;
    } catch (error) {
      console.error("내 강아지 목록 조회 실패:", error);
      return [];
    }
  };

  const uploadDogImage = async (dogId: number, file: File) => {
    try {
      await dogApi.uploadDogImage(dogId, file);
      return true;
    } catch (error) {
      console.error("Image upload failed:", error);
      return false;
    }
  };


  return { registerDog, getMyDogs, uploadDogImage };
};
