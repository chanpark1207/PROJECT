package project;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class LoginController
 */	
@WebServlet(name = "LoginControllers", urlPatterns = { "/loginControllers" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("register.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		request.setCharacterEncoding("UTF-8");

        String studentIdStr = request.getParameter("studentId");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String major = request.getParameter("major");
        String gradeStr = request.getParameter("grade");

        if(studentIdStr == null || userId == null || password == null ||
                name == null || major == null || gradeStr == null ||
                studentIdStr.isEmpty() || userId.isEmpty() || password.isEmpty() ||
                name.isEmpty() || major.isEmpty() || gradeStr.isEmpty()) {
                 // 누락된 값이 있을 때
                 request.setAttribute("errorMessage", "모든 항목을 입력해주세요.");
                 request.getRequestDispatcher("register.jsp").forward(request, response);
                 return;
             }
        try {
            int studentId = Integer.parseInt(studentIdStr);
            int grade = Integer.parseInt(gradeStr);

            Login login = new Login();
            login.setSTUDENT_ID(studentId);
            login.setUSERID(userId);
            login.setPASSWORD(password);
            login.setNAME(name);
            login.setMAJOR(major);
            login.setGRADE(grade);

            LoginDao dao = new LoginDao();
            dao.insert(login);
            

            // 성공하면 로그인 페이지로 이동
            response.sendRedirect("login.jsp");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "학번과 학년은 숫자로 입력해야 합니다.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "회원가입 중 오류가 발생했습니다.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
	}


