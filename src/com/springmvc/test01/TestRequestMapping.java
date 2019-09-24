package com.springmvc.test01;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
@SessionAttributes(value={"user"},types={String.class})
@Controller
@RequestMapping("/class_test")
public class TestRequestMapping {

	private static final String SUCCESS="success";
	@RequestMapping("/testview")
	public String testView(){
		System.out.println("testView");
		return "helloView";
	}
	
	
	
	/**
	 * 
	 * 由@ModelAttribute 标记的方法 会在每个目标方法执行前被springmvc调用
	 */
//	@ModelAttribute
//	public void getUser(@RequestParam(value="id",required=false) Integer id,
//			Map<String,Object> map) {
//		if (id!=null) {
//			//模拟数据库获取对象
//			User user=new User("yang", 1, "12345", "yang@abc.com",12);
//			map.put("abc", user);
//		}
//	}
	/**
	 * 流程 
	 * 1 执行@ModelAttribute 修饰的方法  从数据库中取出对象 放到map中 键为 “user”
	 * 
	 *2 springMVC从map中取出user对象 把表单中的属性赋值给user对象对应属性
	 *
	 *3 SpringMVC把上述对象 传入目标方法中
	 *
	 *注意 @ModelAttribute 修饰方法中 放入到map中的键 应该与 目标方法传入的参数名一致
	 * @param user
	 * @return
	 */
	/**
	 * 1确定key
	 * 有@ModelAttribute(value="abc")修饰 目标方法 POJO类型入参 key为abc
	 * 没有@ModelAttribute(value="abc")修饰  key为user（类名首字母小写）
	 * 
	 * @ModelAttribute(value="abc")修饰 目标方法 POJO类型入参
	 * 在@ModelAttribute 修饰的方法中put 键名为“abc”
	 * 若不使用 没有名为"abc" 的对象 就用反射创建一个名为user（类名首字母小写）的对象作为目标函数入参
	 * 使用后在map中查找名为"abc"的对象 作为入参 
	 * 
	 * 同时将名为"abc"的对象 存入requestScope中
	 * 
	 * 过程
	 * 1 用key 在map中找
	 * 2 若有@SessionAttributes注解  key与session中value一致  value对应对象作为入参 否则异常
	 * 3 没有@SessionAttributes注解 或session value 不含key 反射创建user 作为入参
	 * 4 把key POJO对象 保存到map request中
	 *
	 * @param user
	 * @return
	 */
	@RequestMapping("/testModelAttributes")
	public String testModelAttributes(@ModelAttribute(value="abc")User user){
		System.out.println("user 修改为："+user);
		return SUCCESS;
	}
	
	/**
	 * @SessionAttributes(value={"user"},types={User.class,String.class})
	 * Map返回的到requestScope 不会在session中
	 * SessionAttributes 指定在session中名称value或类行type
	 * 
	 * SessionAttributes 只能加在类上 不能在方法上
	 * @param map
	 * @return
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map) {
		User user=new User("yang","12345", "yang@abc.com", 11);
		map.put("user", user);
		map.put("string", "ahuaibnga");
		return SUCCESS;
	}
	
	/**
	 * 目标方法也可以添加Map类型参数  将在map中存入值返回到相关view中
	 * @param map
	 * @return
	 */
	
	@RequestMapping("/testMap")
	public String testMap(Map<String, Object> map) {
		System.out.println(map.getClass().getName());
		map.put("name", Arrays.asList("yang","li","zhang"));
		return SUCCESS;
	}
	
	/**
	 * 目标方法的返回值可以是 ModelAndView
	 * 包含视图和模型的信息
	 * @return
	 */
	
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView() {
		String viewame=SUCCESS;
		ModelAndView modelAndView=new ModelAndView(viewame);
		
		modelAndView.addObject("time", new Date());
		
		return modelAndView;
		
	}
	
	
	
	
	
	/**
	 * POJO 自动对属性名进行匹配 支持级联属性 如address.city
	 * @param user
	 * @return
	 */
	
	@RequestMapping("/testPOJO")
	public String testPOJO(User user){
		System.out.println(user);
		return SUCCESS;
	}
	

	/**
	 * @RequestParam 获取请求参数
	 * defaultValue=设置默认值,required=请求参数是否必须
	 * @return
	 */
	
	@RequestMapping("/testRequestParam")
	public String testRequestParam(@RequestParam(value=("username")) String un,
			@RequestParam(value="age",defaultValue="0",required=false) int age) {
		System.out.println("testRequestParam "+ un+age);
		return SUCCESS;
	}
	
	/**
	 * Rest 风格URL
	 * 增删改查为例
	 * 增 /order POST
	 * 删 /order/1（1是id）DELETE
	 * 改 /order/1（1是id）PUT
	 * 查 /order/1（1是id）GET
	 * 
	 * 发送put delete请求
	 * 1  配置HiddenHttpMethodFilter web.xml 中
	 * 2  发送POST请求
	 * 3  再POST请求中 携带隐藏域 name="_methoe" value="DELETE"
	 *
	 * 再springmvc中 使用 @PathVariable 得到相关参数
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping(value="/testPut/{id}",method=RequestMethod.PUT)
	public String testResttPut(@PathVariable Integer id) {
		System.out.println("testRsetPut " +id);
		return SUCCESS;
		
	}
	
	@RequestMapping(value="/testDelete/{id}",method=RequestMethod.DELETE)
	public String testRestDelete(@PathVariable Integer id) {
		System.out.println("testRsetDelete "+id);
		return SUCCESS;
		
	}
	
	@RequestMapping(value="/testPost",method=RequestMethod.POST)
	public String testRestPost() {
		System.out.println("testRsetPost");
		return SUCCESS;
		
	}
	
	@RequestMapping(value="/testGet/{id}",method=RequestMethod.GET)
	public String testRestGet(@PathVariable(value="id") Integer id) {
		System.out.println("testRestGet"+id);
		return SUCCESS;
	}
	
	/**
	 * @PathVariable(value="id") 可以映射URl中的占位符到 目标方法中
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/testPathVariable/{id}")
	public String testPathVariable(@PathVariable(value="id") Integer id){
		System.out.println("testPathVariable"+id);
		return SUCCESS;
		
	}
	
	@RequestMapping("/hello")
	public String hello() {
		System.out.println("hello test");
		return "success";
	}
	/**
	 * method 指定请求方法
	 * @return
	 */
	@RequestMapping(value="/testMethod",method=RequestMethod.POST)
	public String testMethod(){
		System.out.println("testMethod");
		return SUCCESS;
	}

}
