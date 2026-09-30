package project;

public class Login {
    private int STUDENT_ID;    // 학번
    private String USERID;     // 로그인 아이디
    private String PASSWORD;   // 비밀번호
    private String NAME;       // 이름
    private String MAJOR;      // 학과
    private int GRADE;         // 학년

    // getter & setter
    public int getSTUDENT_ID() {
        return STUDENT_ID;
    }
    public void setSTUDENT_ID(int STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }
    public String getUSERID() {
        return USERID;
    }
    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }
    public String getPASSWORD() {
        return PASSWORD;
    }
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
    public String getNAME() {
        return NAME;
    }
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
    public String getMAJOR() {
        return MAJOR;
    }
    public void setMAJOR(String MAJOR) {
        this.MAJOR = MAJOR;
    }
    public int getGRADE() {
        return GRADE;
    }
    public void setGRADE(int GRADE) {
        this.GRADE = GRADE;
    }
}
