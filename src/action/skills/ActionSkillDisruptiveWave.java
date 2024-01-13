package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillDisruptiveWave extends ActionSkill {

	public ActionSkillDisruptiveWave(Chr me) {
		super(me);
		name = "いてつくはどう";
	}

	// 効果範囲：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// 効果：呪文効果解除
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		IO.clearAllMagicEffects(me);
	}

}
