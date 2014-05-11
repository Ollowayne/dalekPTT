package Main;

import java.util.LinkedList;

import Github.Repository;

public class DataMain {

	public static void main(String[] args) {
		LinkedList<Repository> repos = RepositoryHelper.getRepositories("TheSumm");
		if(repos.size() > 0)
			System.out.println(repos.get(0).getOwner().getAvatarUrl());
		else
			System.out.println("No repositories loaded..");
	}

}