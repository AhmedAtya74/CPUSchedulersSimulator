package osassigment3;

import static java.lang.Integer.min;
import static java.lang.Integer.max;
import java.util.Scanner;

public class RR {

    int cal_cycle(int MAX, int Quantum) {
        if (MAX % Quantum == 0) {
            return MAX / Quantum;
        }

        return (MAX / Quantum) + 1;
    }

    public void sort_based_arrival_time(String process_name[], int burst_time[], int arrival_time[], int n) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arrival_time[i] > arrival_time[j]) {
                    int temp = arrival_time[i];
                    arrival_time[i] = arrival_time[j];
                    arrival_time[j] = temp;

                    String str = process_name[i];
                    process_name[i] = process_name[j];
                    process_name[j] = str;

                    temp = burst_time[i];
                    burst_time[i] = burst_time[j];
                    burst_time[j] = temp;
                }
            }

        }
    }

    public void execute(String process_name[], int burst_time[], int arrival_time[], int n, int MAX_burst, int quantum_time, int switching_time) {

        int cycle = cal_cycle(MAX_burst, quantum_time);

        sort_based_arrival_time(process_name, burst_time, arrival_time, n);

        int burst_time_temp[] = new int[n];

        System.arraycopy(burst_time, 0, burst_time_temp, 0, n);

        int waiting_time[] = new int[n];

        int turn_around_time[] = new int[n];

        int limit = arrival_time[0];

        int waiting_sum = 0, turn_around_sum = 0;

        for (int i = 0; i < cycle; i++) {
            for (int j = 0; j < n; j++) {
                if (burst_time_temp[j] > 0) {
                    int start = limit;
                    limit += min(quantum_time, burst_time_temp[j]) + switching_time;

                    burst_time_temp[j] -= min(quantum_time, burst_time_temp[j]);

                    System.out.print("Process Name: ");
                    System.out.println(process_name[j]);

                    System.out.print("start Time: ");
                    System.out.println(start);

                    System.out.print("limit Time: ");
                    System.out.println(limit);

                    if (burst_time_temp[j] == 0) {
                        waiting_time[j] = limit - arrival_time[j] - burst_time[j];
                        waiting_sum += waiting_time[j];
                        turn_around_time[j] = waiting_time[j] + burst_time[j];
                        turn_around_sum += turn_around_time[j];
                    }
                }
            }
        }

        print(process_name, burst_time, arrival_time, n, turn_around_time, waiting_time, waiting_sum, turn_around_sum);
    }

    public void print(String process_name[], int burst_time[], int arrival_time[], int n, int turn_around_time[], int waiting_time[], int waiting_sum, int turn_around_sum) {
        for (int i = 0; i < n; i++) {
            System.out.print(process_name[i]);
            System.out.print("\t");

            System.out.print(burst_time[i]);
            System.out.print("\t");

            System.out.print(arrival_time[i]);
            System.out.print("\t");

            System.out.print(waiting_time[i]);
            System.out.print("\t");

            System.out.println(turn_around_time[i]);

        }

        System.out.println("Waiting Time AVG: " + waiting_sum * 1.0f / n);
        System.out.println("Waiting Time AVG: " + turn_around_sum * 1.0f / n);
    }

    public void printRR() {
        System.out.println("Enter Number Of Process");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        String process_name[] = new String[n];
        int burst_time[] = new int[n];
        int arrival_time[] = new int[n];
        int MAX_burst = (int) -1e5;
        for (int i = 0; i < n; i++) {
            System.out.println("Enter Name Of Process");
            process_name[i] = scanner.next();
            System.out.println("Enter Burst Time Of Process");
            burst_time[i] = scanner.nextInt();
            MAX_burst = max(MAX_burst, burst_time[i]);
            System.out.println("Enter Arrive Time Of Process");
            arrival_time[i] = scanner.nextInt();
        }
        System.out.println("Enter Quantum Time");
        int quantum_time = scanner.nextInt();
        System.out.println("Enter Context switching Time");
        int switching_time = scanner.nextInt();
        execute(process_name, burst_time, arrival_time, n, MAX_burst, quantum_time, switching_time);
    }
}
