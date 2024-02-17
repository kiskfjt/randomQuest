package others;

import action.Action;
import chr.Chr;
import chr.Party;
import item.Item;

public class Calc {
	// クリティカル時の倍率
	private static final int CRITICAL_MULTI = 2;
	// 回避率の最大値
	private static final int EVASION_MAX_RATE = 100;
	
	public static int physSingleDmg(Chr me, Chr target) {
		double dDmg = 0;
		int Dmg = 0;
		dDmg =  me.ATK * me.action.multi * me.ATKNext * IO.randomNum(me.action.rangeMin, me.action.rangeMax)
			   / target.DEF / target.DEFNext;
		Dmg = (int) dDmg;
		
		// 受け流し判定
		target = IO.changeTarget(me, target);
		
		// 回避率
		int evasionRate = me.action.missRate + target.evasionRate;
		if (evasionRate > EVASION_MAX_RATE) {
			evasionRate = EVASION_MAX_RATE;
		}
		// 回避判定
		if (IO.probability(evasionRate)) {
			Dmg = 0;
			IO.msgln("%sはひらりとかわした！", target.name);
		} else {
			// 会心痛恨判定（物理攻撃のみ）
			if (Dmg > 0 && IO.probability(me.action.criticalRate)) {
				Dmg *= CRITICAL_MULTI;
				if (me.party.pKind == Party.PARTY_KIND_ALLY) {
					IO.msgln("<<<かいしんのいちげき！>>>");
				} else {
					IO.msgln("<<<つうこんのいちげき！>>>");
				}
			}
			// ダメージがマイナスや9999を超えた時の処理
			if (Dmg < 0) {
				Dmg = 0;
			} else if (Dmg > Chr.MAX_HP) {
				Dmg = Chr.MAX_HP;
			}
			IO.msgln("%sに%dのダメージ！", target.name, Dmg);
			target.HP -= Dmg;
			IO.judgeHP(me, target);
			IO.recoverFromAsleep(target);
		}
		
		// 1回のみのバフの消化。攻撃が外れても消化する
		if (me.ATKNext != me.ATK_MULTI_DEFAULT) {
			// me.attackedFlg = true;
			me.ATKNext = me.ATK_MULTI_DEFAULT;
		}
		return Dmg;
	}
	
	/**
	 * 単体物理ダメージ計算式
	 * 通常はこれで計算を受け付ける
	 * @param me
	 */
	public static int physSingleDmg(Chr me) {
		return physSingleDmg(me, me.targets.get(0));
	}
	
	/**
	 * 反動ダメージ有りの物理攻撃ダメージ計算メソッド
	 * @param me
	 * @param kickback
	 */
	public static void physSingleDmgWithKickback(Chr me, double kickback) {
		int Dmg = physSingleDmg(me, me.targets.get(0));
		int kickbackDmg = (int) (Dmg * kickback);
		me.HP -= kickbackDmg;
		
		if (kickbackDmg > 0) {
			IO.msgln("%sは%dの反動ダメージをうけた！", me.name, kickbackDmg);
		}
		
		IO.judgeHP(me.targets.get(0), me);
	}
	
	public static void physMultiDmg(Chr me) {
		for (Chr c : me.targets) {
			if (c.isAlive()) {
				physSingleDmg(me, c);
			}
		}
	}
	
