package others;

import action.Action;
import chr.Chr;
import item.Item;

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
	
	public static int physSingleDmg(Chr me) {
		return physSingleDmg(me, me.targets.get(0));
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
	
	public static void singleHeal(Chr me, Chr target, int rangeMin, int rangeMax) {
		int HPbefore = 0;
		int HPafter = 0;
		int value = 0;
		
		HPbefore = target.HP;
		
		target.HP += IO.randomNum(rangeMin, rangeMax);
		if (target.HP > target.maxHP) {
			target.HP = target.maxHP;
		}
		
		HPafter = target.HP;
		value = HPafter - HPbefore;
		IO.msgln("%sのHPが%d回復した！", target.name, value);
	}
	
	public static void singleHeal(Chr me, Object obj) {
		if (obj instanceof Action) {
			Action act = (Action) obj;
			singleHeal(me, me.targets.get(0), act.rangeMin, act.rangeMax);
		} else if (obj instanceof Item) {
			Item itm = (Item) obj;
			singleHeal(me, me.targets.get(0), itm.rangeMin, itm.rangeMax);
		}
	}
	
}
