package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillDeathDance extends ActionSkill {

	public ActionSkillDeathDance(Chr me) {
		super(me);
		name ="しのおどり";
		successRate = 50;
	}

	// 対象：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// 効果：確率で即死
	public void execute() {
		IO.msgln("【%sは%sを踊った！】", me.name, name);
		
		for (Chr target : me.targets) {
			if (target.isAlive() && target.canBeKilledInstantly) {
				if (IO.probability(successRate)) {
					target.HP = 0;
					target.status = Chr.STATUS_DEAD;
					target.statusStr = Chr.STATUS_STR_DEAD;
					IO.msgln("%sの息の根を止めた！", target.name);
				} else {
					IO.msgln("%sには効かなかった！", target.name);
				}
			}
		}
	}
}
