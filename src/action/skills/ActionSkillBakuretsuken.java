package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillBakuretsuken extends ActionSkill {

	public ActionSkillBakuretsuken(Chr me) {
		super(me);
		name = "ばくれつけん";
		multi = 30;
	}
	
	// 連続攻撃回数
	public int numOfTimes = 4;
	
	// 攻撃範囲：敵ランダム4体
	public boolean playerTarget() {
		return IO.selectMultiRandomTargets(me.party.enemy.member, me, numOfTimes);
	}

	// ダメージ：物理、掛け算方式
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);
		
		IO.msgln("【%sの%s！】", me.name, name);

		Calc.physMultiDmg(me);
	}

}
