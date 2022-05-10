package edu.upb.lp.progra.BugWorld;
public class Cell {
	private BugWorld world;

	private Bug bug;
	private int food;
	private int h;
	private int v;

	public Cell(int v, int h, BugWorld world) {
		this.h = h;
		this.v = v;
		this.world = world;
	}

	public boolean isEmpty() {
		return bug == null && food == 0;
	}

	public int getHorizontal() {
		return h;
	}

	public int getVertical() {
		return v;
	}

	public Bug getBug() {
		return bug;
	}

	public boolean isBugAlive() {
		return bug != null && !bug.isDead();
	}

	public void setBug(Bug bug) {
		this.bug = bug;
		if (bug != null) {
			bug.setCell(this);
		}
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public boolean eat() {
		if (food > 0) {
			food--;
			return true;
		} else {
			return false;
		}
	}

	public boolean tryToEat() {
		return world.tryToEat(v, h);
	}

	public void day() {
		if (getBug() != null) {
			getBug().day();
		}
	}

	public void bugDied(String reason) {
		world.bugDied(v, h, reason);
	}

	public void createBug() {
		bug = new Bug(this);
	}

	public boolean hasCloseFriend() {
		return world.hasCloseFriend(v, h);
	}

	public void tryToHaveBaby() {
		world.tryToHaveBaby(v, h);
	}
}
