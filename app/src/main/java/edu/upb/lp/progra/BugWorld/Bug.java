package edu.upb.lp.progra.BugWorld;

public class Bug {
	private int hunger;
	private int fun;
	
	private int age;
	private boolean dead;
	
	private Cell cell;
	
	public Bug(Cell cell) {
		this.cell = cell;
		this.hunger = 0;
		this.fun = 15;
		this.age = 0;
		this.dead = false;
	}
	
	
	public int getHunger() {
		return hunger;
	}


	public int getFun() {
		return fun;
	}


	public int getAge() {
		return age;
	}


	public boolean isDead() {
		return dead;
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	public void day() {
		if (!dead) {
			hunger++;
			age++;
			fun--;
			if (hunger > 10) {
				tryToEat();
			} else {
				tryToPlay();
			}
			if (age >= 30) {
				die("old age");
			} else if (hunger == 20) {
				die("hunger");
			} else if (fun == 0) {
				die("boredom");
			}
		}
	}
	
	public void tryToEat() {
		if (cell.tryToEat()) {
			hunger = 0;
		}
	}
	
	public void tryToPlay() {
		if (cell.hasCloseFriend()) {
			if (fun < 19) {
				fun+=2;
			} else if (fun == 19) {
				fun = 20;
			}
			if (fun == 20) {
				double randomBaby = Math.random();
				if (randomBaby > 0.9) {
					cell.tryToHaveBaby();
				}
			}
		}
	}
	
	public void die(String reason) {
		dead = true;
		cell.bugDied(reason);
	}
	
	public int price() {
		if (dead) {
			return 0;
		} else if (age < 15) {
			return age;
		} else {
			return 30 - age;
		}
	}
}
