package others;

import chr.Chr;
import chr.bosses.BossMortamor2nd;

public class Main {

	public static void main(String[] args) {
		String[] enemyName = {"おそざわ"};
		Chr[] enemyChr = {new BossMortamor2nd()};
		
		Battle battle = new Battle();
		battle.init(enemyName, enemyChr);
		battle.start();
	}

}
