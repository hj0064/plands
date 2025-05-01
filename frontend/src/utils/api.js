import axios from 'axios';

// axios 인스턴스 생성
const api = axios.create({
  baseURL: 'http://localhost:8081', // Spring Boot 서버 주소
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터: 모든 요청 전에 Authorization 헤더에 토큰 추가
api.interceptors.request.use(
  (config) => {
    // 로컬스토리지나 Vuex에서 토큰을 가져와서 Authorization 헤더에 추가
    const token = localStorage.getItem('authToken'); // 예: 로컬스토리지에서 JWT 토큰 가져오기
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;  // config 객체를 반환하여 요청을 계속 진행
  },
  (error) => {
    return Promise.reject(error);  // 요청 오류가 발생하면 그 오류를 반환
  }
);

// 응답 인터셉터: 서버 응답 처리 (예: 401 에러 처리)
api.interceptors.response.use(
  (response) => {
    return response;  // 정상 응답은 그대로 반환
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      // 토큰 만료 등의 이유로 401 오류가 발생한 경우
      console.log('인증 오류 발생! 로그인 페이지로 이동합니다.');
      // 예: 로그인 페이지로 리다이렉트
      window.location.href = '/login';
    }
    return Promise.reject(error);  // 오류를 그대로 반환
  }
);

export default api;
