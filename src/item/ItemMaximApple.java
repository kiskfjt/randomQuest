package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemMaximApple extends Item {

	public ItemMaximApple(Chr me) {
		super(me);
		name = "マキシムアップル";
	}

	/**
	 * 回復対象：味方単体
	 */
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	/**
	 * MP全回復
	 */
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		
		Calc.maxSingleMPRecover(me);
		
		// 対象が生きていたらこのインスタンスをitemリストから削除
		if (IO.isTargetAlive(me)) {
			IO.removeFromItemList(me, this);
		}
	}
	
}
