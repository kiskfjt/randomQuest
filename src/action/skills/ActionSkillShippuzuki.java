package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillShippuzuki extends ActionSkill {

	public ActionSkillShippuzuki(Chr me) {
		super(me);
		name = "しっぷう突き";
		multi = 40;
	}
	
	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		me.SPDNext = me.MAX_SPD;
		return IO.selectSingleTarget(me.party.enemy.member, me);
	}

	// ダメージ：物理、掛け算方式
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);
		
		IO.msgln("【%sの%s！】", me.name, name);

		Calc.physSingleDmg(me);
	}
}