	/**
	 * rangeMinからrangeMaxの範囲内でランダムな値をダメージとして計算する
	 * @param me
	 * @param target
	 * @return
	 */
	public static int physRangeSingleDmg(Chr me, Chr target) {
		double dDmg = 0;
		int Dmg = 0;
		dDmg = IO.randomNum(me.action.rangeMinInt, me.action.rangeMaxInt)
			   / target.DEFNext;
		Dmg = (int) dDmg;
		
		// 確率判定
		if (IO.probability(me.action.missRate)) {
			Dmg = 0;
			IO.msgln("%sはひらりとかわした！", target.name);
		} else {
			// 会心痛恨判定（物理攻撃のみ）
			if (Dmg > 0 && IO.probability(me.action.criticalRate)) {
				Dmg *= CRITICAL_MULTI;
				if (me.party.pKind == Party.PARTY_KIND_ALLY) {
					IO.msgln("<<かいしんのいちげき！>>");
				} else {
					IO.msgln("<<つうこんのいちげき！>>");
				}
			}
			if (Dmg < 0) {
				Dmg = 0;
			} else if (Dmg > Chr.MAX_HP) {
				Dmg = Chr.MAX_HP;
			}
			IO.msgln("%sに%dのダメージ！", target.name, Dmg);
			target.HP -= Dmg;
			IO.judgeHP(me, target);
			IO.recoverFromAsleep(target);
		}
		
		// 1回のみのバフの消化。攻撃が外れても消化する
		if (me.ATKNext != me.ATK_MULTI_DEFAULT) {
			// me.attackedFlg = true;
			me.ATKNext = me.ATK_MULTI_DEFAULT;
		}
		return Dmg;
	}
	
	/**
	 * 単体範囲物理ダメージ計算式
	 * 通常はこれで計算を受け付ける
	 * @param me
	 */
	public static int physRangeSingleDmg(Chr me) {
		return physRangeSingleDmg(me, me.targets.get(0));
	}
	
	/**
	 * 魔法単体ダメージ計算メソッド
	 * ダメージ計算を行っているのがこれ
	 * @param me
	 * @param target
	 */
	public static void mgcSingleDmg(Chr me, Chr target) {
		double dDmg = 0;
		int Dmg = 0;
		dDmg = me.MAT * me.action.multi * IO.randomNum(me.action.rangeMin, me.action.rangeMax)
				/ target.MDF / target.DEFNext * target.magicResistance;
		Dmg = (int) dDmg;
		
		judgeProbabilityAndFixDmg(me, target, Dmg);
	}
	
	/**
	 * 魔法単体ダメージ計算メソッド
	 * 引数1つのこのメソッドで基本的に処理を受け付ける
	 * @param me
	 */
	public static void mgcSingleDmg(Chr me) {
		mgcSingleDmg(me, me.targets.get(0));
	}
	
	/**
	 * 魔法複数ダメージ計算メソッド
	 * 単体魔法ダメージ計算メソッドを利用している
	 * @param me
	 */
	public static void mgcMultiDmg(Chr me) {
		for (Chr c : me.targets) {
			if (c.isAlive()) {
				mgcSingleDmg(me, c);
			}
		}
	}
	
	/**
	 * 防御力を無視したダメージ計算メソッド
	 * ダメージ乱数あり
	 * @param me
	 * @param target
	 * @param rangeMin
	 * @param rangeMax
	 */
	public static void ignoreDefenseDmg(Chr me, Chr target, double rangeMin, double rangeMax) {
		double dDmg = IO.randomNum(rangeMin, rangeMax) / target.DEFNext;
		int Dmg = (int) dDmg;
		
		judgeProbabilityAndFixDmg(me, target, Dmg);
	}
	
	/**
	 * 防御力を無視したダメージ計算メソッド
	 * ターゲット単体バージョン
	 * @param me
	 * @param rangeMin
	 * @param rangeMax
	 */
	public static void ignoreDefenseDmg(Chr me, double rangeMin, double rangeMax) {
		Chr target = me.targets.get(0);
		ignoreDefenseDmg(me, target, rangeMin, rangeMax);
	}
	
	/**
	 * 回避判定とダメージの上限下限足きりとダメージ計算メソッド
	 * 敵を倒したかどうかの判定も行う
	 * @param me
	 * @param target
	 * @param Dmg
	 */
	private static void judgeProbabilityAndFixDmg(Chr me, Chr target, int Dmg) {
		// 確率判定
		if (IO.probability(me.action.missRate)) {
			Dmg = 0;
			IO.msgln("%sはひらりとかわした！", target.name);
		} else {
			if (Dmg < 0) {
				Dmg = 0;
			} else if (Dmg > Chr.MAX_HP) {
				Dmg = Chr.MAX_HP;
			}
			IO.msgln("%sに%dのダメージ！", target.name, Dmg);
			target.HP -= Dmg;
			IO.judgeHP(me, target);
		}
	}
	
