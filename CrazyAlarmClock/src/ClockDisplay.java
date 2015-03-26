import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClockDisplay extends JPanel{
	private int hr;
	private int[] time;
	private JLabel[] timeLabel;
	private	JPanel[] timePanel;
	private JLabel[] colonLabel;
	private JPanel[] colonPanel;
	
	private boolean is24hr;
	private boolean pm;
	private JLabel ampm;
	private JPanel ampmPanel;
	
	private JTextField[] timeInput;
	
	public ClockDisplay(){
		hr = 0;
		//allocate space for everything
		time = new int[3];
		timeLabel = new JLabel[3];
		timePanel = new JPanel[3];
		colonLabel = new JLabel[2];
		colonPanel = new JPanel[2];
		
		//set up the main panel (this object)
		setPreferredSize(new Dimension(600, 110));
		setBackground(Color.CYAN);
		
		//set everything up
		for(int i = 0; i < 3; i++){
			//initialize time to 0:0:0
			time[i] = 0;
			//set up the time label
			timeLabel[i] = new JLabel(Integer.toString(time[i]));
			timeLabel[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 80));
			//set up the time panel
			timePanel[i] = new JPanel();
			timePanel[i].setPreferredSize(new Dimension(100, 100));
			timePanel[i].setBackground(Color.CYAN);
			timePanel[i].add(timeLabel[i]);

			//add it to the frame
			add(timePanel[i]);
			
			//set up the colon label and panel (only 2 of these)
			if(i < 2){
				//set up the colon label
				colonLabel[i] = new JLabel(":");
				colonLabel[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 80));
				//set up the colon panel
				colonPanel[i] = new JPanel();
				colonPanel[i].setPreferredSize(new Dimension(25,100));
				colonPanel[i].setBackground(Color.CYAN);
				colonPanel[i].add(colonLabel[i]);
				//add it to the frame
				add(colonPanel[i]);
			}
		}
		//set up ampm/24hr stuff
		ampm = new JLabel("AM");
		ampm.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		ampmPanel = new JPanel();
		ampmPanel.setPreferredSize(new Dimension(100, 50));
		ampmPanel.setBackground(Color.CYAN);
		ampmPanel.add(ampm);
		add(ampmPanel);
	}

	//change a 24hr to 12hr based on the mode
	private void modifyHour(){
		if(!is24hr){
			//0 -> 12 am
			if(hr == 0){
				time[0] = 12;
			}
			//1-12 -> 1-11 am 12 pm
			else if(hr > 0 && hr <= 12){
				time[0] = hr;
			}
			//13-23 - > 1-12
			else if(hr > 12 && hr <= 23){
				time[0] = hr - 12;
			}
		}
		//24 hour clock doesn't change
		else{
			time[0] = hr;
		}
	}
	
	//general update function
	public void update(){
		updateTime();
		updateDisplay();
	}
	
	// update the display
	public void updateDisplay(){
		//modify hours based on ampm or not
		modifyHour();
		
		//update display members
		for(int i = 0; i < 3; i++){
			timeLabel[i].setText(Integer.toString(time[i]));
		}
		//deal with ampm
		if(!is24hr){
			if(pm){
				ampm.setText("PM");
			}
			else{
				ampm.setText("AM");
			}
		}
		else{
			ampm.setText("");
		}
	}
	
	//updates the time
	public void updateTime(){
		//update seconds
		time[2]++;
		if(time[2] >= 60){
			//update minutes
			time[2] = 0;
			time[1]++;
		}
		if(time[1] >= 60){
			//update hours
			time[1] = 0;
			hr++;
		}
		//cycle hours
		if(hr >= 24){
			hr = 0;
		}
		//am hours
		if(hr >=  0 && hr < 12){
			pm = false;
		}
		else{
			pm = true;
		}
	}
	
	//Activate set time mode
	public void setTime(){
		//create textfields
		timeInput = new JTextField[3];
		for(int i = 0; i < 3; i++){			
			timeInput[i] = new JTextField();
			timeInput[i].setPreferredSize(new Dimension(100, 100));
			timeInput[i].addActionListener(new TextFieldListener());

			timePanel[i].remove(timeLabel[i]);
			timePanel[i].add(timeInput[i]);
		}
		repaint();
		revalidate();
	}
	
	//For setting the time
	private class TextFieldListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			for(int i = 0; i < 3; i++){
				if(timeInput[i].equals(e.getSource())){
					time[i] = stringToInt(timeInput[i].getText());
					//set hour member if it's hours
					if(i == 0){
						time[i] %= 24;
						hr = time[i];
					}
					else{
						time[i] %= 60;
					}
					timePanel[i].remove(timeInput[i]);
					timePanel[i].add(timeLabel[i]);
					break;
				}
			}
			updateDisplay();
			repaint();
			revalidate();
		}
		private int stringToInt(String s){
			int i;
			i = Character.getNumericValue(s.charAt(s.length() - 1));
			if(s.length() > 1){
				i += 10 * Character.getNumericValue(s.charAt(s.length() - 2));
			}
			return i;
		}
	}
	
	//getters
	public int getHrs(){ return time[0]; }
	public int getMin(){ return time[1]; }
	public int getSec(){ return time[2]; }
	public boolean get24hr(){ return is24hr; }
	//setters
	public void setHrs(int h){ time[0] = h; }
	public void setMin(int m){ time[1] = m; }
	public void setSec(int s){ time[2] = s; }
	public void set24hr(boolean b){ is24hr = b; }
}
