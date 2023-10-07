package action.items;

import action.ActionItem;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionItemMedHerb extends ActionItem {

	public ActionItemMedHerb(Chr me) {
		super(me);
		name = "やくそう";
		rangeMin = 30;
		rangeMax = 50;
	}
	
	// 回復対象：味方単体
	public void playerTarget() {
		IO.selectSingleTarget(me.party.member);
	}
	
	// 回復：30～50の範囲、ヒールより少し劣る
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		
		Calc.singleHeal(me, rangeMin, rangeMax);
		
		// このインスタンスをitemリストから削除
		//IO.removeFromActionList();
	}
}