	/**
	 * 単体回復メソッド
	 * 実際に回復処理を行っているのがこれ
	 * @param me
	 * @param target
	 * @param rangeMin
	 * @param rangeMax
	 * @param MPCons
	 */
	public static void singleHeal(Chr me, Chr target, double rangeMin, double rangeMax) {
		int HPbefore = 0;
		int HPafter = 0;
		int value = 0;
		
		HPbefore = target.HP;
		
		if (target.isAlive()) {
			target.HP += IO.randomNum(rangeMin, rangeMax);
			if (target.HP > target.maxHP) {
				target.HP = target.maxHP;
			}
			
			HPafter = target.HP;
			value = HPafter - HPbefore;
			IO.msgln("%sのHPが%d回復した！", target.name, value);
		}
	}
	
	/**
	 * 単体回復メソッド
	 * スキル回復、魔法回復、道具回復どれでも処理を受け取るメソッド
	 * @param me
	 * @param target
	 * @param obj
	 */
	public static void singleHeal(Chr me, Chr target, Object obj) {
		if (obj instanceof Action) {
			Action act = (Action) obj;
			singleHeal(me, target, act.rangeMin, act.rangeMax);
		} else if (obj instanceof Item) {
			Item itm = (Item) obj;
			singleHeal(me, target, itm.rangeMin, itm.rangeMax);
		}
	}
	
	/**
	 * 単体回復メソッド
	 * 引数2つバージョン。回復系のクラスに書くメソッドは基本的にはこれ。
	 * @param me
	 * @param obj
	 */
	public static void singleHeal(Chr me, Object obj) {
		Chr target = me.targets.get(0);
		singleHeal(me, target, obj);
	}
	
	/**
	 * 複数回復メソッド
	 * 魔法のときのみ最後に消費MPをマイナスする
	 */
	public static void multiHeal(Chr me, Object obj) {
		for (Chr target : me.targets) {
			singleHeal(me, target, obj);
		}
	}
	
	/**
	 * 単体HP全快メソッド
	 * 実際に回復処理を行っているのがこれ
	 * @param target
	 * @param me
	 */
	public static void maxSingleHeal(Chr target, Chr me) {
		if (target.isAlive()) {
			target.HP = target.maxHP;
			IO.msgln("%sのHPが全回復した！", target.name);
		} else {
			IO.msgln("%sはすでに死んでいる！", target.name);
		}
	}
	
	/**
	 * 単体HP全快メソッドの引数1つ版
	 * このメソッドで主に全快処理を受け付ける
	 * @param me
	 */
	public static void maxSingleHeal(Chr me) {
		maxSingleHeal(me.targets.get(0), me);
	}
	
	/**
	 * 全体HP全快メソッド
	 * @param me
	 */
	public static void maxMultiHeal(Chr me) {
		me.targets.forEach(chr -> maxSingleHeal(chr, me));
	}
	
