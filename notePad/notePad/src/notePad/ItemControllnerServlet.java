package notePad;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ItemControllnerServlet
 */
@WebServlet("/ItemControllnerServlet")
public class ItemControllnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ItemDbUtil itemDbUtil;
    /**
     * Default constructor. 
     */
	@Resource(name="jdbc/todolist")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			itemDbUtil = new ItemDbUtil(dataSource);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String command = request.getParameter("command");
			if (command == null) {
				command = "LIST";
			}
			switch(command) {
				case "LIST":
					listItems(request, response);
					break;
				case "ADD":
					addItems(request, response);
					break;
				case "DELETE":
					deleteItems(request, response);
					break;
				default :
					listItems(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteItems(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		String theItemId = request.getParameter("itemId");
		itemDbUtil.deleteItem(theItemId);
		listItems(request, response);
	}

	private void addItems(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		String todo = request.getParameter("todo");
		
		Item theItem = new Item(todo);
		
		itemDbUtil.addItem(theItem);
		
		listItems(request, response);
	}

	private void listItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<Item> items = itemDbUtil.getItems();
		
		request.setAttribute("ITEM_LIST", items);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-items.jsp");
		
		dispatcher.forward(request, response);
	}
	
	

}
