package osassigment3;



//----------------------------------------------------------------------------------------

import java.io.IOException;
import java.util.Scanner;

//	Copyright © 2006 - 2020 Tangible Software Solutions, Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class provides the ability to initialize and delete array elements.
//----------------------------------------------------------------------------------------
public final class Arrays
{
	public static Process[] initializeWithDefaultProcessInstances(int length)
	{
            Scanner input = new Scanner(System.in);
		Process[] array = new Process[length];
                System.out.println("Enter name of process");
		for (int i = 0; i < length; i++)
		{
			array[i] = new Process();
                        
                        array[i].name=input.next();
		}
		return array;
	}

	public static <T extends java.io.Closeable> void deleteArray(T[] array) throws IOException
	{
		for (T element : array)
		{
			if (element != null)
				element.close();
		}
	}
}