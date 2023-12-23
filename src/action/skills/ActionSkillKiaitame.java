package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillKiaitame extends ActionSkill {

	public ActionSkillKiaitame(Chr me) {
		super(me);
		name = "きあいため";
		ATKNext = 2.0;
	}
	
	// 対象：自分自身
	public boolean playerTarget() {
		return true;
	}

	// 次回の物理攻撃のダメージが2倍になる
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("%sはちからをためている！", me.name);
		
		me.ATKNext = ATKNext;
	}
}
