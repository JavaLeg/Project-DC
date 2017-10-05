package State;

public class MoveRandom extends MoveBehaviour {
	
	public MoveRandom() {
		
	}
	
	
	@Override
	public Coordinates nextStep(State s, Coordinates currentPos) {
		
		return currentPos;
	}
	
	
}
