import { RegisterDogDTO, UserDog } from "../types/mungjoin.interface";
import { UserEntity } from "../types/user.interface";
import { api } from "./apiInterceptor";

export const dogApi = {
  registerDog: (mungJoinDTO: RegisterDogDTO) =>
    api().post<UserEntity>("/dog-service/dogs", mungJoinDTO),

  getMyDogs: (userId: number) =>
    api().get<UserDog[]>(`/dog-service/dogs/${userId}`),

  uploadDogImage: (dogId: number, file: File) => {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("dogId", dogId.toString());

    return api().post(`/dog-service/dogs/upload-image`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
};
