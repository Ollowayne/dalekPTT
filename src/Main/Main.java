package Main;


public class Main {

	public static void main(String[] args) {
		System.out.println(RepositoryHelper.getRepositories("Ollowayne").get(0).getOwner().getAvatarUrl());	
	}

}