	public static void singleBuff(Chr me, Chr target, int buffNo, double buffValue) {
		int buff = 0;
		int beforeBuff = 0;
		String buffName = "";
		
		if (buffNo == Action.BUFF_ATK) {
			if (target.canLowerATK || (!target.canLowerATK && buffValue >= 0)) {
				beforeBuff = target.ATK;
				buff = (int) (target.baseATK * buffValue);
				target.ATK += buff;
				if (target.ATK > target.baseATK * target.MAX_ATK_COEF) {
					target.ATK = target.baseATK * target.MAX_ATK_COEF;
					buff = target.ATK - beforeBuff;
				} else if (target.ATK < target.baseATK * target.MIN_ATK_COEF) {
					target.ATK = (int) (target.baseATK * target.MIN_ATK_COEF);
					buff = beforeBuff - target.ATK;
				}
				buffName = "ATK";
			}
		} else if (buffNo == Action.BUFF_DEF) {
			if (target.canLowerDEF || (!target.canLowerDEF && buffValue >= 0)) {
				beforeBuff = target.DEF;
				buff = (int) (target.baseDEF * buffValue);
				target.DEF += buff;
				if (target.DEF > target.baseDEF * target.MAX_DEF_COEF) {
					target.DEF = target.baseDEF * target.MAX_DEF_COEF;
					buff = target.DEF - beforeBuff;
				} else if (target.DEF < target.baseDEF * target.MIN_DEF_COEF) {
					target.DEF = (int) (target.baseDEF * target.MIN_DEF_COEF);
					buff = beforeBuff - target.DEF;
				}
				buffName = "DEF";
			}
		} else if (buffNo == Action.BUFF_MAT) {
			if (target.canLowerMAT || (!target.canLowerMAT && buffValue >= 0)) {
				beforeBuff = target.MAT;
				buff = (int) (target.baseMAT * buffValue);
				target.MAT += buff;
				if (target.MAT > target.baseMAT * target.MAX_MAT_COEF) {
					target.MAT = target.baseMAT * target.MAX_MAT_COEF;
					buff = target.MAT - beforeBuff;
				} else if (target.MAT < target.baseMAT * target.MIN_MAT_COEF) {
					target.MAT = (int) (target.baseMAT * target.MIN_MAT_COEF);
					buff = beforeBuff - target.MAT;
				}
				buffName = "MAT";
			}
		} else if (buffNo == Action.BUFF_MDF) {
			if (target.canLowerMDF || (!target.canLowerMDF && buffValue >= 0)) {
				beforeBuff = target.MDF;
				buff = (int) (target.baseMDF * buffValue);
				target.MDF += buff;
				if (target.MDF > target.baseMDF * target.MAX_MDF_COEF) {
					target.MDF = target.baseMDF * target.MAX_MDF_COEF;
					buff = target.MDF - beforeBuff;
				} else if (target.MDF < target.baseMDF * target.MIN_MDF_COEF) {
					target.MDF = (int) (target.baseMDF * target.MIN_MDF_COEF);
					buff = beforeBuff - target.MDF;
				}
				buffName = "MDF";
			}
		} else if (buffNo == Action.BUFF_SPD) {
			if (target.canLowerSPD || (!target.canLowerSPD && buffValue >= 0)) {
				beforeBuff = target.SPD;
				buff = (int) (target.baseSPD * buffValue);
				target.SPD += buff;
				if (target.SPD > target.baseSPD * target.MAX_SPD_COEF) {
					target.SPD = target.baseSPD * target.MAX_SPD_COEF;
					buff = target.SPD - beforeBuff;
				} else if (target.SPD < target.baseSPD * target.MIN_SPD_COEF) {
					target.SPD = (int) (target.baseSPD * target.MIN_SPD_COEF);
					buff = beforeBuff - target.SPD;
				}
				buffName = "SPD";
				target.buffSPD = target.SPD - target.baseSPD;
			}
		}
		
		if (buff > 0) {
			IO.msgln("%sの%sが%d増加した！", target.name, buffName, buff);
		} else if (buff == 0 && beforeBuff == 0) {
			IO.msgln("%sには効果がないみたいだ", target.name);
		} else if (buff == 0) {
			IO.msgln("しかし、何も変わらなかった！");
		} else if (buff < 0) {
			IO.msgln("%sの%sが%d減少した！", target.name, buffName, buff);
		}
	}
	
	public static void singleBuff(Chr me, int buffNo, double buffValue) {
		Chr target = me.targets.get(0);
		singleBuff(me, target, buffNo, buffValue);
	}
	
	public static void multiBuff(Chr me, int buffNo, double buffValue) {
		for (Chr c : me.targets) {
			if (c.isDead()) {
				System.out.println(c.name + "はしんでいる！");
			} else if (c.isAlive()) {
				singleBuff(me, c, buffNo, buffValue);
			}
		}
	}
	
