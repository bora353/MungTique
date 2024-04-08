const { createProxyMiddleware } = require("http-proxy-middleware");

// TODO : 환경변수로 변경
module.exports = function (app) {
  app.use(
    "/api",
    createProxyMiddleware({
      target: "http://localhost:8082/api",
      changeOrigin: true,
    })
  );
};
