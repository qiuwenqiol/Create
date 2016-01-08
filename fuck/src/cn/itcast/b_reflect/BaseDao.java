package cn.itcast.b_reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * ����dao�Ĺ��õķ�������������ʵ��
 * @author Jie.Yuan
 *
 */
public class BaseDao<T>{
	
	// ���浱ǰ������Ĳ����������е�ʵ�ʵ�����
	private Class clazz;
	// ����
	private String tableName;
	
	
	
	// ���캯���� 1. ��ȡ��ǰ������Ĳ��������ͣ� 2. ��ȡ������������ʵ�����͵Ķ���(class)
	public BaseDao(){
		//  this  ��ʾ��ǰ������  (AccountDao/AdminDao)
		//  this.getClass()  ��ǰ��������ֽ���(AccountDao.class/AdminDao.class)
		//  this.getClass().getGenericSuperclass();  ��ǰ������ĸ��࣬��ΪBaseDao<Account>
		//                                           ��ʵ���ǡ����������͡��� ParameterizedType   
		Type type = this.getClass().getGenericSuperclass();
		// ǿ��ת��Ϊ�����������͡�  ��BaseDao<Account>��
		ParameterizedType pt = (ParameterizedType) type;
		// ��ȡ�����������У�ʵ�����͵Ķ���  ��new Type[]{Account.class}��
		Type types[] =  pt.getActualTypeArguments();
		// ��ȡ���ݵĵ�һ��Ԫ�أ�Accout.class
		clazz = (Class) types[0];
		// ����  (������һ����ֻҪ��ȡ�����Ϳ���)
		tableName = clazz.getSimpleName();
	}
	

	/**
	 * ������ѯ
	 * @param id	����ֵ
	 * @return      ���ط�װ��Ķ���
	 */
	public T findById(int id){
		/*
		 * 1. ֪����װ�Ķ��������
		 * 2. �������������������һ���� ��������Ϊid��
		 * 
		 * ����
		 * 	  ---���õ���ǰ������̳еĸ���  BaseDao<Account>
		 *   ----�� �õ�Account.class
		 */
		
		String sql = "select * from " + tableName + " where id=? ";
		try {
			return JdbcUtils.getQuerrRunner().query(sql, new BeanHandler<T>(clazz), id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * ��ѯȫ��
	 * @return
	 */
	public List<T> getAll(){
		String sql = "select * from " + tableName ;
		try {
			return JdbcUtils.getQuerrRunner().query(sql, new BeanListHandler<T>(clazz));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}













 