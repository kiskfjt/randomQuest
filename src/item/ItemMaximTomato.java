package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemMaximTomato extends Item {

	public ItemMaximTomato(Chr me) {
		super(me);
		name = "マキシムトマト";
	}

	/**
	 * 回復対象：味方単体
	 */
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	/**
	 * HP全回復
	 */
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);

		Calc.maxSingleHeal(me);

		// 対象が生きていたらこのインスタンスをitemリストから削除
		if (IO.isTargetAlive(me)) {
			IO.removeFromItemList(me, this);
		}
	}
}
