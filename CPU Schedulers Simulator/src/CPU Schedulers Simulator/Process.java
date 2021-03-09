package osassigment3;

public class Process
{
    public int pid;
       public  String name;
    public  int Arrival;
    public  int Burst;
    public  int Completion;
    public  int Turnaround;
    public  int Waited;
    public int priority;
    public int start_time;
    Process()
    {
        name=" ";
        Arrival=-1;
        Burst=-1;
        Completion=0;
        Waited=0;
        Turnaround=0;
        priority=0;
        start_time=0;
             
    }
 
    public void setTurnaround()
    {
        Turnaround = Completion-Arrival;
    }
    
    
    
}
