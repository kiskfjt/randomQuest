package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillMysteryWaltz extends ActionSkill {

	public ActionSkillMysteryWaltz(Chr me) {
		super(me);
		name = "ミステリーワルツ";
		rangeMin = 0.8;
		rangeMax = 1.2;
		actionType = ACTION_TYPE_DANCE;
	}

	// 効果対象：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}

	// MP吸収、吸収量はキャラレベルも影響
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);

		IO.msgln("【%sは%sを踊った！】", me.name, name);
		
		Chr target = me.targets.get(0);
		
		int MPBefore = target.MP;

		int value = (int) ((me.Lv / 4 + 5) * IO.randomNum(rangeMin, rangeMax));
		target.MP -= value;

		if (target.MP < 0) {
			target.MP = 0;
			value = MPBefore;
		}

		me.MP += value;

		IO.msgln("%sのMPを%d奪った！", target.name, value);
	}

}
