package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillMeditation extends ActionSkill {

	public ActionSkillMeditation(Chr me) {
		super(me);
		name = "めいそう";
		rangeMin = 150;
		rangeMax = 200;
	}

	// 回復対象：自分自身
	public boolean playerTarget() {
		me.targets.add(me);
		return true;
	}

	// 回復量：150～200の範囲
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.singleHeal(me, this);
	}

}
