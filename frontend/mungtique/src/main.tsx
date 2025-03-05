import { QueryClient } from "@tanstack/react-query";
import { CookiesProvider } from "react-cookie";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const queryClient = new QueryClient();

// TODO : SnackbarProvider도 여기에 적용할 수 있으니 알아볼 것

root.render(
  //  <React.StrictMode>
  // <QueryClientProvider client={queryClient}>
  <CookiesProvider>
    <App />
  </CookiesProvider>
  // </QueryClientProvider>
  //  </React.StrictMode>
);
