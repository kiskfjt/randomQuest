package action.skills;

import action.ActionSkill;
import action.effects.ActionEffectBlind;
import chr.Chr;
import others.IO;

public class ActionSkillDazzleFlash extends ActionSkill {

	public ActionSkillDazzleFlash(Chr me) {
		super(me);
		name = "まばゆいひかり";
		
	}
	
	// 命中率
	private int probability = 50;
	
	// 効果範囲：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// 効果：敵を1ターン行動不能にさせる
	public void execute() {
		IO.msgln("【%sは身体中から%sを放った！】", me.name, name);
		
		for (Chr chr : me.targets) {
			if (IO.probability(probability)) {
				IO.msgln("%sは目が眩んだ！", chr.name);
				chr.action = new ActionEffectBlind(chr);
			} else {
				IO.msgln("%sは目を閉じてかわした！", chr.name);
			}
		}
		
	}

}
