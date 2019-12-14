package cn.itcast.itcaststore.service;

import java.sql.SQLException;
import java.util.List;
import cn.itcast.itcaststore.dao.OrderDao;
import cn.itcast.itcaststore.dao.ProductDao;
import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.domain.User;
import cn.itcast.itcaststore.utils.DataSourceUtils;

public class OrderService {

	private OrderDao odao = new OrderDao();
	private ProductDao pdao = new ProductDao();
	
	// 1.��Ӷ���
	public void addOrder(Order order) {
		try {
			// 1.��������
			DataSourceUtils.startTransaction();
			// 2.��ɲ���
			// 2.1��orders�����������
			odao.addProduct(order);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DataSourceUtils.rollback(); // ����ع�
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				// �رգ��ͷ��Լ��ύ����
				DataSourceUtils.releaseAndCloseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// �������ж���
		public List<Order> findOrderByManyCondition(String id,String name, int type) {
			List<Order> orders = null;
			try {
				// ���ҳ�������Ϣ
				orders = odao.findOrderByManyCondition(id,name,type);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return orders;
		}

	//����idɾ������ ����Աɾ������
	public void delOrderById(String id) {			
		try {
			//DataSourceUtils.startTransaction();//��������
			odao.delOrderById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				DataSourceUtils.releaseAndCloseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	
	//��ͨ�û�ɾ������
	public void delOrderByIdWithClient(String id) {
		try {
			//DataSourceUtils.startTransaction();//��������
			odao.delOrderByIdWithClient(id); //ɾ������
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				DataSourceUtils.releaseAndCloseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
