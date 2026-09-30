<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>수강신청 시스템</title>
    <link rel="stylesheet" href="./style.css"> 
    <script>
    function openTimetable() {
        document.getElementById("timetableModal").style.display = "block";
    }
    function closeTimetable() {
        document.getElementById("timetableModal").style.display = "none";
    }

    window.onclick = function(e){
        let modal = document.getElementById("timetableModal");
        if(e.target === modal){
            modal.style.display = "none";
        }
    }
    </script>


<style type="text/css">
	/* ================================
   📌 시간표 모달 전체 배경
================================ */
.modal {
    display: none;
    position: fixed;
    z-index: 9999;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.4);
}

/* ================================
   📌 시간표 모달 박스
================================ */
.modal-content {
    background-color: #fff;
    width: 450px;
    margin: 120px auto;
    padding: 20px;
    border-radius: 10px;
    position: relative;
    animation: fadeIn 0.2s ease-in-out;
}

/* 모달 등장 효과 */
@keyframes fadeIn {
    from { opacity: 0; transform: scale(0.95); }
    to { opacity: 1; transform: scale(1); }
}

/* ================================
   📌 닫기 버튼 (X)
================================ */
.close-btn {
    position: absolute;
    right: 12px;
    top: 8px;
    font-size: 22px;
    font-weight: bold;
    cursor: pointer;
    color: #555;
}

.close-btn:hover {
    color: #222;
}

/* ================================
   📌 시간표 그리드 테이블
================================ */
.timetable-grid {
    width: 60%;
    border-collapse: collapse;
    margin-top: 10%;
    margin-left: 20%;
    table-layout: fixed;
}

/* 테이블 스타일 */
.timetable-grid th,
.timetable-grid td {
    border: 1px solid #ccc;
    padding: 8px;
    height: 55px;
    text-align: center;
    vertical-align: middle;
}

.timetable-grid th {
    background: #f2f6ff;
    font-weight: bold;
    color: #333;
}

/* 강의가 들어간 칸 스타일 */
.timetable-grid td {
    background: #ffffff;
    font-size: 13px;
    line-height: 1.2;
}

/* 강의 이름 강조 */
.timetable-grid td b {
    display: block;
    margin-bottom: 2px;
    color: #003366;
}


.timetable{
	background: #4ea9ff;
    color: white;
    padding: 5px 12px;
    border-radius: 5px;
    border: none;
    font-size: 14px;
    cursor: pointer;
    vertical-align: middle;
    display: inline-block;
}

</style>
    
</head>
<body>
    <div class="main-container">
        <aside class="sidebar">
            <div class="logo-area">
                <h3>KANGNAM UNIVERSITY</h3>
            </div>
            <div class="user-info-panel">
			<c:choose>
    			<c:when test="${not empty loglog}">
        			<h4 id="enrollment-header">2025년 웹프로그래밍 수강신청</h4>
        
        			<div class="info-item"><span>이름</span><span>${loglog.NAME}</span></div>
        			<div class="info-item"><span>학번</span><span>${loglog.STUDENT_ID}</span></div>
        			<div class="info-item"><span>학과</span><span>${loglog.MAJOR}</span></div>
        			<div class="info-item"><span>학년</span><span>${loglog.GRADE}</span></div>
        			<div class="info-item"><span>주야</span><span>주간</span></div>
        			<div class="info-item"><span>최대신청학점</span><span>${totalCount}</span> / 19</div>
        			<button class="logout-btn">로그아웃</button>
        
    			</c:when>
    			<c:otherwise>
        			<h4 id="login-error">로그인정보 없음!</h4>
    			</c:otherwise>
			</c:choose>
