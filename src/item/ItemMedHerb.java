package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemMedHerb extends Item {
	public ItemMedHerb(Chr me) {
		super(me);
		name = "やくそう";
		rangeMin = 30;
		rangeMax = 50;
	}

	/**
	 *  回復対象：味方単体
	 */
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	/**
	 * 回復：30～50の範囲、ヒールより少し劣る
	 */
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);

		Calc.singleHeal(me, this);

	    // このインスタンスをitemリストから削除
	    IO.removeFromItemList(me, this);
	}
}
