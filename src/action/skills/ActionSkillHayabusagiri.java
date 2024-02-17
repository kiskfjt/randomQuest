package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillHayabusagiri extends ActionSkill {

	public ActionSkillHayabusagiri(Chr me) {
		super(me);
		name = "はやぶさぎり";
		multi = 40;
	}
	
	// 連続攻撃回数
	public int numOfTimes = 2;
	
	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.enemy.member, me);
	}

	// ダメージ：物理、掛け算方式、2連続攻撃
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);
		
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.physSingleDmg(me);
		Calc.physSingleDmg(me);
	}

}
