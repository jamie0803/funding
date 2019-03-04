package junit.test;

import java.util.List;

public class Test1 {

	public static void main(String[] args) throws InterruptedException {
		//String str = null ; //成员方法是依赖于对象引用调用的,所以一个null对象调用成员方法或成员变量就会报空指针
		//str = str.toLowerCase(); //java.lang.NullPointerException
		//System.out.println(str);

		//
		//Thread thread = null ;
		//thread.sleep(1000); //静态方法是依赖于类的,不是依赖于对象应用,所以,不报空指针.
		//Thread.sleep(1000);
		
		/*List<String> list = null ;
		
		for(int i=0; i<list.size() ; i++) {
			System.out.println(i);
		}
		
		for (String s : list) { //java.lang.NullPointerException
			System.out.println(s);
		}*/
		
		Integer pageno = null ; //pageno.intValue()
		
		int i = pageno ; //java.lang.NullPointerException
		
		System.out.println(i);
		
		//  int startIndex = (pageno-1)*pagesize
		
		Integer s = 1 ;
		String str =  Integer.toString(s);
		
	}

}
