package action.skills;

import action.ActionBasicAttack;
import action.ActionSkill;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionSkillSatsugekiBukouken extends ActionSkill {

	public ActionSkillSatsugekiBukouken(Chr me) {
		super(me);
		name = "殺撃舞荒剣";
		multi = 40;
		MPCons = 50;
		specialAction = ACTION_SPECIAL_LOW_HP;
		actionSpecialHPCondition = 0.10;
		missRate = 0;
	}
	
	// 連続攻撃回数
		public int numOfTimes = 10;

	// 攻撃範囲：敵全体
	public boolean playerTarget() {
		return IO.selectMultiRandomTargets(me.party.enemy.member, me, numOfTimes);
	}

	// ダメージ：物理、掛け算方式、命中率100%
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);

		if (me.HP <= (int) (me.maxHP * actionSpecialHPCondition)) {

			IO.msgln("【%sの%s！】", me.name, name);
			IO.msgln("【%s】燃え上がれ！紅蓮の刃！%s！", me.name, name);

			Calc.physMultiDmg(me);
			
		// 条件を満たさない場合はランダムターゲットの通常攻撃に変更
		} else {
			me.targets.clear();
			me.action = null;
			IO.selectSingleRandomTarget(me.party.enemy.member, me);
			me.action = new ActionBasicAttack(me);
			me.action.execute();
		}
	}
}
