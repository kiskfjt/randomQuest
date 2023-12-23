package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemStone extends Item {

	public ItemStone(Chr me) {
		super(me);
		name = "石ころ";
	}
	
	// 大当たりの確率(%)
	private int probability = 1;

	
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}

	
	public void execute() {
		IO.msgln("【%sは%sを投げつけた！】", me.name, name);
		
		boolean isLucky = IO.probability(probability);
		
		if (isLucky) {
			IO.msgln("なんとそれは巨大な岩石だった！");
			
			rangeMin = 100;
			rangeMax = 300;
			
		} else {
			rangeMin = 2;
			rangeMax = 5;
		}
		Calc.ignoreDefenseDmg(me, rangeMin, rangeMax);
		
		// このインスタンスをitemリストから削除
	    IO.removeFromItemList(me, this);
	}

}
