package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicHeal extends ActionMagic {

	public ActionMagicHeal(Chr me) {
		super(me);
		name = "ヒール";
		MPCons = 10;
		rangeMin = 40;
		rangeMax = 60;
	}
	
	// 回復対象：味方単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}
	
	// 回復：40～60の範囲
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);
		
		Calc.singleHeal(me, this);
		
		Calc.subtractMPConsIfTargetIsAlive(me, MPCons);
	}
}
