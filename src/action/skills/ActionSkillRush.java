package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillRush extends ActionSkill {

	public ActionSkillRush(Chr me) {
		super(me);
		name = "とっしん";
		multi = 60;
		rangeMin = 0.8;
		rangeMax = 1.2;
	}
	
	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.enemy.member, me);
	}
	
	// ダメージ：物理、掛け算方式
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.physSingleDmg(me);
	}
}
