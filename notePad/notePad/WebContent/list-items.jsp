<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Notepad App</title>
</head>
<body>
	<div id="sample" style="width: 500px; float: left; margin-right: 100px;">
  <script type="text/javascript" src="http://js.nicedit.com/nicEdit-latest.js"></script> <script type="text/javascript">
//<![CDATA[
        bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
  //]]>
  </script>
  <h2>
    Add new item
  </h2>
  <form action = "ItemControllnerServlet" method = "GET">
  <input type="hidden" name="command" value="ADD" />
  <textarea name="todo" style="width: 500px; height: 300px;">
  </textarea>
  <input type="submit" value="Save" class="save" />
  </form>
</div>
<div class = "todo" style = "float: left;">
	<div id="wrapper">
		<div id="header">
			<h2>Todo List</h2>
		</div>
	</div>
	<div id="container">
	
		<div id="content">
			
			<ol>
				<c:forEach var="tempItem" items="${ITEM_LIST}">
					<c:url var="deleteLink" value="ItemControllnerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="itemId" value="${tempItem.id}" />
					</c:url>  
					<li> ${tempItem.todo}
					<a href = "${deleteLink}"
					onclick = "if (!(confirm('Are you sure you want to delete this item'))) return false"> Delete</a></li>
				</c:forEach>
				
			</ol>
		
		</div>
	</div>
	</div>
</body>
</html>