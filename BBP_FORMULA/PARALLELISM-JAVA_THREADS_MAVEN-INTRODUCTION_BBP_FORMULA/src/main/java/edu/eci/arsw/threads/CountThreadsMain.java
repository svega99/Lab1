/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
    	CountThread ct= new CountThread(0,99);
    	CountThread ct2= new CountThread(99,199);
    	CountThread ct3= new CountThread(200,299);
    	
    	ct.run();
    	ct2.run();
    	ct3.run();
    }
    
}
