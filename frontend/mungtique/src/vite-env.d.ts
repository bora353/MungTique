/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_BACKEND_SERVER: string;
  readonly VITE_NAVER_MAP_CLIENT_ID: string;
  readonly VITE_BACKEND_SERVER_CARE: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
