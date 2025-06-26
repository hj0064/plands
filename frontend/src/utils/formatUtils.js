// 날짜 전용 유틸 (Date → yyyy-MM-dd)
export function formatDate(dateStr) {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  const y = date.getFullYear()
  const m = String(date.getMonth() + 1).padStart(2, '0')
  const d = String(date.getDate()).padStart(2, '0')
  return `${y}-${m}-${d}`
}

// 날짜 + 시간 포맷 (Date → yyyy-MM-dd HH:mm)
export function formatDateTime(dateStr) {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  const y = date.getFullYear()
  const m = String(date.getMonth() + 1).padStart(2, '0')
  const d = String(date.getDate()).padStart(2, '0')
  const hh = String(date.getHours()).padStart(2, '0')
  const mm = String(date.getMinutes()).padStart(2, '0')
  return `${y}-${m}-${d} ${hh}:${mm}`
}

// 사용자 상태 표시 (ex. ACTIVE → 활성)
export function statusToText(status) {
  const map = {
    ACTIVE: '활성',
    BANNED: '정지',
    WITHDRAW: '탈퇴'
  }
  return map[status] || '알 수 없음'
}

// 사용자 권한 표시 (ex. ROLE_ADMIN → 관리자)
export function roleToText(role) {
  const map = {
    ROLE_USER: '사용자',
    ROLE_ADMIN: '관리자'
  }
  return map[role] || '알 수 없음'
}

// SNS 제공자 표시 (ex. google → 구글)
export function providerToText(provider) {
  const map = {
    google: '구글',
    kakao: '카카오',
    naver: '네이버'
  }
  return map[provider] || '이메일'
}

// 성별 표시 (ex. M → 남성, F → 여성)
export function genderToText(gender) {
  const map = {
    M: '남성',
    F: '여성',
  }
  return map[gender] || '알 수 없음'
}

// 연령대 표시
export function ageGroupToText(age) {
  if (typeof age !== 'number') return '알 수 없음'
  if (age < 10) return '10세 미만'
  if (age >= 10 && age < 100) {
    const decade = Math.floor(age / 10) * 10
    return `${decade}대`
  }
  return '알 수 없음'
}

// 전화번호 포맷 (숫자만 연속된 문자열 → XXX-XXXX-XXXX)
export function formatPhoneNumber(phone) {
  if (!phone) return ''
  const digits = phone.replace(/\D/g, '')
  if (digits.length === 11) {
    return digits.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3')
  } else if (digits.length === 10) {
    return digits.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3')
  }
  return phone
}

// 이메일 마스킹
function maskEmail(email) {
  if (!email) return ''
  const [name, domain] = email.split('@')
  if (name.length <= 2) return email
  return name[0] + '*'.repeat(name.length - 2) + name.slice(-1) + '@' + domain
}

// 퍼센트 포맷
function formatPercent(value) {
  if (isNaN(value)) return ''
  return (value * 100).toFixed(1) + '%'
}

// 금액 포맷
function formatPrice(value) {
  if (isNaN(value)) return ''
  return value.toLocaleString() + '원'
}

