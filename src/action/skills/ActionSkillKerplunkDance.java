package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillKerplunkDance extends ActionSkill {

	public ActionSkillKerplunkDance(Chr me) {
		super(me);
		name = "メガザルダンス";
		successRate = 50;
	}

	// 効果範囲：自分以外の味方全員
	public boolean playerTarget() {
		return IO.selectAllTargets(IO.listExcludeMe(me), me);
	}

	// 効果：味方全員のHPを全快し、自身は死ぬ　成功率50%
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		if (IO.probability(successRate)) {
			Calc.maxMultiHeal(me);
		} else {
			IO.msgln("しかし、何も起こらなかった");
		}
		
		me.HP = 0;
		IO.msgln("%sは踊り疲れていきたえた", me.name);
	}

}
