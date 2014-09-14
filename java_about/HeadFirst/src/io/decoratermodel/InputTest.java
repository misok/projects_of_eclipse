package io.decoratermodel;

import java.io.IOException;
import java.io.InputStream;

public class InputTest {
	public static void main(String[] args)throws IOException{
		int c;
		try{
			InputStream in=new LowerCaseInputStream(new BufferedInputStream(new FileInputStream("test.txt")));
			
		}
	}

}
