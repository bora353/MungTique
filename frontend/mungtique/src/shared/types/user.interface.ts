export interface UserEntity {
  id: number;
  username: string;
  password: string;

  email: string;
  phone: string;
}

export interface UserDto {
  username: string;
  email: string;
  phone: string;
}