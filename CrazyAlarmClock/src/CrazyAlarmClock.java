import javax.swing.*;
import java.awt.*;

public class CrazyAlarmClock{
	public static void main(String[] args){
		//set up the frame for the clock
		JFrame clock = new JFrame();
		clock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clock.setPreferredSize(new Dimension(600, 300));
		clock.setBackground(Color.CYAN);
		
		//set up the clock
		Clock cl = new Clock();
		
		//add the clock to the frame
		clock.getContentPane().add(cl);
		clock.pack();
		clock.setVisible(true);
		cl.update();
		
		//run a loop for the timer
		while(true){
			try{
				Thread.sleep(1000);
				cl.update();
			}
			catch(InterruptedException e){}
		}
	}
}
