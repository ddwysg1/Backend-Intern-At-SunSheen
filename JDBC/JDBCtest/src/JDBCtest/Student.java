package JDBCtest;

public class Student {
	private String Id;
	private String Name;
	private String Sex;
	private String Age;
	private String Tel;
	
	Student(String Name, String Sex, String Age, String Tel) {
		this.Id = null;
		this.Name = Name;
		this.Sex = Sex;
		this.Age = Age;
		this.Tel = Tel;
	}
	
	public String getId() {
		return Id;
	}
	
	public void setId(String Id) {
		this.Id = Id;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String Name) {
		this.Name = Name;
	}
	
	public String getSex() {
		return Sex;
	}
	
	public void setSex(String Sex) {
		this.Sex = Sex;
	}
	
	public String getAge() {
		return Age;
	}
	
	public void setAge(String Age) {
		this.Age = Age;
	}
	
	public String getTel() {
		return Tel;
	}
	
	public void setTel(String Tel) {
		this.Tel = Tel;
	}
	
}
