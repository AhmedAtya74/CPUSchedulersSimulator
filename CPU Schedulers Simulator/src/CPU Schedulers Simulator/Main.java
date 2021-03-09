package osassigment3;


import java.io.IOException;
import static java.lang.System.out;
import java.util.Scanner;
public class Main
{
   public static void main(String[] args) throws IOException 
    {
        SJF s =new SJF();
        RR r = new RR();
        PPS p=new PPS();
        MultiLevel multi=new MultiLevel();
        while (true)
        {
            System.out.println("[1]preemptive Shortest-Job First(SJF) Scheduling with context switching ");
            System.out.println("[2]Round Robin (RR) with context switching");
            System.out.println("[3]Preemptive  Priority Scheduling ");
            System.out.println("[4] Multi Level scheduling");
            Scanner input = new Scanner(System.in);
            System.out.println("Please Enter your choice ");
            int choice = input.nextInt();
            switch (choice) 
            {
                case 1:
                    s.sjf();
                    break;
                case 2:
                    r.printRR();
                    break;
                case 3:
                    p.priority();
                    break;
                case 4: 
                   multi.MultiLevelRun();
                    break;
                default:
                    out.println("GOOD BYE");
                    System.exit(0);                   
            }
        }
       
   } 
}
