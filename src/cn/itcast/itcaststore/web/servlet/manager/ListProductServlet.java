package cn.itcast.itcaststore.web.servlet.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.itcaststore.domain.Product;
import cn.itcast.itcaststore.exception.ListProductException;
import cn.itcast.itcaststore.service.ProductService;

/**
 * Servlet implementation class ListProductServlet
 */
@WebServlet(name="listProduct",urlPatterns="/listProduct")
public class ListProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 1.����service��Ķ���
		ProductService service = new ProductService();
		try {
			// 2.����service�����ڲ�ѯ������Ʒ�ķ���
			List<Product> ps = service.listAll();
			// 3.����ѯ����������Ʒ�Ž�request����
			request.setAttribute("ps", ps);
			// 4.�ض���list.jspҳ��
			request.getRequestDispatcher("/admin/products/list.jsp").forward(
					request, response);
			return;
		} catch (ListProductException e) {
			e.printStackTrace();
			response.getWriter().write(e.getMessage());
			return;
		}
	}

}
