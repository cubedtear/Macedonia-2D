package net.m2d.main;


public class Logger {
	
	private Level level = Level.OFF;
	private String name;
	
	public Logger() {
	}
	
	public Logger(String name, Level level){
		this.name = name;
	}
	public Level getLevel() {
		return level;
	}

	public Logger setLevel(Level level) {
		this.level = level;
		return this;
	}

	public void log(String s, Level l){
		if(l.ordinal()==0) return;
		if(l.ordinal()>= this.level.ordinal()) logg(s);
	}
	
	private void logg(String s){
		System.out.println(name + ": " + s);
	}
	
	public enum Level{
		OFF, COMMENTS, DEBUG, RELEASE_DEBUG, ERROR, ALL
		// Cuanto m�s a la dcha. m�s se ve!
	} 

}


