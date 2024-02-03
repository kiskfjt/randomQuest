package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillSeikenzuki extends ActionSkill {

	public ActionSkillSeikenzuki(Chr me) {
		super(me);
		name = "せいけんづき";
		multi = 100;
		missRate = 20;
	}
	
	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}
	
	// ダメージ：物理、掛け算方式、命中率80%
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("%sはこしをふかくおとし、まっすぐにあいてをついた！", me.name);
		
		Calc.physSingleDmg(me);
	}
}
