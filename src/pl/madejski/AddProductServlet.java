package pl.madejski;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.madejski.dao.ProductDao;
import pl.madejski.model.Product;

/**
 * Servlet implementation class AddProductServlet
 */
@WebServlet("/products/add")
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/addproduct.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String productName = request.getParameter("productName");
			if(productName == null || productName.length() == 0) throw new NullPointerException("Pole \"Nazwa produktu\" jest puste!");
			Product newProduct = new Product(null, productName);
			ProductDao productDao = new ProductDao();
			
			productDao.save(newProduct);
			
			response.sendRedirect("/IDDBlab2/products");
		} catch (Exception e) {
			request.setAttribute("error", true);
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("/addproduct.jsp").forward(request, response);
		}
	}

}
