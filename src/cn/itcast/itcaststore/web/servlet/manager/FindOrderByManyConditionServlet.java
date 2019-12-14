package cn.itcast.itcaststore.web.servlet.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.service.OrderService;

/**
 * Servlet implementation class FindOrderByManyConditionServlet
 */
@WebServlet(name="findOrderByManyCondition",urlPatterns="/findOrderByManyCondition")
public class FindOrderByManyConditionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        //��ȡ������ź��ռ�������
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
       
		if(type.equals("admin")) {
			 //����Service�����
			OrderService service = new OrderService();
			//����Service��OrderService���findOrderByManyCondition()������ѯ����
			List<Order> orders = service.findOrderByManyCondition(id, name, 1);
	        //����ѯ�����ӵ�request��������
			request.setAttribute("orders", orders);
			
            //����ת����list.jspҳ�棬����request�����response��ӦҲת������ҳ����
		    request.getRequestDispatcher("/admin/orders/list.jsp").forward(request,
				    response);
		}else {
			//�û�
			//����Service�����
			OrderService service = new OrderService();
			//����Service��OrderService���findOrderByManyCondition()������ѯ����
			List<Order> orders = service.findOrderByManyCondition(id, name, 2);
	        //����ѯ�����ӵ�request��������
			request.setAttribute("orders", orders);
			
			request.getRequestDispatcher("/client/list.jsp").forward(request,
				    response);
		}
	}

}
