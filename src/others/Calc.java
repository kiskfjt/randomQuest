package others;

import action.Action;
import chr.Chr;

public class Calc {
	public static int physSingleDmg(Chr me, Chr target) {
		int Dmg = 0;
		Dmg = (int) (me.ATK * me.action.multi * IO.randomNum(me.action.rangeMin, me.action.rangeMax)
				/ Action.MULTI_DEFAULT_VALUE / Action.RANGE_DEFAULT_VALUE
				- target.DEF) / target.DEFMulti;
		if (Dmg < 0) {
			Dmg = 0;
		} else if (Dmg > Chr.MAX_HP) {
			Dmg = Chr.MAX_HP;
		}
		return Dmg;
	}
	
	public static void physMultiDmg(Chr me) {
		int Dmg = 0;
		for (Chr c : me.targets) {
			if (c.isDead()) {
				System.out.println(c.name + "はしんでいる！");
			} else if (c.isAlive()) {
				Dmg = physSingleDmg(me, c);
				IO.msgln("%sに%dのダメージ！", c.name, Dmg);
				c.HP -= Dmg;
				IO.judgeHP(me, c);
			}
		}
	}
}
