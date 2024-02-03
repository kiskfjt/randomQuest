package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicResurrection extends ActionMagic {

	public ActionMagicResurrection(Chr me) {
		super(me);
		name = "リザレクション";
		MPCons = 40;
		rangeMin = 1.0;
		rangeMax = 1.0;
		successRate = 100;
	}

	// 対象：味方単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	// 効果：HP全快で復活
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);
		
		Calc.revive(me);
		
		Calc.subtractMPConsIfTargetIsDead(me, MPCons);
	}

}
