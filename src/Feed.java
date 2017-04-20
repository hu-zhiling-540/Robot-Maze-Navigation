import java.util.Timer;
import java.util.TimerTask;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * the Feed class implements Behavior,
 * and uses a light sensor to check and see if our robot is over a food source
 * @author Guest
 *
 */
public class Feed implements Behavior {
	
	public LightSensor light; 
	public DifferentialPilot robot;
	
	private long lastFeed = 0;
	
	/**
	 * constructor creates a light sensor object attached to the specified port, 
	 * and sets flood lighting on or off.
	 */
	public Feed(LightSensor light, DifferentialPilot robot)	{
		
		this.robot = robot;
		this.light = light;
		this.light.setFloodlight(true);

		//System.out.println("feed");
	}


	/** 
	 * method allows robot to stop and feed once it's over a food source. 
	 * if a light sensor has a brightness value between 0 and 100%, with
	 * 0 = darkness and 100 = intense sunlight (if sensors can detect and object),
	 * then it feeds for 3 seconds
	 */
	@Override
	public void action() { 
		
		long rn = System.currentTimeMillis();
		
		try {		
			if ((rn-lastFeed) >= 5000)	{
				Thread.yield();
				Thread.sleep(3000); // stops for a short time (3 seconds)
			}
			lastFeed = System.currentTimeMillis(); 	// updates feed finish time
		}
		catch(InterruptedException ie) {}
	}

	@Override
	public boolean takeControl() {
		// if the light source is no smaller than 80
		if (light.readValue() >= 80)	{
			System.out.println("Yes");
			return true;
		}
		return false;
	}

	@Override
	public void suppress() {
		robot.stop();	
	}
}
