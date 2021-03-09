package osassigment3;

import static java.lang.System.out;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class SJF {

    static CPU cpu = new CPU(); 
    private static int processNumber = 0; 
    static Process[] Processes;
    static Queue<Process> Ready = new LinkedList<Process>();
    static Queue<Process> completed = new LinkedList<Process>();
    static int ContextSwith;

    public void sjf() {
        inputs(); //input Process & Arrival Time & Burst Time & ContextSwith
        OrderFC(); // //compare arrival time and order them
        int Time = 0;
        int overhead = 0; 
        while (completed.size() != processNumber) { // loop till every process is done
            recevingTime(Time);  //get arrival time for each process         
            if (!Ready.isEmpty()) { //context switching
                if (Ready.peek().Burst < cpu.currentProcess.Burst && cpu.currentProcess.Burst > 0) {
                    overhead = ContextSwith;
                    while (overhead != 0) { // when overhead has value increase (waiting time & receving time by 1) & decrease overhead by 1
                        overhead--;
                        increaseWatingTime();
                        Time++;
                        recevingTime(Time);
                    }
                    insertInPQ(cpu.currentProcess); 
                    cpu.currentProcess = Ready.poll(); //take first value in queue in put in currentProcess
                }
            }
            if (cpu.currentProcess.Burst < 0) { //when burst less than 0 don't make any operations
                if (!Ready.isEmpty()) {
                    cpu.currentProcess = Ready.poll(); 
                }
            }
            if (cpu.currentProcess.Burst == 0) { //when burst equal 0 then put this process in completed queue 
                insertIntoCompletedQ(cpu.currentProcess, Time);
                if (Ready.isEmpty()) { 
                    cpu.currentProcess = new Process();
                } else { //if ready not empty take the first value in the queue
                    cpu.currentProcess = Ready.poll();
                }
            }
            if (cpu.currentProcess.Burst > 0) { //when the burst greater than 0 (increase wating & decrease burst)
                cpu.currentProcess.Burst--;
                increaseWatingTime();
            }
            Time++;
        }
        outPut();
    }

    static void insertIntoCompletedQ(Process p, int T) {
        completed.offer(p); 
        p.Completion = T;
        p.setTurnaround(); 
    }

    static void recevingTime(int arrival_T) {
        for (int i = 0; i < processNumber; i++) {
            if (Processes[i].Arrival == arrival_T) {
                insertInPQ(Processes[i]);
            }
        }
    }

    static void insertInPQ(Process p) {
        if (Ready.isEmpty()) {
            Ready.offer(p);
            return;
        }
        Process hold;
        boolean inserted = false;
        int n = Ready.size();
        for (int i = 0; i < n; i++) {
            hold = Ready.poll();
            if (p.Burst < hold.Burst && !inserted) {
                inserted = true;
                Ready.offer(p);
            }
            Ready.offer(hold);
        }
        if (!inserted) {
            Ready.offer(p);
        }
    }

    private static void OrderFC() {
        for (int i = 0; i < processNumber - 1; i++) {
            for (int j = 0; j < processNumber - 1; j++) {
                if (Processes[j].Arrival > Processes[j + 1].Arrival) { //compare arrival time 
                    Process hold = Processes[j];
                    Processes[j] = Processes[j + 1];
                    Processes[j + 1] = hold;
                }
            }
        }
    }

    public void inputs() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of Processes: ");
        processNumber = input.nextInt();
        Initialize();
        input.nextLine();
        out.println("Process_Name -> Arrival_time -> Burst_time ");
        for (int i = 0; i < processNumber; i++) {
            String in = input.nextLine();
            String[] s = in.split(" ");
            Processes[i].name = s[0];
            Processes[i].Arrival = (Integer.parseInt(s[1]));
            Processes[i].Burst = (Integer.parseInt(s[2]));
        }
        System.out.println("Context Switching: ");
        ContextSwith = input.nextInt();
    }

    static void Initialize() {
        Processes = new Process[processNumber];
        for (int i = 0; i < processNumber; i++) {
            Processes[i] = new Process();
        }
    }

    static void outPut() {
        System.out.println("\n");
        System.out.println("p_name   p_completionTime   p_turnarroundTime   p_waitingTime");
        for (Process p : completed) {
            System.out.println(p.name + "\t\t" + p.Completion + "\t\t" + p.Turnaround + "\t\t" + p.Waited);
        }
        System.out.println("Average Waiting Time: ");
        System.out.println(" =" + AverageWaitingTime());
        System.out.println("Average Turnaround Time: ");
        System.out.println(" =" + AverageTurnaround());
        out.println();
        for (Process p : completed) {
            System.out.print(p.name + " Waited for " + p.Waited);
            System.out.println(" with turnaround time of " + p.Turnaround);
        }
        out.println();
    }

    static float AverageTurnaround() {
        float sum = 0;
        for (Process p : completed) {
            sum += p.Turnaround;
        }
        return (float) sum / processNumber;
    }

    static float AverageWaitingTime() {
        float sum = 0;
        for (Process p : completed) {
            sum += p.Waited;
        }
        return (float) sum / processNumber;
    }

    static void increaseWatingTime() {
        for (Process p : Ready) {
            p.Waited++;
        }
    }
}
