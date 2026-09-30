package project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    Connection conn = null;
    PreparedStatement pstmt;

    final String JDBC_DRIVER = "org.h2.Driver";
    final String JDBC_URL = "jdbc:h2:~/minidb;AUTO_SERVER=TRUE";


    // DB 연결
    public void open() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL, "mini", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DB 자원 해제
    public void close() {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ 현재 신청된 총 학점 (APPLIED_COURSES 합계)
    public int getTotalCredits() {
        open();
        int total = 0;
        String sql = "SELECT COALESCE(SUM(A_CREDITS), 0) FROM APPLIED_COURSES";

        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return total;
    }

    // 조회(조건 검색)
    public List<Course> selectWhereAll(String id, String credits) {
        open();
        List<Course> cs = new ArrayList<>();
        String sql;
        ResultSet rs;

        try {
            if ("".equals(id)) {
                // id는 없지만 credits는 있음
                sql = "SELECT * FROM COURSES WHERE CREDITS = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, credits);
            } else if ("NO".equals(credits)) {
                // credits는 없지만 id는 있음
                sql = "SELECT * FROM COURSES WHERE ID = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id);
            } else {
                // 둘 다 있는 경우
                sql = "SELECT * FROM COURSES WHERE CREDITS = ? AND ID = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, credits);
                pstmt.setString(2, id);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Course c = new Course();
                c.setDivision(rs.getString("DIVISION"));
                c.setCredits(rs.getInt("CREDITS"));
                c.setTime(rs.getString("TIME"));
                c.setId(rs.getString("ID"));
                c.setName(rs.getString("NAME"));
                c.setProfessor(rs.getString("PROFESSOR"));
                c.setRating(rs.getString("RATING"));
                cs.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return cs;
    }

    // 신청버튼 눌렀을때 강좌신청내역으로 insert
    public void applClass(Appl ap) {
        if (ap.getId() != null) {
            open();
            String sql = "INSERT INTO APPLIED_COURSES VALUES(?,?,?,?,?,?,?,?)";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, ap.getNumber());
                pstmt.setString(2, ap.getDivision());
                pstmt.setInt(3, ap.getCredits());
                pstmt.setString(4, ap.getTime());
                pstmt.setString(5, ap.getId());
                pstmt.setString(6, ap.getName());
                pstmt.setString(7, ap.getProfessor());
                pstmt.setString(8, ap.getRating());

                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    // 삭제버튼 눌렀을때 전체강좌로 insert (다시 COURSES로 복귀)
    public void allApplCLass(Course cs) {
        open();
        String sql = "INSERT INTO COURSES VALUES(?,?,?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cs.getId());
            pstmt.setString(2, cs.getDivision());
            pstmt.setInt(3, cs.getCredits());
            pstmt.setString(4, cs.getTime());
            pstmt.setString(5, cs.getName());
            pstmt.setString(6, cs.getProfessor());
            pstmt.setString(7, cs.getRating());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    // 신청버튼 누르면 전체강좌에서 목록 delete
    public void deleteAllList(Course c) {
        open();
        String sql = "DELETE FROM COURSES WHERE ID = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, c.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    // 삭제버튼 누르면 강좌신청에서 해당 목록 delete
    public void deleteYesApplication(Appl a) {
        open();
        String sql = "DELETE FROM APPLIED_COURSES WHERE A_ID = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, a.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    // 강좌신청내역 list 조회
    public List<Appl> yesApplication() {
        open();
        List<Appl> ap = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement("SELECT * FROM APPLIED_COURSES");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Appl a = new Appl();
                a.setNumber(rs.getString("NUMBER"));
                a.setId(rs.getString("A_ID"));
                a.setDivision(rs.getString("A_DIVISION"));
                a.setCredits(rs.getInt("A_CREDITS"));
                a.setTime(rs.getString("A_TIME"));
                a.setName(rs.getString("A_NAME"));
                a.setProfessor(rs.getString("A_PROFESSOR"));
                a.setRating(rs.getString("A_RATING"));
                ap.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return ap;
    }

    // 전체 강좌 신청 목록 select
    public List<Course> notApplication() {
        open();
        List<Course> courses = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement("SELECT * FROM COURSES");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getString("id"));
                c.setDivision(rs.getString("division"));
                c.setCredits(rs.getInt("credits"));
                c.setTime(rs.getString("time"));
                c.setName(rs.getString("name"));
                c.setProfessor(rs.getString("professor"));
                c.setRating(rs.getString("rating"));
                courses.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return courses;
    }
    
    
}
