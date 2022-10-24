package Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Simulation {
	
	//data members
	private static Scanner keyboard;
	private static WaitingQueue<Event> waitingQueue;
	private static PriorityQueue<Event> eventQueue;
	
	private static int totalTime;
	private static double unitTime;
	private static int aveConTime;
	private static int numOfModem;
	private static int sizeOfWaitingQueue;
	
	private static int timeDone;
	private static int totalWaitTime;

	private static int clock;
	private static int modemRunningTotal;

	private static int modemAvailable;
	private static int numOfDial;
	private static int countWaiting;
	private static int id;
	
	//private static File file;
	private static PrintWriter output;

	public static void main(String[] args) {
		
		eventQueue = new PriorityQueue<>();
		waitingQueue = new WaitingQueue<>();
		
		//input parameters
		enterData();
		
		
		runSimulation();
		
		
		System.out.println("totalwaiting time: " + totalWaitTime);
		System.out.println("countWaiting: " + countWaiting);
		System.out.println("average wait time: " + (double)totalWaitTime / countWaiting);
		System.out.println("Percentage of time modems were busy = " + (double)modemRunningTotal / (numOfModem * totalTime) * 100) ;
		
		
		//toReport();
	}
	
	/**
	 *  get the input data
	 *  @param  none
	 *  @return none
	 */
	private static void enterData() {
		keyboard = new Scanner(System.in);
		System.out.print("Enter the length of simulation: ");
		totalTime = keyboard.nextInt();
		System.out.println();
		System.out.print("Enter the average time between dial-in attempts: ");
		unitTime = keyboard.nextDouble();
		System.out.println();
		System.out.print("Enter the average connection time: ");
		aveConTime = keyboard.nextInt();
		System.out.println();
		System.out.print("Enter the number of modems: ");
		numOfModem = keyboard.nextInt();
		System.out.println();
		System.out.print("Enter the size of the waiting queue, -1 for an infinite queue: ");
		sizeOfWaitingQueue = keyboard.nextInt();
		System.out.println();
	}
	
	/**
	 *  write a report to file
	 *  @param  none
	 *  @return none
	 */
	private static void toReport() {
		try {
			output = new PrintWriter("report.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output.println(
				  "1- Length of simulation\n"
				+ "2- Average time between dial-in attempts\n"
				+ "3- Average connection time\n"
				+ "4- Number of modems in the bank\n"
				+ "5- Size of the waiting queue, -1 for an infinite queue\n"
				+ "6- Averge wait time\n"
				+ "7- Percentage of time modems were busy\n"
				+ "8- Number of customers left in the waiting queue"
				);
	}
	
	/**
	 *  Initialize eventQueue with dial-in attempts
	 *  @param  none
	 *  @return none
	 */
	private static void iniEveQueue() {
		
		numOfDial = pGenNumOfCustomer();
		for(int i = 0; i < numOfDial; i++) {
			eventQueue.offer(new Event(clock, id, "dialIn"));
			id++;
		}	
		
		modemAvailable = numOfModem - numOfDial;
		if(modemAvailable < 0)
			modemAvailable = 0;
	}
	
	/**
	 *  run simulation
	 *  @param  none
	 *  @return none
	 */
	private static void runSimulation() {
		
		for(clock = 0; clock < totalTime; clock++) {
			
			//initialize eventQueue
			iniEveQueue();
			
			//connection time
			timeDone = pGenConnectionTime();
			
			if(clock >= timeDone)
				startModem();
		}
	}

	/**
	 *  selects a queue and update the waitingQueue and eventQueue
	 *  @param  none
	 *  @return none
	 */
	private static void startModem() {
		
		while (!eventQueue.empty()) {
			
			Event event = eventQueue.remove();

			if(event.getType().equals("hangUp")) {
				modemAvailable++;
			}
			
			else if (event.getType().equals("dialIn")) {
				
				if((waitingQueue.size() < sizeOfWaitingQueue)) {
					waitingQueue.offer(event);
					//countWaiting++;
				}
				else {
					System.out.println("customer " + event.getID() +  " was denied service at time unit " + clock);
					event = null;
				}		
			}
		}
		
		while (modemAvailable > 0 && !waitingQueue.empty()) {
			Event event = waitingQueue.remove();
			countWaiting++;
			
			//System.out.println(event.toString());
			
			totalWaitTime += clock - event.getClock();
			
			//System.out.println(totalWaitTime);
			
			timeDone = pGenConnectionTime();
			//System.out.println("modem connecting time: " + timeDone);
			
			eventQueue.offer(new Event(clock + timeDone, 0, "hangUp"));
			
			if(clock + timeDone <= totalTime)
				modemRunningTotal += timeDone;
			else 
				modemRunningTotal += 0;
			
			modemAvailable--;

		}
	}
	
	private static int pGenNumOfCustomer() {
		Poisson gen = new Poisson (1/unitTime);
		return gen.nextInt(); 
	}

	private static int pGenConnectionTime() {
		Poisson gen = new Poisson (aveConTime);
		return gen.nextInt();
	}
	
}

