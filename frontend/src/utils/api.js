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
    // 401 에러가 발생한 경우
    if (error.response && error.response.status === 401) {
      console.log('🚨 AccessToken 만료됨! RefreshToken으로 새로운 AccessToken을 요청합니다.');

      // RefreshToken을 사용하여 새로운 AccessToken을 발급받는 요청
      const refreshToken = localStorage.getItem('refreshToken');
      if (refreshToken) {
        try {
          // RefreshToken을 사용하여 새로운 AccessToken 요청
          const refreshResponse = await axios.post('/api/auth/refresh-token', {}, {
            headers: { 'Refresh-Token': refreshToken }
          });

          const newAccessToken = refreshResponse.data.accessToken;
          localStorage.setItem('accessToken', newAccessToken); // 새로운 AccessToken 저장

          console.log('🔑 새로운 AccessToken 발급됨:', newAccessToken);

          // 실패한 원래 요청을 새로운 AccessToken을 포함하여 재요청
          error.config.headers['Authorization'] = `Bearer ${newAccessToken}`;
          return axios(error.config); // 원래 요청 재시도
        } catch (refreshError) {
          console.error('RefreshToken으로 AccessToken 갱신 실패:', refreshError);
          // RefreshToken 갱신 실패 시 로그아웃 처리
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
          window.location.href = '/login'; // 로그인 페이지로 리다이렉트
        }
      } else {
        console.error('RefreshToken이 존재하지 않습니다.');
        // RefreshToken이 없는 경우 로그인 페이지로 이동
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export default api;
