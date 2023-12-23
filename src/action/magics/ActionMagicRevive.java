package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicRevive extends ActionMagic {

	public ActionMagicRevive(Chr me) {
		super(me);
		name = "リバイブ";
		MPCons = 20;
		rangeMin = 0.4;
		rangeMax = 0.6;
	}
	
	// 蘇生の確率（%）
	private int probability = 50;
	
	// 対象：味方単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	// 蘇生時のHP40～60%
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);

		Calc.revive(me, rangeMin, rangeMax, probability);
		
		Calc.subtractMPConsIfTargetIsDead(me, MPCons);
	}

}
