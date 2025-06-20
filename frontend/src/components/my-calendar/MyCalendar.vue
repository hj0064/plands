<template>
  <div>
    calendar component
  </div>
</template>

<script>
export default {
  name: 'MyCalendar',
  methods: {
    // CalendarView에서 직접적으로 처리할 메서드가 있다면 여기에 정의
    // 예: 페이지 전체를 새로고침하는 기능
    refreshPage() {
      console.log('페이지 새로고침');
    }
  },

  // 5. 컴포넌트가 DOM에 마운트된 후 실행될 코드
  mounted() {
    console.log('MyCalendar가 마운트되었습니다.');
    // 초기 데이터 로딩 등 페이지 로드 시 필요한 작업 수행
  },
};
</script>

