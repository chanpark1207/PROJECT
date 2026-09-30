<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="project.Login" %>
<%@ page import="project.LoginDao" %>

<%
    request.setCharacterEncoding("UTF-8");
    String message = "";

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String name = request.getParameter("name");
        String studentIdStr = request.getParameter("studentId");
        String major = request.getParameter("major");
        String gradeStr = request.getParameter("grade");
        String userId = request.getParameter("userId"); 
        String userPw = request.getParameter("userPw"); 

        if (name != null && !name.isEmpty() &&
            studentIdStr != null && !studentIdStr.isEmpty() &&
            major != null && !major.isEmpty() &&
            gradeStr != null && !gradeStr.isEmpty() &&
            userId != null && !userId.isEmpty() &&
            userPw != null && !userPw.isEmpty()) {

            try {
                int studentId = Integer.parseInt(studentIdStr);
                int grade = Integer.parseInt(gradeStr);

                Login newUser = new Login();
                newUser.setNAME(name);
                newUser.setSTUDENT_ID(studentId);
                newUser.setMAJOR(major);
                newUser.setGRADE(grade);
                newUser.setUSERID(userId);
                newUser.setPASSWORD(userPw);

                LoginDao dao = new LoginDao();
                dao.insert(newUser);

                response.sendRedirect("login.jsp");
                return;

            } catch (NumberFormatException e) {
                message = "학번과 학년은 숫자로 입력해야 합니다.";
            } catch (Exception e) {
                e.printStackTrace();
                message = "회원가입 중 오류가 발생했습니다.";
            }

        } else {
            message = "모든 정보를 입력해야 합니다.";
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
* { box-sizing: border-box; }
body {
    background: linear-gradient(135deg, #f6d365, #fda085);
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    font-family: Arial, sans-serif;
}
h2 { color: #fff; font-size: 50px; margin-bottom: 30px; }
form { width: 300px; background: white; padding: 20px; border-radius: 10px; }
input, select, button { width: 100%; padding: 10px; margin: 8px 0; border-radius: 5px; border: 1px solid #ccc; }
button { background: #f57c00; color: white; border: none; font-size: 15px; cursor: pointer; }
button:hover { background: #e65100; }
p.error { color: red; text-align: center; }
</style>
</head>
<body>

<h2>회원가입</h2>

<form action="register.jsp" method="post">
    <% if (!message.isEmpty()) { %>
        <p class="error"><%= message %></p>
    <% } %>

    이름
    <input type="text" name="name" required>
    학번
    <input type="text" name="studentId" required>
    학과
    <input type="text" name="major" required>
    학년
    <select name="grade" required>
        <option value="">선택</option>
        <option value="1">1학년</option>
        <option value="2">2학년</option>
        <option value="3">3학년</option>
        <option value="4">4학년</option>
    </select>
    아이디
    <input type="text" name="userId" required>
    비밀번호
    <input type="password" name="userPw" required>

    <button type="submit">가입하기</button>
</form>

</body>
</html>
