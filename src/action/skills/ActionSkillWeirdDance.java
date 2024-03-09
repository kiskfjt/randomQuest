package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillWeirdDance extends ActionSkill {

	public ActionSkillWeirdDance(Chr me) {
		super(me);
		name = "ふしぎなおどり";
		rangeMin = 0.8;
		rangeMax = 1.2;
		actionType = ACTION_TYPE_DANCE;
	}

	// 効果範囲：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}

	// 効果：敵MP10～30減少
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);
		
		IO.msgln("【%sは%sを踊った！】", me.name, name);
		
		int MPBefore = me.targets.get(0).MP;
		
		int value = (int) ((me.Lv / 4 + 5) * IO.randomNum(rangeMin, rangeMax));
		me.targets.get(0).MP -= value;
		
		if (me.targets.get(0).MP < 0) {
			me.targets.get(0).MP = 0;
			value = MPBefore;
		}
		
		IO.msgln("%sのMPが%d下がった！", me.targets.get(0), value);
	}

}
