package com.demo.blog;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * BlogController
 */
@Before(BlogInterceptor.class)
public class BlogController extends Controller {
	public void index() {
		setAttr("blogPage", Blog.dao.paginate(getParaToInt(0, 1), 10, "select *", "from blog order by id asc"));
		render("blog.html");
	}
	
	public void add() {
		setAttr("pageNumber", getParaToInt(0, 1));
	}
	
	@Before(BlogValidator.class)
	public void save() {
		getModel(Blog.class).save();
		//forwardAction("/blog");
		redirect(getAttr("base") + "/blog/" + getPara("pageNumber","1"));
	}
	
	public void edit() {
		setAttr("blog", Blog.dao.findById(getParaToInt()));
	}
	
	@Before(BlogValidator.class)
	public void update() {
		getModel(Blog.class).update();
		forwardAction("/blog");
	}
	
	public void delete() {
		Blog.dao.deleteById(getParaToInt());
		forwardAction("/blog");
	}
}


