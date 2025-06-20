<template>
  <div>
    <p></p>
    <p></p>
    <p></p>
    <p></p>
    <p></p>
    <p></p>
    calender
    dfsfeafsdf
    <my-calendar/>
  </div>
</template>

<script>
// 사용할 하위 컴포넌트들을 import 합니다.
import MyCalendar from '@/components/my-calendar/MyCalendar.vue';
// import EventModal from '@/components/EventModal.vue'; // 필요시

export default {
  // 1. 컴포넌트 이름 (선택 사항이지만 유용)
  name: 'CalendarView',

  // 2. 이 컴포넌트 내에서 사용할 다른 컴포넌트들을 등록합니다.
  components: {
    MyCalendar,
    // EventModal // 필요시
  },

  // 3. 컴포넌트의 반응형 데이터를 정의합니다. (함수여야 함)
  data() {
    return {
      // CalendarView에서 전역적으로 관리할 상태가 있다면 여기에 정의
      // 예: 전체 페이지의 로딩 상태 등
      isLoading: false
    };
  },

  // 4. 컴포넌트의 메서드를 정의합니다.
  methods: {
    // CalendarView에서 직접적으로 처리할 메서드가 있다면 여기에 정의
    // 예: 페이지 전체를 새로고침하는 기능
    refreshPage() {
      console.log('페이지 새로고침');
    }
  },

  // 5. 컴포넌트가 DOM에 마운트된 후 실행될 코드
  mounted() {
    console.log('CalendarView가 마운트되었습니다.');
    // 초기 데이터 로딩 등 페이지 로드 시 필요한 작업 수행
  },

  // 다른 옵션들 (computed, watch, props 등)도 필요에 따라 여기에 추가할 수 있습니다.
};
</script>
