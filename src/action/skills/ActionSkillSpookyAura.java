package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillSpookyAura extends ActionSkill {

	public ActionSkillSpookyAura(Chr me) {
		super(me);
		name = "ぶきみなひかり";
		successRate = 50;
		effectValue = 0.2;
	}

	// 効果範囲：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// 効果：呪文耐性を低くする
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		IO.changeMagicResistance(me);
	}

}