</div>
        </aside>

        <main class="content-area">
            <div class="tab-menu">
                <button class="tab-btn active">개설강좌신청</button>
            </div>

            <form action="/project/enrollment" method="GET">
    			<input type="hidden" name="action" value="select">
                <div class="search-filter-bar">
                    <label for="searchCourseId">학수번호</label>
                    <input type="text" id="searchCourseId" name="searchCourseId" value="${searchCourseId}" placeholder="예: CS..." style="padding: 5px; border: 1px solid #ccc;">
                    
                    <label for="searchCredits">학점</label>
                    <select id="searchCredits" name="searchCredits">
                        <option value="NO" ${searchCredits == 0 ? 'selected' : ''}>전체</option>
                        <option value="1" ${searchCredits == 1 ? 'selected' : ''}>1학점</option>
                        <option value="2" ${searchCredits == 2 ? 'selected' : ''}>2학점</option>
                        <option value="3" ${searchCredits == 3 ? 'selected' : ''}>3학점</option>
                    </select>
                    
                    <button type="submit" class="search-btn">조회</button>
                </div>
            </form>
            
            <div class="course-list-section">
                <h4 class="section-title">개설강좌신청 목록</h4>
                <table class="course-table">
                    <thead>
                        <tr>
                            <th>이수구분</th><th>학점</th><th>강의시간</th><th>학수번호</th>
                            <th>교과목</th><th>담당교수</th><th>강의평점</th><th>신청</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty availableCourses}">
                                <c:forEach items="${availableCourses}" var="course">
                                    <tr>
                                        <td>${course.division}</td>
                                        <td>${course.credits}</td>
                                        <td>${course.time}</td>
                                        <td>${course.id}</td>
                                        <td>${course.name}</td>
                                        <td>${course.professor}</td>
                                        <td class="star-rating">${course.rating}</td>
                                        <td>
                                            <form action="/project/enrollment?action=applClass" method="post" style="margin: 0;">
                                                <input type="hidden" name="number" value="${sessionScope.userId}">
                                                <input type="hidden" name="division" value="${course.division}">
                                                <input type="hidden" name="credits" value="${course.credits}">
                                                <input type="hidden" name="time" value="${course.time}">
                                                <input type="hidden" name="id" value="${course.id}">
                                                <input type="hidden" name="name" value="${course.name}">
                                                <input type="hidden" name="professor" value="${course.professor}">
                                                <input type="hidden" name="rating" value="${course.rating}">
                                                <button type="submit" class="apply-btn">신청</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr><td colspan="9" class="no-data">개설 강좌가 없습니다.</td></tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <div class="applied-list-section">
                <h4 class="section-title">강좌신청 내역 
                	<span class="badge">신청학점(${totalCount})</span>
                	<button class="timetable" onclick="openTimetable()">시간표</button>
                </h4>
                <table class="applied-table">
                    <thead>
                        <tr>
                            <th>이수구분</th><th>학점</th><th>강의시간</th><th>학수번호</th>
                            <th>교과목</th><th>담당교수</th><th>강의평점</th><th>삭제</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty appliedCourses}">
                                <c:forEach items="${appliedCourses}" var="cs">
                                    <tr>
                                        <td>${cs.division}</td>
                                        <td>${cs.credits}</td>
                                        <td>${cs.time}</td>
                                        <td>${cs.id}</td>
                                        <td>${cs.name}</td>
                                        <td>${cs.professor}</td>
                                        <td class="star-rating">${cs.rating}</td>
                                        <td>
                                            <form action="/project/enrollment?action=deleteClass" method="post" style="margin: 0;">
                                                <input type="hidden" name="number" value="${cs.number }">
                                                <input type="hidden" name="division" value="${cs.division}">
                                                <input type="hidden" name="credits" value="${cs.credits}">
                                                <input type="hidden" name="time" value="${cs.time}">
                                                <input type="hidden" name="id" value="${cs.id}">
                                                <input type="hidden" name="name" value="${cs.name}">
                                                <input type="hidden" name="professor" value="${cs.professor}">
                                                <input type="hidden" name="rating" value="${cs.rating}">
                                                <button type="submit" class="delete-btn">삭제</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr><td colspan="9" class="no-data">개설신청 내역이 없습니다.</td></tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
    
    <!-- 시간표 팝업 -->
	<div id="timetableModal" class="modal">
   		<table class="timetable-grid">
    <thead>
        <tr>
            <th></th>
            <th>월</th>
            <th>화</th>
            <th>수</th>
            <th>목</th>
            <th>금</th>
        </tr>
    </thead>
    <tbody>
        <%
            // 1) (요일:1~5) x (교시:1~5) 시간표 2차원 배열 생성
            String[][] table = new String[6][6];

            // 2) appliedCourses 가져오기
            java.util.List<project.Appl> list = 
                (java.util.List<project.Appl>) request.getAttribute("appliedCourses");

            if (list != null) {
                for (project.Appl c : list) {
                    try {
                        String time = c.getTime();   // 예: "2.3"
                        String[] parts = time.split("\\.");

                        int day = Integer.parseInt(parts[0]);   // 요일
                        int period = Integer.parseInt(parts[1]); // 교시

                        // 요일, 교시가 유효한 범위인지 확인
                        if (day >= 1 && day <= 5 && period >= 1 && period <= 5) {
                            table[period][day] = c.getName() + "<br>(" + c.getProfessor() + ")";
                        }
                    } catch (Exception e) {}
                }
            }

            // 3) 시간표 출력
            for (int i = 1; i <= 5; i++) {
        %>
        <tr>
            <th><%= i %>교시</th>
            <% for (int j = 1; j <= 5; j++) { %>
                <td><%= table[i][j] == null ? "" : table[i][j] %></td>
            <% } %>
        </tr>
        <% } %>
    </tbody>
</table>
   		
    	</div>
	</div>
    
    
    <script src="js/script.js"></script>
</body>
</html>