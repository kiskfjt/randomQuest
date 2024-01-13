package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillMorobagiri extends ActionSkill {

	public ActionSkillMorobagiri(Chr me) {
		super(me);
		name = "もろばぎり";
		multi = 100;
	}
	
	private double kickback = 0.25;
	
	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.enemy.member, me);
	}
	
	// ダメージ：物理、掛け算方式、与ダメージの25%反動ダメージを受ける
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.physSingleDmgWithKickback(me, kickback);
	}

}
