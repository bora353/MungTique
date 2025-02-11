/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_GATEWAY_SERVER: string;
  readonly VITE_AUTH_SERVER_NAVER: string;
  readonly VITE_AUTH_SERVER_KAKAO: string;
  readonly VITE_BACKEND_SERVER_USER: string;
  readonly VITE_BACKEND_SERVER_DOG: string;
  readonly VITE_BACKEND_SERVER_MUNGSHOP: string;
  readonly VITE_NAVER_MAP_CLIENT_ID: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
