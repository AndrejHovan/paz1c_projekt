package sk.upjs.ed.persistent;

public class EntityNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 6896250580249471441L;
	
	private long entityId;

	public EntityNotFoundException(long entityId) {
		this.entityId = entityId;
	}

	public long getEntityId() {
		return entityId;
	}
}
