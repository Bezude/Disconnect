/*
 *  Timer.java
 *  ECS 163 Final
 *  Alan Tai and Benjamin Roye
 *
 */
public class Timer
{
	private long lastCheckTime;
	
	public Timer(){
		lastCheckTime = System.nanoTime();
	}
	
	public long getNanoDelta(){
		long temp = System.nanoTime();
		long delta = temp - lastCheckTime;
		lastCheckTime = temp;
		return delta;
	}
	
	public void pushBack(long howFar) {
		lastCheckTime -= howFar;
	}
}
