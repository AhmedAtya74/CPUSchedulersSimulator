package osassigment3;


import java.io.IOException;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HP
 */
public class PPS {
    public static void priority() throws IOException
    {
        int number_of_process;
		float avg_turnaround_time;
		float avg_waiting_time;
		int total_turnaround_time = 0;
		int total_waiting_time = 0;

		//***************************************
		System.out.print("Enter the number of processes: ");
		number_of_process = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
		Process[] p = osassigment3.Arrays.initializeWithDefaultProcessInstances(number_of_process);
		int[] burst_remaining = new int[number_of_process];
		int[] is_completed = new int[number_of_process];
//C++ TO JAVA CONVERTER TODO TASK: The memory management function 'memset' has no equivalent in Java:
		Arrays.fill(is_completed, 0); 


		for (int i = 0; i < number_of_process; i++)
		{
			System.out.print("Enter arrival time of process ");
			System.out.print(i + 1);
			System.out.print(": ");
			p[i].Arrival = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
			System.out.print("Enter burst time of process ");
			System.out.print(i + 1);
			System.out.print(": ");
			p[i].Burst = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
			System.out.print("Enter priority of the process ");
			System.out.print(i + 1);
			System.out.print(": ");
			p[i].priority = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
			p[i].pid = i + 1;
			burst_remaining[i] = p[i].Burst;
			System.out.print("\n");
		}

		int current_time = 0;
		int completed = 0;
		int prev = 0;

		while (completed != number_of_process)
		{
			int idx = -1;
			int mx = -1;
			for (int i = 0; i < number_of_process; i++)
			{
				if (p[i].Arrival<= current_time && is_completed[i] == 0)
				{
					if (p[i].priority > mx)
					{
						mx = p[i].priority;
						idx = i;
					}
					if (p[i].priority == mx)
					{
						if (p[i].Arrival< p[idx].Arrival)
						{
							mx = p[i].priority;
							idx = i;
						}
					}
				}
			}

			if (idx != -1)
			{
				if (burst_remaining[idx] == p[idx].Burst)
				{
					p[idx].start_time = current_time;
				}
				burst_remaining[idx] -= 1;
				current_time++;
				prev = current_time;

				if (burst_remaining[idx] == 0)
				{
					p[idx].Completion = current_time;
					p[idx].Turnaround = p[idx].Completion - p[idx].Arrival;
					p[idx].Waited = p[idx].Turnaround - p[idx].Burst;

					total_turnaround_time += p[idx].Turnaround;
					total_waiting_time += p[idx].Waited;

					is_completed[idx] = 1;
					completed++;
				}
			}
			else
			{
				 current_time++;
			}
		}

		int min_arrival_time = 10000000;
		int max_completion_time = -1;
		for (int i = 0; i < number_of_process; i++)
		{
			min_arrival_time = Math.min(min_arrival_time,p[i].Arrival);
			max_completion_time = Math.max(max_completion_time,p[i].Completion);
		}

		avg_turnaround_time = (float) total_turnaround_time / number_of_process;
		avg_waiting_time = (float) total_waiting_time / number_of_process;

		System.out.print("\n");
		System.out.print("\n");

		System.out.print("#Process Name\t");
		System.out.print("Wait Time\t");
		System.out.print("Turn Around Time\t");
		System.out.print("\n");
		System.out.print("\n");

		for (int i = 0; i < number_of_process; i++)
		{
			System.out.print(p[i].name);
			System.out.print("\t\t");
			System.out.print(p[i].Waited);
			System.out.print("\t\t");
			System.out.print(p[i].Turnaround);
			System.out.print("\t");
			System.out.print("\n");
			System.out.print("\n");
		}
		System.out.print("Average Turnaround Time = ");
		System.out.print(avg_turnaround_time);
		System.out.print("\n");
		System.out.print("Average Waiting Time = ");
		System.out.print(avg_waiting_time);
		System.out.print("\n");
    }
}
