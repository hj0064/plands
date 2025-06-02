import axios from 'axios';

// axios 인스턴스 생성
const api = axios.create({
  baseURL: 'http://localhost:8081', // Spring Boot 서버 주소
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,  // 쿠키 포함을 허용
});

// 요청 인터셉터: 모든 요청 전에 Authorization 헤더에 토큰 추가
api.interceptors.request.use(
  (config) => {
    // 회원가입 요청에서는 Authorization 헤더를 추가하지 않도록 처리
    if (config.url !== '/api/auth/signup') {
      const token = localStorage.getItem('accessToken');
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
        console.log('🚀 요청에 토큰 추가:', token);
      }
      const refreshToken = localStorage.getItem('refreshToken');
      if (refreshToken) {
        config.headers['Refresh-Token'] = refreshToken;
        console.log('🚀 요청에 Refresh-Token 추가:', refreshToken);
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);  // 요청 오류가 발생하면 그 오류를 반환
  }
);

// 응답 인터셉터: 서버 응답 처리 (예: 401 에러 처리)
api.interceptors.response.use(
  (response) => {
    const authHeader = response.headers['authorization'];
    if (authHeader && authHeader.startsWith('Bearer ')) {
      const token = authHeader.replace('Bearer ', '');
      localStorage.setItem('accessToken', token);
      console.log('✅ accessToken 저장됨:', token);
    }
    // 👉 응답 본문에서 refreshToken이 있으면 저장
    if (response.data && response.data.refreshToken) {
      localStorage.setItem('refreshToken', response.data.refreshToken);
      console.log('🔄 RefreshToken 저장됨:', response.data.refreshToken);
    }

    return response;
  },
  async (error) => {
    if (error.response && error.response.status === 401) {
      console.log('🚨 AccessToken 만료됨! RefreshToken으로 새로운 AccessToken을 요청합니다.');

      const refreshToken = localStorage.getItem('refreshToken');
      if (refreshToken) {
        try {
          const refreshResponse = await api.post(
            '/api/auth/refresh-token',
            {},
            {
              headers: {
                ...api.defaults.headers.common,
                'Refresh-Token': refreshToken,
              },
            }
          );

          const newAccessToken = refreshResponse.data.accessToken;
          localStorage.setItem('accessToken', newAccessToken);

          console.log('🔑 새로운 AccessToken 발급됨:', newAccessToken);

          error.config.headers['Authorization'] = `Bearer ${newAccessToken}`;

          if (!error.config._retry) {
            error.config._retry = true;
            return api(error.config);  // axios -> api로 변경
          }
        } catch (refreshError) {
          console.error('RefreshToken으로 AccessToken 갱신 실패:', refreshError);
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
          window.location.href = '/login';
        }
      } else {
        console.error('RefreshToken이 존재하지 않습니다.');
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);


export default api;