	/**
	 * 単体蘇生メソッド
	 * 実際に蘇生処理をしているのがこれ
	 * @param target
	 * @param me
	 */
	public static void revive(Chr target, Chr me) {
		boolean isRevived = IO.probability(me.action.successRate);
		
		int count = 0;
		
		if (target.HP > 0) {
			count++;
			if (count == me.party.member.size()) {
				IO.msgln("しかし何も起こらなかった！");
			}
		} else if (target.HP == 0 && isRevived) {
			target.HP = (int) (target.maxHP * IO.randomNum(me.action.rangeMin, me.action.rangeMax));
			target.status = target.STATUS_NOMAL;
			IO.msgln("%sは生き返った！", target.name);
		} else {
			IO.msgln("しかし%sは生き返らなかった！", target.name);
		}
	}
	
	/**
	 * 単体蘇生メソッド
	 * 引数meで受け取るのがこれ
	 * @param me
	 */
	public static void revive(Chr me) {
		revive(me.targets.get(0), me);
	}
	
	/**
	 * 複数蘇生メソッド
	 * @param memList
	 * @param me
	 */
	public static void multiRevive(Chr me) {
		for (Chr target : me.targets) {
			revive(target, me);
		}
	}
	
	/**
	 * 単体MP回復メソッド
	 * 実際にMP回復処理を行っているのがこれ
	 * @param me
	 * @param target
	 * @param rangeMin
	 * @param rangeMax
	 */
	public static void singleMPRecover(Chr me, Chr target, double rangeMin, double rangeMax) {
		int MPbefore = 0;
		int MPafter = 0;
		int value = 0;
		
		MPbefore = target.MP;
		
		if (target.isAlive()) {
			target.MP += IO.randomNum(rangeMin, rangeMax);
			if (target.MP > target.maxMP) {
				target.MP = target.maxMP;
			}
			
			MPafter = target.MP;
			value = MPafter - MPbefore;
			IO.msgln("%sのMPが%d回復した！", target.name, value);
			
		} else {
			IO.msgln("%sはすでに死んでいる！", target.name);
		}
	}
	
	/**
	 * 単体MP回復メソッド
	 * 主にこのメソッドで単体MP回復処理を受け付ける
	 * @param me
	 * @param obj
	 */
	public static void singleMPRecover(Chr me, Object obj) {
		Chr target = me.targets.get(0);
		if (obj instanceof Item) {
			Item item = (Item) obj;
			singleMPRecover(me, target, item.rangeMin, item.rangeMax);
		}
		
	}
	
	/**
	 * 単体MP全回復メソッド
	 * @param me
	 */
	public static void maxSingleMPRecover(Chr me) {
		Chr target = me.targets.get(0);
		if (target.isAlive()) {
			target.MP = target.maxMP;
			IO.msgln("%sのMPが全回復した！", target.name);
		} else {
			IO.msgln("%sはすでに死んでいる！", target.name);
		}
	}
	
	/**
	 * 単体HP&MP全回復メソッド
	 * @param me
	 */
	public static void maxSingleHPAndMPRecover(Chr me) {
		Chr target = me.targets.get(0);
		if (target.isAlive()) {
			target.HP = target.maxHP;
			target.MP = target.maxMP;
			IO.msgln("%sのHPとMPが全回復した！", target.name);
		} else {
			IO.msgln("%sはすでに死んでいる！", target.name);
		}
	}
	
	/**
	 * 呪文を使った後消費MP分を引き算するメソッド
	 * ターゲットが1人でも生きていたらMPを消費する
	 * @param me
	 * @param MPCons
	 */
	public static void subtractMPConsIfTargetIsAlive(Chr me, int MPCons) {
		if (IO.isTargetAlive(me)) {
			me.MP -= MPCons;
		}
	}
	
	/**
	 * 呪文を使った後消費MP分を引き算するメソッド
	 * ターゲットが1人でも死んでいたらMPを消費する
	 * @param me
	 * @param MPCons
	 */
	public static void subtractMPConsIfTargetIsDead(Chr me, int MPCons) {
		if (IO.isTargetDead(me)) {
			me.MP -= MPCons;
		}
	}
}
