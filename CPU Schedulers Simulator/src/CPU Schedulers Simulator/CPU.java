package osassigment3;

public final class CPU
{
    CPU()
    {
        setTimer(0);
        this.currentProcess=new Process();
    }
    public Process currentProcess;
    public  int Timer;
    public void setCurrentProcess(Process currentP) {
        currentProcess = currentP;
    }
    public void setTimer(int timer) {
        Timer = timer;
    }
}
