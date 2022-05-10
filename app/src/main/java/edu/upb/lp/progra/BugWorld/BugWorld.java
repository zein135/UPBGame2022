package edu.upb.lp.progra.BugWorld;

public class BugWorld {
	private int sizeHorizontal = 8;
	private int sizeVertical = 8;
	private Cell[][] cells = new Cell[sizeVertical][sizeHorizontal];

	private BugWorldUI ui;

	private int money = 100;

	private int foodPrice = 10;
	private int score;

	public BugWorld(BugWorldUI ui, int highScore, String highScoreName) {
		this.ui = ui;
		for (int horizontal = 0; horizontal < sizeHorizontal; horizontal++) {
			for (int vertical = 0; vertical < sizeVertical; vertical++) {
				cells[vertical][horizontal] = new Cell(vertical, horizontal,
						this);
				if (horizontal == sizeHorizontal / 2 - 1
						&& vertical == sizeVertical / 2 - 1) {
					cells[vertical][horizontal].createBug();
				} else if (horizontal == sizeHorizontal / 2
						&& vertical == sizeVertical / 2 - 1) {
					cells[vertical][horizontal].createBug();
				}
			}
		}
		score = 0;
	}

	public int getMoney() {
		return money;
	}

	public int getSizeHorizontal() {
		return sizeHorizontal;
	}

	public int getSizeVertical() {
		return sizeVertical;
	}

	public Cell getCell(int v, int h) {
		return cells[v][h];
	}

	public int getScore() {
		return score;
	}

	public int getFoodPrice() {
		return foodPrice;
	}

	public void buyFood() {
		if (money < foodPrice) {
			ui.showMessage("Cannot buy any more food!");
		} else {
			int h = (int) (Math.random() * (sizeHorizontal));
			int v = (int) (Math.random() * (sizeVertical));

			int currenth = h;
			int currentv = v;
			boolean found = false;
			do {
				if (cells[currentv][currenth].isEmpty()) {
					cells[currentv][currenth].setFood(20);
					money -= foodPrice;
					foodPrice++;
					day();
					found = true;
				} else {
					if (currenth == sizeHorizontal - 1) {
						currenth = 0;
						if (currentv == sizeVertical - 1) {
							currentv = 0;
						} else {
							currentv++;
						}
					} else {
						currenth++;
					}
				}
			} while ((currenth != h || currentv != v) && !found);
			if (!found) {
				ui.showMessage("No more room for food!");
			}
		}
	}

	public void moveBug(int v, int h, int destv, int desth) {
		Cell origin = cells[v][h];
		Cell destination = cells[destv][desth];
		if (origin.getBug() != null && destination.isEmpty()) {
			destination.setBug(origin.getBug());
			emptyCell(v, h);
			day();
		}
	}

	public void sellBug(int v, int h) {
		Cell cell = cells[v][h];
		if (cell.isBugAlive()) {
			Bug bug = cell.getBug();
			money += bug.price();
			emptyCell(v, h);
		}
		day();
	}

	public void emptyCell(int v, int h) {
		cells[v][h].setBug(null);
		cells[v][h].setFood(0);
	}

	public void cleanCell(int v, int h) {
		emptyCell(v, h);
		day();
	}

	public void day() {
		for (int h = 0; h < sizeHorizontal; h++) {
			for (int v = 0; v < sizeVertical; v++) {
				cells[v][h].day();
			}
		}
		ui.showMessage("A day has passed");
	}

	public boolean tryToEat(int v, int h) {
		return (h > 0 && cells[v][h - 1].eat())
				|| (v > 0 && cells[v - 1][h].eat())
				|| (h < sizeHorizontal - 1 && cells[v][h + 1].eat())
				|| (v < sizeVertical - 1 && cells[v + 1][h].eat());
	}

	public void bugDied(int v, int h, String reason) {
		ui.showMessage("The bug in position (" + v + "," + h
				+ ") just passed away. Reason: " + reason);
	}

	public boolean hasCloseFriend(int v, int h) {
		return (h > 0 && cells[v][h - 1].isBugAlive())
				|| (v > 0 && cells[v - 1][h].isBugAlive())
				|| (h < sizeHorizontal - 1 && cells[v][h + 1].isBugAlive())
				|| (v < sizeVertical - 1 && cells[v + 1][h].isBugAlive());
	}

	public void tryToHaveBaby(int v, int h) {
		int nv = -1, nh = -1;
		if (h > 0 && cells[v][h - 1].isEmpty()) {
			nv = v;
			nh = h - 1;
		} else if (v > 0 && cells[v - 1][h].isEmpty()) {
			nv = v-1;
			nh = h;
		} else if (h < sizeHorizontal - 1 && cells[v][h + 1].isEmpty()) {
			nv = v;
			nh = h + 1;
		} else if (v < sizeVertical - 1 && cells[v + 1][h].isEmpty()) {
			nv = v;
			nh = h - 1;
		}
		if (nv != -1 && nh != -1) {
			ui.showMessage("A bug was born in the position (" + nv + "," + nh
					+ ")");
			cells[nv][nh].createBug();
			score++;
		} else {
			ui.showMessage("The bug in position (" + v + "," + h
					+ ") is trying to have a baby, but there is not room!");
		}
	}
}
