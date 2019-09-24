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
	 * ��@ModelAttribute ��ǵķ��� ����ÿ��Ŀ�귽��ִ��ǰ��springmvc����
	 */
//	@ModelAttribute
//	public void getUser(@RequestParam(value="id",required=false) Integer id,
//			Map<String,Object> map) {
//		if (id!=null) {
//			//ģ�����ݿ��ȡ����
//			User user=new User("yang", 1, "12345", "yang@abc.com",12);
//			map.put("abc", user);
//		}
//	}
	/**
	 * ���� 
	 * 1 ִ��@ModelAttribute ���εķ���  �����ݿ���ȡ������ �ŵ�map�� ��Ϊ ��user��
	 * 
	 *2 springMVC��map��ȡ��user���� �ѱ��е����Ը�ֵ��user�����Ӧ����
	 *
	 *3 SpringMVC���������� ����Ŀ�귽����
	 *
	 *ע�� @ModelAttribute ���η����� ���뵽map�еļ� Ӧ���� Ŀ�귽������Ĳ�����һ��
	 * @param user
	 * @return
	 */
	/**
	 * 1ȷ��key
	 * ��@ModelAttribute(value="abc")���� Ŀ�귽�� POJO������� keyΪabc
	 * û��@ModelAttribute(value="abc")����  keyΪuser����������ĸСд��
	 * 
	 * @ModelAttribute(value="abc")���� Ŀ�귽�� POJO�������
	 * ��@ModelAttribute ���εķ�����put ����Ϊ��abc��
	 * ����ʹ�� û����Ϊ"abc" �Ķ��� ���÷��䴴��һ����Ϊuser����������ĸСд���Ķ�����ΪĿ�꺯�����
	 * ʹ�ú���map�в�����Ϊ"abc"�Ķ��� ��Ϊ��� 
	 * 
	 * ͬʱ����Ϊ"abc"�Ķ��� ����requestScope��
	 * 
	 * ����
	 * 1 ��key ��map����
	 * 2 ����@SessionAttributesע��  key��session��valueһ��  value��Ӧ������Ϊ��� �����쳣
	 * 3 û��@SessionAttributesע�� ��session value ����key ���䴴��user ��Ϊ���
	 * 4 ��key POJO���� ���浽map request��
	 *
	 * @param user
	 * @return
	 */
	@RequestMapping("/testModelAttributes")
	public String testModelAttributes(@ModelAttribute(value="abc")User user){
		System.out.println("user �޸�Ϊ��"+user);
		return SUCCESS;
	}
	
	/**
	 * @SessionAttributes(value={"user"},types={User.class,String.class})
	 * Map���صĵ�requestScope ������session��
	 * SessionAttributes ָ����session������value������type
	 * 
	 * SessionAttributes ֻ�ܼ������� �����ڷ�����
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
	 * Ŀ�귽��Ҳ�������Map���Ͳ���  ����map�д���ֵ���ص����view��
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
	 * Ŀ�귽���ķ���ֵ������ ModelAndView
	 * ������ͼ��ģ�͵���Ϣ
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
	 * POJO �Զ�������������ƥ�� ֧�ּ������� ��address.city
	 * @param user
	 * @return
	 */
	
	@RequestMapping("/testPOJO")
	public String testPOJO(User user){
		System.out.println(user);
		return SUCCESS;
	}
	

	/**
	 * @RequestParam ��ȡ�������
	 * defaultValue=����Ĭ��ֵ,required=��������Ƿ����
	 * @return
	 */
	
	@RequestMapping("/testRequestParam")
	public String testRequestParam(@RequestParam(value=("username")) String un,
			@RequestParam(value="age",defaultValue="0",required=false) int age) {
		System.out.println("testRequestParam "+ un+age);
		return SUCCESS;
	}
	
	/**
	 * Rest ���URL
	 * ��ɾ�Ĳ�Ϊ��
	 * �� /order POST
	 * ɾ /order/1��1��id��DELETE
	 * �� /order/1��1��id��PUT
	 * �� /order/1��1��id��GET
	 * 
	 * ����put delete����
	 * 1  ����HiddenHttpMethodFilter web.xml ��
	 * 2  ����POST����
	 * 3  ��POST������ Я�������� name="_methoe" value="DELETE"
	 *
	 * ��springmvc�� ʹ�� @PathVariable �õ���ز���
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
	 * @PathVariable(value="id") ����ӳ��URl�е�ռλ���� Ŀ�귽����
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
	 * method ָ�����󷽷�
	 * @return
	 */
	@RequestMapping(value="/testMethod",method=RequestMethod.POST)
	public String testMethod(){
		System.out.println("testMethod");
		return SUCCESS;
	}

}
