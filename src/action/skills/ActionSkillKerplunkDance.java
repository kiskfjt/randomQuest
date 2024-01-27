package action.skills;

import java.util.ArrayList;

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
		ArrayList<Chr> withoutMe = new ArrayList<>(me.party.member);
		// 味方パーティーリストからmeを削除
		withoutMe.remove(withoutMe.indexOf(me));
		
		return IO.selectAllTargets(withoutMe, me);
	}

	// 効果：味方全員のHPを全快し、自身は死ぬ　成功率50%
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		if (IO.probability(successRate)) {
			Calc.maxMultiHeal(me);
		}
		
		me.HP = 0;
		IO.msgln("%sは踊り疲れていきたえた", me.name);
	}

}
