import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import ContentCutIcon from "@mui/icons-material/ContentCut";
import { FaDog } from "react-icons/fa";

import { AppBar, Box, Button, Typography } from "@mui/material";
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
      <AppBar position="fixed" color="inherit">
        {/* 헤더 */}
        <header className="bg-white shadow-md">
          <div className="container mx-auto px-4 py-3 flex justify-between items-center">
            <RouterLink to="/" className="flex items-center">
              <FaDog className="text-pink-500 text-3xl mr-2" />
              <h1 className="text-2xl font-bold text-gray-800">뭉티끄</h1>
            </RouterLink>
            <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
              {[
                {
                  path: "/mungshop",
                  icon: <ContentCutIcon sx={{ color: "#F7B5D8" }} />,
                  label: "뭉샵",
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
          </div>
        </header>
      </AppBar>
    </ThemeProvider>
  );
}
