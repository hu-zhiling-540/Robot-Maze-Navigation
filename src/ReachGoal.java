import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import lejos.nxt.ColorSensor.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;


/**
 * The goal class implements Behavior, 
 * plays a short sample music when reaching the goal,
 * generates the final path ,
 * and travels back to the starting cell
 *
 */
public class ReachGoal implements Behavior{

	public DifferentialPilot robot;
//	protected boolean isGoal;
	// light sensor!!!!!
	private static final short[] note = {2349,115, 0,5, 1760,165, 0,35};
	public Explore explore;
	public LightSensor light;
	
	
	public ReachGoal(DifferentialPilot robot, LightSensor light, Explore explore)		{
		this.robot = robot;
		this.light = light;
		this.explore = explore;
		System.out.println("Goal");
		
	}
	
	
	@Override
	public boolean takeControl() {
		if(light.readValue() >= 40 && light.getFloodlight() == Color.WHITE ){ 
			return true;
		}
		return false;
	}

	
	@Override
	public void action() {
		
		// update the flag in explore class
		explore.reachGoal = true;
		
		for(int i=0;i <note.length; i+=2) {
			short w = note[i+1];
			int n = note[i];
			if (n != 0) {
				Sound.playTone(n, w*10);
			}
			
			try {
				Thread.sleep(w*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void suppress() {
		robot.stop();	
		
	}

}
