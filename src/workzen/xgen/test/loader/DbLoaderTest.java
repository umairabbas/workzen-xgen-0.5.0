package workzen.xgen.test.loader;

import workzen.xgen.loader.JavaModelDbLoader;
 
public class DbLoaderTest {
	
	JavaModelDbLoader loader = new JavaModelDbLoader();
	
	public static void main(String[] args){
		DbLoaderTest test = new DbLoaderTest();
		test.run();
	}

	
	public void run(){
		System.out.println(convert("test_table_name"));
		System.out.println(convert("TEST_TABLE_NAME"));
		System.out.println(convert("test"));
	}
	
	private String convert(String name){	
		return loader.convertTableNameToJavaname(name);
	}
}
