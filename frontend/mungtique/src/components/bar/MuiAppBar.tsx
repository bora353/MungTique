import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import ContentCutIcon from "@mui/icons-material/ContentCut";
import StorefrontIcon from "@mui/icons-material/Storefront";
import {
  AppBar,
  Box,
  Button,
  Container,
  Toolbar,
  Typography
} from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Link as RouterLink } from "react-router-dom";

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
      fontFamily: "'KCC-Hanbit', sans-serif",
    },
  });

  return (
    <ThemeProvider theme={customTheme}>
      <AppBar
        position="sticky"
        color="inherit"
        sx={{ backgroundColor: "#FAF9F1" }}
      >
        <Container maxWidth="lg">
          <Toolbar disableGutters>
            {/* 로고 및 홈 버튼 */}
            <Box sx={{ flexGrow: 1, display: "flex", alignItems: "center" }}>
              <RouterLink to="/">
                <img
                  src="/images/logo.png"
                  alt="뭉티끄"
                  width="140"
                  height="80"
                />
              </RouterLink>
            </Box>

            {/* 네비게이션 메뉴 */}
            <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
              {[
                {
                  path: "/mungshop",
                  icon: <ContentCutIcon sx={{ color: "#F7B5D8" }} />,
                  label: "뭉샵",
                },
                {
                  path: "/shop",
                  icon: <StorefrontIcon sx={{ color: "skyblue" }} />,
                  label: "스토어",
                },
                {
                  path: "/mypage",
                  icon: <AccountCircleIcon sx={{ color: "#AF69EE" }} />,
                  label: "마이페이지",
                },
              ].map((item, index) => (
                <Button
                  key={index}
                  component={RouterLink}
                  to={item.path}
                  sx={{
                    fontSize: "1rem",
                    color: "#333",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    textTransform: "none",
                    "&:hover": { color: "#007FFF", transform: "scale(1.1)" },
                    transition: "all 0.2s ease-in-out",
                  }}
                >
                  {item.icon}
                  <Typography variant="caption" sx={{ mt: 0.5 }}>
                    {item.label}
                  </Typography>
                </Button>
              ))}
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
    </ThemeProvider>
  );
}
