package Github;


public class Repository {

	private User m_owner;
	
	public Repository() {
		m_owner = new User();
	}

	public User getOwner() {
		return m_owner;
	}
	public void setOwner(User owner) {
		m_owner = owner;
	}
}
