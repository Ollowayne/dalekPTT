package src.Main;

public class DataMain {

	public static void main(String[] args) {
		System.out.println(RepositoryHelper.getRepositories("TheSumm").get(0).getOwner().getAvatarUrl());	
	}

}