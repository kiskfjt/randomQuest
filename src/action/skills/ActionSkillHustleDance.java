package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillHustleDance extends ActionSkill {

	public ActionSkillHustleDance(Chr me) {
		super(me);
		name = "ハッスルダンス";
		rangeMin = 110;
		rangeMax = 140;
	}

	// 効果対象：味方全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.member, me);
	}

	// 味方全体のHP回復
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("そーれハッスルハッスル！");
		
		Calc.multiHeal(me, this);
	}

}
