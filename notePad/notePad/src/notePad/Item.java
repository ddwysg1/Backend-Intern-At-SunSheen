package notePad;

public class Item {
	int id;
	String todo;
	public Item(int id, String todo) {
		super();
		this.id = id;
		this.todo = todo;
	}
	public Item(String todo) {
		super();
		this.todo = todo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
}
