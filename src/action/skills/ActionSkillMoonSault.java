package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillMoonSault extends ActionSkill {

	public ActionSkillMoonSault(Chr me) {
		super(me);
		name = "ムーンサルト";
		multi = 35;
	}

	// 攻撃範囲：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// ダメージ：物理、掛け算方式
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("%sはそらたかくジャンプ！かていんしつつたいあたりをした！", me.name);
		
		Calc.physMultiDmg(me);
	}

}
