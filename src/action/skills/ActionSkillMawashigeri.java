package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillMawashigeri extends ActionSkill {

	public ActionSkillMawashigeri(Chr me) {
		super(me);
		name = "まわしげり";
		multi = 120;
		rangeMin = 80;
		rangeMax = 120;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public void playerTarget() {
		IO.selectMultiTargets(me.party.enemy.member, me.targets);
	}
	
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.physMultiDmg(me);
	}
}
