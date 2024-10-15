const path = require("path");

// vue.config.js
module.exports = {
    devServer: {
        port: 8787,  // 개발 서버 포트 설정
        proxy: {
            '/api': {
                target: 'http://localhost:8081',  // 백엔드 API 서버 주소 (Spring Boot)
                changeOrigin: true,
            },
        },
    },
    // 다른 설정 추가 가능
};
