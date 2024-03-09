package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillMeteorStrike extends ActionSkill {

	public ActionSkillMeteorStrike(Chr me) {
		super(me);
		name = "ミーティアストライク";
		multi = 70;
		rangeMin = 0.6;
		rangeMax = 1.4;
		missRate = 10;
	}
	
	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.enemy.member, me);
	}
	
	// ダメージ：物理、掛け算方式
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);
		
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.physSingleDmg(me);
	}
}
