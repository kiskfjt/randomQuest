package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillPassionateTango extends ActionSkill {

	public ActionSkillPassionateTango(Chr me) {
		super(me);
		name = "情熱タンゴ";
		rangeMin = 0.8;
		rangeMax = 1.2;
		actionType = ACTION_TYPE_DANCE;
	}

	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}
	
	// ダメージ：物理、30～50の範囲
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);

		IO.msgln("【%sは%sを踊った！】", me.name, name);
		
		Chr target = me.targets.get(0);
		
		int HPBefore = target.HP;
		int value = (int) ((me.Lv / 4 + 5) * IO.randomNum(rangeMin, rangeMax));
		target.HP -= value;

		if (target.HP < 0) {
			target.HP = 0;
			value = HPBefore;
		}

		me.HP += value;
		
		// 生死判定
		IO.judgeHP(me, target);

		IO.msgln("%sのHPを%d奪った！", target.name, value);
	}

}
