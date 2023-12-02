package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillShippu extends ActionSkill {

	public ActionSkillShippu(Chr me) {
		super(me);
		name = "しっぷう突き";
		multi = 40;
		rangeMin = 0.8;
		rangeMax = 1.2;
	}
	
	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		me.onceBuffSPD = me.MAX_SPD;
		return IO.selectSingleTarget(me.party.enemy.member, me);
	}

	// ダメージ：物理、掛け算方式
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);

		Calc.physSingleDmg(me);
	}
}
