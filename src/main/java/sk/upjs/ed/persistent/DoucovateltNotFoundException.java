package sk.upjs.ed.persistent;

public class DoucovateltNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 6896250580249471441L;
	
	private long doucovatelId;

	public DoucovateltNotFoundException(long doucovatelId) {
		this.doucovatelId = doucovatelId;
	}

	public long getParticipantId() {
		return doucovatelId;
	}
}
