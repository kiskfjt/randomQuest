package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillBanDance extends ActionSkill {

	public ActionSkillBanDance(Chr me) {
		super(me);
		name = "おどり封じ";
		actionType = ACTION_TYPE_DANCE;
		successRate = 50;
	}

	// 対象：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// 効果：確率で敵を踊り封じ状態にする
	public void execute() {
		IO.msgln("【%sは%sを踊った！】", me.name, name);

		for (Chr target : me.targets) {
			if (target.isAlive()) {
				if (IO.probability(successRate)) {
					target.bannedActionTypeList.add(ACTION_TYPE_DANCE);
					IO.msgln("%sの踊りは封じられた！", target.name);
				} else {
					IO.msgln("%sには効かなかった！", target.name);
				}
			}
		}
	}
}
