import { CookiesProvider } from "react-cookie";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { SnackbarProvider } from "notistack";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

root.render(
  <SnackbarProvider
    maxSnack={3}
    anchorOrigin={{
      vertical: "top",
      horizontal: "center",
    }}
    autoHideDuration={2000}
  >
    <CookiesProvider>
      <App />
    </CookiesProvider>
  </SnackbarProvider>
);
