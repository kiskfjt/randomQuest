package others;

import action.Action;
import chr.Chr;
import item.Item;

public class Calc {
	public static void physSingleDmg(Chr me, Chr target) {
		double dDmg = 0;
		int Dmg = 0;
		dDmg =  me.ATK * me.action.multi * me.ATKNext * IO.randomNum(me.action.rangeMin, me.action.rangeMax)
			   / target.DEF / target.DEFNext;
		Dmg = (int) dDmg;
		if (Dmg < 0) {
			Dmg = 0;
		} else if (Dmg > Chr.MAX_HP) {
			Dmg = Chr.MAX_HP;
		}
		IO.msgln("%sに%dのダメージ！", target.name, Dmg);
		target.HP -= Dmg;
		IO.judgeHP(me, target);
		if (me.ATKNext != me.ATK_MULTI_DEFAULT) {
			// me.attackedFlg = true;
			me.ATKNext = me.ATK_MULTI_DEFAULT;
		}
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
		double dDmg = 0;
		int Dmg = 0;
		dDmg = me.MAT * me.action.multi * IO.randomNum(me.action.rangeMin, me.action.rangeMax)
				/ target.MDF / target.DEFNext;
		Dmg = (int) dDmg;
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
	
	public static void singleHeal(Chr me, Chr target, double rangeMin, double rangeMax) {
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
	
	public static void singleBuff(Chr me, Chr target, int buffNo, int buffValue) {
		int buff = 0;
		int beforeBuff = 0;
		String buffName = "";
		
		if (buffNo == Action.BUFF_ATK) {
			beforeBuff = target.ATK;
			buff = target.baseATK * buffValue / Action.BUFF_DEFAULT_VALUE;
			target.ATK += buff;
			if (target.ATK > target.baseATK * target.MAX_ATK_COEF) {
				target.ATK = target.baseATK * target.MAX_ATK_COEF;
				buff = target.ATK - beforeBuff;
			} else if (target.ATK < target.baseATK * target.MIN_ATK_COEF) {
				target.ATK = (int)(target.baseATK * target.MIN_ATK_COEF);
				buff = beforeBuff - target.ATK;
			}
			buffName = "ATK";
		} else if (buffNo == Action.BUFF_DEF) {
			beforeBuff = target.DEF;
			buff = target.baseDEF * buffValue / Action.BUFF_DEFAULT_VALUE;
			target.DEF += buff;
			if (target.DEF > target.baseDEF * target.MAX_DEF_COEF) {
				target.DEF = target.baseDEF * target.MAX_DEF_COEF;
				buff = target.DEF - beforeBuff;
			} else if (target.DEF < target.baseDEF * target.MIN_DEF_COEF) {
				target.DEF = (int)(target.baseDEF * target.MIN_DEF_COEF);
				buff = beforeBuff - target.DEF;
			}
			buffName = "DEF";
		} else if (buffNo == Action.BUFF_MAT) {
			beforeBuff = target.MAT;
			buff = target.baseMAT * buffValue / Action.BUFF_DEFAULT_VALUE;
			target.MAT += buff;
			if (target.MAT > target.baseMAT * target.MAX_MAT_COEF) {
				target.MAT = target.baseMAT * target.MAX_MAT_COEF;
				buff = target.MAT - beforeBuff;
			} else if (target.MAT < target.baseMAT * target.MIN_MAT_COEF) {
				target.MAT = (int)(target.baseMAT * target.MIN_MAT_COEF);
				buff = beforeBuff - target.MAT;
			}
			buffName = "MAT";
		} else if (buffNo == Action.BUFF_MDF) {
			beforeBuff = target.MDF;
			buff = target.baseMDF * buffValue / Action.BUFF_DEFAULT_VALUE;
			target.MDF += buff;
			if (target.MDF > target.baseMDF * target.MAX_MDF_COEF) {
				target.MDF = target.baseMDF * target.MAX_MDF_COEF;
				buff = target.MDF - beforeBuff;
			} else if (target.MDF < target.baseMDF * target.MIN_MDF_COEF) {
				target.MDF = (int)(target.baseMDF * target.MIN_MDF_COEF);
				buff = beforeBuff - target.MDF;
			}
			buffName = "MDF";
		} else if (buffNo == Action.BUFF_SPD) {
			beforeBuff = target.SPD;
			buff = target.baseSPD * buffValue / Action.BUFF_DEFAULT_VALUE;
			target.SPD += buff;
			if (target.SPD > target.baseSPD * target.MAX_SPD_COEF) {
				target.SPD = target.baseSPD * target.MAX_SPD_COEF;
				buff = target.SPD - beforeBuff;
			} else if (target.SPD < target.baseSPD * target.MIN_SPD_COEF) {
				target.SPD = (int)(target.baseSPD * target.MIN_SPD_COEF);
				buff = beforeBuff - target.SPD;
			}
			buffName = "SPD";
			target.buffSPD = target.SPD - target.baseSPD; 
		}
		
		if (buff > 0) {
			IO.msgln("%sの%sが%d増加した！", target.name, buffName, buff);
		} else if (buff == 0) {
			IO.msgln("しかし、何も変わらなかった！");
		} else if (buff < 0) {
			IO.msgln("%sの%sが%d減少した！", target.name, buffName, buff);
		}
	}
	
	public static void singleBuff(Chr me, int buffNo, int buffValue) {
		Chr target = me.targets.get(0);
		singleBuff(me, target, buffNo, buffValue);
	}
	
	public static void multiBuff(Chr me, int buffNo, int buffValue) {
		for (Chr c : me.targets) {
			if (c.isDead()) {
				System.out.println(c.name + "はしんでいる！");
			} else if (c.isAlive()) {
				singleBuff(me, c, buffNo, buffValue);
			}
		}
	}
	
	public static void revive(Chr me, double rangeMin, double rangeMax, int p) {
		Chr target = me.targets.get(0);
		boolean isRevived = IO.probability(p);
		
		if (target.HP > 0) {
			IO.msgln("しかし何も起こらなかった！");
		} else if (target.HP == 0 && isRevived) {
			target.HP = (int) (target.maxHP * IO.randomNum(rangeMin, rangeMax));
			IO.msgln("%sは生き返った！", target.name);
		} else {
			IO.msgln("しかし%sは生き返らなかった！", target.name);
		}
	}
}
