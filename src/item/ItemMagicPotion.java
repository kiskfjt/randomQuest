package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemMagicPotion extends Item {

	public ItemMagicPotion(Chr me) {
		super(me);
		name = "マジックポーション";
		rangeMin = 20;
		rangeMax = 40;
	}

	/**
	 * 使用対象：味方単体
	 */
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	/**
	 * MP回復：20～40の範囲
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
