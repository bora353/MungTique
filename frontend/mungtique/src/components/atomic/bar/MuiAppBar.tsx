import { Link as RouterLink } from "react-router-dom";
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
      <AppBar position="static" color="inherit">
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
              <Typography
                variant="h6"
                noWrap
                component="div"
                sx={{ flexGrow: 1, color: "primary.main" }}
              >
                뭉티끄
              </Typography>
            </Box>
            {["/", "/reservation", "/shopping", "/mypage"].map(
              (path, index) => (
                <Button
                  key={index}
                  color="inherit"
                  component={RouterLink}
                  to={path}
                  sx={{ color: "primary.main" }}
                >
                  {["홈", "예약", "쇼핑", "마이뭉"][index]}
                </Button>
              )
            )}
          </Toolbar>
        </Container>
      </AppBar>
    </ThemeProvider>
  );
}
