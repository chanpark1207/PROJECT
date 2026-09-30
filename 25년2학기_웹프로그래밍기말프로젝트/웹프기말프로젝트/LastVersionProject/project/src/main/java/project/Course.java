package project;

public class Course {
	private String division; // 이수구분
	private int credits; // 학점
	private String time; // 강의시간
    private String id; // 학수번호
    private String name; // 교과목
    private String professor; // 담당교수
    private String rating; // 강의평점 (★★★★☆ 형태의 문자열)

    @Override
    public String toString() {
    	return "Course [division=" + division + ", credits=" + credits + ", time=" + time + ", id=" + id + ", name=" + name + ", professor=" + professor + ", rating=" + rating + "]";
    	
    }

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
    
}