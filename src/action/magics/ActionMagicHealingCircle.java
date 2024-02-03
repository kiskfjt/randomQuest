package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicHealingCircle extends ActionMagic {

	public ActionMagicHealingCircle(Chr me) {
		super(me);
		name = "ヒーリングサークル";
		MPCons = 30;
		rangeMin = 80;
		rangeMax = 100;
	}

	// 回復対象：味方全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.member, me);
	}

	// 回復量：80～100の範囲
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);
		
		Calc.multiHeal(me, this);
		
		me.MP -= MPCons;
	}
	
}
