package cn.itcast.itcaststore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import cn.itcast.itcaststore.domain.Order;
import cn.itcast.itcaststore.domain.Product;
import cn.itcast.itcaststore.domain.User;
import cn.itcast.itcaststore.utils.DataSourceUtils;

public class OrderDao {
	public void addProduct(Order order) throws SQLException {
		//String sql = "insert into `order`(oNo,oMoney,paystate,ordertime,id,gNo) values(?,?,1,CURDATE(),?,?)";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		runner.execute("{call addOrderList(?,?,?,?)}", order.getoNo(),order.getoMoney(),
				order.getUser().getId(), order.getProduct().getgNo());
	}
	

	public void delOrderById(String id) throws SQLException {
		//state=2��ʾ����Աɾ���˶���
		String sql="update `order` set adminState=2 where oNo=?";		
		QueryRunner runner = new QueryRunner();		
		runner.update(DataSourceUtils.getConnection(),sql,id);	
		DataSourceUtils.releaseAndCloseConnection();
	}
	
	public void delOrderByIdWithClient(String id) throws SQLException {
		//state=3��ʾ�û�ɾ���˶���
		String sql="update `order` set clientState=2 where oNo=?";		
		QueryRunner runner = new QueryRunner();		
		runner.update(DataSourceUtils.getConnection(),sql,id);	
		DataSourceUtils.releaseAndCloseConnection();
	}
	
	public List<Order> findOrderByManyCondition(String id, String name, int type)
			throws SQLException {
		//1.�������϶���
		List<Object> objs = new ArrayList<Object>();
		//2.�����ѯsql
		String sql = "select `order`.*,user.*,product.* from `order`,user,product where user.id=`order`.id and `order`.gNo=product.gNo ";
		//3.���ݲ���ƴ��sql���
		if (id != null && id.trim().length() > 0) {
			sql += " and `order`.oNo=?";
			objs.add(id);
		}
		if (name != null && name.trim().length() > 0) {
			sql += " and `order`.id=?";
			objs.add(name);
		}
		if (type == 1) {
			//����Ա�����
			sql += " and `order`.adminState=1";
		}
		else if(type == 2) {
			sql += " and `order`.clientState=1";
		}
		//4.����QueryRunner����
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//5.����QueryRunner����query������ִ�н��
		return runner.query(sql, new ResultSetHandler<List<Order>>() {
			public List<Order> handle(ResultSet rs) throws SQLException {
				List<Order> orders = new ArrayList<Order>();
               //ѭ���������������û���Ϣ
				while (rs.next()) {
					Order order = new Order();
					order.setoNo(rs.getString("order.oNo"));
					order.setoMoney(rs.getDouble("order.oMoney"));
					order.setOrdertime(rs.getDate("order.ordertime"));
					order.setClientState(rs.getInt("order.clientState"));
					order.setAdminState(rs.getInt("order.adminState"));
					orders.add(order);
					
					User user = new User();
					user.setId(rs.getString("user.id"));
					user.setEmail(rs.getString("user.email"));
					user.setPassword(rs.getString("user.password"));
					user.setRole(rs.getString("user.role"));
					user.setMoney(rs.getInt("user.money"));
					user.setName(rs.getString("user.name"));
					order.setUser(user);
					
					Product product=new Product();
					product.setgDescription(rs.getString("gDescription"));
					product.setgImgurl(rs.getString("gImgurl"));
					product.setgName(rs.getString("gName"));
					product.setgNo(rs.getString("product.gNo"));
					product.setgPostTime(rs.getDate("gPostTime"));
					product.setgPrice(rs.getDouble("gPrice"));
					product.setgScore(rs.getDouble("gScore"));
					product.setgType(rs.getString("gType"));
					product.setgState(rs.getInt("gState"));
					order.setProduct(product);
				}

				return orders;
			}
		}, objs.toArray());
	}

}
