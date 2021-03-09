/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osassigment3;

/**
 *
 * @author Ahmed
 */import java.util.*;

public class MultiLevel {
    int Quantum;
    int ProcessesNum;
    double averageWaitingTime =0.0;
    double averageTurnAroundTime=0.0;
    int currentTime=0;

    ArrayList<multilevelProcess> processesList = new ArrayList<multilevelProcess>();
    ArrayList<multilevelProcess> excudeOrder = new ArrayList<multilevelProcess>(); 
    Queue<multilevelProcess>RRqueue = new LinkedList<multilevelProcess>();
    Queue<multilevelProcess>FCFSqueue = new LinkedList<multilevelProcess>();
    public static class sortByArrivalTime implements Comparator<multilevelProcess> {

        @Override
        public int compare(multilevelProcess o1, multilevelProcess o2) { 
            if(o1.arrivalTime - o2.arrivalTime == 0){
                return o1.queueNumber - o2.queueNumber;
            }
            return o1.arrivalTime - o2.arrivalTime;

        }
    }


    void Inputs(){ 
        Scanner input = new Scanner(System.in);
        System.out.println("Please Enter number of processes");
        ProcessesNum = input.nextInt();
        
        System.out.println("Please Enter process name - Burst time - arrival time - Queue Number"); 
        for (int i = 0; i < ProcessesNum; i++) {
            String processName = input.next();
            multilevelProcess p = new multilevelProcess();
            p.name = processName;
            p.CopyBurstTime = input.nextInt();
            p.burstTime=p.CopyBurstTime;
            Integer n = input.nextInt();
            p.arrivalTime=n;
            p.queueNumber = input.nextInt();
            processesList.add(p);
        }      
        System.out.println("Please Enter Time Quantum");
        Quantum = input.nextInt();
        
        Collections.sort(processesList, new MultiLevel.sortByArrivalTime());

    }
    public void MultiLevelRun(){
       Inputs();
        execute();
        print();

    }
    
    void execute(){   
        int quantumTimer=0;
        multilevelProcess prevProcess = null; 
        for (currentTime = 0; ProcessesNum >0 ; currentTime++) {
            for (int i = 0; i < processesList.size(); i++) { 
                    multilevelProcess p = processesList.get(i);
                    if(p.arrivalTime==currentTime){
                        if(p.queueNumber==1){
                            RRqueue.add(p);
                        }else{
                            FCFSqueue.add(p);
                        }
                    }
               }
            if(prevProcess!=null){
                RRqueue.add(prevProcess);
                prevProcess=null;
            }
            if(RRqueue.size()>0) {
                multilevelProcess p = RRqueue.peek();
                if(excudeOrder.size()==0 || excudeOrder.get(excudeOrder.size()-1)!=p){ 
                	excudeOrder.add(p);
                }
                   p.CopyBurstTime--;
                    quantumTimer++;
                    if(p.CopyBurstTime==0){ 
                        p.turnAroundTime=(currentTime+1) - p.arrivalTime;
                        p.waitingTime=p.turnAroundTime - p.burstTime;
                        quantumTimer=0;
                        prevProcess = null;
                        RRqueue.poll();
                        ProcessesNum--;
                        processesList.set(processesList.indexOf(p),p) ; 
                        continue;
                    }
                  
                    else if (currentTime > 0 && quantumTimer % Quantum == 0) {
                        if(RRqueue.size()>1){
                            prevProcess = p;
                            
                           RRqueue.poll();
                           continue;
                       }

                    }



            }
            else if(FCFSqueue.size()>0){  
                quantumTimer=0; 
                multilevelProcess p = FCFSqueue.peek();
                if(excudeOrder.size()==0 || excudeOrder.get(excudeOrder.size()-1)!=p){
                	excudeOrder.add(p);
                }
                p.CopyBurstTime--;
                if(p.CopyBurstTime==0){ 
                    p.turnAroundTime=(currentTime+1) - p.arrivalTime;
                    p.waitingTime=p.turnAroundTime - p.burstTime;
                    processesList.set(processesList.indexOf(p),p) ; 
                    ProcessesNum--;
                    FCFSqueue.poll();
                }
                    continue;
            }
        }
    }

    void print(){
        for (int i = 0; i < excudeOrder.size(); i++) {
            System.out.print(excudeOrder.get(i).name + "  ");
        }
        System.out.println();

        System.out.println("Process Name   Waiting Time   Turnaround Time");
        for (int i = 0; i < processesList.size(); i++) {
            multilevelProcess p = processesList.get(i);
            averageTurnAroundTime+=p.turnAroundTime;
            averageWaitingTime+=p.waitingTime;
            System.out.println(" "+p.name+"         "+p.waitingTime+"         "+p.turnAroundTime);
        }

        averageTurnAroundTime/=  processesList.size();
        averageWaitingTime /= processesList.size();

        System.out.println("Average Turnaround Time = " + averageTurnAroundTime);
        System.out.println("Average WaitingTime = " + averageWaitingTime);
    }
}