package workzen.xgen.test;

import java.sql.Types;

import workzen.xgen.type.Byte;
import workzen.xgen.type.XGenType;

public class TypeTest {
	
	public static void main(String[] args){
		TypeTest test = new TypeTest();
		test.run();
	}
	
	public void run(){
		XGenType type = new workzen.xgen.type.Integer();
		System.out.println(type);
		System.out.println(Types.BIT);
		System.out.println(Types.BIGINT);
		System.out.println(Types.INTEGER);
	}

}
