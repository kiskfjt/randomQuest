package others;

import action.Action;
import chr.Chr;
import item.Item;

public class Calc {
	public static void physSingleDmg(Chr me, Chr target) {
		int Dmg = 0;
		Dmg = (int) (me.ATK * me.action.multi * IO.randomNum(me.action.rangeMin, me.action.rangeMax)
				/ Action.MULTI_DEFAULT_VALUE / Action.RANGE_DEFAULT_VALUE
				- target.DEF) / target.DEFMulti;
		if (Dmg < 0) {
			Dmg = 0;
		} else if (Dmg > Chr.MAX_HP) {
			Dmg = Chr.MAX_HP;
		}
		IO.msgln("%sに%dのダメージ！", target.name, Dmg);
		target.HP -= Dmg;
		IO.judgeHP(me, target);
	}
	
	public static void physSingleDmg(Chr me) {
		physSingleDmg(me, me.targets.get(0));
	}
	
	public static void physMultiDmg(Chr me) {
		for (Chr c : me.targets) {
			if (c.isDead()) {
				System.out.println(c.name + "はしんでいる！");
			} else if (c.isAlive()) {
				physSingleDmg(me, c);
			}
		}
	}
	
	public static void mgcSingleDmg(Chr me, Chr target) {
		int Dmg = 0;
		Dmg = (int) (me.MAT * me.action.multi * IO.randomNum(me.action.rangeMin, me.action.rangeMax)
				/ Action.MULTI_DEFAULT_VALUE / Action.RANGE_DEFAULT_VALUE
				- target.MDF) / target.DEFMulti;
		if (Dmg < 0) {
			Dmg = 0;
		} else if (Dmg > Chr.MAX_HP) {
			Dmg = Chr.MAX_HP;
		}
		IO.msgln("%sに%dのダメージ！", target.name, Dmg);
		target.HP -= Dmg;
		IO.judgeHP(me, target);
	}
	
	public static void mgcSingleDmg(Chr me) {
		mgcSingleDmg(me, me.targets.get(0));
	}
	
	public static void mgcMultiDmg(Chr me) {
		for (Chr c : me.targets) {
			if (c.isDead()) {
				System.out.println(c.name + "はしんでいる！");
			} else if (c.isAlive()) {
				mgcSingleDmg(me, c);
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
