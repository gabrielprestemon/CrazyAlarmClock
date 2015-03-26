import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Clock extends JPanel{
	private ClockDisplay cd;
	private AlarmDisplay ad;
	
	private JCheckBox select24hr;
	
	private JButton setTime;
	private JButton setAlarm;

	public Clock(){
		cd = new ClockDisplay();
		ad = new AlarmDisplay();
		
		//set up the main panel (this object)
		setPreferredSize(new Dimension(600, 300));
		setBackground(Color.CYAN);
		add(cd);
		
		//set up and add buttons for set options
		setTime = new JButton();
		setTime.setPreferredSize(new Dimension(200, 60));
		setTime.setText("SET TIME");
		setTime.addActionListener(new ButtonListener());
		setAlarm = new JButton();
		setAlarm.setPreferredSize(new Dimension(200, 60));
		setAlarm.setText("SET ALARM");
		setAlarm.addActionListener(new ButtonListener());
		add(setTime);
		add(setAlarm);
		
		//set up ampm/24 checkbox stuff
		select24hr = new JCheckBox();
		select24hr.setText("24 hr cycle");
		select24hr.setBackground(Color.CYAN);
		select24hr.addActionListener(new CheckBoxListener());
		add(select24hr);
		
		add(ad);
	}
	
	public void update(){
		cd.update();
		ad.checkAlarm(cd.getHrs(), cd.getMin(), cd.getSec());
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(setTime.equals(e.getSource())){
				cd.setTime();
			}
			else if(setAlarm.equals(e.getSource())){
				ad.setAlarm();
			}
		}
	}
	private class CheckBoxListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			cd.set24hr(select24hr.isSelected());
			ad.set24hr(select24hr.isSelected());
		}
	}
}
