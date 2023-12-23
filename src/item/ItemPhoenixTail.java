package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemPhoenixTail extends Item {

	public ItemPhoenixTail(Chr me) {
		super(me);
		name = "フェニックスの尾";
		rangeMin = 0.4;
		rangeMax = 0.6;
	}
	
	// 蘇生の確率（%）
	private int probability = 100;
	
	public boolean playerTarget() {
		return IO.selectSingleDead(me.party.member, me);
	}
	
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);

		Calc.revive(me, rangeMin, rangeMax, probability);
		
		// 対象が死んでいたらこのインスタンスをitemリストから削除
		if (IO.isTargetDead(me)) {
			IO.removeFromItemList(me, this);
		}
	}
}
