package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillFireBreath extends ActionSkill {

	public ActionSkillFireBreath(Chr me) {
		super(me);
		name = "炎の息";
		multi = 50;
		rangeMin = 0.8;
		rangeMax = 1.2;
	}
	
	// 攻撃範囲：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// ダメージ：魔法、掛け算方式
	public void execute() {
		IO.msgln("【%sは%sを吐いた！】", me.name, name);

		Calc.mgcMultiDmg(me);
	}
}
