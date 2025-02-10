import { Link, Link as RouterLink } from "react-router-dom";
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Container,
  Box,
  IconButton,
} from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import ContentCutIcon from "@mui/icons-material/ContentCut";
import StorefrontIcon from "@mui/icons-material/Storefront";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

export default function MuiAppBar() {
  const customTheme = createTheme({
    palette: {
      background: { default: "#ffffff" },
      primary: { main: "#007FFF" },
    },
    components: {
      MuiAppBar: {
        styleOverrides: {
          colorInherit: {
            backgroundColor: "#ffffff",
            color: "#007FFF",
            boxShadow: "none",
            borderBottom: "1px solid #e0e0e0",
          },
        },
      },
    },
    typography: {
      fontFamily: "'KCC-Hanbit', sans-serif", // 글꼴 지정
    },
  });

  return (
    <ThemeProvider theme={customTheme}>
      <AppBar
        position="static"
        color="inherit"
        sx={{ backgroundColor: "#FAF9F1" }}
      >
        <Container maxWidth="lg">
          <Toolbar disableGutters>
            <Box sx={{ flexGrow: 1, display: "flex", alignItems: "center" }}>
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="open drawer"
                sx={{ mr: 2 }}
              ></IconButton>
              <div>
                <Link to="/">
                  <img
                    src="/images/logo.png"
                    alt="뭉티끄"
                    width="130"
                    height="80"
                  />
                </Link>
              </div>
            </Box>
            {["/mungshop", "/shop", "/mypage"].map((path, index) => (
              <Button
                key={index}
                color="inherit"
                component={RouterLink}
                to={path}
                sx={{ fontSize: "1rem", color: "#333" }}
              >
                {index === 0 ? (
                  <ContentCutIcon sx={{ color: "#F7B5D8" }} />
                ) : index === 1 ? (
                  <StorefrontIcon sx={{ color: "skyblue" }} />
                ) : (
                  <AccountCircleIcon sx={{ color: "#AF69EE" }} />
                )}
              </Button>
            ))}
          </Toolbar>
        </Container>
      </AppBar>
    </ThemeProvider>
  );
}
