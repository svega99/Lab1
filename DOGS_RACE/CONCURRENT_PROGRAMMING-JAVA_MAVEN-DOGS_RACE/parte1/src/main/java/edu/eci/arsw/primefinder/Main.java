package edu.eci.arsw.primefinder;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
	
	private boolean pausa=false;

	public static void main(String[] args) {
		
		PrimeFinderThread pft=new PrimeFinderThread(0, 10000000);
		PrimeFinderThread pft2=new PrimeFinderThread(10000001, 20000000);
		PrimeFinderThread pft3=new PrimeFinderThread(20000001, 30000000);
		pft.start();	
		pft2.start();
		pft3.start();
		
		
	}
	
	new Timer().schedule(new TimerTask() {
	    @Override
	    public void run() {
	        if (!pausa) {
	        	
	        }
	    }
	}, 5000);
}
