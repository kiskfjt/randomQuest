package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillMysteryWaltz extends ActionSkill {

	public ActionSkillMysteryWaltz(Chr me) {
		super(me);
		name = "ミステリーワルツ";
		rangeMinInt = 15;
		rangeMaxInt = 30;
	}

	// 効果対象：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}

	// MP吸収、15～30
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		int value = IO.randomNum(rangeMinInt, rangeMaxInt);
		
		me.targets.get(0).MP -= value;
		
		IO.msgln("%sのMPを%d吸収した！", me.targets.get(0).name, value);
	}

}
