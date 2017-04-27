import lejos.nxt.Sound;
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
	protected boolean isGoal;
	// light sensor!!!!!
	private static final short[] note = {2349,115, 0,5, 1760,165, 0,35};
	
	
	public ReachGoal(DifferentialPilot robot, Boolean reachGoal)		{
		this.robot = robot;
		isGoal = reachGoal;
		System.out.println("Goal");
		
	}
	// http://www.lejos.org/nxt/nxj/tutorial/LCD_Sensors/LCD_Sensors.htm#5
	public void playSound() throws InterruptedException	{
		for(int i=0;i <note.length; i+=2) {
			short w = note[i+1];
			int n = note[i];
			if (n != 0) {
				Sound.playTone(n, w*10);
			}
			Thread.sleep(w*10);
		}
	}
	
	/**
	 * constructor
	 * @param robot
	 */
	public ReachGoal(DifferentialPilot robot)	{
		this.robot = robot;
		isGoal = true;
	}
	
	
	@Override
	public boolean takeControl() {
		return isGoal;
	}

	@Override
	public void action() {
		for(int i=0;i <note.length; i+=2) {
			short w = note[i+1];
			int n = note[i];
			if (n != 0) {
				Sound.playTone(n, w*10);
			}
			try {
				Thread.sleep(w*10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void suppress() {
		robot.stop();	
		
	}

}
