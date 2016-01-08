package cn.itcast.b_reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * 所有dao的公用的方法，都在这里实现
 * @author Jie.Yuan
 *
 */
public class BaseDao<T>{
	
	// 保存当前运行类的参数化类型中的实际的类型
	private Class clazz;
	// 表名
	private String tableName;
	
	
	
	// 构造函数： 1. 获取当前运行类的参数化类型； 2. 获取参数化类型中实际类型的定义(class)
	public BaseDao(){
		//  this  表示当前运行类  (AccountDao/AdminDao)
		//  this.getClass()  当前运行类的字节码(AccountDao.class/AdminDao.class)
		//  this.getClass().getGenericSuperclass();  当前运行类的父类，即为BaseDao<Account>
		//                                           其实就是“参数化类型”， ParameterizedType   
		Type type = this.getClass().getGenericSuperclass();
		// 强制转换为“参数化类型”  【BaseDao<Account>】
		ParameterizedType pt = (ParameterizedType) type;
		// 获取参数化类型中，实际类型的定义  【new Type[]{Account.class}】
		Type types[] =  pt.getActualTypeArguments();
		// 获取数据的第一个元素：Accout.class
		clazz = (Class) types[0];
		// 表名  (与类名一样，只要获取类名就可以)
		tableName = clazz.getSimpleName();
	}
	

	/**
	 * 主键查询
	 * @param id	主键值
	 * @return      返回封装后的对象
	 */
	public T findById(int id){
		/*
		 * 1. 知道封装的对象的类型
		 * 2. 表名【表名与对象名称一样， 且主键都为id】
		 * 
		 * 即，
		 * 	  ---》得到当前运行类继承的父类  BaseDao<Account>
		 *   ----》 得到Account.class
		 */
		
		String sql = "select * from " + tableName + " where id=? ";
		try {
			return JdbcUtils.getQuerrRunner().query(sql, new BeanHandler<T>(clazz), id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 查询全部
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













 