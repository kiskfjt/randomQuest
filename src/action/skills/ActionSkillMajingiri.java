package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillMajingiri extends ActionSkill {

	public ActionSkillMajingiri(Chr me) {
		super(me);
		name = "まじんぎり";
		multi = 75;
		missRate = 67;
		criticalRate = 100;
	}

	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.enemy.member, me);
	}

	// ダメージ：物理、掛け算方式、命中率33%、必ず会心
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);
		
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.physSingleDmg(me);
	}

}
