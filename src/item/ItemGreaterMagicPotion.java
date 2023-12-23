package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemGreaterMagicPotion extends Item {
	
	public ItemGreaterMagicPotion(Chr me) {
		super(me);
		name = "上級マジックポーション";
		rangeMin = 50;
		rangeMax = 70;
	}

	/**
	 * 使用対象：味方単体
	 */
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	/**
	 * MP回復：50～70の範囲
	 */
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		
		Calc.singleMPRecover(me, this);
		
		// 対象が生きていたらこのインスタンスをitemリストから削除
		if (IO.isTargetAlive(me)) {
			IO.removeFromItemList(me, this);
		}
	}
}
