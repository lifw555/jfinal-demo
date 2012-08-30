package com.demo.common;

import java.util.List;

import com.demo.blog.Blog;
import com.demo.blog.BlogController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * API引导式配置
 */
public class DemoConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadPropertyFile("a_little_config.txt");				// 加载少量必要配置，随后可用getProperty(...)获取值
		me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setBaseViewPath("/WEB-INF/action");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", CommonController.class);
		me.add("/blog", BlogController.class);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		//C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password"));
		//me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		//ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		//me.add(arp);
		//arp.addMapping("blog", Blog.class);	// 映射blog 表到 Blog模型
		
		// 配置ActiveRecord插件
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl(getProperty("jdbcUrl"));
		ds.setUser(getProperty("user"));
		ds.setPassword(getProperty("password"));
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(ds);
		me.add(arp);
		arp.addMapping("blog", Blog.class);	// 映射blog 表到 Blog模型
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("base"));
	}
	
	@Override
	public void afterJFinalStart() {
		//这个地方可以用来初始化数据？或者建立一个插件？检查数据库状态？
		//List<Record> count = Db.find("select count(0) as count from blog");
		//System.out.println("count:" + count.get(0).getLong("count"));
	}
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
