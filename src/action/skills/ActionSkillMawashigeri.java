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
	}
	
	// 攻撃範囲：敵全体
	public void playerTarget() {
		IO.selectMultiTargets(me.party.enemy.member, me);
	}
	
	// ダメージ：物理、掛け算方式
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.physMultiDmg(me);
	}
}
