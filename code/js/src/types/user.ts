export type UserCreationInput = {
  name: string;
  email: string;
  password: string;
  avatar: string;
};

export type UserInfo = {
  id: number;
  name: string;
  avatarUrl?: string;
  role: string;
  rank: string;
};

export type UserAvatar = {
  name: string;
  avatarUrl: string;
};
