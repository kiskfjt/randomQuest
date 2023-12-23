package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemElixir extends Item {

	public ItemElixir(Chr me) {
		super(me);
		name = "エリクサー";
	}

	/**
	 * 回復対象：味方単体
	 */
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	/**
	 * HP&MP全回復
	 */
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		
		Calc.maxSingleHPAndMPRecover(me);
		
		// 対象が生きていたらこのインスタンスをitemリストから削除
		if (IO.isTargetAlive(me)) {
			IO.removeFromItemList(me, this);
		}
	}
	
}
