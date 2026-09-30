<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="project.LoginDao, project.Login" %>
<%
    request.setCharacterEncoding("UTF-8");
    String loginError = "";

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");

        if (userId != null && !userId.trim().isEmpty() &&
            userPw != null && !userPw.trim().isEmpty()) {

            LoginDao dao = new LoginDao();
            boolean isLogin = dao.loginCheck(userId, userPw);

            if (isLogin) {
                Login user = dao.getUserById(userId);
				
                
                session.setAttribute("STUDENT_ID", user.getSTUDENT_ID());
                session.setAttribute("NAME", user.getNAME());
                session.setAttribute("MAJOR", user.getMAJOR());
                session.setAttribute("GRADE", user.getGRADE());
                
				
                response.sendRedirect("/project/enrollment?action=login");
                return;
            } else {
                loginError = "아이디 또는 비밀번호가 틀렸습니다.";
            }
        } else {
            loginError = "아이디와 비밀번호를 모두 입력하세요.";
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
* { box-sizing: border-box; }
body {
    background: linear-gradient(135deg, #f6d365, #fda085);
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
h2 { color: #fff; font-size: 56px; margin-bottom: 50px; }
input { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 5px; }
button { 
    width: 100%; padding: 10px; background: #f57c00; border: none; border-radius: 5px; 
    margin: 10px 0; color: white; font-size: 15px; cursor: pointer;
}
button:hover { background: #e65100; }
.reset-btn { background: darkred; }
.reset-btn:hover { background: red; }
p.error { color: red; text-align: center; }
</style>
</head>
<body>

<h2>로그인</h2>
<hr>

<div id="loginform">
    <% if (!loginError.isEmpty()) { %>
        <p class="error"><%= loginError %></p>
    <% } %>

    <form action="/project/enrollment?action=login" method="post">
        <label>아이디</label><br>
        <input type="text" name="userId" required><br>

        <label>비밀번호</label><br>
        <input type="password" name="userPw" required><br>

        <button type="submit">로그인</button>
    </form>

    <form action="/project/enrollment?action=register" method="post">
        <button type="submit">회원가입</button>
    </form>

    <form action="/project/enrollment?action=dbDelete" method="post">
        <button type="submit" class="reset-btn">DB 초기화</button>
    </form>
</div>

</body>
</html>
