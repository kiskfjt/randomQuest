package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemHealingPotion extends Item {

	public ItemHealingPotion(Chr me) {
		super(me);
		name = "回復ポーション";
		rangeMin = 50;
		rangeMax = 70;
	}

	/**
	 * 回復対象：味方単体
	 */
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	/**
	 * 回復：50～70の範囲、ヒールと同程度
	 */
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		
		Calc.singleHeal(me, this);
		
		// 対象が生きていたらこのインスタンスをitemリストから削除
		if (IO.isTargetAlive(me)) {
			IO.removeFromItemList(me, this);
		}
	}
}
