import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { CookiesProvider } from "react-cookie";
import { ThemeProvider } from "@emotion/react";
import { theme } from "./theme.tsx";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const queryClient = new QueryClient();

// TODO : theme은 중복된게 이미 있을 수도 있으니 확인하고 적용할 것
// TODO : SnackbarProvider도 여기에 적용할 수 있으니 알아볼 것

root.render(
  //  <React.StrictMode>
  //<ThemeProvider theme={theme}>
  <QueryClientProvider client={queryClient}>
    <CookiesProvider>
      <App />
    </CookiesProvider>
  </QueryClientProvider>
  // </ThemeProvider>
  //  </React.StrictMode>
);
