const { createProxyMiddleware } = require("http-proxy-middleware");

// TODO : 환경변수로 변경
module.exports = function (app) {
  app.use(
    "/api",
    createProxyMiddleware({
      target: import.meta.env.VITE_BACKEND_SERVER,
      changeOrigin: true,
    })
  );
};
