package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemGreaterHealingPotion extends Item {

	public ItemGreaterHealingPotion(Chr me) {
		super(me);
		name = "上級回復ポーション";
		rangeMin = 100;
		rangeMax = 120;
	}

	/**
	 * 回復対象：味方単体
	 */
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	/**
	 * 回復：100～120の範囲、回復ポーションより多く回復
	 */
	public void execute() {
		IO.msgln("【%sは%sに%sをつかった！】", me.name, me.targets.get(0), name);

		Calc.singleHeal(me, this);

		// 対象が生きていたらこのインスタンスをitemリストから削除
		if (IO.isTargetAlive(me)) {
			IO.removeFromItemList(me, this);
		}
	}

}
