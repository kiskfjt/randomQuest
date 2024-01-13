package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemPrayerRing extends Item {

	public ItemPrayerRing(Chr me) {
		super(me);
		name = "祈りの指輪";
		rangeMin = 20;
		rangeMax = 40;
	}
	
	// 壊れる確率(%)
	private int probability = 20;

	/**
	 * 回復対象：自分自身
	 */
	public boolean playerTarget() {
		me.targets.add(me);
		return true;
	}

	/**
	 * MP回復：20～40の範囲
	 */
	public void execute() {
		IO.msgln("【%sは%sを指にはめ祈りをささげた！】", me.name, name);
		
		Calc.singleMPRecover(me, this);
		
		// 一定の確率で無くなる
		if (IO.probability(probability)) {
			IO.removeFromItemList(me, this);
			IO.msgln("%sは音もなく崩れ去った！", name);
		}
	}
	
}
