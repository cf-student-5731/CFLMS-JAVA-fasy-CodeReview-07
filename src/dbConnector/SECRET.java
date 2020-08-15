package dbConnector;

//change this for your local settings
public class SECRET {
	private final String url ="jdbc:mysql://localmaria:3306/CR7_fasy?useTimezone=true&serverTimezone=UTC";
	private final String user ="java";
	private final String password ="java123";

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
