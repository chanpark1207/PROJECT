// script.js (클라이언트 측 UI 및 초기화 로직만 남음)



document.addEventListener('DOMContentLoaded', () => {
    // 1. 신청/삭제 로직은 이제 서버 (Servlet)가 처리하므로,
    //    handleEnrollment, handleDeletion 함수 및 이벤트 리스너는 삭제합니다.
    
    // 2. 신청학점 뱃지 업데이트는 서버 측에서 totalCredits을 계산하여 
    //    index.jsp에 직접 출력하므로, 이 함수도 필요하지 않습니다.
    
    // 3. 만약 '로그아웃' 버튼에 대한 클라이언트 측 로직이 필요하다면 여기에 추가합니다.
    const logoutBtn = document.querySelector('.logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            // 실제 로그아웃 서블릿으로 이동하는 로직을 추가
            window.location.href = 'logout.jsp'; // 예시
        });
    }
});