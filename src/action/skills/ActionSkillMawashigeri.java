package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillMawashigeri extends ActionSkill {

	public ActionSkillMawashigeri(Chr me) {
		super(me);
		name = "まわしげり";
		multi = 50;
		rangeMin = 0.8;
		rangeMax = 1.2;
	}
	
	// 攻撃範囲：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}
	
	// ダメージ：物理、掛け算方式
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.physMultiDmg(me);
	}
}
