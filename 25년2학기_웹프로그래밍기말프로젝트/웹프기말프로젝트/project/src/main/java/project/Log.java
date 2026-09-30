package project;

public class Log {
	private int number;
	private String userid;
	private String username;
	
	@Override
	public String toString() {
		return "Log [number=" + number + ", userid=" + userid + ", username=" + username + "]";		
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
