package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillFuddleDance extends ActionSkill {

	public ActionSkillFuddleDance(Chr me) {
		super(me);
		name = "ベリーダンス";
		successRate = 50;
		actionType = ACTION_TYPE_DANCE;
	}

	// 効果範囲：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// 効果：50%の確率で耐性の無い敵を混乱状態にする
	public void execute() {
		IO.msgln("【%sは%sを踊った！】", me.name, name);
		
	}

}
