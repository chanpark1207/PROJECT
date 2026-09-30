package project;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.apache.commons.beanutils.BeanUtils;

@WebServlet("/enrollment")
public class EnrollmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseDAO dao;
    private LoginDao daolog;
    private String loginUserID = null; // 현재 로그인한 사용자 ID

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new CourseDAO();
        daolog = new LoginDao();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");

        if (action == null) {
            // action 이 없으면 로그인 페이지로
            response.sendRedirect("login.jsp");
        } else {
            switch (action) {

            case "login":
                // 로그인 후 목록 출력
                getUserID(request, response);
                request.setAttribute("loglog", daolog.getUserById(loginUserID));
                allList(request, response);
                yesList(request, response);
                // ✅ DB 기준 신청 총 학점
                request.setAttribute("totalCount", dao.getTotalCredits());
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;

            case "dbDelete":
                daolog.deleteAll();
                response.sendRedirect("login.jsp");
                break;

            case "register":
                request.getRequestDispatcher("register.jsp").forward(request, response);
                registerInsert(request, response);
                break;

            case "allList":
                request.setAttribute("loglog", daolog.getUserById(loginUserID));
                allList(request, response);
                yesList(request, response);
                request.setAttribute("totalCount", dao.getTotalCredits());
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;

            case "applClass":
                // 강좌 신청
                request.setAttribute("loglog", daolog.getUserById(loginUserID));
                applClass(request, response);         // APPLIED_COURSES로 insert
                deleteAllList(request, response);     // COURSES 에서 삭제
                allList(request, response);
                yesList(request, response);
                request.setAttribute("totalCount", dao.getTotalCredits());
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;

            case "deleteClass":
                // 강좌 신청 내역에서 삭제 (→ 위 테이블로 복귀)
                request.setAttribute("loglog", daolog.getUserById(loginUserID));
                deleteYesApplication(request, response); // APPLIED_COURSES 에서 삭제
                allApplCLass(request, response);         // COURSES 로 insert
                allList(request, response);
                yesList(request, response);
                request.setAttribute("totalCount", dao.getTotalCredits());
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;

            case "select":
                // 검색 기능
                String searchCourseId = request.getParameter("searchCourseId");
                String searchCredits = request.getParameter("searchCredits");

                if ("".equals(searchCourseId) && "NO".equals(searchCredits)) {
                    allList(request, response);
                } else {
                    request.setAttribute("availableCourses",
                            dao.selectWhereAll(searchCourseId, searchCredits));
                }

                yesList(request, response);
                request.setAttribute("loglog", daolog.getUserById(loginUserID));
                request.setAttribute("totalCount", dao.getTotalCredits());
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            }
        }
    }

    // login 후 userId 세션 저장
    public void getUserID(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
        loginUserID = userId;
    }

    // 회원가입 insert
    public void registerInsert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Login l = new Login();
        String studentIdStr = request.getParameter("studentId");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String major = request.getParameter("major");
        String gradeStr = request.getParameter("grade");

        if (studentIdStr == null || userId == null || password == null || name == null || major == null
                || gradeStr == null || studentIdStr.isEmpty() || userId.isEmpty() || password.isEmpty()
                || name.isEmpty() || major.isEmpty() || gradeStr.isEmpty()) {
            // 누락된 값이 있을 때
            request.setAttribute("errorMessage", "모든 항목을 입력해주세요.");
            return;
        }

        int studentId = Integer.parseInt(studentIdStr);
        int grade = Integer.parseInt(gradeStr);
        HttpSession session = request.getSession();
        session.setAttribute("studentId", studentId);
        session.setAttribute("userId", userId);
        session.setAttribute("password", password);
        session.setAttribute("name", name);
        session.setAttribute("major", major);
        session.setAttribute("grade", grade);

        try {
            BeanUtils.populate(l, request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        daolog.insert(l);
        response.sendRedirect("login.jsp");
    }

    // ✅ 신청버튼 눌렀을때 강좌신청내역으로 insert + 학점제한 체크
    public void applClass(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int credits = Integer.parseInt(request.getParameter("credits"));

        // 현재 신청된 총 학점(DB 기준)
        int currentTotal = dao.getTotalCredits();

        // 학점 제한 체크
        if (currentTotal + credits > 19) {
            // 경고 메시지 JSP로 전달
            request.setAttribute("creditError", "최대 신청 학점(19학점)을 초과하여 신청할 수 없습니다.");

            // 다시 index.jsp로 이동
            try {
                request.setAttribute("loglog", daolog.getUserById(loginUserID));
                allList(request, response);
                yesList(request, response);
                request.setAttribute("totalCount", currentTotal);

                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }

        Appl ap = new Appl();
        String number = request.getParameter("number");
        String division = request.getParameter("division");
        String time = request.getParameter("time");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String professor = request.getParameter("professor");
        String rating = request.getParameter("rating");

        // (세션에 저장하는 부분은 실제로는 안 써서 지워도 되지만, 형식 유지 차원에서 남겨둬도 됨)
        HttpSession session = request.getSession();
        session.setAttribute("number", number);
        session.setAttribute("division", division);
        session.setAttribute("credits", credits);
        session.setAttribute("time", time);
        session.setAttribute("id", id);
        session.setAttribute("name", name);
        session.setAttribute("professor", professor);
        session.setAttribute("rating", rating);

        try {
            BeanUtils.populate(ap, request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 실제 DB insert
        dao.applClass(ap);
    }

    // 삭제버튼 눌렀을때 전체강좌로 insert
    public void allApplCLass(HttpServletRequest request, HttpServletResponse response) {

        Course cs = new Course();
        String id = request.getParameter("id");
        String division = request.getParameter("division");
        int credits = Integer.parseInt(request.getParameter("credits"));
        String time = request.getParameter("time");
        String name = request.getParameter("name");
        String professor = request.getParameter("professor");
        String rating = request.getParameter("rating");

        HttpSession session = request.getSession();
        session.setAttribute("id", id);
        session.setAttribute("division", division);
        session.setAttribute("credits", credits);
        session.setAttribute("time", time);
        session.setAttribute("name", name);
        session.setAttribute("professor", professor);
        session.setAttribute("rating", rating);

        try {
            BeanUtils.populate(cs, request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 신청내역에서 삭제한 과목을 다시 COURSES에 넣기
        dao.allApplCLass(cs);
    }

    // 신청버튼 누르면 전체강좌에서 목록 delete
    public void deleteAllList(HttpServletRequest request, HttpServletResponse response) {
        Course cc = new Course();
        String id = request.getParameter("id");
        cc.setId(id);
        dao.deleteAllList(cc);
    }

    // 삭제버튼 누르면 강좌신청에서 해당 목록 delete
    public void deleteYesApplication(HttpServletRequest request, HttpServletResponse response) {
        Appl ap = new Appl();
        String id = request.getParameter("id");
        ap.setId(id);
        dao.deleteYesApplication(ap);
    }

    // 전체 강좌 신청 목록 select
    public void allList(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("availableCourses", dao.notApplication());
    }

    // 강좌신청내역 목록 select
    public void yesList(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("appliedCourses", dao.yesApplication());
    }
}
