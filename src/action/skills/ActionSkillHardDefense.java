package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillHardDefense extends ActionSkill {

	public ActionSkillHardDefense(Chr me) {
		super(me);
		name = "大防御";
		multi = 10;
	}
	
	// バフ対象：自分自身
	public boolean playerTarget() {
		me.DEFNext = multi;
		return true;
	}

	// あらゆるダメージを1/10にする
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("%sはすべてのこうげきに対して身構えている！", me.name);
	}
}
