import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class AlarmDisplay extends JPanel{
	private int hr;
	private int[] alarm;
	private JLabel[] alarmLabel;
	private	JPanel[] alarmPanel;
	private JLabel[] colonLabel;
	private JPanel[] colonPanel;
	
	private boolean is24hr;
	private boolean pm;
	private JLabel ampm;
	private JPanel ampmPanel;
	
	private JTextField[] alarmInput;
	private Thread alarmDialog;
	
	public AlarmDisplay(){
		hr = 0;
		//allocate space for everything
		alarm = new int[3];
		alarmLabel = new JLabel[3];
		alarmPanel = new JPanel[3];
		colonLabel = new JLabel[2];
		colonPanel = new JPanel[2];
		
		//set up the main panel (this object)
		setPreferredSize(new Dimension(600, 60));
		setBackground(Color.CYAN);
		
		//set everything up
		for(int i = 0; i < 3; i++){
			//initialize time to 0:0:0
			alarm[i] = 0;
			//set up the time label
			alarmLabel[i] = new JLabel(Integer.toString(alarm[i]));
			alarmLabel[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
			//set up the time panel
			alarmPanel[i] = new JPanel();
			alarmPanel[i].setPreferredSize(new Dimension(50, 50));
			alarmPanel[i].setBackground(Color.CYAN);
			alarmPanel[i].add(alarmLabel[i]);

			//add it to the frame
			add(alarmPanel[i]);
			
			//set up the colon label and panel (only 2 of these)
			if(i < 2){
				//set up the colon label
				colonLabel[i] = new JLabel(":");
				colonLabel[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
				//set up the colon panel
				colonPanel[i] = new JPanel();
				colonPanel[i].setPreferredSize(new Dimension(13, 50));
				colonPanel[i].setBackground(Color.CYAN);
				colonPanel[i].add(colonLabel[i]);
				//add it to the frame
				add(colonPanel[i]);
			}
		}
		//set up ampm/24hr stuff
		ampm = new JLabel("AM");
		ampm.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		ampmPanel = new JPanel();
		ampmPanel.setPreferredSize(new Dimension(50, 30));
		ampmPanel.setBackground(Color.CYAN);
		ampmPanel.add(ampm);
		add(ampmPanel);
	}
	
	public void update24hr(){
		modifyHour();
		if(is24hr){
			ampm.setText("");
		}
		else{
			if(pm){
				ampm.setText("PM");
			}
			else{
				ampm.setText("AM");
			}
		}
	}
	
	//change a 24hr to 12hr based on the mode
	private void modifyHour(){
		if(!is24hr){
			if(hr >= 12){
				pm = true;
			}
			else{
				pm = false;
			}
			//0 -> 12 am
			if(hr == 0){
				alarm[0] = 12;
			}
			//1-12 -> 1-11 am 12 pm
			else if(hr > 0 && hr <= 12){
				alarm[0] = hr;
			}
			//13-23 - > 1-12
			else if(hr > 12 && hr <= 23){
				alarm[0] = hr - 12;
			}
		}
		//24 hour clock doesn't change
		else{
			alarm[0] = hr;
		}
		alarmLabel[0].setText(Integer.toString(alarm[0]));
	}	
	
	//Activate set time mode
	public void setAlarm(){
		//create textfields
		alarmInput = new JTextField[3];
		for(int i = 0; i < 3; i++){			
			alarmInput[i] = new JTextField();
			alarmInput[i].setPreferredSize(new Dimension(50, 50));
			alarmInput[i].addActionListener(new TextFieldListener());

			alarmPanel[i].remove(alarmLabel[i]);
			alarmPanel[i].add(alarmInput[i]);
		}
		repaint();
		revalidate();
	}
	
	//For setting the time
	private class TextFieldListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			for(int i = 0; i < 3; i++){
				if(alarmInput[i].equals(e.getSource())){
					alarm[i] = stringToInt(alarmInput[i].getText());
					//set hour member if it's hours
					if(i == 0){
						alarm[i] %= 24;
						hr = alarm[i];
						update24hr(); 
					}
					else{
						alarm[i] %= 60;
					}
					alarmPanel[i].remove(alarmInput[i]);
					alarmPanel[i].add(alarmLabel[i]);
					alarmLabel[i].setText(Integer.toString(alarm[i]));
					break;
				}
			}
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
	
	//checks the time to see if it matches the alarm
	public void checkAlarm(int h, int m, int s){
		if(alarm[0] == h && alarm[1] == m && alarm[2] == s){
			alarm();
		}
	}
	
	//sets off the alarm
	public void alarm(){
		AlarmDialogThread t = new AlarmDialogThread();
	}
	
	public class AlarmDialogThread implements Runnable{
		private Thread t;
		public AlarmDialogThread(){
			start();
		}
		public void start(){
			t = new Thread(this);
			t.start();
		}
		public void run(){
			JOptionPane.showMessageDialog(null, "ALARM!!!", "ALARM!!!", JOptionPane.WARNING_MESSAGE);
		}
		public void stop(){
			t = null;
		}
	}
	
	//getters
	public int getHrs(){ return alarm[0]; }
	public int getMin(){ return alarm[1]; }
	public int getSec(){ return alarm[2]; }
	public boolean get24hr(){ return is24hr; }
	//setters
	public void setHrs(int h){ alarm[0] = h; }
	public void setMin(int m){ alarm[1] = m; }
	public void setSec(int s){ alarm[2] = s; }
	public void set24hr(boolean b){ is24hr = b; update24hr(); }
}
