package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicGreaterHeal extends ActionMagic {

	public ActionMagicGreaterHeal(Chr me) {
		super(me);
		name = "グレーターヒール";
		MPCons = 20;
		rangeMin = 100;
		rangeMax = 120;
	}

	// 回復対象：味方単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	// 回復：100～120の範囲
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);

		Calc.singleHeal(me, this);

		Calc.subtractMPConsIfTargetIsAlive(me, MPCons);
	}
	
}
